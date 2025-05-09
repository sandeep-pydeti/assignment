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




