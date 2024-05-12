SET sql_safe_updates=0;

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
  `order_number` varchar(20) NOT NULL DEFAULT 0,
  `name` VARCHAR(100) NULL,
  `aim` INT NOT NULL DEFAULT 0,
  `produce` int DEFAULT '0',
  `weight` INT NOT NULL DEFAULT 0,
  `raw` MEDIUMTEXT NOT NULL,
  `process` MEDIUMTEXT,
  `delete` TINYINT DEFAULT '0',
  PRIMARY KEY (`order_number`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `screw`.`equipment` (
  `name` VARCHAR(20) NOT NULL,
  `data_date` DATE NOT NULL,
  `data_run_avg` DOUBLE DEFAULT 0,
  `data_idle_avg` DOUBLE DEFAULT 0,
  `data_error_avg` DOUBLE DEFAULT 0,
  `data_pass_avg` DOUBLE DEFAULT 0,
  `data_current_avg` DOUBLE DEFAULT 0,
  `run_it` DOUBLE DEFAULT 0,
  `idle_it` DOUBLE DEFAULT 0,
  `error_it` DOUBLE DEFAULT 0,
  `del` TINYINT DEFAULT '0',
  PRIMARY KEY (`name`, `data_date`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


