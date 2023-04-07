#
# Build stage
#
FROM maven:3.9.1-eclipse-temurin-20-alpine AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests

#
# Package stage
#
FROM openjdk:19-jdk-alpine
COPY --from=build /target/Ideas100-0.0.1-SNAPSHOT.jar ideas100.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","ideas100.jar"]