# Spring Boot Backend - Online Shopping Mall System

## Overview
This is a complete REST API backend for an online shopping mall system built with Spring Boot 3.4.8, MySQL, Redis, and JWT authentication.

## Features
- **User Authentication & Authorization**: JWT-based authentication with role-based access control (USER, ADMIN)
- **Product Management**: Full CRUD operations for products with pagination and search
- **Shopping Cart**: Add, update, remove items from cart
- **Order Management**: Create orders from cart, view order history, cancel orders
- **Admin Functions**: Manage products, users, and orders
- **Security**: Spring Security with JWT tokens, password encryption
- **Validation**: Input validation with Jakarta Validation
- **Exception Handling**: Global exception handler for consistent error responses
- **API Documentation**: Swagger/OpenAPI integration
- **Caching**: Redis caching support
- **CORS**: Configured for Vue.js frontend

## Technology Stack
- Java 17
- Spring Boot 3.4.8
- Spring Data JPA
- Spring Security
- MySQL Database
- Redis Cache
- JWT (JSON Web Tokens)
- Lombok
- Gradle
- Swagger/OpenAPI 3

## Prerequisites
- JDK 17 or higher
- MySQL 8.0 or higher
- Redis Server
- Gradle (or use included Gradle wrapper)

## Configuration

### Database Setup
The application connects to a MySQL database named `shopping_mall`. Connection details are in `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/shopping_mall
spring.datasource.username=root
spring.datasource.password=
```

### Environment Variables
Create a `.env` file based on `.env.example` and configure:

- `JWT_SECRET`: Secret key for JWT token generation (change in production!)
- `SPRING_DATASOURCE_URL`: MySQL connection URL
- `SPRING_DATASOURCE_USERNAME`: MySQL username
- `SPRING_DATASOURCE_PASSWORD`: MySQL password
- `SPRING_DATA_REDIS_HOST`: Redis host (default: localhost)
- `SPRING_DATA_REDIS_PORT`: Redis port (default: 6379)

## Building the Application

```bash
# Build without tests
./gradlew build -x test

# Build with tests
./gradlew build

# Clean and build
./gradlew clean build
```

## Running the Application

```bash
# Run with Gradle
./gradlew bootRun

# Or run the JAR file
java -jar build/libs/springbootbackend-0.1.0.jar
```

The application will start on port 3001 (or the port specified in your configuration).

## API Documentation

Once the application is running, access the API documentation at:
- Swagger UI: `http://localhost:3001/swagger-ui.html`
- OpenAPI JSON: `http://localhost:3001/openapi.json`
- Quick access: `http://localhost:3001/docs`

## Sample Data

The application includes a DataInitializer that creates sample data on first startup:

**Admin User:**
- Username: `admin`
- Password: `admin123`
- Roles: ADMIN, USER

**Regular User:**
- Username: `john`
- Password: `password123`
- Roles: USER

**Sample Products:** 10 products across various categories (Electronics, Home & Kitchen, Sports & Outdoors, Bags & Luggage, Accessories)

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login and get JWT token

### Products
- `GET /api/products` - Get all products (paginated)
- `GET /api/products/{id}` - Get product by ID
- `GET /api/products/available` - Get available products
- `GET /api/products/search?name={name}` - Search products
- `GET /api/products/category/{category}` - Get products by category
- `POST /api/products` - Create product (Admin only)
- `PUT /api/products/{id}` - Update product (Admin only)
- `DELETE /api/products/{id}` - Delete product (Admin only)

### Shopping Cart
- `GET /api/carts` - Get current user's cart
- `POST /api/carts/items` - Add item to cart
- `PUT /api/carts/items/{itemId}?quantity={qty}` - Update cart item quantity
- `DELETE /api/carts/items/{itemId}` - Remove item from cart
- `DELETE /api/carts` - Clear cart

### Orders
- `GET /api/orders` - Get current user's orders
- `GET /api/orders/{id}` - Get order by ID
- `POST /api/orders` - Create order from cart
- `POST /api/orders/{id}/cancel` - Cancel order

### Users
- `GET /api/users/me` - Get current user profile
- `GET /api/users/{id}` - Get user by ID (Admin only)
- `GET /api/users` - Get all users (Admin only, paginated)
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user (Admin only)

### Admin
- `GET /api/admin/orders` - Get all orders (paginated)
- `PATCH /api/admin/orders/{id}/status?status={status}` - Update order status
- `GET /api/admin/users` - Get all users (paginated)

### Application
- `GET /` - Welcome message
- `GET /health` - Health check
- `GET /api/info` - Application info
- `GET /docs` - Redirect to Swagger UI

## Authentication

All authenticated endpoints require a JWT token in the Authorization header:

```
Authorization: Bearer <your-jwt-token>
```

To get a token:
1. Register a new user or use sample credentials
2. Login via `/api/auth/login`
3. Use the returned token in subsequent requests

## Order Status Flow

Orders go through the following statuses:
- `PENDING` - Initial status after order creation
- `CONFIRMED` - Order confirmed
- `PROCESSING` - Order is being processed
- `SHIPPED` - Order has been shipped
- `DELIVERED` - Order delivered to customer
- `CANCELLED` - Order cancelled

## Security Notes

⚠️ **Important for Production:**
- Change the default `JWT_SECRET` in production
- Use strong passwords for database
- Enable HTTPS
- Configure proper CORS origins (don't use `*` in production)
- Review and adjust security configurations

## Development

### Project Structure
```
src/main/java/com/example/springbootbackend/
├── config/          # Configuration classes
├── controller/      # REST controllers
├── dto/            # Data Transfer Objects
├── entity/         # JPA entities
├── exception/      # Exception handlers
├── mapper/         # Entity-DTO mappers
├── repository/     # JPA repositories
├── security/       # Security components (JWT, UserDetails)
└── service/        # Business logic services
```

### Code Quality
The project uses:
- Lombok for reducing boilerplate code
- Jakarta Validation for input validation
- Spring Security for authentication/authorization
- Global exception handler for consistent error responses
- Comprehensive API documentation with Swagger

## Troubleshooting

### Database Connection Issues
- Ensure MySQL is running
- Verify database credentials in `application.properties`
- Check if `shopping_mall` database exists

### Redis Connection Issues
- Ensure Redis server is running
- Verify Redis host and port in configuration

### Port Already in Use
- Change the port in `application.properties`: `server.port=8080`

## License
This project is part of the online shopping mall system.
