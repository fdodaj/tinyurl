# TinyURL

## Overview

The TinyURL project is a URL shortening service that allows users to generate short URLs for long ones. It includes features such as URL expiration, click tracking, and user authentication. The project is implemented using Java, Spring Boot, and PostgreSQL.

---

## Features

1. **URL Shortening**
   - Generate short URLs for long ones.
   - Prevent duplicate records for existing long URLs.

2. **URL Management**
   - Retrieve the long URL from a short URL.
   - Renew the expiration time of URLs.
   - Automatic deletion of expired URLs.

3. **Expiration**
   - Each URL expires after 10 minutes by default (configurable).

4. **Click Tracking**
   - Track the number of clicks for each shortened URL.

5. **Authentication**
   - User registration and login.
   - JWT authentication for API requests.

6. **Pagination**
   - View a paginated list of all shortened URLs.

---

## Technologies Used

- **Java 21**
- **Spring Boot**
- **PostgreSQL**
- **Maven**
- **Swagger**
- **JUnit 5**
- **JaCoCo**

---

## Setup and Installation

### Prerequisites

- Java 21
- Maven
- PostgreSQL
- An IDE like IntelliJ IDEA

### Steps

1. **Clone the Repository**
   ```bash
   git clone https://github.com/fdodaj/tinyurl
   cd TinyURL
   ```

2. **Set Up the Database**
   - Create a PostgreSQL database.
   - Update the `application.properties` file with your database credentials:
     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5432/tinyurl
     spring.datasource.username=<db-username>
     spring.datasource.password=<db-password>
     ```

3. **Build and Run the Application**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Access the Application**
   - Swagger UI: `http://localhost:8080/swagger-ui.html`

---

## API Endpoints

### Authentication

1. **Register**
   - `POST /auth/register`
   - Request Body:
     ```json
     {
       "username": "exampleUser",
       "password": "password123"
     }
     ```

2. **Login**
   - `POST /auth/login`
   - Request Body:
     ```json
     {
       "username": "exampleUser",
       "password": "password123"
     }
     ```

### URL Management

1. **Shorten URL**
   - `POST /api/urls/shorten`
   - Request Body:
     ```json
     {
       "longUrl": "https://example.com"
     }
     ```

2. **Retrieve Long URL**
   - `GET /api/urls/{shortUrl}`

3. **Renew URL Expiration**
   - `PUT /api/urls/urls/renew?shortUrl={shortUrl}`

4. **List All URLs**
   - `GET /api/urls?page=0&size=10`

---

## Testing

1. **Run Tests**
   ```bash
   mvn test
   ```

2. **Code Coverage**
   - The project uses JaCoCo for test coverage.
   - Generate the coverage report:
     ```bash
     mvn jacoco:report
     ```
   - The report will be available at `target/site/jacoco/index.html`.

---

## Configuration

- **Expiration Time:**
  - Set in `application.properties`:
    ```properties
    expiration-time=600
    ```

---
 **Florian Dodaj**.

