SET sql_safe_updates=0;

CREATE TABLE IF NOT EXISTS `screw`.`receive_data` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(10) NOT NULL,
  `status` varchar(10) DEFAULT NULL,
  `order_number` varchar(20) NOT NULL,
  `current` double DEFAULT NULL,
  `pass` int DEFAULT '0',
  `ng` int DEFAULT '0',
  `type` VARCHAR(45) NOT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `screw`.`order` (
  `order_number` varchar(20) NOT NULL DEFAULT 0,
  `name` VARCHAR(100) NULL,
  `aim` INT NOT NULL DEFAULT 0,
  `produce` int DEFAULT '0',
  `weight` INT NOT NULL DEFAULT 0,
  `raw` MEDIUMTEXT NOT NULL,
  `process` MEDIUMTEXT,
  `del` TINYINT DEFAULT '0',
  PRIMARY KEY (`order_number`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `screw`.`equipment` (
  `name` VARCHAR(20) NOT NULL,
  `data_date` DATE NOT NULL,
  `pass` INT DEFAULT 0,
  `voltage` INT DEFAULT 0,
  `type` VARCHAR(45) NOT NULL,
  `data_run_avg` DOUBLE DEFAULT 0,
  `data_idle_avg` DOUBLE DEFAULT 0,
  `data_error_avg` DOUBLE DEFAULT 0,
  `data_pass_avg` DOUBLE DEFAULT 0,
  `data_current_avg` DOUBLE DEFAULT 0,
  `run_it` DOUBLE DEFAULT 0,
  `idle_it` DOUBLE DEFAULT 0,
  `error_it` DOUBLE DEFAULT 0,
  `del` TINYINT DEFAULT '0',
  `phone` VARCHAR(45) DEFAULT '',
  `location` VARCHAR(100) DEFAULT '',
  `warranty_date` DATE NULL,
  `spec` VARCHAR(200) DEFAULT '',
  `purchase_date` DATE NULL,
  `record` VARCHAR(200) DEFAULT '',
  PRIMARY KEY (`name`, `data_date`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `account` (
  `account` varchar(20) NOT NULL,
  `pwd` varchar(200) NOT NULL,
  PRIMARY KEY (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `equipment_hour` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  `pass` INT DEFAULT 0,
  `power` DOUBLE DEFAULT 0,
  `time` datetime DEFAULT NULL,
  `type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


