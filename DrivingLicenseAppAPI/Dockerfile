# Build stage
FROM openjdk:21-jdk AS build
WORKDIR /app

COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

COPY src src
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM openjdk:21-jdk-slim
WORKDIR /app
VOLUME /tmp

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]
EXPOSE 8080