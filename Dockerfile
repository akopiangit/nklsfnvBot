FROM openjdk:17-oracle
COPY target/nklsfnvBot-0.0.1-SNAPSHOT.jar nklsfnvBot-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/nklsfnvBot-0.0.1-SNAPSHOT.jar"]