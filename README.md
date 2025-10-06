


#  Task Management System

A **Spring Boot** application designed to manage tasks efficiently enabling users to **create, update, assign, and track tasks** with real-time status management.
Built using **Spring Boot** and **PostgreSQL**

---

## Features

* Create an user
* Create, Read, Update, and Delete (CRUD) operations for tasks
* Assign tasks to users
* Track due dates and completion status
* Store all data securely in a **PostgreSQL** database


---

## Tech Stack

| Layer | Technology              |
| ---- |-------------------------|
| Backend | Spring Boot 3 (Java 21) |
| Database | PostgreSQL              |
| Build Tool | Maven                   |
| API Testing | Postman                 |

---


---

##  API Endpoints

| Method | Endpoint                      | Description                       |
|--------|-------------------------------|-----------------------------------|
| GET    | `/all`                        | Fetch all users                   |
| POST   | `/create-user`                | Create a new user                 |
| POST   | `/create-task`                | Create a new task                 |
| PUT    | `/update-task/{taskId}`       | Update an existing task           |
| DELETE | `/delete/{taskId}/{userId}`   | Delete a task                     |
| GET    | `/get-all-status/{assignerId}/{statds}` | Get all tasks by status           |
| GET    | `/get-all-date/{assignerId}?date=date` | Get all tasks before a date       |
| GET    | `/get-all-date/{assignerId}`  | Get all tasks of an assigned user |


##  Author

**Arkodip Das**




