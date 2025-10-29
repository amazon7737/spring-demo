docker-compose down
docker rmi spring-app-image:latest
./gradlew build
docker build -t spring-app-image:latest .
docker-compose up -d
