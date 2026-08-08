# URL Shortener API

A RESTful URL Shortener API built with **Spring Boot**, **Spring Security**, **JWT**, **Spring Data JPA**, and **MySQL**. It allows users to register, log in, create short URLs, redirect to original URLs, and manage URLs securely.

## Features

- User Registration & Login
- JWT Authentication
- BCrypt Password Encryption
- Create Short URLs
- Redirect to Original URL
- Get All URLs
- Update URL
- Delete URL
- Click Count Tracking
- Global Exception Handling

## Technologies

- Java 17
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- Hibernate
- MySQL
- Maven

## API Endpoints

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/users/register` | Register a new user |
| POST | `/api/users/login` | Login and receive JWT |
| POST | `/api/urls` | Create a short URL |
| GET | `/api/urls` | Get all URLs |
| PUT | `/api/urls/{id}` | Update a URL |
| DELETE | `/api/urls/{id}` | Delete a URL |
| GET | `/r/{shortCode}` | Redirect to original URL |

## Setup

1. Clone the repository
2. Create a MySQL database.
3. Update `application.properties` with your database credentials.
4. Run the project using Maven or your IDE.
