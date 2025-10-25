# Plant Growth Service

A Spring Boot application for managing plant growth monitoring and control.

## Technologies

- Java 21
- Spring Boot 3.5.7
- Spring Security with OAuth2
- Spring Data JPA
- PostgreSQL
- Docker
- Maven
- Lombok

## Prerequisites

- JDK 21
- Maven 3.9.x
- Docker (optional)
- PostgreSQL

## Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.7/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.5.7/maven-plugin/build-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.5.7/reference/web/servlet.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/3.5.7/reference/data/sql.html#data.sql.jpa-and-spring-data)
* [Spring Security](https://docs.spring.io/spring-boot/3.5.7/reference/web/spring-security.html)
* [OAuth2 Resource Server](https://docs.spring.io/spring-boot/3.5.7/reference/web/spring-security.html#web.security.oauth2.server)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/3.5.7/reference/using/devtools.html)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/3.5.7/reference/actuator/index.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)

### Local Development Setup

1. Clone the repository:
```bash
git clone <repository-url>
cd plant-growth-service
```

2. Configure the database:
   - Create a PostgreSQL database
   - Update `src/main/resources/application.properties` with your database configuration

3. Build the project:
```bash
./mvnw clean install
```

4. Run the application:
```bash
./mvnw spring-boot:run
```

The application will be available at `http://localhost:8080`

### Docker Deployment

1. Build the Docker image:
```bash
docker build -t plant-growth-service .
```

2. Run the container:
```bash
docker run -p 8080:8080 plant-growth-service
```

## Configuration

### Application Properties

Configure the application by modifying `src/main/resources/application.properties`:

```properties
# Server Configuration
server.port=8080

# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# OAuth2 Configuration
# Add your OAuth2 configuration here
```

## Security

The application uses Spring Security with OAuth2 for authentication and authorization. Configure your OAuth2 provider settings in the application properties.

## API Documentation

The API endpoints will be documented here. Example endpoints:

- `GET /api/users` - Get all users
- `POST /api/users` - Create a new user
- More endpoints to be documented...

## Testing

Run the tests using Maven:

```bash
./mvnw test
```

## Building for Production

To build for production:

```bash
./mvnw clean package -DskipTests
```

The built JAR file will be in the `target` directory.

## Container Environment Variables

When running in Docker, you can configure the application using environment variables:

```bash
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/plantdb \
  -e SPRING_DATASOURCE_USERNAME=dbuser \
  -e SPRING_DATASOURCE_PASSWORD=dbpass \
  plant-growth-service
```

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request