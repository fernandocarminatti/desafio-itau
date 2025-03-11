FROM openjdk:17-jdk-alpine
COPY target/ChallengeApp-1.0.jar /app/ChallengeApp-1.0.jar
ENTRYPOINT ["java", "-jar", "/app/ChallengeApp-1.0.jar"]