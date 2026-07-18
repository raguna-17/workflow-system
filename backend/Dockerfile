# build stage
FROM gradle:8.7-jdk21 AS builder

WORKDIR /app

COPY . .

RUN ./gradlew clean build -x test


# runtime stage
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]