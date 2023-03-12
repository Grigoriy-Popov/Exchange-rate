FROM amazoncorretto:17
COPY target/*.jar exchange-rate.jar
ENTRYPOINT ["java","-jar","/exchange-rate.jar"]
