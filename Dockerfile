FROM openjdk:17
 ADD target/restaurantmanager-0.0.1-SNAPSHOT.jar RestaurantService.jar
 EXPOSE 8080
ENTRYPOINT ["java","-jar","RestaurantService.jar"]