# Step 1: Build the application
FROM maven:3.9.7 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and install dependencies
COPY backend/LaboMicroServices/Service-Laboratoire/pom.xml .
COPY backend/LaboMicroServices/Service-Laboratoire/src ./src

# Run Maven to build the application
RUN mvn clean package -DskipTests

# Step 2: Create a smaller runtime image
FROM openjdk:17-jdk

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
