# Gunakan image JDK yang lebih ringan untuk runtime
FROM openjdk:21-jdk-slim

ENV SPRING_MVC_DISPATCH_TRACE_REQUEST=false
ENV SPRING_MVC_DISPATCH_DELETE_REQUEST=false

WORKDIR /app

# Salin hasil build dari tahap sebelumnya
COPY target/*.jar /app/app.jar

EXPOSE 8383

CMD ["java", "-Dspring.mvc.dispatch-trace-request=false", "-Dspring.mvc.dispatch-delete-request=false", "-jar", "/app/app.jar"]
