## Criar rede docker para sistema locadora de carros
```
docker network create djava-net
```

## Baixar imagem do banco MySQL
```
docker pull mysql
```

## Criar um container utilizando a imagem MySQL e a rede locadora de carros
```
docker run --name dsjava-mysql --network djava-net -e MYSQL_ROOT_PASSWORD=12345678 -d mysql:latest
```

## Gerar .jar do projeto 
```
Na pasta raiz do projeto (LocadoraCarros), executar o comando:

./mvnw clean package -DskipTests
```

## Gerar a imagem com o .jar da aplicação a partir do docker file
```
Na pasta raiz do projeto (LocadoraCarros), executar o comando:

docker build -t locadora-carros:v1 .
```

## Gerar o container da aplicação utilizando a rede locadora de carros
```
docker run -p 8888:8888 --name locadora-carros --network djava-net locadora-carros:v1
```

## Link para realização de testes da API
```
http://localhost:8888/swagger-ui/index.html
```


