FROM eclipse-temurin:21-jdk-noble AS builder
WORKDIR /app
COPY . .
RUN chmod +x gradlew && ./gradlew clean build --no-daemon

FROM eclipse-temurin:21-jre-noble
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar"]