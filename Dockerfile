# Base image: dùng JDK 17
FROM openjdk:17-jdk-slim

# Thư mục làm việc trong container
WORKDIR /app

# Copy toàn bộ file vào container
COPY . .

# Compile file Java
RUN javac Server.java ProcessServer.java

# Mở port server lắng nghe (VD: 1503)
EXPOSE 1503

# Lệnh khởi động container (chạy Server)
CMD ["java", "Server"]
