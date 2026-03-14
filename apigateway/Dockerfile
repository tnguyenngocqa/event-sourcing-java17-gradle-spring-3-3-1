#===============
# Stage 1: BUILD Jar using gradle image
#===============
# Sử dụng image gradle:8.14.4-jdk17-ubi9 làm base image
FROM gradle:8.14.4-jdk17-ubi9 AS builder

# Thiết lập thư mục làm việc trong container
WORKDIR /app

# Sao chép mã nguồn ứng dụng apigateway files to container
# COPY . ./apigateway
COPY ./apigateway ./apigateway

# Thiết lập thư mục làm việc trong container under /app/apigateway
WORKDIR /app/apigateway

# Cài đặt các dependencies và tạo jar file thực thi
RUN ./gradlew clean build

#===============
# Stage 2:
#===============
# Sử dụng image openjdk:17-jdk-slim làm base image
FROM eclipse-temurin:17-jdk-jammy

# Set the active Spring profile
ENV SPRING_PROFILES_ACTIVE=dev

# Thiết lập thư mục làm việc trong container
WORKDIR /app

# Sao chép mã nguồn ứng dụng
COPY --from=builder /app/apigateway/build/libs/*.jar apigateway.jar

# Mở cổng 8080
EXPOSE 8080

# Lệnh mặc định để chạy ứng dụng khi container khởi động
ENTRYPOINT [ "java", "-jar" , "apigateway.jar"]
