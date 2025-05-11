FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

# Make mvnw script executable
RUN chmod +x mvnw

# Build the application
RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/pet-food-finder-api-0.0.1-SNAPSHOT.jar"]
