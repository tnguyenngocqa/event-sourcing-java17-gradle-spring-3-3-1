#===============
# Stage 1: BUILD Jar using gradle image
#===============
# Sử dụng image gradle:8.14.4-jdk17-ubi9 làm base image
FROM gradle:8.14.4-jdk17-ubi9 AS builder

# Thiết lập thư mục làm việc trong container
WORKDIR /app

# Sao chép mã nguồn ứng dụng commonservice files to container
COPY ./commonservice ./commonservice
# Sao chép mã nguồn ứng dụng notificationservice files to container
COPY ./notificationservice ./notificationservice


# Thiết lập thư mục làm việc trong container under /app/commonservice
WORKDIR /app/commonservice

# Set the active Spring profile
ENV SPRING_PROFILES_ACTIVE=dev

# Cài đặt các dependencies và tạo jar file thực thi
RUN ./gradlew clean bootJar publishToMavenLocal

# Thiết lập thư mục làm việc trong container under /app/notificationservice
WORKDIR /app/notificationservice

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
COPY --from=builder /app/notificationservice/build/libs/*.jar notificationservice.jar

# Mở cổng 9003
EXPOSE 9003

# Lệnh mặc định để chạy ứng dụng khi container khởi động
ENTRYPOINT [ "java", "-jar" , "notificationservice.jar"]
