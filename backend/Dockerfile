FROM openjdk:11
VOLUME /tmp
EXPOSE 8080
ADD ./build/libs/dsmovie-0.0.1-SNAPSHOT.jar dsmovie.jar
ENTRYPOINT ["java","-jar","/dsmovie.jar"]