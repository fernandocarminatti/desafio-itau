FROM maven:3.8.8-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests
RUN cp target/ChallengeApp-1.0.jar .

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/ChallengeApp-1.0.jar .
ENTRYPOINT ["java", "-jar", "/app/ChallengeApp-1.0.jar"]