-- =========================================================
-- ShopSphere Full Database Creation & Data Insertion Script
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

-- Seed All Products
INSERT INTO `products` (`product_id`, `category_id`, `name`, `description`, `price`, `discount_percent`, `image_url`, `is_active`) VALUES
-- Category 1: Electronics & Gadgets
(1, 1, 'OnePlus 12 5G (16GB RAM, 512GB)', 'Snapdragon 8 Gen 3, 50MP Hasselblad Camera, 100W SuperVOOC Fast Charging.', 64999.00, 10.00, 'https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=600&auto=format&fit=crop', 1),
(2, 1, 'Sony WH-1000XM5 Wireless Headphones', 'Industry leading noise canceling with Auto NC Optimizer and 30-hr battery life.', 29990.00, 15.00, 'https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=600&auto=format&fit=crop', 1),
(3, 1, 'Apple Watch Series 9 GPS 45mm', 'Advanced health sensors, S9 SiP chip, Double Tap gesture control, Always-On Retina display.', 44900.00, 5.00, 'https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=600&auto=format&fit=crop', 1),
(4, 1, 'MacBook Air 15-inch M2', 'Liquid Retina Display, 8GB Unified Memory, 512GB SSD, 18-Hour Battery Life.', 134900.00, 8.00, 'https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=600&auto=format&fit=crop', 1),
(5, 1, 'Samsung Galaxy Tab S9 Ultra', '14.6 Dynamic AMOLED 2X, S Pen included, Snapdragon 8 Gen 2, Armor Aluminum casing.', 108999.00, 12.00, 'https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0?w=600&auto=format&fit=crop', 1),
(6, 1, 'JBL Flip 6 Portable Bluetooth Speaker', '2-way speaker system, IP67 waterproof and dustproof, 12 hours playtime.', 9999.00, 20.00, 'https://images.unsplash.com/photo-1608043152269-423dbba4e7e1?w=600&auto=format&fit=crop', 1),
(7, 1, 'Logitech MX Master 3S Wireless Mouse', '8K DPI tracking on any surface, Quiet Clicks, Ergonomic design, USB-C fast charge.', 9495.00, 10.00, 'https://images.unsplash.com/photo-1615663245857-ac93bb7c39e7?w=600&auto=format&fit=crop', 1),
(8, 1, 'Asus ROG Zephyrus G16 Gaming Laptop', 'Intel Core Ultra 9, RTX 4070 8GB, 32GB LPDDR5X, 240Hz OLED Display.', 189990.00, 7.00, 'https://images.unsplash.com/photo-1603302576837-37561b2e2302?w=600&auto=format&fit=crop', 1),
(9, 1, 'Canon EOS R6 Mark II Mirrorless Camera', '24.2 MP Full-Frame CMOS Sensor, 40 fps continuous shooting, 4K 60p video.', 215995.00, 5.00, 'https://images.unsplash.com/photo-1516035069371-29a1b244cc32?w=600&auto=format&fit=crop', 1),
(10, 1, 'GoPro HERO12 Black Action Camera', '5.3K60 Video, HDR, HyperSmooth 6.0 Stabilization, Waterproof to 33ft.', 37990.00, 15.00, 'https://images.unsplash.com/photo-1526170375885-4d8ecf77b99f?w=600&auto=format&fit=crop', 1),
(11, 1, 'Anker PowerCore 24000mAh Power Bank', '140W Ultra-Powerful 3-Port Portable Charger with Smart Digital Display.', 12999.00, 18.00, 'https://images.unsplash.com/photo-1609592424074-1296f018e690?w=600&auto=format&fit=crop', 1),
(12, 1, 'Bose QuietComfort Ultra Earbuds', 'World-class noise cancellation, Spatial audio, CustomTune technology, 6-hr battery.', 25900.00, 10.00, 'https://images.unsplash.com/photo-1590658268037-6bf12165a8df?w=600&auto=format&fit=crop', 1),
(13, 1, 'Dell UltraSharp 27 4K USB-C Hub Monitor', 'IPS Black technology, 98% DCI-P3 color coverage, ComfortView Plus, 90W power delivery.', 54999.00, 12.00, 'https://images.unsplash.com/photo-1527443224154-c4a3942d3acf?w=600&auto=format&fit=crop', 1),
(14, 1, 'Kindle Paperwhite 16GB (11th Gen)', '6.8 display with adjustable warm light, waterproof, USB-C, up to 10 weeks battery.', 14999.00, 8.00, 'https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?w=600&auto=format&fit=crop', 1),
(15, 1, 'Keychron K2 Pro Wireless Mechanical Keyboard', 'QMK/VIA Programmable, Hot-swappable Red Switches, RGB Backlight, Mac & Windows.', 9990.00, 10.00, 'https://images.unsplash.com/photo-1587829741301-dc798b83add3?w=600&auto=format&fit=crop', 1),
(16, 1, 'Dyson Zone Absolute Noise Canceling Headphones', 'Purified air technology, ultra-low distortion audio, advanced active noise cancellation.', 59900.00, 15.00, 'https://images.unsplash.com/photo-1546435770-a3e426bf472b?w=600&auto=format&fit=crop', 1),
(17, 1, 'Sennheiser AMBEO Soundbar Mini', '3D Sound with Dolby Atmos & DTS:X, Dual integrated 4 subwoofers, Bluetooth & Wi-Fi.', 74990.00, 20.00, 'https://images.unsplash.com/photo-1545454675-3531b543be5d?w=600&auto=format&fit=crop', 1),
(18, 1, 'DJI Mini 4 Pro Fly More Combo', 'Under 249g, 4K/60fps HDR True Vertical Shooting, Omnidirectional Obstacle Sensing.', 109900.00, 5.00, 'https://images.unsplash.com/photo-1508614589041-895b88991e3e?w=600&auto=format&fit=crop', 1),
(19, 1, 'Nothing Phone (2a) 5G', 'Glyph Interface, Dimensity 7200 Pro, 50MP Dual Camera, 120Hz Flexible AMOLED.', 23999.00, 10.00, 'https://images.unsplash.com/photo-1565849904461-04a58ad377e0?w=600&auto=format&fit=crop', 1),
(20, 1, 'Marshall Stanmore III Bluetooth Speaker', 'Iconic vintage design, re-engineered wider soundstage, dynamic loudness.', 31999.00, 12.00, 'https://images.unsplash.com/photo-1545454675-3531b543be5d?w=600&auto=format&fit=crop', 1),

-- Category 2: Fashion & Apparel
(21, 2, 'Handcrafted Pure Silk Banarasi Saree', 'Rich zari weave, traditional royal motifs, accompanied by unstitched matching blouse piece.', 14999.00, 20.00, 'https://images.unsplash.com/photo-1610030469983-98e550d6193c?w=600&auto=format&fit=crop', 1),
(22, 2, 'Men\'s Slim Fit Linen Blazer', 'Breathable 100% European linen fabric with structured lapels and internal lining.', 6999.00, 15.00, 'https://images.unsplash.com/photo-1507679799987-c73779587ccf?w=600&auto=format&fit=crop', 1),
(23, 2, 'Nike Air Force 1 \'07 Sneakers', 'Classic crisp leather upper, stitched overlays, legendary Nike Air cushioning.', 8995.00, 10.00, 'https://images.unsplash.com/photo-1595950653106-6c9ebd614d3a?w=600&auto=format&fit=crop', 1),
(24, 2, 'Women\'s Embroidered Anarkali Kurta Set', 'Intricate chikan embroidery on georgette with dupatta and churidar trousers.', 4599.00, 25.00, 'https://images.unsplash.com/photo-1583391733956-6c78276477e2?w=600&auto=format&fit=crop', 1),
(25, 2, 'Levi\'s 501 Original Fit Jeans', 'The iconic straight leg denim jeans with button fly and signature copper rivets.', 3999.00, 15.00, 'https://images.unsplash.com/photo-1542272604-780c96856592?w=600&auto=format&fit=crop', 1),
(26, 2, 'Ray-Ban Aviator Classic Sunglasses', 'G-15 green glass lenses with 100% UV protection and polished gold metal frame.', 9890.00, 12.00, 'https://images.unsplash.com/photo-1572635196237-14b3f281503f?w=600&auto=format&fit=crop', 1),
(27, 2, 'Fossil Gen 6 Leather Hybrid Watch', 'Rose gold stainless steel case with genuine brown leather strap and smart notifications.', 13495.00, 30.00, 'https://images.unsplash.com/photo-1524805444758-089113d48a6d?w=600&auto=format&fit=crop', 1),
(28, 2, 'Women\'s Leather Tote Shoulder Bag', 'Full grain Italian leather with gold hardware, laptop compartment and zipper closure.', 7899.00, 20.00, 'https://images.unsplash.com/photo-1584917865442-de89df76afd3?w=600&auto=format&fit=crop', 1),
(29, 2, 'Men\'s Waterproof Winter Puffer Jacket', 'Insulated down jacket with detachable hood and fleece-lined handwarmer pockets.', 5499.00, 18.00, 'https://images.unsplash.com/photo-1544923246-77307dd654cb?w=600&auto=format&fit=crop', 1),
(30, 2, 'Adidas Ultraboost Light Running Shoes', 'Light Boost material, Continental rubber outsole, Primeknit textile upper.', 13999.00, 25.00, 'https://images.unsplash.com/photo-1584735935682-2f2b69dff9d2?w=600&auto=format&fit=crop', 1),

-- Category 3: Home & Living
(31, 3, 'Dyson V15 Detect Cordless Vacuum Cleaner', 'Laser reveals microscopic dust, LCD screen displays real-time scientific proof of deep clean.', 65900.00, 10.00, 'https://images.unsplash.com/photo-1558317374-067fb5f30001?w=600&auto=format&fit=crop', 1),
(32, 3, 'Philips Digital Air Fryer HD9252/90', 'Rapid Air Technology, 7 Touchscreen presets, 90% less fat cooking.', 8999.00, 25.00, 'https://images.unsplash.com/photo-1585515320310-259814833e62?w=600&auto=format&fit=crop', 1),
(33, 3, 'Nespresso Vertuo Pop Espresso Machine', 'Centrifusion technology, 5 cup sizes, Bluetooth & Wi-Fi connectivity.', 16499.00, 15.00, 'https://images.unsplash.com/photo-1517668808822-9ebe02afd2a4?w=600&auto=format&fit=crop', 1),
(34, 3, 'Handmade Ceramic Nordic Vase Set', 'Set of 2 minimalist matte white ceramic flower vases for living room decor.', 1899.00, 20.00, 'https://images.unsplash.com/photo-1612196808214-b7e239e5f6b7?w=600&auto=format&fit=crop', 1),
(35, 3, 'Orthopedic Memory Foam Mattress', 'Triple-layer cooling gel memory foam mattress with breathable knitted outer cover.', 14999.00, 30.00, 'https://images.unsplash.com/photo-1631049307264-da0ec9d70304?w=600&auto=format&fit=crop', 1),

-- Category 4: Beauty & Personal Care
(36, 4, 'Forest Essentials Soundarya Radiance Cream', '24K Gold infused Ayurveda face cream with SPF 25, restores natural firmness.', 5800.00, 10.00, 'https://images.unsplash.com/photo-1570172619644-dfd03ed5d881?w=600&auto=format&fit=crop', 1),
(37, 4, 'Philips SkinIQ Series 7000 Electric Shaver', 'SteelPrecision blades, 360-D flexing heads, wet & dry shaving.', 8495.00, 15.00, 'https://images.unsplash.com/photo-1621607512214-68297480165e?w=600&auto=format&fit=crop', 1),
(38, 4, 'Minimalist 10% Vitamin C Face Serum', 'Brightening serum with acetyl glucosamine, reduces dark spots and pigmentation.', 699.00, 10.00, 'https://images.unsplash.com/photo-1620916566398-39f1143ab7be?w=600&auto=format&fit=crop', 1),
(39, 4, 'Dyson Airwrap Multi-Styler Complete', 'Curl, shape, smooth and hide flyaways with Coanda airflow, no extreme heat.', 49900.00, 8.00, 'https://images.unsplash.com/photo-1522337360788-8b13dee7a37e?w=600&auto=format&fit=crop', 1),
(40, 4, 'Versace Eros Eau De Parfum for Men', 'Mint leaves, Italian lemon zest, tonka beans, and sensual amber woody notes.', 7850.00, 15.00, 'https://images.unsplash.com/photo-1592945403244-b3fbafd7f539?w=600&auto=format&fit=crop', 1);

-- Seed Product Variants / Sizes
INSERT INTO `product_sizes` (`size_id`, `product_id`, `size_name`, `stock_quantity`, `sku`, `is_active`) VALUES
(1, 1, 'Emerald Green', 25, 'SKU-1-A', 1), (2, 1, 'Silky Black', 15, 'SKU-1-B', 1),
(3, 2, 'Silver', 20, 'SKU-2-A', 1), (4, 2, 'Midnight Black', 18, 'SKU-2-B', 1),
(5, 3, 'Midnight Sport Loop', 30, 'SKU-3-A', 1), (6, 3, 'Starlight Aluminum', 12, 'SKU-3-B', 1),
(7, 4, 'Space Grey', 10, 'SKU-4-A', 1), (8, 4, 'Starlight', 8, 'SKU-4-B', 1),
(9, 5, '256GB Graphite', 14, 'SKU-5-A', 1), (10, 5, '512GB Beige', 6, 'SKU-5-B', 1),
(11, 6, 'Ocean Blue', 40, 'SKU-6-A', 1), (12, 6, 'Squad Camo', 25, 'SKU-6-B', 1),
(13, 7, 'Graphite', 50, 'SKU-7-A', 1), (14, 7, 'Pale Grey', 30, 'SKU-7-B', 1),
(15, 8, 'Eclipse Grey', 6, 'SKU-8-A', 1), (16, 8, 'Platinum White', 4, 'SKU-8-B', 1),
(17, 9, 'Body Only', 8, 'SKU-9-A', 1), (18, 9, 'With 24-105mm Lens', 5, 'SKU-9-B', 1),
(19, 10, 'Standard Bundle', 22, 'SKU-10-A', 1), (20, 10, 'Creator Edition', 11, 'SKU-10-B', 1),
(21, 21, 'Royal Blue & Gold', 15, 'SKU-21-A', 1), (22, 21, 'Crimson Red', 10, 'SKU-21-B', 1),
(23, 22, 'Size 40 (M)', 25, 'SKU-22-A', 1), (24, 22, 'Size 42 (L)', 20, 'SKU-22-B', 1),
(25, 23, 'UK 8', 18, 'SKU-23-A', 1), (26, 23, 'UK 9', 22, 'SKU-23-B', 1),
(27, 31, 'Standard Gold', 10, 'SKU-31-A', 1), (28, 31, 'Complete Kit', 6, 'SKU-31-B', 1),
(29, 32, '4.1 Liter Black', 30, 'SKU-32-A', 1), (30, 32, '4.1 Liter White', 20, 'SKU-32-B', 1),
(31, 36, '50g Jar', 20, 'SKU-36-A', 1), (32, 36, '30g Travel Size', 15, 'SKU-36-B', 1);
