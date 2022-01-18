FROM adoptopenjdk:11-jre-hotspot
ADD target/ld-study.jar ld-study.jar
EXPOSE 9000
ENTRYPOINT ["java", "-jar", "ld-study.jar"]