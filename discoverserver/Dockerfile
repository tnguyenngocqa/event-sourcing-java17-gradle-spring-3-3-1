#===============
# Stage 1: BUILD Jar using gradle image
#===============
# Sử dụng image gradle:8.14.4-jdk17-ubi9 làm base image
FROM gradle:8.14.4-jdk17-ubi9 AS builder

# Thiết lập thư mục làm việc trong container
WORKDIR /app

# Sao chép mã nguồn ứng dụng discoverserver files to container
# COPY . ./discoverserver
COPY ./discoverserver ./discoverserver

# Thiết lập thư mục làm việc trong container under /app/discoverserver
WORKDIR /app/discoverserver

# Cài đặt các dependencies và tạo jar file thực thi
RUN ./gradlew clean build



#===============
# Stage 2:
#===============
# Sử dụng image openjdk:17-jdk-slim làm base image
FROM eclipse-temurin:17-jdk-jammy

# Thiết lập thư mục làm việc trong container
WORKDIR /app

# Sao chép mã nguồn ứng dụng
COPY --from=builder /app/discoverserver/build/libs/*.jar discoverserver.jar

# Mở cổng 8761
EXPOSE 8761

# Lệnh mặc định để chạy ứng dụng khi container khởi động
ENTRYPOINT [ "java", "-jar" , "discoverserver.jar"]
