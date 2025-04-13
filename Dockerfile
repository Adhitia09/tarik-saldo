# Gunakan image Maven untuk build
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Salin file pom.xml terlebih dahulu
COPY pom.xml .

# Download dependencies sebelum build agar lebih efisien
RUN mvn dependency:go-offline

# Salin seluruh source code proyek
COPY src ./src

# Jalankan build
RUN mvn clean package -DskipTests

# Gunakan image JDK yang lebih ringan untuk runtime
FROM openjdk:21-jdk-slim

ENV SPRING_MVC_DISPATCH_TRACE_REQUEST=false
ENV SPRING_MVC_DISPATCH_DELETE_REQUEST=false

WORKDIR /app

# Salin hasil build dari tahap sebelumnya
COPY --from=build /app/target/*.jar /app/app.jar

CMD ["java", "-Dspring.mvc.dispatch-trace-request=false", "-Dspring.mvc.dispatch-delete-request=false", "-jar", "/app/app.jar"]
