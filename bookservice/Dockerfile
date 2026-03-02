#===============
# Stage 1: BUILD Jar using gradle image
#===============
# Sử dụng image gradle:8.14.4-jdk17-ubi9 làm base image
FROM gradle:8.14.4-jdk17-ubi9 AS builder

# Thiết lập thư mục làm việc trong container
WORKDIR /app

# Sao chép mã nguồn ứng dụng commonservice files to container
COPY ./commonservice ./commonservice
# Sao chép mã nguồn ứng dụng bookservice files to container
COPY ./bookservice ./bookservice


# Thiết lập thư mục làm việc trong container under /app/commonservice
WORKDIR /app/commonservice

# Cài đặt các dependencies và tạo jar file thực thi
RUN ./gradlew clean bootJar publishToMavenLocal

# Thiết lập thư mục làm việc trong container under /app/bookservice
WORKDIR /app/bookservice

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
COPY --from=builder /app/bookservice/build/libs/*.jar bookservice.jar

# Mở cổng 8761
EXPOSE 9001

# Lệnh mặc định để chạy ứng dụng khi container khởi động
ENTRYPOINT [ "java", "-jar" , "bookservice.jar"]
