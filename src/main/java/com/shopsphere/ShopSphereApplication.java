package com.shopsphere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ShopSphere Application Entry Point.
 * Starts the Spring Boot embedded servlet container (Tomcat) and initializes the
 * Spring ApplicationContext, scanning components under package com.shopsphere.
 */
@SpringBootApplication
public class ShopSphereApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopSphereApplication.class, args);
    }
}
