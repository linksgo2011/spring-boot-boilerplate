# spring-boot-boilerplate

Launch a spring boot based project quickly with basic features 

## run 

- find environment in `env` folder
- start mysql & redis `docker-compose --file docker/docker-compose.yml up`
- run spring boot application `./gradlew bootRun`

## feature

- actuator
- JPA
- flyway
- redis session
- lombok
- Pagination
- Bean validation
- Password Hash

## Focus on 

- Authentication

## TODO

- Authorization
- Change tomcat to Undertow
- Search with criteria  
- Cache
- CRUD for User and Role
- DB R/W Splitting 
- Druid
- Table Relationship 
- UUID
- fileupload to cloud
- webflux
- Zerocode concurrence Testing by code
- API Testing
- Optimistic Lock by @Version 
- Database sharding
- API Documentation
- Deployment automation
- Jib

## Maybe

- Redis Lock
- API limitation
- JWT
- EFK
- QueryDsl
- HATEOAS
- K8S
