FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY target/taskapi-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=80"
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]