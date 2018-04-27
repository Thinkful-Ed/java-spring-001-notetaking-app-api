# RESTful Notetaking API
__java-spring-001-notetaking-app-api__

Note-taking app API for Thinkful's JAVA-SPRING-001 module

## Requirements

- Java 8 or higher
- Gradle 4.6 or higher

## Getting started
Clone this repository and run the command

```
gradle assemble
```

To download all dependencies, compile and assemble the artifacts for this application. 

### Configure Database
Create a database and a user. Edit the _application.properties_ file with the credentials for your database. For instance:

```
spring.datasource.url = jdbc:postgresql://localhost:5432/<YOUR_DATABASE_NAME>
spring.datasource.username = <YOUR_DATABASE_USER>
spring.datasource.password = <YOUR_DATABASE_USER_PASSWORD>
```

### Run the application
Run the command

```
gradle bootRun
```
This starts the embedded Tomcat server on port 8080.


~~**NOTE**: A single user with username 'samwise' and password 'gamgee' has been configured in the _application.properties_ file.~~ **Security temporarily disabled**

Visit http://localhost:8080 in your browser and log in to the application.

## Test End Points
To test the CRUD end points use CURL.

### Users
#### Create a new User
```
curl -u samwise:gamgee -i -XPOST -d "{\"username\":\"frodo\", \"password\":\"baggins\" }" -H "Content-Type:application/json" http://localhost:8080/api/users
```

