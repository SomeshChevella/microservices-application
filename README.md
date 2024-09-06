# Microservices Application

This application is built using a microservices architecture and includes the following services:

- **Question Service**
- **Quiz Service**
- **Service Registry**
- **API Gateway**

## Overview

The application utilizes the following technologies and frameworks:

- **Eureka Client**
- **Eureka Server**
- **OpenFeign**
- **Spring Cloud API Gateway**

The application is developed based on a YouTube tutorial by Naveen Reddy.

## Services

### Question Service

The Question Service provides endpoints to manage questions and categories.

#### Endpoints

- `GET /question/allQuestions`  
  Retrieves all questions from the service.

- `GET /question/category/{categoryName}`  
  Retrieves questions based on the specified category.

- `POST /question/add`  
  Adds a new question to the service.

#### Internal Endpoints (Used by Quiz Service)

- `POST /quiz/create`  
  Creates a new quiz. This endpoint is used by the Quiz Service.

- `GET /get/{id}`  
  Retrieves a specific question by ID. This endpoint is used by the Quiz Service.

- `POST /submit/{id}`  
  Submits a specific question by ID. This endpoint is used by the Quiz Service.

### Quiz Service

The Quiz Service manages quizzes and their submissions.

#### Endpoints

- `POST /quiz/submit/{id}`  
  Submits a quiz with the specified ID.

- `POST /quiz/create`  
  Creates a new quiz.

- `GET /quiz/get/{id}`  
  Retrieves a quiz by ID.

#### Integration

The Quiz Service uses OpenFeign to communicate with the Question Service for processing requests.

### Service Registry

The Service Registry is responsible for registering and discovering microservices.

#### Configuration

- **Dependencies**: Add `eureka-server` dependency.
- **application.properties**:

    ```properties
    spring.application.name=service-registry
    server.port=8761

    eureka.instance.hostname=localhost
    eureka.client.fetch-registry=false
    eureka.client.registry-with-eureka=false
    ```

- **Annotation**: Annotate the main application class with `@EnableEurekaServer`.

### API Gateway

The API Gateway handles routing and load balancing for the microservices.

#### Configuration

- **Dependencies**: Add `cloud-api-gateway` and `eureka-client` dependencies.
- **application.properties**:

    ```properties
    spring.application.name=api-gateway
    server.port=8765

    spring.cloud.gateway.discovery.locator.enabled=true
    spring.cloud.gateway.discovery.locator.lower-case-service-id=true
    ```

## Getting Started

### Prerequisites

- Java 11 or later
- Maven
- Docker (optional, for containerization)

### Running the Application

1. **Start the Service Registry**:  
   Run the `Service Registry` application. This will be available on `http://localhost:8761`.

2. **Start the Question Service**:  
   Run the `Question Service` application. It will register itself with the Service Registry.

3. **Start the Quiz Service**:  
   Run the `Quiz Service` application. It will register itself with the Service Registry.

4. **Start the API Gateway**:  
   Run the `API Gateway` application. It will route requests to the appropriate services.

### Accessing the Services

- **Service Registry**:  
  Access the Eureka dashboard at `http://localhost:8761`.

- **API Gateway**:  
  Use the API Gateway to interact with the services. The default port is `8765`.

## Acknowledgements

This application is based on a tutorial by Naveen Reddy. Thanks to the open-source community for providing the tools and libraries used in this project.
