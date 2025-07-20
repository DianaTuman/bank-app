# bank-app

Bank-like application created using Spring Boot. Homework for the Java developer training course.
The application will start at **localhost:8080**. 
To activate blocker feature try to cash or transfer using number 666.

### Versions of software used in the development:

* Java JDK 21
* Spring Boot 3.5.3
* PostgreSQL 17
* Redis 7.4.2
* Docker 4.40.0
* KeyCloak 26.2.5
* HashiCorp Consul 1.21


### **To run this application with Docker:**

**Docker** must be installed and running. Localhost port **8080** must be free.

1. **gradle clean buildAll** command to create jars
2. **docker-compose up --build** command to run the jars inside the docker container
