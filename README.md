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

1. **gradle clean buildAll** command to create jars
2. **.\build_images.sh**

Commands for minikube:
minikube start --vm-driver=hyperv
minikube docker-env | Invoke-Expression
minikube addons enable ingress
minikube tunnel
minikube service list
kubectl create configmap keycloak-realm-config --from-file=realm-export.json

### **To deploy this application with Jenkins please read README-JENKINS.md**