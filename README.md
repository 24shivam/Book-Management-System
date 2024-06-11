# Book Management System Documentation

## Overview

The Book Management System is a sophisticated Spring Boot application developed to streamline library operations efficiently. With a rich set of features including user authentication, book management, student registration, transaction handling, and Redis integration for caching, this system provides a robust platform for managing books and related activities in libraries, bookstores, and educational institutions.

## Architecture

The system adopts a layered architecture to ensure modularity, maintainability, and extensibility. It comprises the following layers:

- **Controllers:** Handle HTTP requests, route them to appropriate services, and return HTTP responses.
- **Services:** Implement business logic, orchestrate interactions between controllers and repositories, and ensure data consistency.
- **Repositories:** Manage data access operations such as CRUD operations and querying the database.
- **Models:** Define entities representing various domain objects such as books, authors, students, and transactions.


## Table of Contents

1. [Project Structure](#project-structure)
2. [Configuration](#configuration)
    - [Redis Configuration](#redis-configuration)
    - [Security Configuration](#security-configuration)
    - [Swagger Configuration](#swagger-configuration)
3. [Controllers](#controllers)
    - [AdminController](#admincontroller)
    - [BookController](#bookcontroller)
    - [StudentController](#studentcontroller)
    - [TxnController](#txncontroller)
4. [Exception Handling](#exception-handling)
5. [Services](#services)
    - [AdminService](#adminservice)
    - [StudentService](#studentservice)
    - [TxnService](#txnservice)
6. [Repositories](#repositories)
    - [AuthorRepo](#authorrepo)
    - [BookRepo](#bookrepo)
    - [BookRedisCacheRepo](#bookrediscacherepo)
    - [StudentRepo](#studentrepo)
    - [StudentRedisRepo](#studentrediscacherepo)
    - [TxnRepo](#txnrepo)
7. [Models](#models)
8. [Request DTOs](#request-dtos)

## Configuration

### Redis Configuration
The Redis configuration sets up the connection to a Redis server, utilizing the LettuceConnectionFactory and RedisTemplate. It specifies the host and port of the Redis server and defines the serializers for keys and values, allowing objects to be stored and retrieved from Redis.

### Security Configuration
The security configuration defines the authentication and authorization mechanisms for the application. It uses DaoAuthenticationProvider with StudentService for user details and NoOpPasswordEncoder for password encoding. The SecurityFilterChain configures the access rules for different endpoints, specifying which roles can access certain URLs and disabling CSRF protection for simplicity.

### Swagger Configuration
Swagger is configured to provide API documentation and testing interface. The OpenAPI bean sets up the API title, version, and description. The GroupedOpenApi bean groups endpoints related to books, students, transactions, and admin functionalities for easier navigation in the Swagger UI.


## Controllers

### AdminController

**Endpoints:**
- `/admin/create`: Creates a new admin user.

**Functionality:**
- Enables secure creation of admin users with appropriate validation.
- Admin users have privileged access to manage system configurations and user permissions.

### BookController

**Endpoints:**
- `/book/create`: Adds a new book to the system.
- `/book/filterBy`: Filters books based on specified criteria.
- `/book/delete/{id}`: Deletes a book by its ID.

**Functionality:**
- Supports CRUD operations for books, facilitating efficient book inventory management.
- Provides flexible filtering options to retrieve books based on attributes like author, price, and type.

### StudentController

**Endpoints:**
- `/student/create`: Registers a new student in the system.
- `/student/filterBy`: Filters students based on specified criteria.

**Functionality:**
- Facilitates student registration and management, ensuring uniqueness of student details.
- Offers filtering capabilities to retrieve student information based on various parameters.

### TxnController

**Endpoints:**
- `/txn/create`: Records a new book borrowing transaction.
- `/txn/return`: Processes the return of a borrowed book.

**Functionality:**
- Manages book borrowing and returning transactions, maintaining transactional integrity.
- Calculates fines for late returns and updates transaction statuses accordingly.

## Exception Handling

The `ControllerExceptionHandler` class ensures graceful handling of exceptions, providing informative error messages and appropriate HTTP status codes. Custom exceptions like `TxnException` and `DataNotFound` are handled to ensure smooth operation of the system.

## Services

### AdminService

**Functionality:**
- Handles administrative tasks such as creating admin users and ensuring data consistency.

### StudentService

**Functionality:**
- Manages student-related operations including registration, filtering, and caching.

### TxnService

**Functionality:**
- Orchestrates book transaction processes, ensuring accurate recording and updating of transactional data.

## Repositories

### AuthorRepo

**Functionality:**
- Manages data access for authors, supporting various query methods for retrieving author information.

### BookRepo

**Functionality:**
- Handles data access for books, enabling CRUD operations and advanced filtering capabilities.

### BookRedisCacheRepo

**Functionality:**
- Integrates Redis caching for efficient storage and retrieval of book data, enhancing system performance.

### StudentRepo

**Functionality:**
- Manages data access for students, supporting filtering and retrieval of student information.

### StudentRedisRepo

**Functionality:**
- Implements Redis caching for student data, optimizing data access and retrieval operations.

### TxnRepo

**Functionality:**
- Manages data access for transactions, facilitating retrieval of transaction information by ID.

## Models

### Author
Represents an author in the system. It includes fields like ID, name, email, creation date, and update date. Authors can have multiple books associated with them.

### Book
Represents a book in the system. It includes fields like ID, name, book number, cost, type, and associations with students and authors. Books can have multiple transactions associated with them.

### BookType
An enum representing different types of books (e.g., educational, motivational, sports).

### FilterType
An enum representing different criteria for filtering books and students (e.g., author name, book number, cost, book type, student name, student type, student phone, student address, student email).

### Operators
An enum representing different operators for querying data (e.g., equals, less than, greater than, less than equals, in).

### Student
Represents a student in the system. It includes fields like ID, name, email, phone number, type, address, password, authority, creation date, and update date. Students can have multiple books and transactions associated with them and implement UserDetails for Spring Security.

### StudentType
An enum representing different types of students (e.g., active, inactive, blocked).

### Txn
Represents a transaction in the system. It includes fields like ID, transaction ID, associations with students and books, creation date, update date, paid amount, and transaction status.

### TxnStatus
An enum representing different statuses of a transaction (e.g., issued, returned, fined).

## Conclusion

The Book Management System offers a comprehensive solution for managing library operations effectively. With its modular architecture, robust functionalities, and integration with Redis for caching, the system provides a reliable platform for libraries and educational institutions to streamline their book management processes. By leveraging Spring Boot's capabilities and adhering to best practices in software development, this system ensures scalability, performance, and ease of maintenance, making it a valuable asset for any organization dealing with book management tasks.
