# ✈️ FlyMyTrip

FlyMyTrip is a Java console application for booking flight tickets. This project showcases various Java concepts including streams, JDBC, database schema design, hashing libraries, and JSON parsing for fast data uploads.

## 🌟 Features

- 🛫 Book flight tickets
- 📜 View booked tickets
- ❌ Cancel tickets
- 📄 JSON parsing to upload flight data into the database

## 🚀 Getting Started

### 📋 Prerequisites

- ☕ Java 8 or higher
- 🐬 MySQL or any other relational database
- 🐘 Gradle

### ⚙️ Installing

1. **Clone the repository**

```sh
git clone https://github.com/deekshithpranav/FlyMyTrip.git
cd FlyMyTrip
```

Update the DatabaseUtil.java file with your database connection details:
```sh
private static final String URL = "jdbc:mysql://localhost:3306/your_database_name";
private static final String USER = "your_database_user";
private static final String PASSWORD = "your_database_password";
```
2. **Setup Database**
Modify the src/main/java/App.java file:

Set the resetDatabase variable to true to create tables.
Set it back to false to avoid data loss after the initial setup.
```sh
./gradlew build
./gradlew run
```
## 📚 Learnings
During this project, I have learned and applied:

- 🛠️ Database Schema Design
- 🔄 Streams, Map, Filter, Chaining
- 🚦 Exception Handling
- 🗄️ JDBC
- 📦 Jackson Library for JSON Parsing
- 🔐 Spring Boot Hashing Library

## 🤝 Contributing
Feel free to fork the repository, make your changes, and submit a pull request. Any suggestions and improvements are welcome!
