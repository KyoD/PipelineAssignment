FROM anapsix/alpine-java
COPY /target/movie-app-0.0.1-SNAPSHOT.jar /home/movie-app-0.0.1-SNAPSHOT.jar
CMD ["jave], "-jar", "/home/movie-app-0.0.1-SNAPSHOT.jar"]