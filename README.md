| [![CI Actions Status](https://github.com/fhhagenberg-sqe-mcm-ws20/elevator-control-center-team-e/workflows/CI/badge.svg)](https://github.com/fhhagenberg-sqe-mcm-ws20/elevator-control-center-team-e/actions) | [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=fhhagenberg-sqe-mcm-ws20_elevator-control-center-team-e&metric=alert_status)](https://sonarcloud.io/dashboard?id=fhhagenberg-sqe-mcm-ws20_elevator-control-center-team-e) | [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=fhhagenberg-sqe-mcm-ws20_elevator-control-center-team-e&metric=coverage)](https://sonarcloud.io/dashboard?id=fhhagenberg-sqe-mcm-ws20_elevator-control-center-team-e) | [![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=fhhagenberg-sqe-mcm-ws20_elevator-control-center-team-e&metric=ncloc)](https://sonarcloud.io/dashboard?id=fhhagenberg-sqe-mcm-ws20_elevator-control-center-team-e) |
|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|

# Elevator Control Center (Team E)

### Team

- Harald Pinsker s1910455003
- Paul Schmutz s1910455013
- Roman Socovka s1910455006

### Prerequisites

- [x] Java 13 JDK (e.g. Oracle or OpenJDK).
- [x] Maven 3. (If you use an IDE like Eclipse or IntelliJ, Maven is already included.)
	- see http://maven.apache.org/install.html
- [x] An IDE or code editor of your choice.

### Instructions

#### Option A) Download executable JAR

- Make sure you have Java 13 JRE (or JDK) installed
- Download the [latest JAR](https://github.com/fhhagenberg-sqe-mcm-ws20/elevator-control-center-team-e/releases/latest/download/elevator-control-group-e-1.0.jar)
- Run the JAR with the command `java -jar <jar-file.jar>`
- JAR runs on Windows, Linux and Mac

#### Option B) Run with maven

- Run `mvn compile javafx:run`

#### Option C) Build JAR with maven

- Make sure you have all prerequisites above installed
- Open `pom.xml` and adapt it for your purposes:

```xml
<!-- For Windows builds add: -->
<!--
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-graphics</artifactId>
    <version>${javafx.version}</version>
    <classifier>win</classifier>
</dependency>
-->

<!-- For Linux builds add: -->
<!--
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-graphics</artifactId>
    <version>${javafx.version}</version>
    <classifier>linux</classifier>
</dependency>
-->

<!-- For Mac builds add: -->
<!--
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-graphics</artifactId>
    <version>${javafx.version}</version>
    <classifier>mac</classifier>
</dependency>
-->
<!-- Note: For cross-platform builds add all three of the previous dependencies -->
```

- Run `mvn clean package`
- You can find a fat JAR in the target directory (elevator-control-group-e-1.0.jar)
