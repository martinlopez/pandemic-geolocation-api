FROM openjdk:11.0.3-jdk
RUN apt-get update && apt-get install bash
RUN mkdir -p /usr/app/
ENV PROJECT_HOME /usr/app/
COPY build/libs/pandemic_monitoring-0.0.1-SNAPSHOT.jar $PROJECT_HOME/pandemic_monitoring-0.0.1-SNAPSHOT.jar
WORKDIR $PROJECT_HOME
CMD ["java", "-jar", "./pandemic_monitoring-0.0.1-SNAPSHOT.jar"]
