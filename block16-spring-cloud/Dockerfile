# new image will be created from openjdk:17
FROM openjdk:17
# container port (default TCP, add /udp to switch)
EXPOSE 8080
# copy project jar to container's system files
COPY /target/*.jar /url/local/lib/spring.jar
# command to run container
ENTRYPOINT ["java", "-jar", "/url/local/lib/spring.jar"]