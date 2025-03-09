# Planner Backend

This is the backend service for the Planner application, responsible for managing tasks, users, and authentication.

## Features

- User authentication with JWT (bcrypt for password hashing)
- Task management (CRUD operations)
- User roles and access control
- RESTful API built with Spring Boot
- PostgreSQL database integration
- API documentation with Swagger

## Tech Stack

- **Java** (Spring Boot)
- **Spring Security** (Authentication & Authorization)
- **PostgreSQL** (Database)
- **JWT** (Token-based authentication)
- **JPA/Hibernate** (ORM for database interaction)
- **Swagger** (API documentation)


## API Endpoints

### Authentication

| Method | Endpoint         | Description       |
| ------ | ---------------- | ----------------- |
| POST   | `/auth/register` | Register a user   |
| POST   | `/auth/login`    | Authenticate user |

### Tasks

| Method | Endpoint        | Description       |
| ------ | --------------- | ----------------- |
| GET    | `/tasks`        | Get all tasks     |
| POST   | `/tasks/create` | Create a new task |
| PUT    | `/tasks/{id}`   | Update a task     |
| DELETE | `/tasks/{id}`   | Delete a task     |

## Controllers Overview

- **UserController**: Manages user authentication, registration, and profile handling.
- **TaskController**: Handles task creation, retrieval, updating, and deletion.
- **AdminController**: Provides administrative functionalities, such as managing users and permissions.

## API Documentation

Swagger documentation is available at:
```
http://localhost:3000/api/docs
```
or 
```
http://localhost:3000/api/swagger-ui/index.html
```


Use this UI to explore and test the available endpoints interactively.

