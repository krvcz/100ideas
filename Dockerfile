#
# Build stage
#
FROM maven:3.8.2-jdk-19 AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests

#
# Package stage
#
FROM openjdk:19-jdk-slim
COPY --from=build /target/Ideas100-0.0.1-SNAPSHOT.jar ideas100.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","ideas100.jar"]