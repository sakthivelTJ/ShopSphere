package com.shopsphere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * ShopSphere Application Entry Point.
 * Starts the Spring Boot embedded servlet container (Tomcat) and initializes the
 * Spring ApplicationContext, scanning components under package com.shopsphere.
 */
@SpringBootApplication
public class ShopSphereApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ShopSphereApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ShopSphereApplication.class, args);
    }
}

