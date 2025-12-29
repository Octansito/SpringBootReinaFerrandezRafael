![img.png](img.png)
![img_1.png](img_1.png)
![img_2.png](img_2.png)
![img_8.png](img_8.png)
![img_3.png](img_3.png)
![img_4.png](img_4.png)
![img_5.png](img_5.png)
![img_6.png](img_6.png)



Base de Datos
**Los atributos int al hacer reverse engineer/ forward engineer han pasado a ser BIGINT
![img_7.png](img_7.png)
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema protectora
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema protectora
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `protectora` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `protectora` ;

-- -----------------------------------------------------
-- Table `protectora`.`adopcion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `protectora`.`adopcion` (
`id_adopcion` BIGINT NOT NULL AUTO_INCREMENT,
`direccion` VARCHAR(255) NOT NULL,
`fecha_adopcion` DATE NOT NULL,
`nombre_adoptante` VARCHAR(255) NOT NULL,
`telefono` VARCHAR(255) NOT NULL,
PRIMARY KEY (`id_adopcion`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `protectora`.`animal`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `protectora`.`animal` (
`id_animal` BIGINT NOT NULL AUTO_INCREMENT,
`edad` INT NOT NULL,
`estado` ENUM('Adoptado', 'Refugio') NOT NULL,
`fecha_ingreso` DATE NOT NULL,
`nombre` VARCHAR(255) NOT NULL,
`tipo` ENUM('Gato', 'Perro') NOT NULL,
`id_adopcion` BIGINT NULL DEFAULT NULL,
PRIMARY KEY (`id_animal`),
INDEX `FKbhid5502prlyloket4jd3g1vn` (`id_adopcion` ASC) VISIBLE,
CONSTRAINT `FKbhid5502prlyloket4jd3g1vn`
FOREIGN KEY (`id_adopcion`)
REFERENCES `protectora`.`adopcion` (`id_adopcion`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `protectora`.`voluntario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `protectora`.`voluntario` (
`dni` VARCHAR(9) NOT NULL,
`antiguedad` INT NOT NULL,
`nombre` VARCHAR(255) NOT NULL,
`rol` ENUM('Responsable', 'Voluntario') NOT NULL,
`telefono` VARCHAR(255) NOT NULL,
PRIMARY KEY (`dni`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `protectora`.`grupo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `protectora`.`grupo` (
`id_grupo` BIGINT NOT NULL AUTO_INCREMENT,
`dia_semana` ENUM('Domingo', 'Jueves', 'Lunes', 'Martes', 'Miercoles', 'Sabado', 'Viernes') NOT NULL,
`turno` ENUM('Mañana', 'Tarde') NOT NULL,
`responsable` VARCHAR(9) NOT NULL,
PRIMARY KEY (`id_grupo`),
INDEX `FK8g3l3l4ypm936dpp0rofanxi6` (`responsable` ASC) VISIBLE,
CONSTRAINT `FK8g3l3l4ypm936dpp0rofanxi6`
FOREIGN KEY (`responsable`)
REFERENCES `protectora`.`voluntario` (`dni`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `protectora`.`voluntario_animal`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `protectora`.`voluntario_animal` (
`dni_voluntario` VARCHAR(9) NOT NULL,
`id_animal` BIGINT NOT NULL,
PRIMARY KEY (`dni_voluntario`, `id_animal`),
INDEX `FKhc4efvf8v837r62ywh9gky7pp` (`id_animal` ASC) VISIBLE,
CONSTRAINT `FKaw3rqwe7wxmi8yvmmk22ahbc0`
FOREIGN KEY (`dni_voluntario`)
REFERENCES `protectora`.`voluntario` (`dni`),
CONSTRAINT `FKhc4efvf8v837r62ywh9gky7pp`
FOREIGN KEY (`id_animal`)
REFERENCES `protectora`.`animal` (`id_animal`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `protectora`.`voluntario_grupo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `protectora`.`voluntario_grupo` (
`id_grupo` BIGINT NOT NULL,
`dni_voluntario` VARCHAR(9) NOT NULL,
PRIMARY KEY (`id_grupo`, `dni_voluntario`),
INDEX `FKqhu4tri1uw82lhnvidcq74eth` (`dni_voluntario` ASC) VISIBLE,
CONSTRAINT `FK6x3us4erremxc68ts3bo682bi`
FOREIGN KEY (`id_grupo`)
REFERENCES `protectora`.`grupo` (`id_grupo`),
CONSTRAINT `FKqhu4tri1uw82lhnvidcq74eth`
FOREIGN KEY (`dni_voluntario`)
REFERENCES `protectora`.`voluntario` (`dni`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
