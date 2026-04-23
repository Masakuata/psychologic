FROM eclipse-temurin:21-jdk-alpine

COPY target/psychologic-0.0.1.jar app.jar

CMD ["java", "-jar", "./app.jar"]