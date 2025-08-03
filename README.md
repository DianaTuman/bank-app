# bank-app

Bank-like application created using Spring Boot. Homework for the Java developer training course.
The application will start at **localhost:8080**.
To register a user go to **/signup** page.
To activate blocker feature try to cash or transfer using number 666. 

### Versions of software used in the development:

* Java JDK 21
* Spring Boot 3.5.3
* PostgreSQL 17
* Docker 4.43.2
* KeyCloak 26.2.5

### **To run this application with K8s and Helm:**

You must have working K8s and Helm. K8s should be able to work with Ingress and with local Docker registry.

1. **gradle clean buildAll** to create jars
2. **.\build_images.sh** to build local Docker images
3. **.\deploy_with_helm.sh** to deploy app with Helm

The application will start at **localhost:8080**.

### **To deploy this application with Jenkins please read README-JENKINS.md**