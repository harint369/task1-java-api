# ✅ Task 1 – Java REST API with MongoDB (Kaiburr Internship)

This project implements a Spring Boot-based Java REST API that manages and executes shell-based task objects. Each task contains a command that can be executed and logs its execution history. Data is stored in MongoDB.

---

## 📁 Project Structure
src/
└── main/
└── java/
└── com/kaiburr/taskapi/
├── controller/
├── model/
├── repository/
└── service/
└── resources/
└── application.properties


---

## ✅ Features

- Create, fetch, delete, and search task objects.
- Run a shell command stored in a task.
- Track execution time and output.
- Persist data using MongoDB.

---

## 🧰 Technologies Used

- Java 17+
- Spring Boot
- MongoDB
- Spring Data MongoDB
- Maven

---

## ▶️ How to Run the Application

1. Start your **MongoDB** server (locally or via Docker).
2. Clone this repository:
   bash:
   git clone https://github.com/your-username/task1-api.git
   cd task1-api
3.Run the application using Maven:
    bash:
    mvn spring-boot:run

---

##  Sample curl Test Commands
➕ Create a Task:
        curl -X POST http://localhost:8080/tasks -H "Content-Type: application/json" -d "{\"id\":\"123\",\"name\":\"Print Hello\",\"owner\":\"John Smith\",\"command\":\"echo Hello World!\"}"

📋 Get All Tasks
    curl http://localhost:8080/tasks

🔍 Get Task by ID
    curl http://localhost:8080/tasks?id=123

🔎 Search Task by Name
    curl http://localhost:8080/tasks/search?name=print

❌ Delete a Task
    curl -X DELETE http://localhost:8080/tasks/123

🚀 Execute Task Command
    curl -X PUT http://localhost:8080/tasks/123/execute
