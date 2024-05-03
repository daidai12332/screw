CREATE TABLE IF NOT EXISTS `receive_data` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(10) NOT NULL,
  `status` varchar(10) DEFAULT NULL,
  `order_number` varchar(20) NOT NULL,
  `current` double DEFAULT NULL,
  `pass` int DEFAULT '0',
  `ng` int DEFAULT '0',
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `screw`.`screw_material` (
  `order_no` INT NOT NULL DEFAULT 0,
  `name` VARCHAR(100) NULL,
  `aim` INT NOT NULL DEFAULT 0,
  `weight` INT NOT NULL DEFAULT 0,
  `raw` MEDIUMTEXT NOT NULL,
  `process` MEDIUMTEXT,
  PRIMARY KEY (`order_no`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `screw`.`equipment` (
  `name` VARCHAR(20) NOT NULL,
  `purchase_date` DATE NULL,
  PRIMARY KEY (`name`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
  
ALTER TABLE `screw`.`equipment` 
ADD COLUMN `data_date` DATE NOT NULL AFTER `name`,
ADD COLUMN `data_avg` MEDIUMTEXT NULL AFTER `purchase_date`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`name`, `data_date`);