FROM openjdk:17
ADD target/restaurantmanager-0.0.1-SNAPSHOT.jar RestaurantService.jar
EXPOSE 8080
ENTRYPOINT ["java","-Dspring.datasource.hikari.maximum-pool-size=1","-jar","RestaurantService.jar"]
