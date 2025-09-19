# syntax=docker/dockerfile:1
# Stage 1: Build the application
FROM --platform=linux/amd64 maven:3.9.4-eclipse-temurin-17-alpine AS builder
RUN echo "--- [START] Stage 1: Build the application ---"

# Set the working directory inside the container
WORKDIR /build

# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 mvn dependency:go-offline -B

# Copy the source code and build the application
COPY src/ ./src/
RUN --mount=type=cache,target=/root/.m2 mvn clean package -DskipTests

RUN echo "--- [END] Stage 1: Build the application ---"


# Stage 2: Layer the application
FROM --platform=linux/amd64 eclipse-temurin:17-jre-alpine AS layers
RUN echo "--- [START] Stage 2: Layer the application ---"

# Set the working directory inside the container
WORKDIR /layer

# Copy the built jar file from the build stage
COPY --from=builder /build/target/*.jar wmessenger.jar

# Extract the layers from the jar file
RUN java -Djarmode=layertools -jar wmessenger.jar extract

RUN echo "--- [END] Stage 2: Layer the application ---"


# Stage 3: Run the application
FROM --platform=linux/amd64 eclipse-temurin:17-jre-alpine
RUN echo "--- [START] Stage 3: Run the application ---"

# Set the working directory inside the container
WORKDIR /app

# Add security group and user
RUN addgroup --system wmessenger && adduser -S -s /usr/sbin/nologin -G wmessenger wmessenger
COPY --from=layers /layer/dependencies/ ./
COPY --from=layers /layer/spring-boot-loader/ ./
COPY --from=layers /layer/snapshot-dependencies/ ./
COPY --from=layers /layer/application/ ./
RUN chown -R wmessenger:wmessenger /app

RUN apk add gcompat --no-cache
ENV LD_PRELOAD=/lib/libgcompat.so.0

USER wmessenger


# Expose the port your application runs on
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-agentlib:jdwp=server=y,transport=dt_socket,address=0.0.0.0:8086,suspend=n", "org.springframework.boot.loader.launch.JarLauncher"]
RUN echo "--- [END] Stage 3: Run the application ---"
