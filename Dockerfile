# Build stage
FROM maven:3.8.6-openjdk-18-slim as build
COPY src /app/src
COPY pom.xml /app/pom.xml
WORKDIR /app
RUN mvn -f ./pom.xml clean package

# Package stage
FROM openjdk:17-alpine
COPY --from=build /app/target/scheduler-1.0-SNAPSHOT-jar-with-dependencies.jar /usr/local/lib/scheduler.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/scheduler.jar"]