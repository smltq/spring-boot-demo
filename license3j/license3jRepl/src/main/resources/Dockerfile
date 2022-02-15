FROM openjdk:13
COPY target/License3jrepl-${project.version}-jar-with-dependencies.jar /usr/src/License3jrepl-${project.version}-jar-with-dependencies.jar
WORKDIR /opt
CMD ["java" , "-jar", "/usr/src/License3jrepl-${project.version}-jar-with-dependencies.jar"]