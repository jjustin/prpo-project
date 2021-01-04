# PRPO Room Counter

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