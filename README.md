# Hotel Booking System

A full-stack web application for managing hotel room bookings. The system provides secure user authentication, room search and booking functionality, user profile management, and an admin interface. It is built with a robust backend architecture and a lightweight, responsive frontend.

## Features

- **User Authentication**: Secure registration and login with JWT-based authentication.
- **Role-Based Access Control**: Separate roles for Customers and Administrators.
- **Room Management**: Search hotels by city, check-in/out dates, and number of guests.
- **Booking System**: Select available rooms, confirm bookings, and manage reservations.
- **User Profile**: View and update personal information and change password.
- **Admin Capabilities**: Manage hotels, rooms, and user bookings (Admin role).
- **Database Migrations**: Liquibase for version-controlled database schema.
- **API Documentation**: Interactive Swagger UI for exploring and testing endpoints.
- **Docker Support**: Easily deploy using provided Dockerfile and docker-compose.

## Tech Stack

- **Backend**: Java 21, Spring Boot 4.0.6
- **Security**: Spring Security, JWT (jjwt 0.12.6)
- **Database**: PostgreSQL 16, Spring Data JPA, Liquibase
- **Caching**: Redis 7
- **Frontend**: HTML, CSS, JavaScript (static pages)
- **API Documentation**: SpringDoc OpenAPI (swagger-ui)
- **Build Tool**: Maven
- **Containerization**: Docker, Docker Compose

## Project Structure

Hotel-Booking-System/
├── .mvn/wrapper/ # Maven wrapper files
├── src/
│ ├── main/
│ │ ├── java/com/example/hotelbookingsystem/
│ │ │ ├── config/ # App config (e.g., JWT, security)
│ │ │ ├── controller/ # REST API endpoints
│ │ │ ├── entity/ # JPA entities (User, Hotel, Room, Booking)
│ │ │ ├── enums/ # Enumerated types (Role, RoomType, etc.)
│ │ │ ├── exception/ # Custom exceptions & global handler
│ │ │ ├── mapper/ # Map entity ↔ DTO
│ │ │ ├── payload/ # Request/response DTOs
│ │ │ ├── repository/ # Spring Data JPA repositories
│ │ │ ├── security/ # JWT auth filters & config
│ │ │ ├── service/ # Business logic
│ │ │ └── HotelBookingSystemApplication.java
│ │ └── resources/
│ │ ├── static/ # Static HTML/CSS/JS frontend
│ │ ├── db/changelog/ # Liquibase migrations
│ │ └── application.yaml # App config (DB, JWT, etc.)
├── Dockerfile # Multi-stage Docker build
├── docker-compose.yml # Run app with PostgreSQL & Redis
├── pom.xml # Maven dependencies
└── README.md

## Getting Started

### Prerequisites

- Java 21 (JDK)
- Maven 3.8+
- Docker & Docker Compose (optional)

### Local Setup (without Docker)

1. **Clone the repository**
   ```bash
   git clone https://github.com/mirsaidmirjalilov/Hotel-Booking-System.git
   cd Hotel-Booking-System

    Configure PostgreSQL & Redis

        Create a PostgreSQL database and update src/main/resources/application.yaml with your credentials.

        Ensure Redis is running locally (default port 6379).

    Build and run the application
    bash

    ./mvnw clean package
    java -jar target/*.jar

    Access the application

        Frontend: http://localhost:8080

        Swagger UI: http://localhost:8080/swagger-ui.html

Docker Setup (Recommended)

    Ensure Docker & Docker Compose are installed.

    Build and start services
    bash

    docker-compose up --build

    This will start:

        PostgreSQL on port 5432

        Redis on port 6379

        The backend on port 8080

    Stop the containers
    bash

    docker-compose down

API Documentation

Once the application is running, you can explore the REST API via Swagger UI:
text

http://localhost:8080/swagger-ui.html

Key endpoints include:
Method	Endpoint	Description	Access
POST	/api/auth/register	Register new user	Public
POST	/api/auth/login	Login & get JWT token	Public
GET	/api/hotels/search	Search hotels	Authenticated
POST	/api/bookings	Create a booking	Authenticated
GET	/api/users/profile	Get user profile	Authenticated
PUT	/api/users/profile	Update user profile	Authenticated
Admin	/api/admin/hotels	Manage hotels	ADMIN
Admin	/api/admin/rooms	Manage rooms	ADMIN

For complete details, refer to the Swagger documentation.
Frontend Overview

The frontend consists of simple, responsive static page located in src/main/resources/static/:

    index.html – Search hotels, view available rooms, and complete booking.
    
Variable	Description	Default
SPRING_DATASOURCE_URL	PostgreSQL JDBC URL	jdbc:postgresql://localhost:5432/postgres
SPRING_DATASOURCE_USERNAME	DB username	postgres
SPRING_DATASOURCE_PASSWORD	DB password	123
SPRING_DATA_REDIS_HOST	Redis host	localhost
SPRING_DATA_REDIS_PORT	Redis port	6379
JWT_SECRET	Secret key for signing JWT	(configure in yaml)
