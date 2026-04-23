FROM eclipse-temurin:21-jdk-alpine

COPY target/psychologic.jar app.jar

CMD ["java", "-jar", "./app.jar"]