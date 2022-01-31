# Mediscreen (A RETRAVAILLER)
Back end of the application which has been refactored into microservices with Java and Spring Boot framework. TourGuide is an interactive guide that allow users to see the nearby tourist attractions. They also receive rewards for visiting them.

## Getting Started
### Prerequisites

A list of technologies you need in order to install Mediscreen application :

- Java 1.8
- Maven 3.8.1
- Spring Boot 2.1.6 RELEASE
- Feign springCloudVersion: Greenwich.SR6
- MySQL 8.0.22
- MongoDB 5.0.5
- Docker 4.1.1
- Jacoco 0.8.4

### Installing

A step by step series of examples that tell you how to get a development environment running:

1.Install Java:

https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html

2.Install Maven:

https://maven.apache.org/install.html

3.Install Spring Boot:

https://spring.io/guides/gs/spring-boot/ : a step by step tutorial.

https://start.spring.io : please select Spring Web dependency before installing Spring Boot.


4.Install Feign:

https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-openfeign

5.Install MySQL

https://dev.mysql.com/downloads/mysql/

6.Install MongoDB

https://docs.mongodb.com/manual/installation/

7.Install Docker:

https://docs.docker.com/desktop/

8.Install Jacoco:

https://mvnrepository.com/artifact/org.jacoco/org.jacoco.agent


### Running App

Import the code into an IDE of your choice and run Mediscreen microservices by following the above steps :
1. Open the terminal of your IDE
2. Choose the microservice you want to run : `cd microservice-patient` for example
3. Execute the command below :

   `mvn spring-boot:run`
  

### Testing

This application has unit tests written which need to be triggered from maven-surefire plugin. Please use JaCoCo for the code coverage.

1. To run the unit tests from Maven, execute the command below.

`mvn test`

2. To run code coverage with JaCoCo from Maven, execute the following command

`mvn verify`

3. To generate the Surefire Report, please use the command below

`mvn site`

### Containerization with Docker (A RETRAVAILLER)

To build Docker container, please execute the command below:

`docker build -t <nameOfYourDockerImage> .` 

ex : `docker build -t microservice-patient .` 

If you want to run the container, please execute the command below