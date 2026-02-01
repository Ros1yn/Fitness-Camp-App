# Fitness Camp Registration System

A web application for managing participant registration for fitness camps, built with Java Spring Boot, Thymeleaf, and Gradle.

## Features

- **Public Pages**
  - Browse available fitness camps
  - View camp details
  - Register for camps

- **Admin Dashboard** (Protected with Spring Security)
  - Dashboard with statistics overview
  - Full CRUD operations for camps
  - Full CRUD operations for participants
  - Search and filter participants
  - Update registration status (Pending, Confirmed, Cancelled, Waitlisted)

## Tech Stack

- **Backend**: Java 17, Spring Boot 3.2.1
- **Frontend**: Thymeleaf, HTML5, CSS3
- **Security**: Spring Security with BCrypt password encoding
- **Database**: MySQL (configurable, H2 for development)
- **Build Tool**: Gradle

## Prerequisites

- Java 17 or higher
- Gradle 8.x (or use the Gradle wrapper)
- MySQL 8.x (optional, H2 used by default for development)

## Getting Started

### 1. Clone the repository

```bash
git clone <repository-url>
cd fitness-camp-registration
```

### 2. Configure the database

The application uses H2 in-memory database by default. To use MySQL:

1. Create a MySQL database:
```sql
CREATE DATABASE fitness_camp_db;
```

2. Update `src/main/resources/application.properties`:
```properties
# Comment out H2 configuration and uncomment MySQL configuration
spring.datasource.url=jdbc:mysql://localhost:3306/fitness_camp_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

### 3. Build and run

```bash
# Using Gradle wrapper
./gradlew bootRun

# Or using installed Gradle
gradle bootRun
```

The application will start at `http://localhost:8080`

### 4. Access the application

- **Home Page**: http://localhost:8080
- **Browse Camps**: http://localhost:8080/camps
- **Register**: http://localhost:8080/register
- **Admin Login**: http://localhost:8080/login
- **Admin Dashboard**: http://localhost:8080/admin/dashboard (requires login)

### Default Admin Credentials

- **Username**: `admin`
- **Password**: `admin123`

## Project Structure

```
src/main/java/com/fitnesscamp/
├── FitnessCampApplication.java    # Main application class
├── config/
│   ├── SecurityConfig.java        # Spring Security configuration
│   └── DataInitializer.java       # Sample data initialization
├── controller/
│   ├── HomeController.java        # Home and login pages
│   ├── RegistrationController.java # Public registration
│   └── AdminController.java       # Admin dashboard and CRUD
├── entity/
│   ├── Camp.java                  # Camp entity
│   ├── Participant.java           # Participant entity
│   └── User.java                  # Admin user entity
├── repository/
│   ├── CampRepository.java
│   ├── ParticipantRepository.java
│   └── UserRepository.java
└── service/
    ├── CampService.java
    ├── ParticipantService.java
    └── CustomUserDetailsService.java

src/main/resources/
├── application.properties         # Application configuration
├── static/css/style.css          # Styling
└── templates/                     # Thymeleaf templates
    ├── fragments/layout.html     # Shared components
    ├── home.html
    ├── login.html
    ├── camps/
    ├── registration/
    └── admin/
```

## API Endpoints

### Public Endpoints
- `GET /` - Home page
- `GET /camps` - List all camps
- `GET /camps/view/{id}` - View camp details
- `GET /register` - Registration form
- `POST /register` - Submit registration
- `GET /login` - Admin login page

### Admin Endpoints (Protected)
- `GET /admin/dashboard` - Admin dashboard
- `GET /admin/camps` - List all camps
- `GET /admin/camps/new` - Create camp form
- `GET /admin/camps/edit/{id}` - Edit camp form
- `POST /admin/camps/save` - Save camp
- `POST /admin/camps/delete/{id}` - Delete camp
- `GET /admin/camps/{id}/participants` - Camp participants
- `GET /admin/participants` - List all participants
- `GET /admin/participants/new` - Add participant form
- `GET /admin/participants/edit/{id}` - Edit participant form
- `POST /admin/participants/save` - Save participant
- `POST /admin/participants/delete/{id}` - Delete participant
- `POST /admin/participants/{id}/status` - Update status

## License

MIT License
