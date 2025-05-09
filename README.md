This is a application demonstrating a REST API service integrated with a PostgreSQL database and Kafka for event-driven communication. The system is designed to be scalable, containerized, and easy to deploy locally using Docker Compose.

Docker Compose: Used for simplified local development.
PostgreSQL: Chosen as the primary database 
Kafka: Used for asynchronous messaging in between microservices.
REST API: Built with a lightweight framework (springboot) for simplicity and performance.

Prerequisites:
Docker: Install Docker Desktop (or Docker CLI for Linux) to run containers.
Docker Compose: Included with Docker Desktop;
Git: To clone the repository.
curl or Postman: For testing REST API endpoints.
database: pgadmin or dbevaer

Verify Services:
API: Accessible at http://localhost:9004(User service)
API: Accessible at http://localhost:9005(Jouneral service)

Userservice endpoints:
1-/apica/user/register - To register the user
2-/apica/user/getUser/{userId} -  To get the user based on the userId
3-/apica/getAllUsers-To get the allUsers
4-/apica/update/Users- to update the users
5-/apica/delete/user - To delete the users

Jouneral Service endpoints
1-/apica/getEvent/{userId}-To get a event based on the UserId
2-/apica/getAllEvents- To get the all events
3-/apica/delete/Events- To delete the events 


Curls:
UserService
1-register user - curl --location 'http://localhost:9004/apica/user/register' \
--header 'Content-Type: application/json' \
--data-raw '{
    "fullName":"sandeep",
    "phoneNumber":34567877654,
    "username":"sa44",
    "email":"sandb@gmail.com",
    "password":"adsssssssssssssssf",
    "role":"admin"
}'

2-To get the user based on the userId curl --location 'http://localhost:9004/apica/user/getUser/26ff651d-83b4-4e56-a9e3-f7ef3aaeac2d' \
--header 'Content-Type: application/json'

3-To get all users curl --location 'http://localhost:9004/apica/getAllUsers' \
--data ''

4-To update the user curl --location --request PUT 'http://localhost:9004/apica/update/Users?userId=26ff651d-83b4-4e56-a9e3-f7ef3aaeac2d' \
--header 'Content-Type: application/json' \
--data-raw '{
    "fullName":"sandeep",
    "phoneNumber":34567877654,
    "username":"sa44",
    "email":"sandb@gmail.com",
    "password":"adsssssssssssssssf",
    "role":"admin"
}'

5-To delete the user curl --location --request DELETE 'http://localhost:9004/apica/delete/user?userId=fd1af97e-cc1a-43f5-a029-d31cfb49d353' \
--header 'Content-Type: application/json'

Jouneral service 
1- To get event based on UserId curl --location 'http://localhost:9005/apica/getEvent/26ff651d-83b4-4e56-a9e3-f7ef3aaeac2d' \
--header 'Content-Type: application/json'
2-To get all events curl --location 'http://localhost:9005/apica/getAllEvents' \
--header 'Content-Type: application/json'
3- To delete event curl --location --request DELETE 'http://localhost:9005/apica/delete/Events?userId=26ff651d-83b4-4e56-a9e3-f7ef3aaeac2d' \
--header 'Content-Type: application/json'
