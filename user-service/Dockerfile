FROM gradle:8.14.3-jdk21 AS builder
WORKDIR /app
COPY --chown=gradle:gradle . .

RUN gradle clean build --no-daemon -x test

FROM eclipse-temurin:21-jre-noble
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 902
ENTRYPOINT ["java", "-jar", "app.jar"]