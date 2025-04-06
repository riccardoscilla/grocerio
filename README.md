# 🥦 Grocerio Backend

Grocerio is a smart pantry and grocery management system that helps users track what's in their pantry, manage shopping lists, and plan meals efficiently.

This is the **backend** of the application, built with **Spring Boot**, **PostgreSQL (Supabase)**, and secured with **JWT authentication**. It provides RESTful APIs consumed by the [Grocerio UI](https://riccardoscilla.github.io/grocerio-ui/).

---

## 🚀 Features

- 🔐 JWT authentication via Supabase
- 🧺 Pantry management with CRUD for items and categories
- 📝 Recipe support with ingredients and instructions
- 🛒 Smart shopping list generation
- 👥 Shared shelves between multiple users
- ⚡ Optimized API responses (<200ms average)
- 🌍 PWA-ready integration with Angular frontend

---

## 🛠️ Tech Stack

- Java 17 + Spring Boot
- PostgreSQL (Supabase)
- Spring Security (with custom JWT filter)
- Hibernate / JPA
- Hosted on [Render](https://render.com/)
