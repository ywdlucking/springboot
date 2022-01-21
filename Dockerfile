FROM java:8
#FROM java:8
MAINTAINER ywd
ADD /target/springboot-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["/bin/sh","-c","java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Dspring.config.location=/application.yml -jar /app.jar"]
