FROM openjdk:17-jdk-alpine
COPY target/Challenge-0.0.1-SNAPSHOT.jar /app/Challenge-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/app/Challenge-0.0.1-SNAPSHOT.jar"]