FROM openjdk:8-jdk-alpine

COPY ./target/admin-service-service-1.0-SNAPSHOT.jar /app_folder/admin-service-service-1.0-SNAPSHOT.jar

WORKDIR /app_folder

EXPOSE 9082

ENTRYPOINT ["java","-jar","admin-service-service-1.0-SNAPSHOT.jar"]

#use "docker build --tag admin-service:1 ." to build the image
#use "docker run -d --name adm-srv -p 8080:9082 admin-service:1" to start the container
#use "http://{IP of docker VM}:8080/admin_service/swagger-ui.html" to access the service.