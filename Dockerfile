FROM maven:3.5-jdk-8-alpine AS build
COPY . .
RUN mvn clean install -DskipTests

FROM openjdk:8-jre-alpine
COPY --from=build /target/*.jar bookCatalog.jar
ENTRYPOINT ["java","-jar","/bookCatalog.jar"]