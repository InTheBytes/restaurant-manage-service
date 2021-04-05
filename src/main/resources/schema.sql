
CREATE TABLE IF NOT EXISTS `stage_hours`(
	`hours_id` INT PRIMARY KEY AUTO_INCREMENT,
	`monday_open` TIME,
	`monday_close` TIME,
	`tuesday_open` TIME,
	`tuesday_close` TIME,
	`wednesday_open` TIME,
	`wednesday_close` TIME,
	`thursday_open` TIME,
	`thursday_close` TIME,
	`friday_open` TIME,
	`friday_close` TIME,
	`saturday_open` TIME,
	`saturday_close` TIME,
	`sunday_open` TIME,
	`sunday_close` TIME
);

CREATE TABLE IF NOT EXISTS `stage_user`(
	`user_id` INT PRIMARY KEY AUTO_INCREMENT,
	`username` VARCHAR(45) NOT NULL,
	`password` VARCHAR(81) NOT NULL,
	`email` VARCHAR(45) NOT NULL,
	`phone` VARCHAR(45) NOT NULL,
	`first_name` VARCHAR(45),
	`last_name` VARCHAR(45)
);

CREATE TABLE IF NOT EXISTS `stage_restaurant`(
	`restaurant_id` INT PRIMARY KEY AUTO_INCREMENT,
	`manager_id` INT NOT NULL,
	`name` VARCHAR(45) NOT NULL,
	`hours_id` INT NOT NULL
);

ALTER TABLE `stage_restaurant`
	ADD FOREIGN KEY (`manager_id`)
	REFERENCES `stage_user`(`user_id`);
	
ALTER TABLE `stage_restaurant`
	ADD FOREIGN KEY (`hours_id`)
	REFERENCES `stage_hours`(`hours_id`);


CREATE TABLE IF NOT EXISTS `stage_location`(
	`location_id` INT PRIMARY KEY AUTO_INCREMENT,
	`street` VARCHAR(45) NOT NULL,
	`street_addition` VARCHAR(45),
	`unit` VARCHAR(45) NOT NULL,
	`city` VARCHAR(45) NOT NULL,
	`state` VARCHAR(45) NOT NULL,
	`zip_code` INT NOT NULL
);

CREATE TABLE IF NOT EXISTS `stage_restaurant_location`(
	`restaurant_id` INT NOT NULL,
	`location_id` INT NOT NULL
);

ALTER TABLE `stage_restaurant_location`
	ADD FOREIGN KEY (`restaurant_id`)
	REFERENCES `stage_restaurant`(`restaurant_id`);
	
ALTER TABLE `stage_restaurant_location`
	ADD FOREIGN KEY (`location_id`)
	REFERENCES `stage_location`(`location_id`);

CREATE TABLE IF NOT EXISTS `stage_genre`(
	`genre_id` INT PRIMARY KEY AUTO_INCREMENT,
	`genre_title` VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS `stage_restaurant_genre`(
	`restaurant_id` INT NOT NULL,
	`genre_id` INT NOT NULL
);

ALTER TABLE `stage_restaurant_genre`
	ADD FOREIGN KEY (`restaurant_id`)
	REFERENCES `stage_restaurant`(`restaurant_id`);

ALTER TABLE `stage_restaurant_genre`
	ADD FOREIGN KEY (`genre_id`)
	REFERENCES `stage_genre`(`genre_id`);

CREATE TABLE IF NOT EXISTS `stage_menu`(
	`menu_id` INT PRIMARY KEY AUTO_INCREMENT,
	`title` INT NOT NULL
);

CREATE TABLE IF NOT EXISTS `stage_restaurant_menu`(
	`restaurant_id` INT NOT NULL,
	`menu_id` INT NOT NULL
);

ALTER TABLE `stage_restaurant_menu`
	ADD FOREIGN KEY (`restaurant_id`)
	REFERENCES `stage_restaurant`(`restaurant_id`);
	
ALTER TABLE `stage_restaurant_menu`
	ADD FOREIGN KEY (`menu_id`)
	REFERENCES `stage_menu`(`menu_id`);

CREATE TABLE IF NOT EXISTS `stage_food`(
	`food_id` INT PRIMARY KEY AUTO_INCREMENT,
	`name` VARCHAR(45) NOT NULL,
	`price` FLOAT NOT NULL,
	`description` VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS `stage_menu_food`(
	`menu_id` INT NOT NULL,
	`food_id` INT NOT NULL
);

ALTER TABLE `stage_menu_food`
	ADD FOREIGN KEY (`menu_id`)
	REFERENCES `stage_menu`(`menu_id`);
	
ALTER TABLE `stage_menu_food`
	ADD FOREIGN KEY (`food_id`)
	REFERENCES `stage_food`(`food_id`);

CREATE TABLE IF NOT EXISTS `stage_customization`(
	`customize_id` INT PRIMARY KEY AUTO_INCREMENT,
	`description` VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS `stage_food_customization`(
	`customization_id` INT NOT NULL,
	`food_id` INT NOT NULL,
	`charge` DECIMAL NOT NULL
);

ALTER TABLE `stage_food_customization`
	ADD FOREIGN KEY (`customization_id`)
	REFERENCES `stage_customization`(`customize_id`);
	
ALTER TABLE `stage_food_customization`
	ADD FOREIGN KEY (`food_id`)
	REFERENCES `stage_food`(`food_id`);