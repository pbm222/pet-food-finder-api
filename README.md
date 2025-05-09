# Pet Food Finder Api

## 📌 Project Overview
This backend API for Pet Food Finder applications (Portal and Admin), 
providing RESTful APIs for product, reviews, retailers, descriptions management, as well as user authentication. 

## 🛠️ Technologies Used
- Java 31
- Maven
- Spring Boot 3.x
- Spring Security
- MongoDB
- MapStruct
- Lombok
- JUnit & Mockito
- REST
- JWT

## 🧩 Application Structure
_admin_ directory contains functionality related to Pet Food Finder Admin Portal.

_portal_ directory contains functionality related to Pet Food Finder Portal.

- `controller/`: REST API endpoints
- `service/`: Business logic
- `repository/`: MongoDB data access
- `model/`: Entities and DTOs
- `mapper/`: MapStruct mappers
- `exception/`: Custom exceptions
- `utils/`: Utils services

## 📐 API Endpoints
- `GET /products`: Retrieve all products
- `POST /products`: Add a new product
- `PATCH /reviews/{id}`: Update review status
- `GET /users`: List users with pagination
- `POST /auth/login`: User authentication

## ✅ Testing
Testing in this project is done using JUnit in combination with Mockito. 
Mockito allows us to simulate behavior of dependencies and focus on unit 
testing individual components in isolation.

## 📆 Development Log
| Date       | Task              | Time Spent |
|------------|-------------------|------------|
| 2025-02-22 | Setup Mongo DB    | 4h         |
| 2025-03-01 | Project barebone  | 6h         |
| 2025-03-02 | Products CRUD     | 1h         |
| 2025-03-03 | Reviews CRUD      | 1h         |
| 2025-03-04 | Retailers CRUD    | 1h         |
| 2025-03-08 | Descriptions CRUD | 1h         |
| 2025-03-08 | Security config   | 2h         |
| 2025-03-09 | Authentication    | 3h         |
| 2025-03-10 | Users CRUD        | 1h         |
| 2025-03-12 | Test coverage     | 2h         |
| 2025-05-08 | Documentation     | 1h         |
| 2025-05-09 | Deployment        | 2h         |
| **Total**  |                   | **24h**    |

## 🖥 Final Implementation
- User authentication with JWT
- Role-based access control (`USER`, `ADMIN`)
- Product, review, user, retailer, description, statistics management APIs
- Comprehensive error handling
- Test coverage

## 📝 Reflection
It was straight forward task to develop an API in Java, as my daily work includes these technologies.
Some bits of the functionality required an additional research, which enhanced an overall 
knowledge of Security of APIs and role-base access.