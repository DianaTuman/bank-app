# bank-app

Bank-like application created using Spring Boot. Homework for the Java developer training course.
The application will start at **localhost:8080**.
To activate blocker feature try to cash or transfer using number 666.

### Versions of software used in the development:

* Java JDK 21
* Spring Boot 3.5.3
* PostgreSQL 17
* Docker 4.43.2
* KeyCloak 26.2.5

### **To run this application with K8s and Helm:**

You must have working K8s and Helm. K8s should be able to work with Ingress.

1. **gradle clean buildAll** command to create jars
2. **.\build_images.sh** command to build local Docker images 
3. helm dependency build ./bank-app 
4. helm install bank-app ./bank-app

Commands for minikube:
minikube start --vm-driver=hyperv --disk-size=40g
minikube docker-env | Invoke-Expression
minikube addons enable ingress
minikube tunnel
minikube service list

### **To deploy this application with Jenkins please read README-JENKINS.md**