FROM --platform=linux/amd64 eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY target/plantas-service-0.0.1-SNAPSHOT.jar plantas-service.jar

EXPOSE 9921

CMD ["java", "-jar", "plantas-service.jar"]
