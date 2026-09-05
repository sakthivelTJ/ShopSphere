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

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserDAO userDAO;
    private final CategoryDAO categoryDAO;
    private final ProductDAO productDAO;
    private final ProductSizeDAO productSizeDAO;

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

        // 2. Seed 20+ Products per Category if database has < 15 products
        if (productDAO.findAll().size() < 15) {

            // CATEGORY 1: Electronics & Gadgets
            Category electronics = new Category("Electronics & Gadgets", "Smartphones, Audio, Laptops, Wearables & Accessories", true);
            categoryDAO.save(electronics);

            createProductWithSizes(electronics, "OnePlus 12 5G (16GB RAM, 512GB)", "Snapdragon 8 Gen 3, 50MP Hasselblad Camera, 100W SuperVOOC Fast Charging.", "64999.00", "10.00", "https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=600&auto=format&fit=crop", "Emerald Green", 25, "Silky Black", 15);
            createProductWithSizes(electronics, "Sony WH-1000XM5 Wireless Headphones", "Industry leading noise canceling with Auto NC Optimizer and 30-hr battery life.", "29990.00", "15.00", "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=600&auto=format&fit=crop", "Silver", 20, "Midnight Black", 18);
            createProductWithSizes(electronics, "Apple Watch Series 9 GPS 45mm", "Advanced health sensors, S9 SiP chip, Double Tap gesture control, Always-On Retina display.", "44900.00", "5.00", "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=600&auto=format&fit=crop", "Midnight Sport Loop", 30, "Starlight Aluminum", 12);
            createProductWithSizes(electronics, "MacBook Air 15-inch M2", "Liquid Retina Display, 8GB Unified Memory, 512GB SSD, 18-Hour Battery Life.", "134900.00", "8.00", "https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=600&auto=format&fit=crop", "Space Grey", 10, "Starlight", 8);
            createProductWithSizes(electronics, "Samsung Galaxy Tab S9 Ultra", "14.6 Dynamic AMOLED 2X, S Pen included, Snapdragon 8 Gen 2, Armor Aluminum casing.", "108999.00", "12.00", "https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0?w=600&auto=format&fit=crop", "256GB Graphite", 14, "512GB Beige", 6);
            createProductWithSizes(electronics, "JBL Flip 6 Portable Bluetooth Speaker", "2-way speaker system, IP67 waterproof and dustproof, 12 hours playtime.", "9999.00", "20.00", "https://images.unsplash.com/photo-1608043152269-423dbba4e7e1?w=600&auto=format&fit=crop", "Ocean Blue", 40, "Squad Camo", 25);
            createProductWithSizes(electronics, "Logitech MX Master 3S Wireless Mouse", "8K DPI tracking on any surface, Quiet Clicks, Ergonomic design, USB-C fast charge.", "9495.00", "10.00", "https://images.unsplash.com/photo-1615663245857-ac93bb7c39e7?w=600&auto=format&fit=crop", "Graphite", 50, "Pale Grey", 30);
            createProductWithSizes(electronics, "Asus ROG Zephyrus G16 Gaming Laptop", "Intel Core Ultra 9, RTX 4070 8GB, 32GB LPDDR5X, 240Hz OLED Display.", "189990.00", "7.00", "https://images.unsplash.com/photo-1603302576837-37561b2e2302?w=600&auto=format&fit=crop", "Eclipse Grey", 6, "Platinum White", 4);
            createProductWithSizes(electronics, "Canon EOS R6 Mark II Mirrorless Camera", "24.2 MP Full-Frame CMOS Sensor, 40 fps continuous shooting, 4K 60p video.", "215995.00", "5.00", "https://images.unsplash.com/photo-1516035069371-29a1b244cc32?w=600&auto=format&fit=crop", "Body Only", 8, "With 24-105mm Lens", 5);
            createProductWithSizes(electronics, "GoPro HERO12 Black Action Camera", "5.3K60 Video, HDR, HyperSmooth 6.0 Stabilization, Waterproof to 33ft.", "37990.00", "15.00", "https://images.unsplash.com/photo-1526170375885-4d8ecf77b99f?w=600&auto=format&fit=crop", "Standard Bundle", 22, "Creator Edition", 11);
            createProductWithSizes(electronics, "Anker PowerCore 24000mAh Power Bank", "140W Ultra-Powerful 3-Port Portable Charger with Smart Digital Display.", "12999.00", "18.00", "https://images.unsplash.com/photo-1609592424074-1296f018e690?w=600&auto=format&fit=crop", "Black 140W", 45, "Silver 100W", 35);
            createProductWithSizes(electronics, "Bose QuietComfort Ultra Earbuds", "World-class noise cancellation, Spatial audio, CustomTune technology, 6-hr battery.", "25900.00", "10.00", "https://images.unsplash.com/photo-1590658268037-6bf12165a8df?w=600&auto=format&fit=crop", "Black", 19, "White Smoke", 16);
            createProductWithSizes(electronics, "Dell UltraSharp 27 4K USB-C Hub Monitor", "IPS Black technology, 98% DCI-P3 color coverage, ComfortView Plus, 90W power delivery.", "54999.00", "12.00", "https://images.unsplash.com/photo-1527443224154-c4a3942d3acf?w=600&auto=format&fit=crop", "27-inch 4K", 15, "32-inch 4K", 9);
            createProductWithSizes(electronics, "Kindle Paperwhite 16GB (11th Gen)", "6.8 display with adjustable warm light, waterproof, USB-C, up to 10 weeks battery.", "14999.00", "8.00", "https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?w=600&auto=format&fit=crop", "Black 16GB", 35, "Agave Green 16GB", 20);
            createProductWithSizes(electronics, "Keychron K2 Pro Wireless Mechanical Keyboard", "QMK/VIA Programmable, Hot-swappable Red Switches, RGB Backlight, Mac & Windows.", "9990.00", "10.00", "https://images.unsplash.com/photo-1587829741301-dc798b83add3?w=600&auto=format&fit=crop", "Red Switch RGB", 28, "Brown Switch RGB", 24);
            createProductWithSizes(electronics, "Dyson Zone Absolute Noise Canceling Headphones", "Purified air technology, ultra-low distortion audio, advanced active noise cancellation.", "59900.00", "15.00", "https://images.unsplash.com/photo-1546435770-a3e426bf472b?w=600&auto=format&fit=crop", "Prussian Blue", 7, "Copper Edition", 4);
            createProductWithSizes(electronics, "Sennheiser AMBEO Soundbar Mini", "3D Sound with Dolby Atmos & DTS:X, Dual integrated 4 subwoofers, Bluetooth & Wi-Fi.", "74990.00", "20.00", "https://images.unsplash.com/photo-1545454675-3531b543be5d?w=600&auto=format&fit=crop", "Standard Black", 10, "Plus Subwoofer", 6);
            createProductWithSizes(electronics, "DJI Mini 4 Pro Fly More Combo", "Under 249g, 4K/60fps HDR True Vertical Shooting, Omnidirectional Obstacle Sensing.", "109900.00", "5.00", "https://images.unsplash.com/photo-1508614589041-895b88991e3e?w=600&auto=format&fit=crop", "RC 2 Controller Kit", 12, "Standard Kit", 15);
            createProductWithSizes(electronics, "Nothing Phone (2a) 5G", "Glyph Interface, Dimensity 7200 Pro, 50MP Dual Camera, 120Hz Flexible AMOLED.", "23999.00", "10.00", "https://images.unsplash.com/photo-1565849904461-04a58ad377e0?w=600&auto=format&fit=crop", "128GB White", 40, "256GB Black", 30);
            createProductWithSizes(electronics, "Marshall Stanmore III Bluetooth Speaker", "Iconic vintage design, re-engineered wider soundstage, dynamic loudness.", "31999.00", "12.00", "https://images.unsplash.com/photo-1545454675-3531b543be5d?w=600&auto=format&fit=crop", "Black Gold", 18, "Vintage Cream", 12);


            // CATEGORY 2: Fashion & Apparel
            Category fashion = new Category("Fashion & Apparel", "Men & Women Designer Clothing, Footwear & Accessories", true);
            categoryDAO.save(fashion);

            createProductWithSizes(fashion, "Handcrafted Pure Silk Banarasi Saree", "Rich zari weave, traditional royal motifs, accompanied by unstitched matching blouse piece.", "14999.00", "20.00", "https://images.unsplash.com/photo-1610030469983-98e550d6193c?w=600&auto=format&fit=crop", "Royal Blue & Gold", 15, "Crimson Red", 10);
            createProductWithSizes(fashion, "Men's Slim Fit Linen Blazer", "Breathable 100% European linen fabric with structured lapels and internal lining.", "6999.00", "15.00", "https://images.unsplash.com/photo-1507679799987-c73779587ccf?w=600&auto=format&fit=crop", "Size 40 (M)", 25, "Size 42 (L)", 20);
            createProductWithSizes(fashion, "Nike Air Force 1 '07 Sneakers", "Classic crisp leather upper, stitched overlays, legendary Nike Air cushioning.", "8995.00", "10.00", "https://images.unsplash.com/photo-1595950653106-6c9ebd614d3a?w=600&auto=format&fit=crop", "UK 8", 18, "UK 9", 22);
            createProductWithSizes(fashion, "Women's Embroidered Anarkali Kurta Set", "Intricate chikan embroidery on georgette with dupatta and churidar trousers.", "4599.00", "25.00", "https://images.unsplash.com/photo-1583391733956-6c78276477e2?w=600&auto=format&fit=crop", "Medium (M)", 30, "Large (L)", 25);
            createProductWithSizes(fashion, "Levi's 501 Original Fit Jeans", "The iconic straight leg denim jeans with button fly and signature copper rivets.", "3999.00", "15.00", "https://images.unsplash.com/photo-1542272604-780c96856592?w=600&auto=format&fit=crop", "32W x 32L", 35, "34W x 32L", 30);
            createProductWithSizes(fashion, "Ray-Ban Aviator Classic Sunglasses", "G-15 green glass lenses with 100% UV protection and polished gold metal frame.", "9890.00", "12.00", "https://images.unsplash.com/photo-1572635196237-14b3f281503f?w=600&auto=format&fit=crop", "Standard 58mm", 20, "Large 62mm", 15);
            createProductWithSizes(fashion, "Fossil Gen 6 Leather Hybrid Watch", "Rose gold stainless steel case with genuine brown leather strap and smart notifications.", "13495.00", "30.00", "https://images.unsplash.com/photo-1524805444758-089113d48a6d?w=600&auto=format&fit=crop", "Brown Leather", 16, "Black Steel", 12);
            createProductWithSizes(fashion, "Women's Leather Tote Shoulder Bag", "Full grain Italian leather with gold hardware, laptop compartment and zipper closure.", "7899.00", "20.00", "https://images.unsplash.com/photo-1584917865442-de89df76afd3?w=600&auto=format&fit=crop", "Tan Brown", 22, "Obsidian Black", 18);
            createProductWithSizes(fashion, "Men's Waterproof Winter Puffer Jacket", "Insulated down jacket with detachable hood and fleece-lined handwarmer pockets.", "5499.00", "18.00", "https://images.unsplash.com/photo-1544923246-77307dd654cb?w=600&auto=format&fit=crop", "Large (L)", 20, "XL", 15);
            createProductWithSizes(fashion, "Adidas Ultraboost Light Running Shoes", "Light Boost material, Continental rubber outsole, Primeknit textile upper.", "13999.00", "25.00", "https://images.unsplash.com/photo-1584735935682-2f2b69dff9d2?w=600&auto=format&fit=crop", "UK 7", 14, "UK 9", 19);
            createProductWithSizes(fashion, "Women's Floral Chiffon Maxi Dress", "Tiered ruffle hemline, smocked waistline, and breathable double lining.", "2999.00", "15.00", "https://images.unsplash.com/photo-1572804013309-59a88b7e92f1?w=600&auto=format&fit=crop", "Small (S)", 25, "Medium (M)", 20);
            createProductWithSizes(fashion, "Men's Handcrafted Leather Oxford Shoes", "Genuine calfskin leather with Goodyear welt construction and leather sole.", "8499.00", "20.00", "https://images.unsplash.com/photo-1614252235316-8c857d38b5f4?w=600&auto=format&fit=crop", "UK 8", 12, "UK 10", 10);
            createProductWithSizes(fashion, "Puma Unisex Classic Track Jacket", "Retro sporty design with T7 stripe paneling and full zip front.", "3299.00", "10.00", "https://images.unsplash.com/photo-1556905055-8f358a7a47b2?w=600&auto=format&fit=crop", "Medium (M)", 30, "Large (L)", 25);
            createProductWithSizes(fashion, "Women's Cashmere Knit Sweater", "100% Inner Mongolian cashmere crewneck sweater, silky soft feel.", "8999.00", "15.00", "https://images.unsplash.com/photo-1576566588028-4147f3842f27?w=600&auto=format&fit=crop", "Beige", 15, "Charcoal", 12);
            createProductWithSizes(fashion, "Unisex Polarized Retro Round Sunglasses", "UV400 protection, lightweight alloy frame, polarized anti-glare lenses.", "1999.00", "30.00", "https://images.unsplash.com/photo-1511499767150-a48a237f0083?w=600&auto=format&fit=crop", "Matte Black", 50, "Tortoise Shell", 40);
            createProductWithSizes(fashion, "Women's High-Waist Gym Leggings", "Squat-proof 4-way stretch fabric with side pockets and moisture-wicking technology.", "1899.00", "20.00", "https://images.unsplash.com/photo-1506629082925-23688c07465a?w=600&auto=format&fit=crop", "Small (S)", 35, "Medium (M)", 45);
            createProductWithSizes(fashion, "Men's Formal Cotton Dress Shirt", "Non-iron 100% Supima cotton tailored fit shirt with French cuffs.", "2799.00", "15.00", "https://images.unsplash.com/photo-1602810318383-e386cc2a3ccf?w=600&auto=format&fit=crop", "39cm Neck", 25, "41cm Neck", 20);
            createProductWithSizes(fashion, "Women's Block Heel Ankle Boots", "Synthetic suede upper with side zip closure and cushioned memory foam insole.", "3599.00", "25.00", "https://images.unsplash.com/photo-1543163521-1bf539c55dd2?w=600&auto=format&fit=crop", "UK 5", 18, "UK 6", 14);
            createProductWithSizes(fashion, "Pure Wool Muffler Scarf", "Superfine merino wool woven scarf with classic fringed ends.", "1499.00", "10.00", "https://images.unsplash.com/photo-1520903920243-00d872a2d1c9?w=600&auto=format&fit=crop", "Navy Blue", 30, "Burgundy", 25);
            createProductWithSizes(fashion, "Men's Genuine Leather Belt", "Reversible dual-sided leather belt with polished automatic sliding buckle.", "1299.00", "20.00", "https://images.unsplash.com/photo-1624222247344-550fb60583dc?w=600&auto=format&fit=crop", "34-36 Waist", 40, "38-40 Waist", 30);


            // CATEGORY 3: Home & Living
            Category home = new Category("Home & Living", "Furnishings, Kitchen Appliances & Modern Home Decor", true);
            categoryDAO.save(home);

            createProductWithSizes(home, "Dyson V15 Detect Cordless Vacuum Cleaner", "Laser reveals microscopic dust, LCD screen displays real-time scientific proof of deep clean.", "65900.00", "10.00", "https://images.unsplash.com/photo-1558317374-067fb5f30001?w=600&auto=format&fit=crop", "Standard Gold", 10, "Complete Kit", 6);
            createProductWithSizes(home, "Philips Digital Air Fryer HD9252/90", "Rapid Air Technology, 7 Touchscreen presets, 90% less fat cooking.", "8999.00", "25.00", "https://images.unsplash.com/photo-1585515320310-259814833e62?w=600&auto=format&fit=crop", "4.1 Liter Black", 30, "4.1 Liter White", 20);
            createProductWithSizes(home, "Nespresso Vertuo Pop Espresso Machine", "Centrifusion technology, 5 cup sizes, Bluetooth & Wi-Fi connectivity.", "16499.00", "15.00", "https://images.unsplash.com/photo-1517668808822-9ebe02afd2a4?w=600&auto=format&fit=crop", "Pacific Blue", 15, "Coconut White", 12);
            createProductWithSizes(home, "Handmade Ceramic Nordic Vase Set", "Set of 2 minimalist matte white ceramic flower vases for living room decor.", "1899.00", "20.00", "https://images.unsplash.com/photo-1612196808214-b7e239e5f6b7?w=600&auto=format&fit=crop", "Set of 2 White", 40, "Set of 2 Terracotta", 25);
            createProductWithSizes(home, "Orthopedic Memory Foam Mattress", "Triple-layer cooling gel memory foam mattress with breathable knitted outer cover.", "14999.00", "30.00", "https://images.unsplash.com/photo-1631049307264-da0ec9d70304?w=600&auto=format&fit=crop", "Queen Size (78x60)", 12, "King Size (78x72)", 8);
            createProductWithSizes(home, "100% Egyptian Cotton 400 TC Bedsheet", "Sateen weave luxury king size bedsheet with 2 pillow covers.", "2999.00", "20.00", "https://images.unsplash.com/photo-1522771739844-6a9f6d5f14af?w=600&auto=format&fit=crop", "Ivory White", 25, "Pastel Blue", 20);
            createProductWithSizes(home, "Smart Ambient LED Floor Lamp", "RGBIC color changing, music sync, works with Alexa & Google Assistant.", "4499.00", "15.00", "https://images.unsplash.com/photo-1507473885765-e6ed057f782c?w=600&auto=format&fit=crop", "Black Metal", 20, "Silver Aluminum", 15);
            createProductWithSizes(home, "Instant Pot Duo 7-in-1 Pressure Cooker", "Pressure cooker, slow cooker, rice cooker, yogurt maker, steamer, and warmer.", "7999.00", "20.00", "https://images.unsplash.com/photo-1544025162-d76694265947?w=600&auto=format&fit=crop", "6 Litre", 25, "8 Litre", 15);
            createProductWithSizes(home, "Solid Sheesham Wood Dining Table Set", "6-seater natural teak finish dining table with 6 cushioned chairs.", "32999.00", "15.00", "https://images.unsplash.com/photo-1615066390971-03e4e1c36ddf?w=600&auto=format&fit=crop", "6 Seater Teak", 5, "4 Seater Teak", 8);
            createProductWithSizes(home, "Ergonomic Mesh High-Back Office Chair", "Adjustable lumbar support, 3D armrests, heavy duty metal base, 135-degree tilt.", "9499.00", "25.00", "https://images.unsplash.com/photo-1580481072645-022f9a6d5017?w=600&auto=format&fit=crop", "All Black", 30, "Grey Mesh", 20);
            createProductWithSizes(home, "Panasonic 27L Convection Microwave Oven", "Zero oil recipes, auto cook menu, 360-degree heat distribution.", "11990.00", "18.00", "https://images.unsplash.com/photo-1574269909862-7e1d70bb8078?w=600&auto=format&fit=crop", "27L Black Mirror", 18, "27L Silver", 12);
            createProductWithSizes(home, "Mi Smart Air Purifier 4 Pro", "True HEPA Filter, eliminates 99.97% pet dander & pollen, OLED touch display.", "18999.00", "15.00", "https://images.unsplash.com/photo-1585771724684-38269d6639fd?w=600&auto=format&fit=crop", "Pro White", 15, "Standard 4", 22);
            createProductWithSizes(home, "Handwoven Jute Area Rug (5x7 ft)", "Natural eco-friendly braided jute rug with anti-slip backing.", "3499.00", "20.00", "https://images.unsplash.com/photo-1600121848594-d8644e57abab?w=600&auto=format&fit=crop", "5x7 Feet Natural", 20, "6x9 Feet Natural", 12);
            createProductWithSizes(home, "Non-Stick Granite Cookware Set (5 Pcs)", "PFOA free granite coating, induction compatible base with glass lids.", "4299.00", "30.00", "https://images.unsplash.com/photo-1584992236310-6edddc08acff?w=600&auto=format&fit=crop", "5-Piece Black", 35, "5-Piece Red", 25);
            createProductWithSizes(home, "NutriBullet PRO 900W High Speed Blender", "Extracts hidden nutrition from whole fruits & vegetables, compact design.", "6999.00", "15.00", "https://images.unsplash.com/photo-1570222094114-d054a817e56b?w=600&auto=format&fit=crop", "Matte Black", 25, "Champagne Gold", 15);
            createProductWithSizes(home, "Aroma Diffuser & Essential Oil Humidifier", "500ml ultrasonic cool mist diffuser with 7 ambient LED light modes.", "1499.00", "25.00", "https://images.unsplash.com/photo-1602928321679-560b4139c901?w=600&auto=format&fit=crop", "Dark Wood Grain", 50, "Light Wood Grain", 40);
            createProductWithSizes(home, "Stainless Steel Thermal Insulated Water Bottle", "Keeps drinks cold for 24 hrs or hot for 12 hrs, leak-proof sports cap.", "899.00", "10.00", "https://images.unsplash.com/photo-1602143407151-7111542de6e8?w=600&auto=format&fit=crop", "750ml Matte Black", 60, "1000ml Steel", 50);
            createProductWithSizes(home, "Handcrafted Wooden Wall Clock", "Silent non-ticking quartz movement, 12-inch 3D rustic wood frame.", "1299.00", "20.00", "https://images.unsplash.com/photo-1563861826100-9cb868fdbe1c?w=600&auto=format&fit=crop", "12-Inch Walnut", 30, "14-Inch Oak", 20);
            createProductWithSizes(home, "Microfiber Soft Bath Towel Set (Pack of 2)", "Ultra absorbent 500 GSM plush towels, quick dry & fade resistant.", "999.00", "15.00", "https://images.unsplash.com/photo-1616627547584-bf28cee262db?w=600&auto=format&fit=crop", "Navy & Grey", 40, "Beige & White", 35);
            createProductWithSizes(home, "Zero Gravity Recliner Lounge Chair", "Folding outdoor patio chair with adjustable headrest and cup holder.", "3999.00", "20.00", "https://images.unsplash.com/photo-1567538096630-e0c55bd6374c?w=600&auto=format&fit=crop", "Black Fabric", 22, "Brown Mesh", 18);


            // CATEGORY 4: Beauty & Personal Care
            Category beauty = new Category("Beauty & Personal Care", "Skincare, Haircare, Perfumes & Grooming Essentials", true);
            categoryDAO.save(beauty);

            createProductWithSizes(beauty, "Forest Essentials Soundarya Radiance Cream", "24K Gold infused Ayurveda face cream with SPF 25, restores natural firmness.", "5800.00", "10.00", "https://images.unsplash.com/photo-1570172619644-dfd03ed5d881?w=600&auto=format&fit=crop", "50g Jar", 20, "30g Travel Size", 15);
            createProductWithSizes(beauty, "Philips SkinIQ Series 7000 Electric Shaver", "SteelPrecision blades, 360-D flexing heads, wet & dry shaving.", "8495.00", "15.00", "https://images.unsplash.com/photo-1621607512214-68297480165e?w=600&auto=format&fit=crop", "Blue Metallic", 18, "Chrome Edition", 12);
            createProductWithSizes(beauty, "Minimalist 10% Vitamin C Face Serum", "Brightening serum with acetyl glucosamine, reduces dark spots and pigmentation.", "699.00", "10.00", "https://images.unsplash.com/photo-1620916566398-39f1143ab7be?w=600&auto=format&fit=crop", "30ml Dropper", 60, "50ml Refill", 40);
            createProductWithSizes(beauty, "Dyson Airwrap Multi-Styler Complete", "Curl, shape, smooth and hide flyaways with Coanda airflow, no extreme heat.", "49900.00", "8.00", "https://images.unsplash.com/photo-1522337360788-8b13dee7a37e?w=600&auto=format&fit=crop", "Nickel & Copper", 10, "Strawberry Bronze", 6);
            createProductWithSizes(beauty, "L'Oreal Paris Hyaluronic Acid Serum", "1.5% Pure Hyaluronic Acid, intensely hydrates and plumps skin by 42%.", "999.00", "20.00", "https://images.unsplash.com/photo-1608248597261-e4d091058564?w=600&auto=format&fit=crop", "30ml Bottle", 50, "50ml Bottle", 35);
            createProductWithSizes(beauty, "Versace Eros Eau De Parfum for Men", "Mint leaves, Italian lemon zest, tonka beans, and sensual amber woody notes.", "7850.00", "15.00", "https://images.unsplash.com/photo-1592945403244-b3fbafd7f539?w=600&auto=format&fit=crop", "100ml Spray", 15, "50ml Spray", 20);
            createProductWithSizes(beauty, "CHANEL Coco Mademoiselle Intense", "Orient-fresh oriental fragrance with deep patchouli, amber and vanilla.", "12500.00", "5.00", "https://images.unsplash.com/photo-1541643600914-78b084683601?w=600&auto=format&fit=crop", "100ml Spray", 8, "50ml Spray", 12);
            createProductWithSizes(beauty, "Olaplex No. 3 Hair Perfector Treatment", "Repairs damaged hair bonds, restores healthy texture and shine.", "2950.00", "10.00", "https://images.unsplash.com/photo-1535585209827-a15fcdbc4c2d?w=600&auto=format&fit=crop", "100ml Bottle", 30, "250ml Bottle", 15);
            createProductWithSizes(beauty, "Clinique Take The Day Off Cleansing Balm", "Dissolves stubborn makeup and sunscreens, lightweight balm formula.", "3200.00", "15.00", "https://images.unsplash.com/photo-1556228720-195a672e8a03?w=600&auto=format&fit=crop", "125ml Tub", 22, "200ml Tub", 14);
            createProductWithSizes(beauty, "MAC Matte Lipstick - Ruby Woo", "Iconic vivid blue-red matte lipstick with high color payoff.", "1950.00", "10.00", "https://images.unsplash.com/photo-1586495777744-4413f21062fa?w=600&auto=format&fit=crop", "Standard 3g", 40, "Mini 1.8g", 30);
            createProductWithSizes(beauty, "Maybelline SuperStay Vinyl Ink Liquid Lipstick", "No-budge shine color that lasts up to 16 hours, transfer-proof.", "849.00", "20.00", "https://images.unsplash.com/photo-1631730486784-5456119f69ae?w=600&auto=format&fit=crop", "Witty Shade", 50, "Coy Shade", 45);
            createProductWithSizes(beauty, "Neutrogena Hydro Boost Water Gel", "Hyaluronic Acid formula instantly quenches dry skin and locks in moisture.", "1050.00", "15.00", "https://images.unsplash.com/photo-1598440947619-2c35fc9aa908?w=600&auto=format&fit=crop", "50g Jar", 35, "100g Refill", 20);
            createProductWithSizes(beauty, "Oral-B Pro 3 3000 Electric Toothbrush", "3D cleaning action, 360-degree visible pressure control sensor.", "4199.00", "25.00", "https://images.unsplash.com/photo-1559598467-f8b76c8155d0?w=600&auto=format&fit=crop", "Black Edition", 25, "White Edition", 20);
            createProductWithSizes(beauty, "Beardo Godfather Beard Oil & Wash Combo", "Nourishes coarse beard hair, non-greasy fragrance infused with argan oil.", "799.00", "20.00", "https://images.unsplash.com/photo-1626285861696-9f0bf5a49c6d?w=600&auto=format&fit=crop", "30ml Oil + 100ml Wash", 40, "Mega Pack", 25);
            createProductWithSizes(beauty, "Kama Ayurveda Bringadi Intensive Hair Treatment", "100% natural sesame oil infused with Indigo, Eclipta Alba and Gooseberry.", "1695.00", "10.00", "https://images.unsplash.com/photo-1608248597261-e4d091058564?w=600&auto=format&fit=crop", "250ml Bottle", 30, "100ml Bottle", 40);
            createProductWithSizes(beauty, "Cetaphil Gentle Skin Cleanser", "Soap-free non-irritating formula for sensitive & dry skin types.", "949.00", "15.00", "https://images.unsplash.com/photo-1556228720-195a672e8a03?w=600&auto=format&fit=crop", "250ml Pump", 45, "500ml Pump", 30);
            createProductWithSizes(beauty, "The Body Shop British Rose Shower Gel", "Soap-free body wash infused with hand-picked organic rose essence.", "495.00", "20.00", "https://images.unsplash.com/photo-1617897903246-719242758050?w=600&auto=format&fit=crop", "250ml Bottle", 50, "750ml Pump", 25);
            createProductWithSizes(beauty, "Estee Lauder Advanced Night Repair Synchronized Multi-Recovery", "Patented Chronolux Power Signal Technology, deep anti-aging reduction.", "10500.00", "10.00", "https://images.unsplash.com/photo-1608248597261-e4d091058564?w=600&auto=format&fit=crop", "50ml Bottle", 12, "30ml Bottle", 18);
            createProductWithSizes(beauty, "Philips Essential Care Hair Straightener", "SilkPro Care ceramic plates, 210C professional high heat setting.", "1895.00", "20.00", "https://images.unsplash.com/photo-1522337360788-8b13dee7a37e?w=600&auto=format&fit=crop", "Black Ceramic", 30, "Pink Edition", 25);
            createProductWithSizes(beauty, "Tresemme Keratin Smooth Shampoo & Conditioner Set", "Infused with Keratin and Argan oil, controls frizz up to 3 days.", "899.00", "15.00", "https://images.unsplash.com/photo-1535585209827-a15fcdbc4c2d?w=600&auto=format&fit=crop", "Twin Pack 580ml", 50, "Twin Pack 1L", 30);
        }
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
