FROM openjdk:11
COPY target/spring-batch-paging-reader-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]