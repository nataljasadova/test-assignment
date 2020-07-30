# End-to-end tests for the Person REST API using REST Assured framework

Mocking REST API using WireMock standalone

## Prerequisites

This project uses junit, lombok, maven, log4j

Recomended IDE : IntelliJ with enabled lombok plugin, also enabled annotation processing is required.
Java 8

## Configuring

After cloning this repo...

### Wiremock standalone

cd into test-assignment\src\test\resources

Start wiremock... java -jar wiremock-standalone-2.27.0.jar --verbose --port 8080


## Running the tests

```
mvn test
```

## Reporting

```
mvn site
```
And

```
mvn surefire-report:report
```
Report can be found in test-assignment\target\site\surefire-report
