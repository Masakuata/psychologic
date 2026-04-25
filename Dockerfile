FROM eclipse-temurin:21-jre-alpine

COPY target/psychologic.jar app.jar

CMD ["java", "-jar", "./app.jar"]