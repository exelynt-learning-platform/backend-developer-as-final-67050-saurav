# Resource Booking System

A secure RESTful Resource Booking System built using Spring Boot, Java 17,
Spring Security, JWT, PostgreSQL, JPA and Hibernate.

## Technologies

- Java 17
- Spring Boot 3.2.3
- Spring Security
- JWT
- BCrypt
- Spring Data JPA
- Hibernate
- PostgreSQL
- Swagger/OpenAPI
- Maven

## Features

- JWT based authentication
- ADMIN and USER role-based authorization
- BCrypt password encryption
- Resource CRUD operations
- Reservation management
- Reservation ownership
- Reservation statuses:
  - PENDING
  - CONFIRMED
  - CANCELLED
- Reservation filtering by:
  - status
  - minimum price
  - maximum price
- Pagination
- Sorting
- Input validation
- Global exception handling
- PostgreSQL database integration
- Swagger API documentation

## Roles

### ADMIN

ADMIN can:

- Create resources
- View resources
- Update resources
- Delete resources
- View all reservations
- Create reservations
- Confirm reservations
- Cancel reservations
- Delete reservations

### USER

USER can:

- View resources
- Create reservations
- View only their own reservations
- Cancel their own reservations

USER identity is obtained from the authenticated JWT and is not accepted
from the reservation request.

## Database Configuration

Create a PostgreSQL database:

    CREATE DATABASE booking_db;

Configure the database in:

    src/main/resources/application.properties

Example:

    spring.datasource.url=jdbc:postgresql://localhost:5432/booking_db
    spring.datasource.username=postgres
    spring.datasource.password=YOUR_PASSWORD

## Running the Application

1. Install Java 17 or higher.
2. Install PostgreSQL.
3. Create the `booking_db` database.
4. Update database credentials.
5. Run the application using Maven:

    mvn spring-boot:run

Or run `BookingApplication` from Eclipse/IntelliJ.

The application runs on:

    http://localhost:8080

## Authentication

Login:

    POST /auth/login

Request:

    {
      "username": "admin",
      "password": "admin123"
    }

The response contains a JWT token.

Use the token in protected requests:

    Authorization: Bearer <JWT_TOKEN>

## Seed Users

The application creates test users for development/testing.

### ADMIN

    Username: admin
    Password: admin123
    Role: ROLE_ADMIN

### USER

    Username: user
    Password: user123
    Role: ROLE_USER

## API Endpoints

### Authentication

    POST /auth/login

### Resources

    GET    /api/resources
    GET    /api/resources/{id}
    POST   /api/resources
    PUT    /api/resources/{id}
    DELETE /api/resources/{id}

### Reservations

    POST   /api/reservations
    GET    /api/reservations
    GET    /api/reservations/{id}
    PUT    /api/reservations/{id}/cancel
    PUT    /api/reservations/{id}/confirm
    DELETE /api/reservations/{id}

## Reservation Filtering

Filter by status:

    GET /api/reservations?status=CONFIRMED

Minimum price:

    GET /api/reservations?minPrice=1000

Maximum price:

    GET /api/reservations?maxPrice=2000

Combined:

    GET /api/reservations?status=PENDING&minPrice=500&maxPrice=2000

## Pagination

Example:

    GET /api/reservations?page=0&size=10

## Sorting

Ascending:

    GET /api/reservations?sortBy=startTime&sortDir=asc

Descending:

    GET /api/reservations?sortBy=startTime&sortDir=desc

## Swagger

Swagger UI:

    http://localhost:8080/swagger-ui/index.html

OpenAPI documentation:

    http://localhost:8080/v3/api-docs

## Project Structure

    src/main/java/com/exelynt/booking

    ├── controller
    ├── service
    ├── repository
    ├── entity
    ├── dto
    ├── security
    └── exception

## Validation

The application validates:

- Required resource fields
- Positive resource price
- Required reservation resource
- Required start time
- Required end time
- End time must be after start time
- Reservations cannot be created in the past

## Error Handling

The application handles:

- 400 Bad Request
- 401 Unauthorized
- 403 Forbidden
- 404 Not Found
- 500 Internal Server Error

## Security

The application uses:

- JWT authentication
- BCrypt password hashing
- Stateless sessions
- Role-based authorization
- Protected API endpoints
- Reservation ownership validation