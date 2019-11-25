# spring-boot-boilerplate

Launch a spring boot based project quickly with basic features 

## run 

- find environment in `env` folder
- start mysql & redis `docker-compose --file ./env/local/docker-compose.yml up`
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

- CRUD for User and Role with Spring data 

## TODO

- Error redefine for controller advice
- ObjectMapper best practise
- Cache
- Authorization
- Docker https://wycode.cn/2019-09-20-docker_spring.html
- Change tomcat to Undertow
- Search with criteria  
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

## 参考

- https://auauz.net/dashboard
- http://doc.ruoyi.vip/ruoyi-vue/
