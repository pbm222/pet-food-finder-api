# Use a base image with Java 21
FROM eclipse-temurin:21-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the project files
COPY . .

# Build the project using Maven
RUN ./mvnw clean package -DskipTests

# Expose the app's port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/pet-food-finder-api-0.0.1-SNAPSHOT.jar"]
