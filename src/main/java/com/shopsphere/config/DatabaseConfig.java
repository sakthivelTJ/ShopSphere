package com.shopsphere.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
public class DatabaseConfig {

    @Value("${SPRING_DATASOURCE_URL:}")
    private String rawUrl;

    @Value("${DATABASE_URL:}")
    private String databaseUrl;

    @Value("${DATABASE_PRIVATE_URL:}")
    private String databasePrivateUrl;

    @Value("${MYSQL_URL:}")
    private String mysqlUrl;

    @Value("${MYSQLPRIVATEURL:}")
    private String mysqlPrivateUrl;

    @Value("${MYSQL_PRIVATE_URL:}")
    private String mysqlPrivateUrlAlt;

    @Value("${SPRING_DATASOURCE_HOST:${MYSQLHOST:${MYSQL_HOST:${DATABASE_HOST:localhost}}}}")
    private String host;

    @Value("${SPRING_DATASOURCE_PORT:${MYSQLPORT:${MYSQL_PORT:${DATABASE_PORT:3306}}}}")
    private String port;

    @Value("${SPRING_DATASOURCE_DATABASE:${MYSQLDATABASE:${MYSQL_DATABASE:${DATABASE_NAME:ecommerce_db}}}}")
    private String database;

    @Value("${SPRING_DATASOURCE_USERNAME:${MYSQLUSER:${MYSQL_USER:${DATABASE_USER:root}}}}")
    private String username;

    @Value("${SPRING_DATASOURCE_PASSWORD:${MYSQLPASSWORD:${MYSQL_PASSWORD:${DATABASE_PASSWORD:Lucifer.t.j7}}}}")
    private String password;

    private static final Pattern MYSQL_URI_PATTERN = Pattern.compile("mysql://(?:([^:@]+)(?::([^@]*))?@)?([^:/]+)(?::(\\d+))?(?:/(.*))?");

    @Bean
    @Primary
    public DataSource dataSource() {
        String jdbcUrl = null;
        String dbUser = username;
        String dbPass = password;

        // 1. Check if SPRING_DATASOURCE_URL is provided explicitly
        if (rawUrl != null && !rawUrl.trim().isEmpty() && rawUrl.startsWith("jdbc:")) {
            jdbcUrl = rawUrl.trim();
        }

        // 2. Parse Railway environment URLs (DATABASE_URL, DATABASE_PRIVATE_URL, MYSQLPRIVATEURL, MYSQL_PRIVATE_URL, MYSQL_URL, rawUrl)
        if (jdbcUrl == null || jdbcUrl.trim().isEmpty()) {
            String envUrl = firstNonEmpty(databasePrivateUrl, databaseUrl, mysqlPrivateUrl, mysqlPrivateUrlAlt, mysqlUrl, rawUrl);
            if (envUrl != null && !envUrl.trim().isEmpty()) {
                envUrl = envUrl.trim();
                if (envUrl.startsWith("jdbc:mysql://")) {
                    jdbcUrl = envUrl;
                } else if (envUrl.startsWith("mysql://")) {
                    ParsedUrl parsed = parseMysqlUrl(envUrl);
                    if (parsed != null) {
                        if (parsed.user != null) dbUser = parsed.user;
                        if (parsed.pass != null) dbPass = parsed.pass;
                        jdbcUrl = String.format("jdbc:mysql://%s:%d/%s?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                                parsed.host, parsed.port, parsed.database);
                    }
                }
            }
        }

        // 3. Fallback to host, port, database values
        if (jdbcUrl == null || jdbcUrl.trim().isEmpty()) {
            jdbcUrl = String.format("jdbc:mysql://%s:%s/%s?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                    host, port, database);
        }

        System.out.println("=================================================");
        System.out.println(" ShopSphere DataSource Configured:");
        System.out.println(" URL: " + maskUrl(jdbcUrl));
        System.out.println(" User: " + dbUser);
        System.out.println("=================================================");

        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setJdbcUrl(jdbcUrl);
        ds.setUsername(dbUser);
        ds.setPassword(dbPass);
        ds.setMaximumPoolSize(10);
        ds.setMinimumIdle(2);
        ds.setValidationTimeout(3000);
        ds.setConnectionTimeout(30000);
        ds.setMaxLifetime(1800000);
        // Prevent HikariCP connection timeout from killing JVM on boot if MySQL takes a few seconds to warm up
        ds.setInitializationFailTimeout(-1);

        return ds;
    }

    private ParsedUrl parseMysqlUrl(String envUrl) {
        // Try regex first for robust handling of special chars in passwords
        Matcher matcher = MYSQL_URI_PATTERN.matcher(envUrl);
        if (matcher.matches()) {
            String u = matcher.group(1);
            String p = matcher.group(2);
            String h = matcher.group(3);
            String portStr = matcher.group(4);
            String db = matcher.group(5);

            int pt = (portStr != null && !portStr.isEmpty()) ? Integer.parseInt(portStr) : 3306;
            if (db == null || db.trim().isEmpty()) {
                db = "railway";
            } else if (db.contains("?")) {
                db = db.substring(0, db.indexOf("?"));
            }

            return new ParsedUrl(u, p, h, pt, db);
        }

        // Fallback to java.net.URI
        try {
            URI uri = new URI(envUrl);
            String u = null;
            String p = null;
            String userInfo = uri.getUserInfo();
            if (userInfo != null && userInfo.contains(":")) {
                String[] parts = userInfo.split(":", 2);
                u = parts[0];
                p = parts[1];
            }
            String db = uri.getPath();
            if (db != null && db.startsWith("/")) {
                db = db.substring(1);
            }
            if (db == null || db.trim().isEmpty()) {
                db = "railway";
            }
            int pt = uri.getPort() > 0 ? uri.getPort() : 3306;
            return new ParsedUrl(u, p, uri.getHost(), pt, db);
        } catch (Exception e) {
            System.err.println("DatabaseConfig: Failed to parse Railway URL [" + envUrl + "]: " + e.getMessage());
            return null;
        }
    }

    private String firstNonEmpty(String... values) {
        for (String v : values) {
            if (v != null && !v.trim().isEmpty()) return v.trim();
        }
        return null;
    }

    private String maskUrl(String url) {
        if (url == null) return "null";
        return url.replaceAll(":[^/@]+@", ":****@");
    }

    private static class ParsedUrl {
        final String user;
        final String pass;
        final String host;
        final int port;
        final String database;

        ParsedUrl(String user, String pass, String host, int port, String database) {
            this.user = user;
            this.pass = pass;
            this.host = host;
            this.port = port;
            this.database = database;
        }
    }
}
