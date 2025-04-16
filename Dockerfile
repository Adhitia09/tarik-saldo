FROM docker.io/maven:3.3.1 

WORKDIR /app

COPY . .

RUN mvn clean package -Dmaven.test.skip=true

# Gunakan image JDK yang lebih ringan untuk runtime
FROM docker.io/openjdk:21-jdk-slim

ENV SPRING_MVC_DISPATCH_TRACE_REQUEST=false
ENV SPRING_MVC_DISPATCH_DELETE_REQUEST=false

WORKDIR /app/run/

# Salin hasil build dari tahap sebelumnya
COPY --from=builder /app/target/*.jar /app/run/app.jar

EXPOSE 8383

CMD ["java", "-Dspring.mvc.dispatch-trace-request=false", "-Dspring.mvc.dispatch-delete-request=false", "-jar", "/app/run/app.jar"]
