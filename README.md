
#  AdmissionCRM – Smart Admission Management System

🔗 **Live Demo:** https://admission.dhup.online

---

## 📌 Overview

AdmissionCRM is a **microservices-based web application** designed for educational institutions to manage the complete student admission lifecycle — from lead generation to application processing and document verification.

This system helps streamline admission workflows, improve lead tracking, and ensure secure and scalable operations.

---

## 🏗️ Architecture

The project follows a **Microservices Architecture** for better scalability, modularity, and maintainability.

### 🔧 Core Services:

* 🔐 **Authentication Service**

  * JWT-based authentication
  * Role-based access (Admin, Counselor, Student)

* 📊 **Lead Management Service**

  * Capture and manage student inquiries
  * Track lead status

* 📝 **Application Service**

  * Handle complete application form submission
  * Structured data management using DTOs

* 📄 **Document Service**

  * Upload and verify documents

* 🔄 **Workflow Service**

  * Manage application lifecycle stages

* 🌐 **API Gateway**

  * Centralized routing
  * JWT validation

---

## ⚙️ Tech Stack

### 🔹 Backend

* Java
* Spring Boot
* Spring Security (JWT)
* Spring MVC
* JPA / Hibernate
* MySQL
* Maven

### 🔹 Frontend

* React (Vite)
* Tailwind CSS

### 🔹 DevOps & Deployment

* Docker
* AWS EC2
* Nginx (Reverse Proxy)
* Let's Encrypt (SSL)
* Vercel (Frontend Hosting)

---

## 🔐 Features

* Secure JWT-based authentication
* Role-based authorization
* End-to-end admission tracking
* Modular microservices architecture
* Document upload & verification
* OTP-based authentication (Email)

---

## 🚀 Deployment

### 🌐 Frontend

* Hosted on **Vercel**
* Custom domain configured:

  ```
  https://admission.dhup.tech
  ```

### 🔗 Backend

* Deployed on **AWS EC2**

* Reverse proxy using **Nginx**

* Secured with **HTTPS (Let's Encrypt SSL)**

* API Base URL:

  ```
  https://api.dhup.tech
  ```

---

## ⚠️ Challenges Faced

* 🔐 Implementing JWT authentication across microservices
* 🌐 Resolving HTTPS (Mixed Content) issues
* 🌍 DNS & custom domain configuration
* 🧠 Refactoring large entity into modular DTOs
* 📧 Fixing SMTP authentication for OTP emails

---

## 📂 Project Structure (High-Level)

```
AdmissionCRM/
│── api-gateway/
│── authentication-service/
│── lead-service/
│── application-service/
│── document-service/
│── frontend/
```

---

## 🧪 How to Run Locally

### Backend

```bash
git clone <repo-url>
cd authentication-service
mvn spring-boot:run
```

### Frontend

```bash
cd frontend
npm install
npm run dev
```

---

## 📈 Future Improvements

* API rate limiting
* Centralized logging & monitoring
* CI/CD pipeline (GitHub Actions)
* Integration with cloud email services (AWS SES)

---

## 👨‍💻 Author

**Dhup Patel**

* Passionate Java Backend Developer
* Interested in scalable system design & microservices

---

## ⭐ If you like this project

Give it a ⭐ on GitHub!

---
