package com.shopsphere.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.net.URI;

@Configuration
public class DatabaseConfig {

    @Value("${SPRING_DATASOURCE_URL:}")
    private String rawUrl;

    @Value("${MYSQL_URL:}")
    private String mysqlUrl;

    @Value("${MYSQLPRIVATEURL:}")
    private String mysqlPrivateUrl;

    @Value("${MYSQL_PRIVATE_URL:}")
    private String mysqlPrivateUrlAlt;

    @Value("${MYSQLHOST:localhost}")
    private String host;

    @Value("${MYSQLPORT:3306}")
    private String port;

    @Value("${MYSQLDATABASE:ecommerce_db}")
    private String database;

    @Value("${SPRING_DATASOURCE_USERNAME:${MYSQLUSER:root}}")
    private String username;

    @Value("${SPRING_DATASOURCE_PASSWORD:${MYSQLPASSWORD:Lucifer.t.j7}}")
    private String password;

    @Bean
    @Primary
    public DataSource dataSource() {
        String jdbcUrl = null;
        String dbUser = username;
        String dbPass = password;

        // 1. Check if SPRING_DATASOURCE_URL is provided explicitly
        if (rawUrl != null && !rawUrl.trim().isEmpty()) {
            jdbcUrl = rawUrl.trim();
        }

        // 2. Parse Railway environment URLs (MYSQLPRIVATEURL, MYSQL_PRIVATE_URL, MYSQL_URL)
        if (jdbcUrl == null || jdbcUrl.trim().isEmpty()) {
            String envUrl = firstNonEmpty(mysqlPrivateUrl, mysqlPrivateUrlAlt, mysqlUrl);
            if (envUrl != null && !envUrl.trim().isEmpty()) {
                envUrl = envUrl.trim();
                try {
                    if (envUrl.startsWith("mysql://")) {
                        URI uri = new URI(envUrl);
                        String userInfo = uri.getUserInfo();
                        if (userInfo != null && userInfo.contains(":")) {
                            String[] userParts = userInfo.split(":", 2);
                            dbUser = userParts[0];
                            dbPass = userParts[1];
                        }
                        String dbName = uri.getPath();
                        if (dbName != null && dbName.startsWith("/")) {
                            dbName = dbName.substring(1);
                        }
                        if (dbName == null || dbName.trim().isEmpty()) {
                            dbName = "railway";
                        }
                        int targetPort = uri.getPort() > 0 ? uri.getPort() : 3306;
                        jdbcUrl = String.format("jdbc:mysql://%s:%d/%s?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                                uri.getHost(), targetPort, dbName);
                    } else if (envUrl.startsWith("jdbc:mysql://")) {
                        jdbcUrl = envUrl;
                    }
                } catch (Exception e) {
                    System.err.println("DatabaseConfig: Failed to parse Railway URL [" + envUrl + "]: " + e.getMessage());
                }
            }
        }

        // 3. Fallback to MYSQLHOST, MYSQLPORT, MYSQLDATABASE
        if (jdbcUrl == null || jdbcUrl.trim().isEmpty()) {
            jdbcUrl = String.format("jdbc:mysql://%s:%s/%s?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                    host, port, database);
        }

        System.out.println("=================================================");
        System.out.println(" ShopSphere DataSource Configured:");
        System.out.println(" URL: " + maskUrl(jdbcUrl));
        System.out.println(" User: " + dbUser);
        System.out.println("=================================================");

        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url(jdbcUrl)
                .username(dbUser)
                .password(dbPass)
                .build();
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
}
