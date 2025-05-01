# UserManagement Microservices System

A microservice-based system with:

- **user-service**: Handles user registration, login, profile, and communication via RabbitMQ.
- **notification-service**: Listens to RabbitMQ and sends email notifications.
- **Redis**: Caches session/token info.
- **RabbitMQ**: Message queue for service decoupling.
- **Docker Compose**: For full local environment.
- **Swagger**: API documentation for both services.

---

## 🛠 Tech Stack

- Java 8
- Spring Boot 2.3.1
- RabbitMQ
- Redis
- Docker + Docker Compose
- Maven Multi-module
- Swagger (Springfox 2.9.2)
- GitHub Actions CI/CD
- Postman

---

## 🧪 Run Locally

```bash
# Build the project
mvn clean install

# Start all services
docker-compose up --build










[//]: # (# User Management Microservices 🚀)

[//]: # ()
[//]: # (A full-stack microservice architecture using Spring Boot, RabbitMQ, Redis, Docker, Swagger, and CI/CD.)

[//]: # ()
[//]: # (## 📦 Services)

[//]: # ()
[//]: # (- `user-service`: Handles user registration, login, profile, password, etc.)

[//]: # (- `notification-service`: Listens via RabbitMQ and sends emails.)

[//]: # ()
[//]: # (## 📌 Technologies)

[//]: # ()
[//]: # (- Spring Boot 2.3.1)

[//]: # (- Spring Security + Swagger 2.9.2)

[//]: # (- Redis &#40;sessions + cache&#41;)

[//]: # (- RabbitMQ &#40;messaging&#41;)

[//]: # (- MySQL &#40;DB&#41;)

[//]: # (- Docker + Docker Compose)

[//]: # ()
[//]: # (## 🐳 Run With Docker Compose)

[//]: # ()
[//]: # (```bash)

[//]: # (docker-compose up --build)
