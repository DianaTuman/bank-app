# bank-app
Bank-like application created using Spring Boot. Homework for the Java developer training course.
The application will start at **localhost:8080**.

### Versions of software used in the development:

* Java JDK 21
* Spring Boot 3.5.3
* PostgreSQL 17
* Redis 7.4.2
* Docker 4.40.0
* KeyCloak 26.2.5
* HashiCorp Consul 1.21

### **To run this application without Docker:**

* Localhost ports **8080, 8081, 8082, 8083, 8084, 8085, 8086, 8087** must be free.
* **PostgreSQL** must be installed and running at **localhost:5432**.
  Environment variables **'DB_USER'** and **'DB_PASS'** must be set for accessing database and a schema named *
  *practicum** must exist in database.
* Import realm setting to **Keycloak** from ./keycloak/realm-export.json
* **HashiCorp Consul** must be installed and running at **localhost:8500**.
* **Redis** must be installed and running at **localhost:6379**.
* **gradle :main-app:bootRun** command to run main application
* **gradle :payment-app:bootRun** command to run payment REST-service

### **To run this application with Docker:**

**Docker** must be installed and running. Localhost port **8080** must be free.
1. **gradle clean buildAll** command to create a jars
2. **docker-compose up --build** command to run the jars inside the docker container
