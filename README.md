\# 🛒 FNB Fresh Store — Event-Driven Microservices



A fully functional online ordering system built with modern microservices architecture.

Users can browse fresh produce, place orders, and track the full fulfilment pipeline

from payment through to delivery — all powered by event-driven communication via Apache Kafka

and workflow orchestration via Camunda BPMN.



\---



\## 📌 Project Overview



This project was built as part of the FNB Software Development Bootcamp.

The goal was to simulate a real-world distributed order processing system using

industry-standard tools and patterns.



\---



\## 🏗️ Architecture

```

Browser (JSP Frontend)

&#x20;       ↓

&#x20;  API Gateway (port 8080)

&#x20;       ↓

┌───────────────────────────────────────────┐

│            Microservices                  │

│                                           │

│  UserManagementService  (port 8081)       │

│  LoginService           (port 8082)       │

│  OrderService           (port 8083)       │

│  PaymentService         (port 8084)       │

│  InventoryService       (port 8085)       │

│  DeliveryService        (port 8086)       │

│  WorkflowService        (port 8087)       │

└───────────────────────────────────────────┘

&#x20;       ↓

&#x20;  Apache Kafka (Event Streaming)

&#x20;       ↓

&#x20;  PostgreSQL (Data Persistence)

&#x20;       ↓

&#x20;  Camunda BPMN (Workflow Orchestration)

```



\---



\## ⚡ Event Flow

```

User places order

&#x20;     ↓

OrderService → \[order-created] → Kafka

&#x20;     ↓

PaymentService consumes → randomly APPROVES or DECLINES

&#x20;     ↓

PaymentService → \[payment-result] → Kafka

&#x20;     ↓

InventoryService consumes → deducts stock if APPROVED

&#x20;     ↓

InventoryService → \[inventory-updated] → Kafka

&#x20;     ↓

DeliveryService consumes → confirms delivery if DEDUCTED

&#x20;     ↓

DeliveryService → \[delivery-confirmed] → Kafka

&#x20;     ↓

WorkflowService (Camunda) orchestrates the entire process

```



\---



\## 🧩 Services



| Service | Port | Responsibility |

|---|---|---|

| UserManagementService | 8081 | User registration with BCrypt password hashing |

| LoginService | 8082 | Authentication and JWT token generation |

| OrderService | 8083 | Order creation and Kafka event publishing |

| PaymentService | 8084 | Payment simulation (random approve/decline) |

| InventoryService | 8085 | Stock management and deduction |

| DeliveryService | 8086 | Delivery confirmation |

| WorkflowService | 8087 | Camunda BPMN workflow orchestration |

| ApiGateway | 8080 | Single entry point routing all requests |

| Frontend | 8090 | JSP web interface for end users |



\---



\## 🛠️ Tech Stack



| Category | Technology |

|---|---|

| Language | Java 21 |

| Framework | Spring Boot 3.x / 4.x |

| Messaging | Apache Kafka 4.2 |

| Workflow | Camunda BPM 7.21 |

| Database | PostgreSQL |

| Security | Spring Security + JWT (JJWT 0.12.3) |

| API Gateway | Spring Cloud Gateway |

| Frontend | JSP + JSTL |

| Build Tool | Maven |

| API Docs | SpringDoc OpenAPI (Swagger UI) |



\---



\## 📨 Kafka Topics



| Topic | Publisher | Consumer |

|---|---|---|

| `order-created` | OrderService | PaymentService, WorkflowService |

| `payment-result` | PaymentService | InventoryService |

| `inventory-updated` | InventoryService | DeliveryService |

| `delivery-confirmed` | DeliveryService | — |



\---



\## 👤 User Roles



| Role | Permissions |

|---|---|

| USER | Register, Login, Browse products, Place orders |

| ADMIN | All USER permissions + Manage inventory |



\---



\## 🗄️ Database Schema



\*\*users\*\* — userId, username, email, password, role



\*\*orders\*\* — orderId, customerId, productName, quantity, totalPrice, status, createdAt



\*\*payments\*\* — paymentId, orderId, customerId, amount, status, createdAt



\*\*inventory\*\* — productId, productName, quantity, price



\*\*deliveries\*\* — deliveryId, orderId, customerId, productName, quantity, status, createdAt



\---



\## 🚀 Running Locally



\### Prerequisites

\- Java 21

\- Maven 3.9+

\- PostgreSQL

\- Apache Kafka

\- Camunda Modeler (optional)



\### 1. Start Kafka

```bash

cd C:\\kafka

.\\bin\\windows\\kafka-server-start.bat .\\config\\kraft\\server.properties

```



\### 2. Create Database

```sql

CREATE DATABASE usermanagement\_db;

```



\### 3. Start Services (in order)

```bash

\# 1. UserManagementService

cd UserManagementService \&\& mvn spring-boot:run



\# 2. LoginService

cd LoginService \&\& mvn spring-boot:run



\# 3. OrderService

cd OrderService \&\& mvn spring-boot:run



\# 4. PaymentService

cd PaymentService \&\& mvn spring-boot:run



\# 5. InventoryService

cd InventoryService \&\& mvn spring-boot:run



\# 6. DeliveryService

cd DeliveryService \&\& mvn spring-boot:run



\# 7. WorkflowService (Camunda)

cd WorkflowService \&\& mvn spring-boot:run



\# 8. ApiGateway

cd ApiGateway \&\& mvn spring-boot:run



\# 9. Frontend

cd Frontend \&\& mvn spring-boot:run

```



\### 4. Access the Application

| URL | Description |

|---|---|

| http://localhost:8090 | Frontend (JSP) |

| http://localhost:8080 | API Gateway |

| http://localhost:8087/camunda | Camunda Cockpit (admin/admin) |

| http://localhost:808X/swagger-ui/index.html | Swagger UI per service |



\---



\## 📦 Seed Inventory (after first run)



POST to `http://localhost:8085/api/inventory` for each product:

```json

{ "productName": "Apples",  "quantity": 100, "price": 24.99 }

{ "productName": "Oranges", "quantity": 100, "price": 15.99 }

{ "productName": "Bananas", "quantity": 100, "price": 12.99 }

{ "productName": "Grapes",  "quantity": 100, "price": 35.99 }

```



\---



\## 🎓 Learning Objectives



This project demonstrates:



\- Microservices architecture and service independence

\- Event-driven communication with Apache Kafka

\- JWT-based authentication and authorization

\- BPMN workflow orchestration with Camunda

\- API Gateway pattern for centralized routing

\- Role-based access control (USER / ADMIN)

\- PostgreSQL persistence with Spring Data JPA

\- JSP frontend integration with Spring MVC

\- Docker containerization (coming soon)

\- Kubernetes deployment (coming soon)



\---



\## 👨‍💻 Author



Built by \*\*Mustafa\*\* as part of the FNB Software Development Bootcamp 2026.

