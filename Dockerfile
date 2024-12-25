# Базовый образ с JDK 17
FROM eclipse-temurin:17-jdk-alpine

ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk
ENV PATH="$JAVA_HOME/bin:$PATH"

WORKDIR /app

COPY target/dadata-cleaner-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
