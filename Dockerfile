FROM openjdk:17-jdk-slim
WORKDIR /app
COPY . /app
RUN javac Server.java ProcessServer.java
java Server
EXPOSE 1503
CMD ["java", "Server"]
