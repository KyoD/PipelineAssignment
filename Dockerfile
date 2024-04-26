FROM openjdk:17-oracle
COPY /target/movie-app-0.0.1-SNAPSHOT.jar /home/movie-app-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "/home/movie-app-0.0.1-SNAPSHOT.jar"]