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


### Live Server
This program is running live on [heroku](https://damp-coast-35809.herokuapp.com/api/)

## End Points

### Authentication
#### Signup
```
curl -X POST \
  https://damp-coast-35809.herokuapp.com/api/users/sign-up \
  -H 'Content-Type: application/json' \
  -d '{
	"username":"frodo",
	"password":"baggins"
}'
```

#### Login
```
curl -X POST \
  https://damp-coast-35809.herokuapp.com/login \
  -H 'Content-Type: application/json' \
  -d '{
	"username":"frodo",
	"password":"baggins"
}'
```
Response is a header, use this header for future requests:

```
Authorization Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmcm9kbyIsImV4cCI6MTUyNjE2NzQ1M30.CmXnlJaL2AwvEA5ZuKxZLYwhcLa-T2kXegcvz9tD4HnoDPO6Jhlk1l5IZhJYOnBUGzbOMw_TPIRZ6itTy-h5mA
```

### Notes
#### GET
```
curl -X GET \
  https://damp-coast-35809.herokuapp.com/api/notes/ \
  -H 'Authorization: Bearer <TOKEN>' \
```

#### POST
```
curl -X POST \
  https://damp-coast-35809.herokuapp.com/api/notes \
  -H 'Authorization: Bearer <TOKEN>' \
  -H 'Content-Type: application/json' \
  -d '{
	"title":"REST Docs",
	"content": "Spring REST Docs needed here",
	"tags":[1, 3, 2],
  "folder": 5
}'
```

*Note:* Tag ids and folder ids that do not exist are silently ignored

#### PUT
```
curl -X PUT \
  https://damp-coast-35809.herokuapp.com/api/notes/2 \
  -H 'Authorization: Bearer <TOKEN>' \
  -H 'Content-Type: application/json' \
  -d '{
	"title":"Cinco de Mayo",
	"content": "Great Scott!!",
	"tags":[2, 3, 7],
	"folder":4
}'
```

#### DELETE
```
curl -X DELETE \
  https://damp-coast-35809.herokuapp.com/api/notes/2 \
  -H 'Authorization: Bearer <TOKEN>' \
```  
