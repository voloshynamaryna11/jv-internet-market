CREATE SCHEMA ` internet_shop` DEFAULT CHARACTER SET utf8;
CREATE TABLE `internet_shop`.`products`(
`product_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
`name` VARCHAR(255) NOT NULL,
`price` DOUBLE NOT NULL,
`deleted` TINYINT NOT NULL DEFAULT 0,
PRIMARY KEY (`product_id`));

CREATE TABLE `internet_shop`.`users` (
  `user_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(255) NOT NULL,
  `deleted` TINYINT NOT NULL DEFAULT 0,
  `user_login` VARCHAR(255) NOT NULL,
  `user_password` VARCHAR(255) NOT NULL,
  `salt` VARBINARY(16) NOT NULL,
  PRIMARY KEY (`user_id`));

CREATE TABLE `internet_shop`.`roles` (
  `role_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`role_id`));

CREATE TABLE `internet_shop`.`users_roles` (
  `role_id` BIGINT(11) NOT NULL,
  `user_id` BIGINT(11) NOT NULL,
  INDEX `role_id_fk_idx` (`role_id` ASC) VISIBLE,
  INDEX `user_id_fk_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `role_id_fk`
    FOREIGN KEY (`role_id`)
    REFERENCES `internet_shop`.`roles` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `user_id_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `internet_shop`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `internet_shop`.`orders` (
  `order_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(11) NOT NULL,
  `deleted` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`order_id`),
  INDEX `user_id_fk_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `orders_user_id_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `internet_shop`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `internet_shop`.`orders_products` (
  `order_id` BIGINT(11) NOT NULL,
  `product_id` BIGINT(11) NOT NULL,
  INDEX `orders_products_order_id_idx` (`order_id` ASC) VISIBLE,
  INDEX `orders_products_product_id_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `orders_products_order_id`
    FOREIGN KEY (`order_id`)
    REFERENCES `internet_shop`.`orders` (`order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `orders_products_product_id`
    FOREIGN KEY (`product_id`)
    REFERENCES `internet_shop`.`products` (`product_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `internet_shop`.`shopping_carts` (
  `shopping_cart_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(11) NOT NULL,
  `deleted` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`shopping_cart_id`),
  INDEX `shopping_carts_user_id_fk_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `shopping_carts_user_id_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `internet_shop`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `internet_shop`.`shopping_cart_products` (
  `shopping_cart_id` BIGINT(11) NOT NULL,
  `product_id` BIGINT(11) NOT NULL,
  INDEX `shopping_cart_products_id_fk_idx` (`shopping_cart_id` ASC) VISIBLE,
  INDEX `shopping_cart_products_product_id_fk_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `shopping_cart_products_id_fk`
    FOREIGN KEY (`shopping_cart_id`)
    REFERENCES `internet_shop`.`shopping_carts` (`shopping_cart_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `shopping_cart_products_product_id_fk`
    FOREIGN KEY (`product_id`)
    REFERENCES `internet_shop`.`products` (`product_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

ALTER TABLE `internet_shop`.`users`
ADD COLUMN `user_login` VARCHAR(255) NOT NULL AFTER `deleted`;

ALTER TABLE `internet_shop`.`users`
DROP COLUMN `product_price`,
ADD COLUMN `user_password` VARCHAR(255) NOT NULL AFTER `user_login`;

INSERT INTO roles (role_name) VALUES ('ADMIN');
INSERT INTO roles (role_name) VALUES ('USER');
