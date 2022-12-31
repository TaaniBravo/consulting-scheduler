## Build stage
#FROM maven:3.8.6-openjdk-18-slim as build
#COPY ./src /app/src
#COPY pom.xml /app/pom.xml
#WORKDIR /app
#RUN mvn -f ./pom.xml clean package
#
## Package stage
#FROM openjdk:17 as app
#CMD yum -y install libx11
#WORKDIR /app
#COPY --from=build /app/target/scheduler-1.0-SNAPSHOT-jar-with-dependencies.jar /app/scheduler.jar
#EXPOSE 8080
#CMD ls
#ENTRYPOINT ["java", "-jar", "/app/scheduler.jar"]
FROM openjdk:17
COPY target/scheduler-1.0-SNAPSHOT-jar-with-dependencies.jar /app/scheduler.jar
ENTRYPOINT ["java", "-jar", "/app/scheduler.jar"]
