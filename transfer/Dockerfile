FROM openjdk:21-jdk-oracle
COPY build/libs/*.jar app.jar
EXPOSE 8087
ENTRYPOINT ["java", "-jar", "app.jar"]