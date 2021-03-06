## Car Rental Project
### About
It is a simple backend api of car rental service written in Java with use of Hibernate Framework
and Spark framework with a simple database in SQLite.

Api provides: adding, editing, deleting, fetching cars as well as users.
Single car can be rented once until return. Client can rent multiple cars, 
where history of rented cars is stored with rent and return date.

### Database Schema
The database is constructed of three tables with corresponding entities in code: Car, 
Client and RentedCar which is also presented in a schema below.

![image](readme_resources/db.png)

### Available Endpoints
Here is a list of all available endpoints with short descriptions. 
Testing requests for Postman can be found in the workspace by using button below.

[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/11457881-114dc96d-4eb7-4679-8bb9-1ba45f74fe05?action=collection%2Ffork&collection-url=entityId%3D11457881-114dc96d-4eb7-4679-8bb9-1ba45f74fe05%26entityType%3Dcollection%26workspaceId%3D0dd04768-a677-4050-8371-8b4fc452a7b4#?env%5BRental%20Car%5D=W3sia2V5Ijoic3RhcnRfdXJsIiwidmFsdWUiOiJodHRwOi8vbG9jYWxob3N0OjUwMDAvYXBpIiwiZW5hYmxlZCI6dHJ1ZX1d)

All routes have a root path `/api` meaning that accessing for e.g. resource `x` would be described by `http://url.com/api/x`

All responses have `Content-Type` set to `aplication/json`, and all specified `4XX` responses have json message.

Deleting client or car will delete all of their history.

`GET /car` - return all cars stored with code `200` or empty array if none found.

`GET /car/:id`- returns car specified by `:id` with code `200` or `404` if car not found.

`POST /car` - adds new car using query parameters `brand` and `model`, returns code`201` with created resource or `400` if invalid parameters were set.

`PATCH /car/:id` - updates existing car specified by `:id` using query parameters `brand` and `model`, returns `200` with resource or `404` if car not found.

`DELETE /car/:id` - removes car specified by `:id`, returns `204` or `404` if car not found.

`GET /client` - return all clients with code `200` or empty array if none found. If given query parameters `firstName` and `lastName` will lookup for record and return `200` if found or `404` if not.

`GET /client/:id` - return client specified by `:id` with code `200` or `404` if client not found.

`POST /client` - adds new client using query parameters `firstName` and `lastName`, return code `201` with created resource or `400` if invalid parameters were set.

`PATCH /client/:id` - updates existing client specified by `:id` using query parameters `firstName` and `lastName`, returns `200` with resource or `404` if client not found.

`DELETE /client/:id` - removes client specified by `:id`, returns `204` or `404` if client not found.

`GET /rental` - return all rented cars history with code `200` or empty array if none found.

`GET /rental/:id` - returns a record specified by`:id` with code `200` or `404` if record not found.

`POST /rental` - rents a car using query parameters `carId` and `clientId`, returns `200` and a resource. Returns `404` if identifiers were wrong or `400` if selected car is already rented.

`POST /rental/:id` - return a car using `:id` given when renting car, returns `200` and updated record or `404` if record not found.

### Requirements

Java 10+ is required to run packaged file. To build JDK in same version as well as IDE with maven.

### Running

To run application simply download the latest release from github releases and run command

```shell
 > java -jar Car_Rental_Project.jar
 ```

If not provided with `sqlite.db` will create it in where it was run. The `sqlite.db` can be provided int the same folder as running folder 
meaning that if app was run in `C:/Folder` the database should be in `C:Folder`.

Application in runtime prints log into the console, such as starting configuration or accessing specific endpoint with corresponding method.

### Building

To build app yourself import this project into any IDE that provides maven as maven project,
allow it to download all dependencies and execute default build. Also `mvn clean build` can be used.
