# Dockerizing-producer
Demo app for Docker workshop

# Dependencies
* RabbitMQ (docker hub)
* MongoDB (docker hub)
* Consumer App (https://hub.docker.com/r/ferdezo/discount-consumer)

# Run

## Using compose
```
docker-compose build
```
```
docker-compose up
```
# Standalones
Mongodb
```
docker run -p 27017-27019:27017-27019 mongo:4  
```

RabbitMQ
```
docker run -p 5672:5672 -p 15672:15672 rabbitmq:management  
```

Producer
```
docker run -p 8080:8080 -e ENV=def discount-producer  
```

Beware running all containers separately without dedicated network won't make them able to communicate