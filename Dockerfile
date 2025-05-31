# Step 1: Build the JAR using Maven in a container (multi-stage)
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# Step 2: Create a smaller image to run the JAR
FROM openjdk:17-jdk-slim
WORKDIR /app

# ✅ Copy the JAR to the runtime image — correct path
COPY --from=build /app/target/swapskill-backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# ✅ ENTRYPOINT matches actual location of the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
