# Online Shopping Mall System

A full-stack online shopping mall system with Spring Boot backend and Vue.js frontend.

## Features

- **Product Catalog**: Browse products with search, filtering, and pagination
- **Shopping Cart**: Add, update, and remove items from cart
- **Order Management**: Create orders, view order history, track status
- **User Authentication**: JWT-based authentication with role-based access control
- **Admin Dashboard**: Manage products, orders, and users
- **Responsive Design**: Works on desktop, tablet, and mobile devices

## Architecture

This project consists of two main containers:

1. **Spring Boot Backend** (`springboot_backend/`): REST API, business logic, MySQL, Redis, JWT
2. **Vue.js Frontend** (`vue_frontend/`): SPA with customer and admin interfaces

## Prerequisites

- **Java 17** or higher
- **Node.js 18** or higher
- **MySQL 8.0** or higher
- **Redis** (for caching)
- **Git**

## Quick Start

### 1. Database Setup

The MySQL database `shopping_mall` should already be created. Verify connection:

```bash
mysql -uroot shopping_mall -e "SHOW TABLES;"
```

### 2. Start Redis

Redis is required for backend caching:

```bash
# Check if Redis is running
redis-cli ping

# If not running, start Redis
redis-server --daemonize yes
```

### 3. Backend Setup

```bash
cd springboot_backend

# Copy environment file (already configured)
# The .env file contains database, Redis, and JWT configuration

# Build the application
./gradlew clean build -x test

# Run the backend (starts on port 3001)
./gradlew bootRun
```

The backend will be available at: `https://vscode-internal-28250-beta.beta01.cloud.kavia.ai:3001`

**Backend API Documentation:**
- Swagger UI: https://vscode-internal-28250-beta.beta01.cloud.kavia.ai:3001/swagger-ui.html
- OpenAPI JSON: https://vscode-internal-28250-beta.beta01.cloud.kavia.ai:3001/openapi.json

### 4. Frontend Setup

```bash
cd vue_frontend

# Install dependencies (if not already installed)
npm install

# Run the frontend (starts on port 3000)
npm run dev
```

The frontend will be available at: `https://vscode-internal-28250-beta.beta01.cloud.kavia.ai:3000`

## Default Credentials

The system comes with pre-populated sample data:

**Admin User:**
- Username: `admin`
- Password: `admin123`
- Roles: ADMIN, USER

**Regular User:**
- Username: `john`
- Password: `password123`
- Roles: USER

## Environment Configuration

### Backend Environment Variables

The backend uses `.env` file for configuration. Key variables:

- `SPRING_DATASOURCE_URL`: MySQL connection URL
- `SPRING_DATASOURCE_USERNAME`: MySQL username
- `SPRING_DATASOURCE_PASSWORD`: MySQL password
- `SPRING_DATA_REDIS_HOST`: Redis host (default: localhost)
- `SPRING_DATA_REDIS_PORT`: Redis port (default: 6379)
- `JWT_SECRET`: Secret key for JWT token generation
- `PORT`: Backend server port (default: 3001)

See `springboot_backend/.env.example` for full configuration.

### Frontend Environment Variables

The frontend uses `.env` file for configuration. Key variables:

- `VITE_API_BASE_URL`: Backend API base URL (e.g., `http://localhost:3001/api`)
- `VITE_BACKEND_URL`: Backend URL without /api path
- `VITE_PORT`: Frontend server port (default: 3000)

See `vue_frontend/.env.example` for full configuration.

## Integration Points

### Frontend → Backend

The Vue frontend calls the Spring Boot backend REST API:

- **Base URL**: Configured via `VITE_API_BASE_URL` environment variable
- **Authentication**: JWT token in `Authorization: Bearer <token>` header
- **CORS**: Backend is configured to accept requests from frontend origin

### Backend → Database

- **MySQL**: Stores all application data (users, products, orders, carts)
- **Connection**: Configured via `SPRING_DATASOURCE_*` environment variables
- **ORM**: Spring Data JPA with Hibernate

### Backend → Redis

- **Caching**: Product catalog and frequently accessed data
- **Connection**: Configured via `SPRING_DATA_REDIS_*` environment variables
- **Optional**: Application will work without Redis but with reduced performance

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login and get JWT token

### Products
- `GET /api/products` - Get all products (paginated)
- `GET /api/products/{id}` - Get product by ID
- `GET /api/products/search?name={name}` - Search products
- `POST /api/products` - Create product (Admin only)
- `PUT /api/products/{id}` - Update product (Admin only)
- `DELETE /api/products/{id}` - Delete product (Admin only)

### Shopping Cart
- `GET /api/carts` - Get current user's cart
- `POST /api/carts/items` - Add item to cart
- `PUT /api/carts/items/{itemId}` - Update cart item quantity
- `DELETE /api/carts/items/{itemId}` - Remove item from cart
- `DELETE /api/carts` - Clear cart

### Orders
- `GET /api/orders` - Get current user's orders
- `GET /api/orders/{id}` - Get order by ID
- `POST /api/orders` - Create order from cart
- `POST /api/orders/{id}/cancel` - Cancel order

### Admin
- `GET /api/admin/orders` - Get all orders (Admin only)
- `PATCH /api/admin/orders/{id}/status` - Update order status (Admin only)
- `GET /api/admin/users` - Get all users (Admin only)

### Users
- `GET /api/users/me` - Get current user profile
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user (Admin only)

## Testing the Integration

1. **Start Backend**: Ensure backend is running on port 3001
2. **Start Frontend**: Ensure frontend is running on port 3000
3. **Open Browser**: Navigate to frontend URL
4. **Login**: Use default credentials (admin/admin123 or john/password123)
5. **Test Features**:
   - Browse products
   - Add items to cart
   - Create an order
   - View order history
   - Admin: Manage products and orders

## Troubleshooting

### Backend won't start
- **MySQL not running**: Check `mysql` service status
- **Database not found**: Verify `shopping_mall` database exists
- **Port already in use**: Change `PORT` in `.env` file
- **Redis connection error**: Start Redis with `redis-server --daemonize yes`

### Frontend can't connect to backend
- **CORS error**: Verify `VITE_API_BASE_URL` in frontend `.env`
- **401 Unauthorized**: Ensure you're logged in and token is valid
- **Network error**: Check backend is running and accessible

### Database connection issues
- **Access denied**: Verify MySQL credentials in backend `.env`
- **Unknown database**: Run database initialization scripts
- **Connection refused**: Check MySQL is running on port 3306

## Development

### Backend Development

```bash
cd springboot_backend

# Run with hot reload
./gradlew bootRun --continuous

# Run tests
./gradlew test

# Build for production
./gradlew clean build
```

### Frontend Development

```bash
cd vue_frontend

# Run with hot reload
npm run dev

# Run tests
npm run test:unit

# Build for production
npm run build

# Preview production build
npm run preview
```

## Production Deployment

### Backend

1. Update `.env` with production values:
   - Change `JWT_SECRET` to a strong random string
   - Update database credentials
   - Set `SPRING_PROFILES_ACTIVE=prod`
   - Configure Redis connection

2. Build production JAR:
   ```bash
   ./gradlew clean build
   ```

3. Run the JAR:
   ```bash
   java -jar build/libs/springbootbackend-0.1.0.jar
   ```

### Frontend

1. Update `.env` with production values:
   - Set `VITE_API_BASE_URL` to production backend URL
   - Set `VITE_NODE_ENV=production`

2. Build for production:
   ```bash
   npm run build
   ```

3. Deploy the `dist/` folder to a web server or CDN

## Security Notes

⚠️ **Important for Production:**

- Change the default `JWT_SECRET` to a strong, random string
- Use strong passwords for database and Redis
- Enable HTTPS for all connections
- Configure proper CORS origins (don't use `*` in production)
- Review and adjust security configurations
- Implement rate limiting and input validation
- Keep dependencies up to date

## License

This project is part of the online shopping mall system demonstration.

## Support

For issues or questions:
- Check the API documentation at `/swagger-ui.html`
- Review container-specific README files
- Verify environment configuration
- Check application logs for errors
