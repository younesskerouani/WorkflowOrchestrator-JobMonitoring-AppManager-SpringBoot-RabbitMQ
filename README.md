## Overview
Workflow Orchestrator is a microservices-based application for orchestrating and monitoring workflows. It consists of three main components:

1. **AppManager Microservice**: Manages application configurations and web page orchestration from a low-code UI
2. **Job Microservice**: Monitors file transformations (Electronic Data Interchange)
3. **Eureka Server**: Service registry for microservice discovery and communication

## Architecture

### AppManager Microservice
- **Purpose**: Manages web page configurations and UI orchestration
- **Technology Stack**:
  - Spring Boot
  - MongoDB (database: apps, port: 27017)
  - REST API (port: 9099)
- **Key Components**:
  - Controller layer: Manages API endpoints for app configuration
  - Service layer: Business logic for application management
  - Repository layer: Data access for MongoDB
  - Model: App data models
  - Exception handling

### Job Microservice
- **Purpose**: Monitors file transformations (EDI) and processes job events
- **Technology Stack**:
  - Spring Boot
  - RESTful APIs
- **Key Components**:
  - File monitoring: Watches for file changes and triggers processing
  - Job controller: Manages job-related operations
  - Job service: Implements business logic for job processing
  - Job repository: Persists job data
  - Messaging configuration: Handles asynchronous communication
  - Job receiver: Processes incoming job events

### Eureka Server
![eureka](https://github.com/user-attachments/assets/eb76c80c-dc0b-4c1a-80d5-df9fef3d5b76)

- **Purpose**: Service registry and discovery
- **Port**: 9090
- **Configuration**:
  - Service registry for microservices
  - Client configuration for service discovery

## Communication Flow
1. Services register with Eureka Server
2. AppManager configures UI workflows and application settings
3. JobMicroservice monitors files and processes transformations
4. Services communicate through REST APIs and messaging

## Getting Started

### Prerequisites
- JDK 11+
- Maven
- MongoDB

### Running the Services
1. Start MongoDB:
   ```
   mongod --port 27017
   ```

2. Start Eureka Server:
   ```
   cd eureka-server
   mvn spring-boot:run
   ```

3. Start AppManager Microservice:
   ```
   cd AppManager_microservice
   mvn spring-boot:run
   ```

4. Start Job Microservice:
   ```
   cd JobMicroservice
   mvn spring-boot:run
   ```

## Endpoints
- Eureka Server: http://localhost:9090
- AppManager: http://localhost:9099
- Job Microservice: (Configured per deployment)

## Project Structure
The project follows standard Maven project structure with separation of concerns:
- `controller`: REST API endpoints
- `service`: Business logic
- `repository`: Data access
- `model`: Data models
- `config`: Application configuration
- `exception`: Exception handling

## Development
This project uses a microservices architecture to provide:
- Scalability: Each service can scale independently
- Resilience: Failure in one service doesn't affect others
- Technology diversity: Different technologies can be used per service
- Deployment flexibility: Services can be deployed independently

## Monitoring and Management
- Service registry available through Eureka dashboard
- Application monitoring through Spring Boot Actuator
