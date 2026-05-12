# 🚀 MicroSpring

MicroSpring is a **Spring Boot Identity Service** providing user and role management, built with clean architecture and modern backend practices.

---

## 📌 Overview

This service handles:

- 👤 User management (create, retrieve)
- 🔐 Role management
- 🧩 Clean service-based architecture
- 🐘 PostgreSQL integration
- 📖 Swagger API documentation
- 🔒 Ready for JWT authentication

---

## 🛠️ Tech Stack

- Java 21
- Spring Boot 3
- Spring Data JPA
- Spring Security
- PostgreSQL
- Docker & Docker Compose
- Swagger (OpenAPI)

---
🏗️ Architectural Philosophy
This project implements a Pragmatic Clean Architecture. A key design decision was made regarding the Domain Entities:

The "Shared Entity" Defense
In strict Clean Architecture, Domain Entities should be POJOs. However, to optimize development for this microservice, I have opted to include Jakarta Persistence (JPA) annotations directly on the Domain Entities.

Why this choice?

Reduced Boilerplate: Avoids redundant mapping between UserEntity (Infra) and User (Domain).

Jakarta as a Standard: JPA is a Java specification, not a framework-specific leak (like Hibernate-only features).

Infrastructure Isolation: While the Entity is shared, the Dependency Inversion Principle is strictly enforced. The Domain defines a UserRepository interface (Port), and the Infrastructure layer provides the JpaUserRepository (Adapter).

This ensures the Application Core remains independent of the database implementation, satisfying the core "Clean" requirement while maintaining high developer velocity.
