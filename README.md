# Gym Management API 🏋️‍♂️

A Spring Boot REST API for managing gym users, activities, and workout recommendations.  
This project is built using Java and Spring Boot and follows a clean layered architecture suitable for real-world backend applications.

---

## 🚀 Tech Stack

- Java  
- Spring Boot  
- Spring Data JPA  
- REST APIs  
- MySQL  
- Maven  

---

## 📁 Project Structure

com.MrEnineer.Gym
│
├── Controller
│ ├── AuthController.java
│ ├── ActivityController.java
│ └── RecommendationController.java
│
├── Service
│ ├── UserService.java
│ ├── ActivityService.java
│ └── RecommendationService.java
│
├── Repository
│ ├── UserRepository.java
│ ├── ActivityRepo.java
│ └── RecommendationRepository.java
│
├── Entity
│ ├── User.java
│ ├── Activity.java
│ ├── ActivityType.java
│ └── Recommendation.java
│
├── dto
│ ├── RegisterRequest.java
│ ├── UserResponse.java
│ ├── ActivityRequest.java
│ ├── ActivityResponse.java
│ ├── RecommendationRequest.java
│ └── RecommendationResponse.java






---

## ✨ Features

- User Registration  
- User Authentication  
- Activity Management  
- Workout Recommendations  
- RESTful API design  
- DTO-based request and response handling  
- Database integration using Spring Data JPA  

---

## 🧠 API Modules

### 🔐 Authentication
- Register new users  
- Login users  

### 🏃 Activities
- Add gym activities  
- Fetch activities  
- Categorize activities using `ActivityType`  

### 💡 Recommendations
- Generate workout recommendations  
- Fetch recommendations for users  

---

## 🗄️ Database Configuration

This project uses **MySQL**.

Update database details in `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/gym_db
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true



📌 What I Learned from This Project

Building REST APIs using Spring Boot

Layered architecture (Controller → Service → Repository)

DTO pattern for clean API design

JPA and database integration

Structuring backend projects professionally

👨‍💻 Author

Suraj Rathod
Computer Science Engineering Student
Java & Spring Boot Backend Developer
