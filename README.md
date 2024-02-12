# GitHub Repository Fetcher

This application serves as an API to fetch GitHub repositories for a given user, providing details such as repository name, owner login, branches, and their last commit SHA. It ensures that only non-fork repositories are listed, adhering to the provided acceptance criteria.

## How to Run

### Option 1: Docker Image

1. Make sure you have Docker installed on your system.
2. Pull the Docker image from my Docker Hub repository.
   ```bash
   docker pull saiq123/githubapi
4. Execute the Docker container to run the application.
   ```bash
   docker run -p 8080:8080 saiq123/githubapi

### Option 2: Local Setup

1. Clone this repository to your local machine.
2. Navigate to the cloned repository directory.
3. Run the application using Maven with the command:
   ```bash
   ./mvnw spring-boot:run
4. Ensure you have JDK 21 installed on your system.
5. Before running, make sure to set up your GitHub token in the `application.properties` file. Find the `application.properties` file in the project directory and insert your GitHub token in the designated place.
6. Ensure you have Java 21 installed on your computer.

## API Endpoints

- `/repositories/{username}`: Retrieves the list of non-fork GitHub repositories for the specified user.

## Dependencies

- Spring Boot: Provides the foundation to create stand-alone, production-grade Spring-based applications.
- Spring Web: Simplifies building web applications using the Spring framework.
- RestTemplate: Used for consuming RESTful web services.
- Lombok: Reduces boilerplate code for Java classes.
- Docker: Used for containerization and easier deployment.

## Error Handling

The application handles errors related to the inability to find a user. If the specified user is not found on GitHub, the server will return a response code 404.

## Testing

To test the application, open a web browser and enter the address `http://localhost:8080/repositories/{username}`, where `{username}` is the GitHub username whose repositories you want to fetch.
