# 📚 Library Management System - Backend

A backend REST API for managing books, genres, users, subscriptions, and payments.

The application is built using **Java 21** and **Spring Boot** and follows a layered architecture using DTOs, MapStruct, Spring Data JPA, Bean Validation, Spring Security, JWT authentication, role-based authorization, subscription management, and Razorpay payment integration.

---

## 📌 Project Overview

The Library Management System Backend provides REST APIs for managing a digital library and its users.

### Main Features

- 📚 Book management
- 🏷️ Genre management
- 👤 User management
- 🔐 JWT authentication
- 🛡️ Role-based authorization
- 📋 Subscription plan management
- 🔄 Subscription management
- 💳 Payment initiation
- ✅ Payment verification
- 💰 Razorpay payment integration
- 📊 Payment status tracking
- 📄 Pagination and sorting
- 🧩 DTO-based API design
- 🗺️ MapStruct entity/DTO mapping
- ✅ Bean Validation
- ⚠️ Custom exception handling
- 🌐 CORS configuration

---

# 🛠️ Technology Stack

| Technology | Purpose |
|---|---|
| Java 21 | Programming language |
| Spring Boot | Backend framework |
| Spring Web | REST API development |
| Spring Data JPA | Database access |
| Hibernate | ORM / persistence |
| Spring Security | Authentication and authorization |
| JWT | Stateless authentication |
| BCrypt | Password hashing |
| Jakarta Bean Validation | Request validation |
| MapStruct | Entity ↔ DTO mapping |
| Lombok | Boilerplate reduction |
| Maven | Build and dependency management |
| H2 / Relational Database | Data persistence |
| Razorpay | Payment gateway |

---

# 🏗️ Architecture

The application follows a layered architecture.

```text
Client
   |
   v
Controller
   |
   v
DTO + Validation
   |
   v
Service
   |
   +------> Mapper
   |
   v
Repository
   |
   v
Database