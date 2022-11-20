# automatic-irrigation-service
 Automatic Irrigation Service

## API Documentation
The documentation for the APIs is created using Open API - Swagger
Below URL represent the documentation API
http://localhost:8080/irrigation-service/api/v1/swagger-ui/index.html

## Database
The used database in this project is H2 in-memory database.
To access the database, use the below URL with the username/password in application.yml file
http://localhost:8080/irrigation-service/api/v1/h2-console/login.jsp

## Test
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

## Update existing irrigation
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