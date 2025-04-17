# Dùng Maven + OpenJDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS builder

# Thư mục làm việc
WORKDIR /app

# Copy toàn bộ project vào container
COPY . .

# Build project
RUN mvn clean package -DskipTests

# =========================
# Tạo image chỉ chạy JAR (nhẹ hơn)
# =========================
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy file JAR từ builder sang
COPY --from=builder /app/target/Elearning_BE-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
