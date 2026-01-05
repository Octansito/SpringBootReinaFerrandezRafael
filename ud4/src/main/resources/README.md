Proyecto 3: Spring Boot de la Protectora App

En este proyecto había que introducir una nueva tabla. Anteriormente, teníamos Animal, Voluntario y Adopción. Ahora, se ha añadido la tabla Grupo
que conforma una relación M:M con Voluntarios ya que 1 voluntario puede estar en 1 o muchos grupos y 1 Grupo puede tener 1 o muchos voluntarios.

Base de Datos
**Los atributos int al hacer reverse engineer/ forward engineer han pasado a ser BIGINT
![img_7.png](img_7.png).

Para crear el proyecto en IntelliJ de Springboot se siguieron los pasos marcados en los apuntes instalando todas las dependencias marcadas (día 23/12/2025).
![img_71.png](img_71.png)

Una vez creado en la carpeta específica, se estructuraron todas las carpetas según los apuntes y se crearon de las entities en fase beta (día 23/12/2025).
![img_72.png](img_72.png)
![img_32.png](img_32.png)
![img_33.png](img_33.png)

Entities:

-Adopcion
![img_34.png](img_34.png)

-Animal 
![img_35.png](img_35.png)
![img_36.png](img_36.png)

-Enums de animal
![img_37.png](img_37.png)
![img_38.png](img_38.png)

-Grupo
![img_39.png](img_39.png)

-Enums de Grupo
![img_40.png](img_40.png)

-Voluntario
![img_41.png](img_41.png)

-Enums de Voluntario
![img_42.png](img_42.png)

Dos días más tarde, Se realizaron modificaciones menores en las entidades, incorporando la anotación @Column(name="...") para especificar 
explícitamente el nombre de la columna en la base de datos asociada a cada atributo. Además, se crearon las clases DTO y Repository no definitivas porque se fueron 
modificando conforme avanzaba el proyecto(día 25/12/2025).

Ejemplo de modificación de las entities:
![img_43.png](img_43.png)

Clases DTO creadas:
![img_44.png](img_44.png)

-Adopción DTO 
![img_45.png](img_45.png)

-Animal DTO
![img_46.png](img_46.png)

-Grupo DTO
![img_47.png](img_47.png)

-VoluntarioDTO
![img_48.png](img_48.png)


Clases Repository:
![img_54.png](img_54.png)

-AdopciónRepository
![img_55.png](img_55.png)

-AnimalRepository
![img_56.png](img_56.png)

-GrupoRepository
![img_57.png](img_57.png)

-VoluntarioRepository
![img_58.png](img_58.png)


Se crearon las clases controller no definitivas porque se fueron modificando a lo largo del proyecto aunque las que se 
muestran son las definitivas (día 27/12/2025).

Clases Controller:
![img_59.png](img_59.png)

-AdopciónController
![img_60.png](img_60.png)

-AnimalController
![img_64.png](img_64.png)

-GrupoController
![img_62.png](img_62.png)

-VoluntarioController
![img_63.png](img_63.png)


Se crearon las clases Services con los crud, que durante el mismo día se modificaron. Se reescribió y reestructuró
las clases Controller y se comentaron los métodos duplicados para su eliminación porque no me percaté de que
se obtenía la misma información pero hecho de distinta manera(día 28/12/2025).

Clases Services:
![img_65.png](img_65.png)

-AdopcionService
![img_66.png](img_66.png)

-AnimalService
![img_67.png](img_67.png)

-GrupoService
![img_68.png](img_68.png)

-VoluntarioService
![img_69.png](img_69.png)

Al día siguiente, comencé el README.md pegando capturas, añadí el @NamedQuery que solicitaba el enunciado del proyecto 
en animal sustituyendo a la query predefinida con anterioridad y metí subrutas en los controllers (día 29/12/2025).

![img_70.png](img_70.png)


Traté de hacer alguna prueba ya, pero tuve muchísimos problemas de dependencias y de puertos (los puertos 808X) me aparecían
todos como ocupados así que al final opté por usar el puerto 9090 que si me dejó arrancar Sprinboot y con ello meterme al Swagger. Además, hice 
pequeñas modificaciones en las entities (ej: cambiando int por Integer), cambiando la versión del framework de spring y la version del lenguaje Java, modifqué
mil veces todas las dependencias

-Fallo puerto 808X
![img.png](img.png)
![img_1.png](img_1.png)


-Arranque Swagger puerto 9090

![img_2.png](img_2.png)
![img_8.png](img_8.png)

![img_3.png](img_3.png)
![img_4.png](img_4.png)
![img_5.png](img_5.png)
![img_6.png](img_6.png)

Comencé con las pruebas a través de Swagger y Postman, salieron con éxito. En las imágenes están
todas las pruebas que se pedían en el enunciado (día 03/01/2026).

![img_9.png](img_9.png)
![img_10.png](img_10.png)
![img_11.png](img_11.png)
![img_13.png](img_13.png)
![img_12.png](img_12.png)


Prueba subrutas:
![img_14.png](img_14.png)

Me daba fallo el get en Swagger y probé en PostMan para obtener la adopción
![img_15.png](img_15.png)

Insercion Voluntarios
![img_17.png](img_17.png)
![img_16.png](img_16.png)
![img_18.png](img_18.png)
![img_19.png](img_19.png)

Creo varios grupos de los cuales tienen de responsable a la misma persona con el dni 11111111A. Ejemplos:
![img_26.png](img_26.png)
![img_27.png](img_27.png)
![img_28.png](img_28.png)

Comprobación subrutas:

A) Dni del responsable por id del grupo X
![img_29.png](img_29.png)
![img_30.png](img_30.png)

B) Grupos de los que se encarga el voluntario responsable con dni Y
![img_31.png](img_31.png)




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
