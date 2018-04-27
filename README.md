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

**NOTE**: Because Spring Security is in the dependency list a default user and password will be created automatically. In the output on the terminal  look for a line similar to:
```
Using generated security password: d63a6e17-f91e-404c-8f61-3489c4119923
```
and copy the generated password. A single user with username 'user' and that generated password has already been created for you.

Visit http://localhost:8080 in your browser and log in to the application.

## Test End Points
To test the CRUD end points use CURL.

### Users
#### Create a new User
```
curl -u user:d63a6e17-f91e-404c-8f61-3489c4119923 -i -XPOST -d "{\"username\":\"samwise\", \"password\":\"gamgee\" }" -H "Content-Type:application/json" http://localhost:8080/api/users
```
**Note:** The password changes everytime you restart the server
