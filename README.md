# PRPO Room Counter

Simple microservice application developed as part of `Software development procedures` course. Works together with [prpo-frontend](../../../prpo-frontend) and [prpo-history](../../../prpo-history)

## Build and run
```
mvn clean package
docker-compose up -d keycloak
# wait for keycloak to start
docker-compose up -d
java -jar api/target/api-1.0.0-SNAPSHOT.jar
```

## ENV
```
RAPID_API_KEY = "rapid api's key"
RAPID_API_HOST = "rapid api's host"
```
