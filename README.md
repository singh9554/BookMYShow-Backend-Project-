# 🎟️ BookMyShow Backend – Spring Boot

A backend system design and implementation of an online movie ticket booking platform inspired by BookMyShow.  
This project focuses on clean architecture, real-world entity relationships, and scalable backend design.

---

## 📌 Project Overview

This backend application handles the complete flow of a movie ticket booking system including:
- Movie and theater management
- Show and seat allocation
- Seat availability validation
- Booking and payment flow
- Centralized exception handling

The project is built with a strong emphasis on **Low-Level Design (LLD)**, **database relationships**, and **RESTful APIs**.

---

## 🛠 Tech Stack

- **Java**
- **Spring Boot**
- **Spring Data JPA (Hibernate)**
- **MySQL**
- **Maven**
- **REST APIs**

---

## 📂 Project Structure

com.cfs.org.BMS
│
├── Controller
│ └── REST controllers for handling API requests
│
├── DTO
│ ├── BookingDTO
│ ├── BookingRequestDTO
│ ├── MovieDTO
│ ├── PaymentDTO
│ ├── ScreenDTO
│ ├── SeatDTO
│ ├── ShowDTO
│ ├── ShowSeatDTO
│ ├── TheaterDTO
│ └── UserDTO
│
├── Exception
│ ├── ErrorResponse
│ ├── GlobalExceptionHandling
│ ├── ResourceNotFoundException
│ └── SeatUnavailableException
│
├── Model
│ ├── Booking
│ ├── Movie
│ ├── Payment
│ ├── Screen
│ ├── Seat
│ ├── Show
│ ├── ShowSeat
│ ├── Theater
│ └── User
│
└── Repository
├── BookingRepository
├── MovieRepository
├── PaymentRepository
├── ScreenRepository
├── ShowRepository
├── ShowSeatRepository
└── TheaterRepository


---

## 🧩 Core Features Implemented

- ✅ Movie, Theater, Screen, and Show entity design
- ✅ Seat and ShowSeat mapping
- ✅ Booking creation flow
- ✅ Seat availability validation
- ✅ Payment entity integration
- ✅ DTO-based request/response handling
- ✅ Centralized global exception handling
- ✅ Clean separation of layers (Controller, DTO, Model, Repository)

---

## 🔐 Exception Handling

The project includes a **centralized global exception handling mechanism** using `@ControllerAdvice`:
- `ResourceNotFoundException` – for invalid resource access
- `SeatUnavailableException` – when requested seats are already booked
- Custom `ErrorResponse` structure for consistent API error responses

---

## ⚙️ Setup Instructions

1. Clone the repository
   ```bash
   git clone <your-repo-url>
Configure database in application.yml / application.properties

Build and run the project

mvn clean install
mvn spring-boot:run


Access APIs using Postman or browser

🚧 Work in Progress / Planned Enhancements

🔄 Seat locking with concurrency handling

🔄 Payment failure rollback mechanism

🔄 Authentication & authorization

🔄 API documentation (Swagger)

🔄 Caching for performance optimization

🎯 Learning Objective

This project is built to strengthen:

Backend system design thinking

Real-world entity relationships

Spring Boot best practices

Interview-ready LLD concepts

📌 Note

This is an actively evolving project. Improvements and refactoring will continue as new concepts are learned and applied.


---

### 🔥 Pro Tip (Interview POV)
Tumhara project already **LLD interview ready** hai.  
Next level ke liye:
- README me **ER diagram image**
- Booking flow explanation (step-by-step)

Agar chaho to next step me main:
- **Architecture explanation paragraph**
- **Booking flow diagram description**
- **Resume-ready project points**

bhi bana deta hoon 🚀
