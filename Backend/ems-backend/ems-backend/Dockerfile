FROM openjdk:21-jdk
LABEL maintainer="shamikbhattacharjee27@gmail.com"
WORKDIR /app
COPY target/ems-backend-0.0.1-SNAPSHOT.jar /app/ems-backend.jar
ENTRYPOINT ["java", "-jar", "ems-backend.jar"]
