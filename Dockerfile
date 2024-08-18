# Construção
FROM maven:3.8.5-openjdk-17 AS build-stage

RUN mkdir -p /usr/src/app

WORKDIR /usr/src/app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src /usr/src/app/src
RUN mvn package -DskipTests

# Execução
FROM openjdk:17-jdk-slim AS production-stage

COPY --from=build-stage /usr/src/app/target/*.jar /usr/src/app/library-management.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/usr/src/app/library-management.jar"]