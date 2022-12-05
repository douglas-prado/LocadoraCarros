FROM openjdk:11
VOLUME /tmp
EXPOSE 8888
ADD ./target/LocadoraCarros-0.0.1-SNAPSHOT.jar loca-carros.jar
ENTRYPOINT ["java","-jar","/loca-carros.jar"]