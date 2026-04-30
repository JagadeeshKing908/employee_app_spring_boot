FROM eclipse-temurin:17

COPY target/employee-app-1.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]
