FROM eclipse-temurin:24-jdk AS build
WORKDIR /app

COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
COPY images images
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

COPY src src
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:24-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar
COPY --from=build /app/images images

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]