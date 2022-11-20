# automatic-irrigation-service
 Automatic Irrigation Service

# Introduction
As a irrigation system which helps the automatic irrigation of agricultural lands without human intervention, system has to
be designed to fulfil the requirement of maintaining and configuring the plots of land by the irrigation time slots and the
amount of water required for each irrigation period.
The irrigation system should have integration interface with a sensor device to direct letting the sensor to irrigate based o
n
the configured time slots/amount of water.

## Requirement 
### Function Requirement
- Plot of land - Group of APIs to add/config lands and irrigation details
- Automatic irrigation system - Job to communication with the sensor
- Alerting - Implemented by sending email notification alarm

### NonFunction Requirement
- It is not required to implement authentication or authorization
- It is required to implement resilience system when dealing with external APIs

## API Documentation
The documentation for the APIs is created using Open API - Swagger
Below URL represent the documentation API
http://localhost:8080/irrigation-service/api/v1/swagger-ui/index.html

## Assumptions
- It is not required to keep tracking of the registered land, and it's bordered
- This service is responsabile only on simple registration for the lands and irrigation config without consideration for the owners, creators for lands/irrigation config and payments
- The modification for irrigation system type means the new system already implemented in land and the old system will be kept active until switch to the new system.
- The user is setting the amount of water with its unit and period without any restriction but in real world, there should be restriction on the following:
  - Amount of water and duration based on the sensor type and the pressure of water which supported by sensor
  - The relation between amount of water, sensor pressure and duration between two irrigation periods
  - The irrigation type
  - The land type
  - The agricultural crop type
- The time utils in the database is in seconds
- There is no restriction for the update for irrigation configuration and lands
- Each land has single irrigation system
- The alarming notification will be handled by sending email to the admin user

## Technical comments
- The solution is using simple feign client with URL but in real microservice project, the Eureka Naming Service (Discovery Service) should be used with load balancer
- The configuration is kept in properties file but in real world, different files will be used for different profiles/environments.
- In case the request will be missing any of the missing parameter, the consumer will get 400 Bad Request but this can be improved to return the fields which is invalid and more validations can be added to the input requests 
- The irrigation history table includes some information in the irrigation table since the irrigation configuration can be modified so it is better to keep track which information sent to the sensor
- The interval and duration in seconds
- An improvement should be added to send notification with any failure in the complete system
- The system have margin time between the configured next irrigation time and the irrigation request to the sensor based on the SLA
- For supporting scaling, the cron job is using Use conditional update plus versioning to handle the scalability for the system and multithreading work on the same record
- The scheduler job can be improved to work with comparable future in threads
- The APIs are Richardson maturity model level 3


## Database
The used database in this project is H2 in-memory database.
To access the database, use the below URL with the username/password in application.yml file
http://localhost:8080/irrigation-service/api/v1/h2-console/login.jsp

## Actuator URL for monitor
http://localhost:8080/irrigation-service/api/v1/actuator

## Build instruction ###

* Prerequisite
  * Java 17 (Noting that, target java version inside pom.xml can be modified to Java 8 if required)
  * set `JAVA_HOME` env to java installation directory
  * Maven command (mvn)
  * Git

* Build command
  * mvn clean install

### Run instruction ###

* mvn spring-boot:run

### Run Maven Test instruction ###

* mvn test

## Test instruction ##

### Prerequisite
- Change the following configuration in application.yml with suitable information in case of testing the alerts
  - username: <login user to gmail smtp server>
  - password: <login password to smtp server>
  - to: <The receiver email address>
  - To be able to receive the email with gmail, kindly configure Google account by following the instruction in this page https://mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/

###
The following curl commands can be used for testing or the Postman exported collection under this path
resources/postman-collection/

### Get Land By Id
curl -i --location --request GET 'localhost:8080/irrigation-service/api/v1/lands/-1' \
--header 'Content-Type: application/json'

### Register Land
curl -i --location --request POST 'localhost:8080/irrigation-service/api/v1/lands' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "Corn Land",
"description": "Big corn land in CA",
"latitude": 59.3145508,
"longitude": 18.0682806,
"cultivatedArea": 2000.0,
"areaUnit": "SQUARE_METER",
"agriculturalCropType": "CORN"
}'

## Register land with irrigation configuration
curl -i --location --request POST 'localhost:8080/irrigation-service/api/v1/lands' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "Corn Land",
"description": "Big corn land in CA",
"latitude": 59.3145508,
"longitude": 18.0682806,
"cultivatedArea": 2000.0,
"areaUnit": "SQUARE_METER",
"agriculturalCropType": "CORN",
"irrigation": {
"irrigationType": "SPRINKLER",
"amountOfWater": 1000.0,
"liquidUnit": "GALLON",
"duration": 3600,
"interval": 21600.0,
"irrigationStatus": "ACTIVE",
"nextIrrigationAt": "2022-11-20T14:30:15.620801"
}
}'

### Update Land with Irrigation
curl --location --request PUT 'localhost:8080/irrigation-service/api/v1/lands' \
--header 'Content-Type: application/json' \
--data-raw '{
"id": -1,
"name": "Corn Land",
"description": "Big corn land in CA",
"latitude": 59.3145508,
"longitude": 18.0682806,
"cultivatedArea": 2500.0,
"areaUnit": "SQUARE_METER",
"agriculturalCropType": "CORN",
"irrigation": {
"id": -1,
"irrigationType": "SPRINKLER",
"amountOfWater": 1000.0,
"liquidUnit": "GALLON",
"duration": 3600,
"interval": 21600.0,
"irrigationStatus": "ACTIVE",
"nextIrrigationAt": "2022-11-20T14:52:23.634137"
}
}'

### Get All Lands with its irrigation details
curl -i --location --request GET 'localhost:8080/irrigation-service/api/v1/lands' \
--header 'Content-Type: application/json'

### Get Irrigation by Id
curl -i --location --request GET 'localhost:8080/irrigation-service/api/v1/irrigations/-1' \
--header 'Content-Type: application/json'

### Create New Irrigation
curl -i --location --request POST 'localhost:8080/irrigation-service/api/v1/irrigations' \
--header 'Content-Type: application/json' \
--data-raw '{
"landId": -1,
"irrigationType": "SPRINKLER",
"amountOfWater": 1000.0,
"liquidUnit": "GALLON",
"duration": 3600,
"interval": 21600.0,
"irrigationStatus": "ACTIVE",
"nextIrrigationAt": "2022-11-20T14:30:15.620801",
"createdAt": "2022-11-20T14:30:15.620801",
"updatedAt": "2022-11-20T14:30:15.620801"
}'

### Update existing irrigation
curl --location --request PUT 'localhost:8080/irrigation-service/api/v1/irrigations' \
--header 'Content-Type: application/json' \
--data-raw '{
"id": 1,
"irrigationType": "SPRINKLER",
"amountOfWater": 1200.0,
"liquidUnit": "GALLON",
"duration": 3600,
"interval": 21600.0,
"irrigationStatus": "ACTIVE",
"nextIrrigationAt": "2022-11-20T14:30:15.620801",
"createdAt": "2022-11-20T14:36:03.4079959",
"updatedAt": "2022-11-20T14:36:03.4079959"
}'