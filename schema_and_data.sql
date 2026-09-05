-- =========================================================
-- ShopSphere Database Creation Script
-- Database: ecommerce_db
-- Target: MySQL 8.0+
-- =========================================================

CREATE DATABASE IF NOT EXISTS `ecommerce_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `ecommerce_db`;

-- Disable Foreign Key checks temporarily for clean setup
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `order_items`;
DROP TABLE IF EXISTS `orders`;
DROP TABLE IF EXISTS `cart_items`;
DROP TABLE IF EXISTS `cart`;
DROP TABLE IF EXISTS `product_sizes`;
DROP TABLE IF EXISTS `products`;
DROP TABLE IF EXISTS `categories`;
DROP TABLE IF EXISTS `users`;

SET FOREIGN_KEY_CHECKS = 1;

-- 1. USERS TABLE
CREATE TABLE `users` (
    `user_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `full_name` VARCHAR(100) NOT NULL,
    `email` VARCHAR(100) NOT NULL UNIQUE,
    `phone` VARCHAR(20),
    `password` VARCHAR(255) NOT NULL,
    `gender` VARCHAR(20),
    `address` TEXT,
    `role` VARCHAR(20) NOT NULL DEFAULT 'CUSTOMER',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2. CATEGORIES TABLE
CREATE TABLE `categories` (
    `category_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `category_name` VARCHAR(100) NOT NULL UNIQUE,
    `description` TEXT,
    `is_active` BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3. PRODUCTS TABLE
CREATE TABLE `products` (
    `product_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `category_id` BIGINT NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `description` TEXT,
    `price` DECIMAL(10,2) NOT NULL,
    `discount_percent` DECIMAL(5,2) DEFAULT 0.00,
    `image_url` VARCHAR(500),
    `is_active` BOOLEAN DEFAULT TRUE,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT `fk_product_category` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 4. PRODUCT SIZES & VARIANTS TABLE
CREATE TABLE `product_sizes` (
    `size_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `product_id` BIGINT NOT NULL,
    `size_name` VARCHAR(50) NOT NULL,
    `stock_quantity` INT NOT NULL DEFAULT 0,
    `sku` VARCHAR(100),
    `is_active` BOOLEAN DEFAULT TRUE,
    CONSTRAINT `fk_size_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 5. CART TABLE
CREATE TABLE `cart` (
    `cart_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `user_id` BIGINT NOT NULL UNIQUE,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT `fk_cart_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 6. CART ITEMS TABLE
CREATE TABLE `cart_items` (
    `cart_item_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `cart_id` BIGINT NOT NULL,
    `product_id` BIGINT NOT NULL,
    `size_id` BIGINT NOT NULL,
    `quantity` INT NOT NULL DEFAULT 1,
    CONSTRAINT `fk_cart_item_cart` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`cart_id`) ON DELETE CASCADE,
    CONSTRAINT `fk_cart_item_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE,
    CONSTRAINT `fk_cart_item_size` FOREIGN KEY (`size_id`) REFERENCES `product_sizes` (`size_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 7. ORDERS TABLE
CREATE TABLE `orders` (
    `order_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `user_id` BIGINT NOT NULL,
    `total_amount` DECIMAL(10,2) NOT NULL,
    `order_status` VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    `payment_status` VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    `payment_method` VARCHAR(50),
    `shipping_address` TEXT NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT `fk_order_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 8. ORDER ITEMS TABLE
CREATE TABLE `order_items` (
    `order_item_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `order_id` BIGINT NOT NULL,
    `product_id` BIGINT NOT NULL,
    `size_id` BIGINT NOT NULL,
    `quantity` INT NOT NULL,
    `price` DECIMAL(10,2) NOT NULL,
    CONSTRAINT `fk_order_item_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE,
    CONSTRAINT `fk_order_item_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE,
    CONSTRAINT `fk_order_item_size` FOREIGN KEY (`size_id`) REFERENCES `product_sizes` (`size_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- =========================================================
-- INITIAL SEED DATA
-- Note: Spring Boot automatically seeds these via DataInitializer
-- if the database is empty, but you can also run these directly.
-- =========================================================

-- Seed Users (Passwords hashed using SHA-256 Base64: 'admin123' and 'customer123')
INSERT INTO `users` (`user_id`, `full_name`, `email`, `phone`, `password`, `gender`, `address`, `role`) VALUES
(1, 'ShopSphere Admin', 'admin@shopsphere.com', '+91 98765 43210', 'jZAe72hAGMuLKSqApKYTXRGE+h/Jzs5TC76M75PweOI=', 'Male', 'Cyber City, Tower B, Sector 24, Gurugram, Haryana - 122002', 'ADMIN'),
(2, 'Aarav Sharma', 'customer@shopsphere.com', '+91 98123 45678', '71Ww2c9N16wZ4F7y2T+B3Q4/49M+gV9YnN26F12sNq0=', 'Male', 'Flat 402, Royal Palms, Bandra West, Mumbai, Maharashtra - 400050', 'CUSTOMER');

-- Seed Categories
INSERT INTO `categories` (`category_id`, `category_name`, `description`, `is_active`) VALUES
(1, 'Electronics & Gadgets', 'Smartphones, Audio, Laptops, Wearables & Accessories', 1),
(2, 'Fashion & Apparel', 'Men & Women Designer Clothing, Footwear & Accessories', 1),
(3, 'Home & Living', 'Furnishings, Kitchen Appliances & Modern Home Decor', 1),
(4, 'Beauty & Personal Care', 'Skincare, Haircare, Perfumes & Grooming Essentials', 1);

-- Seed Sample Products
INSERT INTO `products` (`product_id`, `category_id`, `name`, `description`, `price`, `discount_percent`, `image_url`, `is_active`) VALUES
(1, 1, 'OnePlus 12 5G (16GB RAM, 512GB)', 'Snapdragon 8 Gen 3, 50MP Hasselblad Camera, 100W SuperVOOC Fast Charging.', 64999.00, 10.00, 'https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=600&auto=format&fit=crop', 1),
(2, 1, 'Sony WH-1000XM5 Wireless Headphones', 'Industry leading noise canceling with Auto NC Optimizer and 30-hr battery life.', 29990.00, 15.00, 'https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=600&auto=format&fit=crop', 1),
(3, 1, 'Apple Watch Series 9 GPS 45mm', 'Advanced health sensors, S9 SiP chip, Double Tap gesture control.', 44900.00, 5.00, 'https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=600&auto=format&fit=crop', 1),
(4, 2, 'Nike Air Force 1 07 Sneakers', 'Classic crisp leather upper, stitched overlays, legendary Nike Air cushioning.', 8995.00, 10.00, 'https://images.unsplash.com/photo-1595950653106-6c9ebd614d3a?w=600&auto=format&fit=crop', 1),
(5, 3, 'Philips Digital Air Fryer HD9252/90', 'Rapid Air Technology, 7 Touchscreen presets, 90% less fat cooking.', 8999.00, 25.00, 'https://images.unsplash.com/photo-1585515320310-259814833e62?w=600&auto=format&fit=crop', 1);

-- Seed Product Variants / Sizes
INSERT INTO `product_sizes` (`size_id`, `product_id`, `size_name`, `stock_quantity`, `sku`, `is_active`) VALUES
(1, 1, 'Emerald Green', 25, 'SKU-1-A', 1),
(2, 1, 'Silky Black', 15, 'SKU-1-B', 1),
(3, 2, 'Silver', 20, 'SKU-2-A', 1),
(4, 2, 'Midnight Black', 18, 'SKU-2-B', 1),
(5, 3, 'Midnight Sport Loop', 30, 'SKU-3-A', 1),
(6, 4, 'UK 8', 18, 'SKU-4-A', 1),
(7, 4, 'UK 9', 22, 'SKU-4-B', 1),
(8, 5, '4.1 Liter Black', 30, 'SKU-5-A', 1);
