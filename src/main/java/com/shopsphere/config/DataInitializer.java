package com.shopsphere.config;

import com.shopsphere.dao.CategoryDAO;
import com.shopsphere.dao.ProductDAO;
import com.shopsphere.dao.ProductSizeDAO;
import com.shopsphere.dao.UserDAO;
import com.shopsphere.entity.Category;
import com.shopsphere.entity.Product;
import com.shopsphere.entity.ProductSize;
import com.shopsphere.entity.User;
import com.shopsphere.util.PasswordUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserDAO userDAO;
    private final CategoryDAO categoryDAO;
    private final ProductDAO productDAO;
    private final ProductSizeDAO productSizeDAO;

    private static final Map<String, String> PRODUCT_IMAGE_MAP = new HashMap<>();

    static {
        // Electronics & Gadgets (#1 - #30)
        PRODUCT_IMAGE_MAP.put("Premium Android Smartphone", "https://images.unsplash.com/photo-1598327105666-5b89351aff97?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Apple iPhone", "https://images.unsplash.com/photo-1510557880182-3d4d3cba35a5?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("5G Performance Smartphone", "https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Noise Cancelling Earbuds", "https://images.unsplash.com/photo-1590658268037-6bf12165a8df?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Premium Wireless Headphones", "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Bluetooth Stereo Headphones", "https://images.unsplash.com/photo-1484704849700-f032a568e944?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Performance Laptop", "https://images.unsplash.com/photo-1496181133206-80ce9b88a853?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Slim Pro Laptop", "https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Gaming Laptop", "https://images.unsplash.com/photo-1603302576837-37561b2e2302?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Fitness Smartwatch", "https://images.unsplash.com/photo-1508685096489-7aacd43bd3b1?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Premium Smartwatch", "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Fitness Smart Band", "https://images.unsplash.com/photo-1575311373937-040b8e1fd5b6?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Portable Bluetooth Speaker", "https://images.unsplash.com/photo-1608043152269-423dbba4e7e1?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Smart Home Speaker", "https://images.unsplash.com/photo-1543512214-318c7553f230?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Fast Charging Power Bank", "https://images.unsplash.com/photo-1609592424074-1296f018e690?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("65W USB-C Charger", "https://images.unsplash.com/photo-1583863788434-e58a36330cf0?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Braided USB-C Cable", "https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Wireless Charging Pad", "https://images.unsplash.com/photo-1622445268465-8378c68ddb16?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("RGB Mechanical Keyboard", "https://images.unsplash.com/photo-1587829741301-dc798b83add3?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Ergonomic Wireless Mouse", "https://images.unsplash.com/photo-1615663245857-ac93bb7c39e7?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Precision Gaming Mouse", "https://images.unsplash.com/photo-1527864550417-7fd91fc51a46?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Full HD Webcam", "https://images.unsplash.com/photo-1587826080692-f439cd0b70da?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Portable External SSD", "https://images.unsplash.com/photo-1597872200969-2b65d56bd16b?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Portable External Hard Drive", "https://images.unsplash.com/photo-1531492746076-161ca9bcad58?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Premium Android Tablet", "https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Digital Drawing Tablet", "https://images.unsplash.com/photo-1626785774573-4b799315345d?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Gaming Headset", "https://images.unsplash.com/photo-1599669454699-248893623440?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("4K Action Camera", "https://images.unsplash.com/photo-1526170375885-4d8ecf77b99f?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Portable Mini Projector", "https://images.unsplash.com/photo-1517668808822-9ebe02afd2a4?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Smart Security Camera", "https://images.unsplash.com/photo-1557324232-b8917d3c3dcb?w=600&auto=format&fit=crop");

        // Fashion & Apparel (#31 - #60)
        PRODUCT_IMAGE_MAP.put("Classic Slim Fit Shirt", "https://images.unsplash.com/photo-1602810318383-e386cc2a3ccf?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Premium Formal Shirt", "https://images.unsplash.com/photo-1598033129183-c4f50c736f10?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Classic Cotton Polo", "https://images.unsplash.com/photo-1625910513413-43183574971c?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Slim Fit Denim Jeans", "https://images.unsplash.com/photo-1542272604-780c96856592?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Relaxed Fit Jeans", "https://images.unsplash.com/photo-1582552938357-32b906df40cb?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Premium Formal Blazer", "https://images.unsplash.com/photo-1507679799987-c73779587ccf?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Linen Casual Blazer", "https://images.unsplash.com/photo-1594938298603-c8148c4dae35?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Classic Streetwear Hoodie", "https://images.unsplash.com/photo-1556905055-8f358a7a47b2?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Premium Cotton Sweatshirt", "https://images.unsplash.com/photo-1620799140408-edc6dcb6d633?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Utility Cargo Pants", "https://images.unsplash.com/photo-1624378439575-d8705ad7ae80?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Elegant Midi Dress", "https://images.unsplash.com/photo-1572804013309-59a88b7e92f1?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Floral Summer Dress", "https://images.unsplash.com/photo-1515372039744-b8f02a3ae446?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Women's Tailored Blazer", "https://images.unsplash.com/photo-1584273143981-41c073dfe8f8?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Wide Leg Trousers", "https://images.unsplash.com/photo-1594633312681-425c7b97ccd1?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Soft Knit Cardigan", "https://images.unsplash.com/photo-1434389677669-e08b4cac3105?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Oversized Streetwear Tee", "https://images.unsplash.com/photo-1521572267360-ee0c2909d518?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Everyday Cotton Joggers", "https://images.unsplash.com/photo-1552902865-b72c031ac5ea?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Performance Leggings", "https://images.unsplash.com/photo-1506629082925-23688c07465a?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Performance Sports T-Shirt", "https://images.unsplash.com/photo-1518310383802-640c2de311b2?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Lightweight Running Shorts", "https://images.unsplash.com/photo-1591195853828-11db59a44f6b?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Classic Leather Sneakers", "https://images.unsplash.com/photo-1595950653106-6c9ebd614d3a?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Cushioned Running Shoes", "https://images.unsplash.com/photo-1584735935682-2f2b69dff9d2?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Minimal White Sneakers", "https://images.unsplash.com/photo-1525966222134-fcfa99b8ae77?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Luxury Leather Handbag", "https://images.unsplash.com/photo-1584917865442-de89df76afd3?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Everyday Crossbody Bag", "https://images.unsplash.com/photo-1548036328-c9fa89d128fa?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Classic Leather Wallet", "https://images.unsplash.com/photo-1627123424574-724758594e93?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Premium Leather Belt", "https://images.unsplash.com/photo-1624222247344-550fb60583dc?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Classic Cotton Cap", "https://images.unsplash.com/photo-1588850561407-ed78c282e89b?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Soft Winter Scarf", "https://images.unsplash.com/photo-1520903920243-00d872a2d1c9?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("UV Protection Sunglasses", "https://images.unsplash.com/photo-1572635196237-14b3f281503f?w=600&auto=format&fit=crop");

        // Home & Living (#61 - #90)
        PRODUCT_IMAGE_MAP.put("Modern Living Room Sofa", "https://images.unsplash.com/photo-1555041469-a586c61ea9bc?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Modern Accent Chair", "https://images.unsplash.com/photo-1580481072645-022f9a6d5017?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Modern Wooden Coffee Table", "https://images.unsplash.com/photo-1533090161767-e6ffed986c88?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Modern Dining Table", "https://images.unsplash.com/photo-1615066390971-03e4e1c36ddf?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Modern Dining Chair Set", "https://images.unsplash.com/photo-1503602642458-232111445657?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Decorative Cushion", "https://images.unsplash.com/photo-1584100936595-c0654b55a2e2?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Premium Throw Blanket", "https://images.unsplash.com/photo-1584100936595-c0654b55a2e2?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Modern LED Floor Lamp", "https://images.unsplash.com/photo-1507473885765-e6ed057f782c?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Warm Ambient Table Lamp", "https://images.unsplash.com/photo-1513506003901-1e6a229e2d15?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Modern Decorative Mirror", "https://images.unsplash.com/photo-1618221195710-dd6b41faaea6?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Modern Ceramic Plant Pot", "https://images.unsplash.com/photo-1485955900006-10f4d324d411?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Decorative Indoor Plant", "https://images.unsplash.com/photo-1614594975525-e45190c55d0b?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Premium Cookware Set", "https://images.unsplash.com/photo-1584992236310-6edddc08acff?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Non-Stick Frying Pan", "https://images.unsplash.com/photo-1585515320310-259814833e62?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Stainless Steel Knife Set", "https://images.unsplash.com/photo-1593618998160-e34014e67546?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Digital Air Fryer", "https://images.unsplash.com/photo-1585515320310-259814833e62?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Compact Microwave Oven", "https://images.unsplash.com/photo-1574269909862-7e1d70bb8078?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Automatic Coffee Maker", "https://images.unsplash.com/photo-1517668808822-9ebe02afd2a4?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Rapid Boil Electric Kettle", "https://images.unsplash.com/photo-1594212699903-ec8a3eca50f6?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Modern Four-Slice Toaster", "https://images.unsplash.com/photo-1583778176476-4a8b02a64c01?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("High Speed Kitchen Blender", "https://images.unsplash.com/photo-1570222094114-d054a817e56b?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Insulated Water Bottle", "https://images.unsplash.com/photo-1602143407151-7111542de6e8?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Ceramic Dinnerware Set", "https://images.unsplash.com/photo-1610701596007-11502861dcfa?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Kitchen Storage Jar Set", "https://images.unsplash.com/photo-1584308666744-24d5c474f2ae?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Modern Laundry Basket", "https://images.unsplash.com/photo-1590490360182-c33d57733427?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Premium Cotton Bath Towel", "https://images.unsplash.com/photo-1616627547584-bf28cee262db?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Memory Foam Pillow", "https://images.unsplash.com/photo-1631049307264-da0ec9d70304?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Premium Cotton Bedsheet Set", "https://images.unsplash.com/photo-1522771739844-6a9f6d5f14af?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Minimalist Wall Clock", "https://images.unsplash.com/photo-1563861826100-9cb868fdbe1c?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Luxury Scented Candle", "https://images.unsplash.com/photo-1603006905003-be475563bc59?w=600&auto=format&fit=crop");

        // Beauty & Personal Care (#91 - #120)
        PRODUCT_IMAGE_MAP.put("Hydrating Face Moisturizer", "https://images.unsplash.com/photo-1598440947619-2c35fc9aa908?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Gentle Face Cleanser", "https://images.unsplash.com/photo-1556228720-195a672e8a03?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Vitamin C Brightening Serum", "https://images.unsplash.com/photo-1620916566398-39f1143ab7be?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Hyaluronic Acid Serum", "https://images.unsplash.com/photo-1608248597261-e4d091058564?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Daily SPF Sunscreen", "https://images.unsplash.com/photo-1598440947619-2c35fc9aa908?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Nourishing Night Cream", "https://images.unsplash.com/photo-1570172619644-dfd03ed5d881?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Purifying Clay Face Mask", "https://images.unsplash.com/photo-1567928269937-ae146e45b428?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Gentle Facial Scrub", "https://images.unsplash.com/photo-1556228720-195a672e8a03?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Nourishing Hair Serum", "https://images.unsplash.com/photo-1535585209827-a15fcdbc4c2d?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Moisturizing Shampoo", "https://images.unsplash.com/photo-1535585209827-a15fcdbc4c2d?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Repairing Hair Conditioner", "https://images.unsplash.com/photo-1527799820374-dcf8d9d4a388?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Deep Repair Hair Mask", "https://images.unsplash.com/photo-1522337360788-8b13dee7a37e?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Daily Hydrating Body Lotion", "https://images.unsplash.com/photo-1556228720-195a672e8a03?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Refreshing Body Wash", "https://images.unsplash.com/photo-1617897903246-719242758050?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Premium Floral Perfume", "https://images.unsplash.com/photo-1541643600914-78b084683601?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Fresh Citrus Fragrance", "https://images.unsplash.com/photo-1592945403244-b3fbafd7f539?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Woody Evening Perfume", "https://images.unsplash.com/photo-1523293182086-7651a899d37f?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Natural Finish Foundation", "https://images.unsplash.com/photo-1599733589046-10c005739ef9?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Matte Finish Lipstick", "https://images.unsplash.com/photo-1586495777744-4413f21062fa?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Volume Boost Mascara", "https://images.unsplash.com/photo-1631730486784-5456119f69ae?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Professional Eyeshadow Palette", "https://images.unsplash.com/photo-1512496015851-a90fb38ba796?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Professional Makeup Brush Set", "https://images.unsplash.com/photo-1522337360788-8b13dee7a37e?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Acrylic Makeup Organizer", "https://images.unsplash.com/photo-1608248597261-e4d091058564?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Electric Facial Cleansing Brush", "https://images.unsplash.com/photo-1556228720-195a672e8a03?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Rechargeable Beard Trimmer", "https://images.unsplash.com/photo-1621607512214-68297480165e?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Professional Hair Dryer", "https://images.unsplash.com/photo-1522337360788-8b13dee7a37e?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Ceramic Hair Straightener", "https://images.unsplash.com/photo-1522337360788-8b13dee7a37e?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Automatic Hair Curler", "https://images.unsplash.com/photo-1522337360788-8b13dee7a37e?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Complete Grooming Kit", "https://images.unsplash.com/photo-1621607512214-68297480165e?w=600&auto=format&fit=crop");
        PRODUCT_IMAGE_MAP.put("Travel Cosmetic Bag", "https://images.unsplash.com/photo-1584917865442-de89df76afd3?w=600&auto=format&fit=crop");
    }

    public DataInitializer(UserDAO userDAO, CategoryDAO categoryDAO, ProductDAO productDAO, ProductSizeDAO productSizeDAO) {
        this.userDAO = userDAO;
        this.categoryDAO = categoryDAO;
        this.productDAO = productDAO;
        this.productSizeDAO = productSizeDAO;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // 1. Seed Demo Users if missing
        if (userDAO.findByEmail("admin@shopsphere.com") == null) {
            User admin = new User(
                    "ShopSphere Admin",
                    "admin@shopsphere.com",
                    "+91 98765 43210",
                    PasswordUtil.hashPassword("admin123"),
                    "Male",
                    "Cyber City, Tower B, Sector 24, Gurugram, Haryana - 122002",
                    "ADMIN"
            );
            userDAO.save(admin);
        }

        if (userDAO.findByEmail("customer@shopsphere.com") == null) {
            User customer = new User(
                    "Aarav Sharma",
                    "customer@shopsphere.com",
                    "+91 98123 45678",
                    PasswordUtil.hashPassword("customer123"),
                    "Male",
                    "Flat 402, Royal Palms, Bandra West, Mumbai, Maharashtra - 400050",
                    "CUSTOMER"
            );
            userDAO.save(customer);
        }

        // 2. Seed default products if database is empty
        if (productDAO.findAll().isEmpty()) {

            // CATEGORY 1: Electronics & Gadgets
            Category electronics = new Category("Electronics & Gadgets", "Smartphones, Audio, Laptops, Wearables & Accessories", true);
            categoryDAO.save(electronics);

            createProductWithSizes(electronics, "Premium Android Smartphone", "High-performance Android smartphone with crisp display and long battery life.", "62999.10", "10.00", PRODUCT_IMAGE_MAP.get("Premium Android Smartphone"), "128GB Black", 25, "256GB Silver", 15);
            createProductWithSizes(electronics, "Apple iPhone", "Flagship smartphone featuring Retina display, Bionic chip, and advanced dual cameras.", "73599.08", "8.00", PRODUCT_IMAGE_MAP.get("Apple iPhone"), "128GB Midnight", 30, "256GB Starlight", 20);
            createProductWithSizes(electronics, "5G Performance Smartphone", "Ultra-fast 5G connectivity, 120Hz AMOLED display, 50MP triple camera system.", "21249.15", "15.00", PRODUCT_IMAGE_MAP.get("5G Performance Smartphone"), "8GB RAM + 128GB", 40, "12GB RAM + 256GB", 25);
            createProductWithSizes(electronics, "Noise Cancelling Earbuds", "Active noise cancelling TWS earbuds with deep bass and 30-hour battery case.", "3999.20", "20.00", PRODUCT_IMAGE_MAP.get("Noise Cancelling Earbuds"), "Matte Black", 50, "Glacier White", 35);
            createProductWithSizes(electronics, "Premium Wireless Headphones", "Over-ear audiophile headphones with spatial audio and plush memory foam earcups.", "21249.15", "15.00", PRODUCT_IMAGE_MAP.get("Premium Wireless Headphones"), "Silver Gray", 15, "Midnight Black", 12);
            createProductWithSizes(electronics, "Bluetooth Stereo Headphones", "Lightweight wireless headphones with rich stereo sound and built-in microphone.", "4919.18", "18.00", PRODUCT_IMAGE_MAP.get("Bluetooth Stereo Headphones"), "Standard Black", 30, "Navy Blue", 20);
            createProductWithSizes(electronics, "Performance Laptop", "15.6-inch FHD laptop with Intel Core i7, 16GB RAM, 512GB NVMe SSD.", "65999.12", "12.00", PRODUCT_IMAGE_MAP.get("Performance Laptop"), "16GB RAM / 512GB SSD", 15, "32GB RAM / 1TB SSD", 10);
            createProductWithSizes(electronics, "Slim Pro Laptop", "Ultra-thin aluminum chassis, 14-inch OLED display, all-day 18-hour battery.", "89999.10", "10.00", PRODUCT_IMAGE_MAP.get("Slim Pro Laptop"), "Core i5 / 16GB", 20, "Core i7 / 16GB", 12);
            createProductWithSizes(electronics, "Gaming Laptop", "High-tier gaming laptop with RTX 4070 GPU, 240Hz display, and per-key RGB keyboard.", "97749.15", "15.00", PRODUCT_IMAGE_MAP.get("Gaming Laptop"), "RTX 4060 / 16GB", 10, "RTX 4070 / 32GB", 6);
            createProductWithSizes(electronics, "Fitness Smartwatch", "Rugged GPS smartwatch with heart rate monitoring, sleep tracking, and 50m water resistance.", "7199.20", "20.00", PRODUCT_IMAGE_MAP.get("Fitness Smartwatch"), "Black Sport Band", 35, "Orange Rugged Strap", 20);

            // CATEGORY 2: Fashion & Apparel
            Category fashion = new Category("Fashion & Apparel", "Men & Women Designer Clothing, Footwear & Accessories", true);
            categoryDAO.save(fashion);

            createProductWithSizes(fashion, "Classic Slim Fit Shirt", "100% premium cotton slim fit formal button-down shirt.", "1874.25", "25.00", PRODUCT_IMAGE_MAP.get("Classic Slim Fit Shirt"), "Size 39 (M)", 30, "Size 40 (L)", 25);
            createProductWithSizes(fashion, "Slim Fit Denim Jeans", "Durable stretch denim jeans with classic 5-pocket styling.", "2399.20", "20.00", PRODUCT_IMAGE_MAP.get("Slim Fit Denim Jeans"), "30W x 32L", 35, "32W x 32L", 40);
            createProductWithSizes(fashion, "Minimal White Sneakers", "Clean minimalist leather sneakers with rubber cupsole.", "2624.25", "25.00", PRODUCT_IMAGE_MAP.get("Minimal White Sneakers"), "UK 8", 25, "UK 9", 30);
            createProductWithSizes(fashion, "Luxury Leather Handbag", "Genuine Italian leather shoulder handbag with gold-tone hardware.", "5949.15", "15.00", PRODUCT_IMAGE_MAP.get("Luxury Leather Handbag"), "Chestnut Brown", 15, "Midnight Black", 12);

            // CATEGORY 3: Home & Living
            Category home = new Category("Home & Living", "Furnishings, Kitchen Appliances & Modern Home Decor", true);
            categoryDAO.save(home);

            createProductWithSizes(home, "Digital Air Fryer", "Rapid hot air circulation technology for healthy oil-free cooking.", "6399.20", "20.00", PRODUCT_IMAGE_MAP.get("Digital Air Fryer"), "4.5 Litre Black", 25, "5.5 Litre Stainless", 18);
            createProductWithSizes(home, "High Speed Kitchen Blender", "1200W motor blender for smoothies, frozen drinks, and food prep.", "2799.20", "20.00", PRODUCT_IMAGE_MAP.get("High Speed Kitchen Blender"), "Standard Pitcher Kit", 30, "Single Serve Cup Kit", 20);
            createProductWithSizes(home, "Modern Living Room Sofa", "Plush 3-seater fabric sofa with high-density foam cushioning.", "25499.15", "15.00", PRODUCT_IMAGE_MAP.get("Modern Living Room Sofa"), "Charcoal Grey", 8, "Beige Linen", 5);

            // CATEGORY 4: Beauty & Personal Care
            Category beauty = new Category("Beauty & Personal Care", "Skincare, Haircare, Perfumes & Grooming Essentials", true);
            categoryDAO.save(beauty);

            createProductWithSizes(beauty, "Vitamin C Brightening Serum", "Antioxidant-rich vitamin C serum for glowing, radiant skin.", "1199.20", "20.00", PRODUCT_IMAGE_MAP.get("Vitamin C Brightening Serum"), "30ml Dropper", 50, "50ml Refill", 30);
            createProductWithSizes(beauty, "Professional Hair Dryer", "2000W ionic salon-grade hair dryer with diffuser attachment.", "2549.15", "15.00", PRODUCT_IMAGE_MAP.get("Professional Hair Dryer"), "Matte Black", 25, "Rose Gold", 20);
            createProductWithSizes(beauty, "Rechargeable Beard Trimmer", "Self-sharpening titanium blades with 20 precision length settings.", "2049.18", "18.00", PRODUCT_IMAGE_MAP.get("Rechargeable Beard Trimmer"), "Black Kit", 40, "Silver Pro", 25);
        }

        // 3. Always update all existing products in DB with accurate, product-specific image URLs
        updateExistingProductImages();
    }

    private void updateExistingProductImages() {
        List<Product> products = productDAO.findAll();
        for (Product p : products) {
            if (p.getProductName() != null) {
                String cleanName = p.getProductName().trim();
                String matchedUrl = findMatchingImageUrl(cleanName);
                if (matchedUrl != null) {
                    p.setImageUrl(matchedUrl);
                    productDAO.save(p);
                }
            }
        }
    }

    private String findMatchingImageUrl(String productName) {
        // Direct match
        if (PRODUCT_IMAGE_MAP.containsKey(productName)) {
            return PRODUCT_IMAGE_MAP.get(productName);
        }

        // Substring / fuzzy match
        for (Map.Entry<String, String> entry : PRODUCT_IMAGE_MAP.entrySet()) {
            String key = entry.getKey();
            if (productName.equalsIgnoreCase(key) || productName.toLowerCase().contains(key.toLowerCase()) || key.toLowerCase().contains(productName.toLowerCase())) {
                return entry.getValue();
            }
        }

        return null;
    }

    private void createProductWithSizes(Category category, String name, String desc, String priceStr, String discStr, String imgUrl, String size1, int stock1, String size2, int stock2) {
        BigDecimal price = new BigDecimal(priceStr);
        BigDecimal disc = new BigDecimal(discStr);

        Product p = new Product(category, name, desc, price, disc, imgUrl, true);
        Product savedProduct = productDAO.save(p);

        ProductSize ps1 = new ProductSize(savedProduct, size1, stock1, "SKU-" + savedProduct.getProductId() + "-A", true);
        productSizeDAO.save(ps1);

        if (size2 != null) {
            ProductSize ps2 = new ProductSize(savedProduct, size2, stock2, "SKU-" + savedProduct.getProductId() + "-B", true);
            productSizeDAO.save(ps2);
        }
    }
}
