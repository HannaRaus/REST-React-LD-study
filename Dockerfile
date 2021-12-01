FROM adoptopenjdk:11-jre-hotspot
ADD target/ld-app.jar ld-app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "ld-app.jar"]