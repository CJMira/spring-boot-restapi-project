# spring-boot-restapi-project

Demo project of a RestAPI with Spring Boot. It is implemented with a PostgreSQL database using Docker.

The example is a game platform with different Companies and their published Games. Functionalities are split in layers: presentation/controllers, services, persistance/data access JPA. The structure followed is based on [Devtiro](https://github.com/devtiro)'s Spring Boot courses' methodology.

Start docker container:
```
docker-compose up
```

Run the application:
```
./mvnw spring-boot:run
```
