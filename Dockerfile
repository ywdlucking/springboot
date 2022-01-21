FROM reg-dev.shie.com.cn/shie-base/java:8
#FROM java:8
MAINTAINER yuwendong@shie.com.cn
ADD /target/springboot-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["/bin/sh","-c","java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Dspring.config.location=/application.yml -jar /app.jar"]
