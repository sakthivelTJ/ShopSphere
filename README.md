<div align="center">

  # 🛒 ShopSphere

  ### **A Modern Glassmorphic Full-Stack E-Commerce Platform**

  [![Java](https://img.shields.io/badge/Java-17%2B-orange.svg?style=for-the-badge&logo=openjdk)](https://www.oracle.com/java/)
  [![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.5-6DB33F.svg?style=for-the-badge&logo=springboot)](https://spring.io/projects/spring-boot)
  [![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1.svg?style=for-the-badge&logo=mysql)](https://www.mysql.com/)
  [![Bootstrap](https://img.shields.io/badge/Bootstrap-5.3-7952B3.svg?style=for-the-badge&logo=bootstrap)](https://getbootstrap.com/)
  [![Deployed on Railway](https://img.shields.io/badge/Deployed_on-Railway-0B0D0E.svg?style=for-the-badge&logo=railway)](https://shopsphere-production-999c.up.railway.app/)

  ---

  🌐 **[Live Demo Application](https://shopsphere-production-999c.up.railway.app/)** • 📖 **[Documentation](#-features)** • ⚡ **[Quick Start](#-getting-started)**

</div>

<br/>

## 🌟 Overview

**ShopSphere** is a feature-packed full-stack e-commerce web application engineered with **Spring Boot 3**, **Spring Data JPA / Hibernate**, **JDBC Template**, **MySQL**, and **JSP Views**. 

Designed with a modern **Glassmorphism Aesthetic** and dynamic **Dark/Light Mode**, ShopSphere delivers an exceptional shopping experience for customers and a robust management portal for store administrators.

---

## ⚡ Live Demo & Demo Accounts

Explore the live production deployment hosted on Railway:

👉 **[Launch ShopSphere Production App](https://shopsphere-production-999c.up.railway.app/)**

| Role | Email | Password | Access Level |
| :--- | :--- | :--- | :--- |
| **👑 Admin** | `admin@shopsphere.com` | `admin123` | Full Access: Analytics, Products, Orders, Users, Inventory |
| **🛍️ Customer** | `customer@shopsphere.com` | `customer123` | Storefront: Browse, Filter, Cart, Checkout, Order History |

---

## ✨ Features

### 🛍️ Customer Experience
- **🎨 Glassmorphic Dark/Light Mode**: Seamless CSS-driven theme switcher with flash-free page transitions.
- **🔍 Smart Search & Filtering**: Instant search across 120+ curated catalog items with category-based filtering.
- **📦 Dynamic Product Variants**: Multi-size/variant selection with real-time stock availability indicators.
- **🛒 Interactive Shopping Cart**: Quantity adjustments, instant subtotal & tax calculation, and persistent user sessions.
- **💳 Streamlined Checkout**: Secure shipping address input and instant order generation.
- **📜 Order History & Tracking**: Order timeline status (Pending, Processing, Shipped, Delivered) with receipt breakdown.

### 👑 Admin Management Portal
- **📊 Real-Time Analytics Dashboard**: Live metrics tracking Gross Revenue, Total Orders, Active Users, and Catalog Volume.
- **🏆 Top Selling Products Report**: Aggregated sales metrics displaying top performers with product thumbnails and total sales revenue.
- **⚠️ Automated Low Stock Alerts**: Instant inventory warnings highlighting variants below threshold limits.
- **📦 Product & Category Management**: Full CRUD operations for product items, discounts, pricing, and categories.
- **👥 User & Order Oversight**: Manage registered customer profiles and update order fulfillment statuses.

---

## 🛠️ Tech Stack & Architecture

| Layer | Technologies Used |
| :--- | :--- |
| **Backend Framework** | Spring Boot 3.2.5 (Java 17), Spring MVC, Spring Data JPA / Hibernate, JDBC Template |
| **Security & Auth** | Custom Role-Based Access Control (RBAC), SHA-256 Password Hashing |
| **Frontend UI** | JSP (Jakarta Tags / JSTL), HTML5, Vanilla CSS3 (Glassmorphism), JavaScript (ES6+), Bootstrap 5 |
| **Database** | MySQL 8.0+ (Relational Database Management System) |
| **Data Seeding** | Automated Startup Seeder (`DataInitializer.java`) with Unsplash HD Product Media Maps |
| **Build & Deploy** | Apache Maven, Git/GitHub, Railway Cloud Platform |

---

## 📁 Directory Structure

```text
ShopSphere/
├── schema_and_data.sql             # Full MySQL database creation & seed script
├── pom.xml                         # Maven dependencies & build configuration
└── src/
    └── main/
        ├── java/com/shopsphere/
        │   ├── config/             # DataInitializer & App Configurations
        │   ├── controller/         # Spring MVC Controllers (Admin, Auth, Cart, Order, Product)
        │   ├── dao/                # Data Access Objects (JPA Repositories & JDBC Reports)
        │   ├── dto/                # Data Transfer Objects (Requests & Forms)
        │   ├── entity/             # JPA Entities (User, Product, Category, Order, Cart)
        │   ├── service/            # Business Logic & Service Interfaces
        │   └── util/               # Security Helpers (SHA-256 Hashing)
        ├── resources/
        │   ├── application.properties
        │   └── static/css/         # Glassmorphism & Theme Stylesheet (style.css)
        └── webapp/WEB-INF/views/   # Modular JSP View Components & Pages
```

---

## 📦 Getting Started & Local Setup

Follow these steps to set up ShopSphere on your local environment:

### Prerequisites
- **Java 17** or higher installed
- **Apache Maven 3.8+** installed
- **MySQL Server 8.0+** running locally

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/sakthivelTJ/ShopSphere.git
cd ShopSphere
```

### 2️⃣ Configure MySQL Database
Create a database named `ecommerce_db` in MySQL or execute [`schema_and_data.sql`](schema_and_data.sql):
```sql
CREATE DATABASE ecommerce_db;
```

Update your connection credentials in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD
spring.jpa.hibernate.ddl-auto=update
```

### 3️⃣ Build & Run the Application
```bash
mvn clean install
mvn spring-boot:run
```

### 4️⃣ Access in Browser
Open your browser and navigate to:
```text
http://localhost:8080
```
> *Note: On first startup, `DataInitializer` will automatically seed default demo categories, products, images, and user accounts if database tables are empty.*

---

## 🛡️ Database ER Diagram Overview

```text
  [ USER ] 1 ─── N [ CART ] 1 ─── N [ CART_ITEM ]
     │                                    │
     1                                    N
     │                                    │
  [ ORDER ] 1 ─── N [ ORDER_ITEM ] ───────┘
                          │
                          N
                          │
                   [ PRODUCT_SIZE ]
                          │
                          N
                          │
                    [ PRODUCT ] N ─── 1 [ CATEGORY ]
```

---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome! Feel free to check the [issues page](https://github.com/sakthivelTJ/ShopSphere/issues).

---

<div align="center">

  Made with ❤️ by [Sakthivel](https://github.com/sakthivelTJ)

</div>
