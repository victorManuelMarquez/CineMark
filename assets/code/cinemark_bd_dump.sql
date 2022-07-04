/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 5.7.33 : Database - cinemark_bd
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`cinemark_bd` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `cinemark_bd`;

/*Table structure for table `funcion` */

DROP TABLE IF EXISTS `funcion`;

CREATE TABLE `funcion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pelicula_id` int(11) NOT NULL,
  `sala_id` int(11) NOT NULL,
  `inicio` datetime NOT NULL,
  `fin` datetime NOT NULL,
  `precio` float NOT NULL,
  PRIMARY KEY (`id`),
  KEY `pelicula_id` (`pelicula_id`),
  KEY `sala_id` (`sala_id`),
  CONSTRAINT `funcion_ibfk_1` FOREIGN KEY (`pelicula_id`) REFERENCES `pelicula` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `funcion_ibfk_2` FOREIGN KEY (`sala_id`) REFERENCES `sala` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `pelicula` */

DROP TABLE IF EXISTS `pelicula`;

CREATE TABLE `pelicula` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `imdb` varchar(20) NOT NULL,
  `titulo` varchar(180) NOT NULL,
  `director` varchar(180) NOT NULL,
  `reparto` varchar(256) NOT NULL,
  `fechaEstreno` date NOT NULL,
  `duracion` smallint(6) NOT NULL,
  `cartelera` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `imdb` (`imdb`),
  KEY `idx_fecha_estreno` (`fechaEstreno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `sala` */

DROP TABLE IF EXISTS `sala`;

CREATE TABLE `sala` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(120) NOT NULL,
  `capacidad` smallint(6) NOT NULL,
  `habilitada` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre_sala` (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/* Procedure structure for procedure `actualizarPelicula` */

/*!50003 DROP PROCEDURE IF EXISTS  `actualizarPelicula` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `actualizarPelicula`(
  arg0 VARCHAR (20),
  arg1 VARCHAR (20),
  arg2 VARCHAR (180),
  arg3 VARCHAR (180),
  arg4 VARCHAR (256),
  arg5 DATE,
  arg6 SMALLINT,
  arg7 BOOL
)
BEGIN
  CALL idPeliculaPorIMDB (arg0, @_id_) ;
  UPDATE 
    pelicula 
  SET
    imdb = arg1,
    titulo = arg2,
    director = arg3,
    reparto = arg4,
    fechaEstreno = arg5,
    duracion = arg6,
    cartelera = arg7 
  WHERE id = @_id_ ;
END */$$
DELIMITER ;

/* Procedure structure for procedure `agregarFuncion` */

/*!50003 DROP PROCEDURE IF EXISTS  `agregarFuncion` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `agregarFuncion`(
  arg0 VARCHAR (20),
  arg1 VARCHAR (120),
  arg2 DATETIME,
  arg3 DATETIME,
  arg4 FLOAT
)
BEGIN
  CALL idPeliculaPorIMDB (arg0, @_pelicula_id_) ;
  CALL idSalaPorNombre (arg1, @_sala_id_) ;
  INSERT INTO funcion (
    pelicula_id,
    sala_id,
    inicio,
    fin,
    precio
  ) 
  VALUES
    (
      (@_pelicula_id_),
      (@_sala_id_),
      arg2,
      arg3,
      arg4
    ) ;
END */$$
DELIMITER ;

/* Procedure structure for procedure `agregarPelicula` */

/*!50003 DROP PROCEDURE IF EXISTS  `agregarPelicula` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `agregarPelicula`(
  arg0 VARCHAR (20),
  arg1 VARCHAR (180),
  arg2 VARCHAR (180),
  arg3 VARCHAR (256),
  arg4 DATE,
  arg5 SMALLINT,
  arg6 BOOL
)
BEGIN
  INSERT INTO pelicula (
    imdb,
    titulo,
    director,
    reparto,
    fechaEstreno,
    duracion,
    cartelera
  ) 
  VALUES
    (arg0, arg1, arg2, arg3, arg4, arg5, arg6) ;
END */$$
DELIMITER ;

/* Procedure structure for procedure `agregarSala` */

/*!50003 DROP PROCEDURE IF EXISTS  `agregarSala` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `agregarSala`(
  arg0 VARCHAR (120),
  arg1 SMALLINT,
  arg2 BOOL
)
BEGIN
  INSERT INTO sala (nombre, capacidad, habilitada) 
  VALUES
    (arg0, arg1, arg2) ;
END */$$
DELIMITER ;

/* Procedure structure for procedure `idPeliculaPorIMDB` */

/*!50003 DROP PROCEDURE IF EXISTS  `idPeliculaPorIMDB` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `idPeliculaPorIMDB`(IN arg0 VARCHAR (20), OUT arg1 INTEGER)
BEGIN
  SELECT 
    id 
  FROM
    pelicula 
  WHERE imdb = arg0 
  LIMIT 1 INTO arg1 ;
END */$$
DELIMITER ;

/* Procedure structure for procedure `idSalaPorNombre` */

/*!50003 DROP PROCEDURE IF EXISTS  `idSalaPorNombre` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `idSalaPorNombre`(
  IN arg0 VARCHAR (120),
  OUT arg1 INTEGER
)
begin
  SELECT 
    id 
  FROM
    sala 
  WHERE nombre = arg0 
  LIMIT 1 INTO arg1 ;
END */$$
DELIMITER ;

/* Procedure structure for procedure `listarFunciones` */

/*!50003 DROP PROCEDURE IF EXISTS  `listarFunciones` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `listarFunciones`(arg0 INTEGER, arg1 INTEGER)
BEGIN
  SELECT 
    * 
  FROM
    funciones 
  LIMIT arg0, arg1 ;
END */$$
DELIMITER ;

/* Procedure structure for procedure `listarPeliculas` */

/*!50003 DROP PROCEDURE IF EXISTS  `listarPeliculas` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `listarPeliculas`(arg0 INTEGER, arg1 INTEGER)
BEGIN
  SELECT 
    * 
  FROM
    peliculas 
  LIMIT arg0, arg1 ;
END */$$
DELIMITER ;

/* Procedure structure for procedure `listarSalas` */

/*!50003 DROP PROCEDURE IF EXISTS  `listarSalas` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `listarSalas`(arg0 INTEGER, arg1 INTEGER)
BEGIN
  SELECT 
    * 
  FROM
    salas 
  LIMIT arg0, arg1 ;
END */$$
DELIMITER ;

/*Table structure for table `funciones` */

DROP TABLE IF EXISTS `funciones`;

/*!50001 DROP VIEW IF EXISTS `funciones` */;
/*!50001 DROP TABLE IF EXISTS `funciones` */;

/*!50001 CREATE TABLE  `funciones`(
 `Película a proyectar` varchar(180) ,
 `Duración de la película en minutos` smallint(6) ,
 `Se exhibe en la sala` varchar(120) ,
 `Total de butacas` smallint(6) ,
 `Precio` float ,
 `Comienza a las` datetime ,
 `Finaliza a las` datetime 
)*/;

/*Table structure for table `peliculas` */

DROP TABLE IF EXISTS `peliculas`;

/*!50001 DROP VIEW IF EXISTS `peliculas` */;
/*!50001 DROP TABLE IF EXISTS `peliculas` */;

/*!50001 CREATE TABLE  `peliculas`(
 `IMDB` varchar(20) ,
 `Título` varchar(180) ,
 `Director/es` varchar(180) ,
 `Reparto principal` varchar(256) ,
 `Fecha de estreno` date ,
 `Duración en minutos` smallint(6) ,
 `¿En cartelera?` tinyint(1) 
)*/;

/*Table structure for table `salas` */

DROP TABLE IF EXISTS `salas`;

/*!50001 DROP VIEW IF EXISTS `salas` */;
/*!50001 DROP TABLE IF EXISTS `salas` */;

/*!50001 CREATE TABLE  `salas`(
 `Nombre` varchar(120) ,
 `Capacidad Máx.` smallint(6) ,
 `¿Habilitada?` tinyint(1) 
)*/;

/*View structure for view funciones */

/*!50001 DROP TABLE IF EXISTS `funciones` */;
/*!50001 DROP VIEW IF EXISTS `funciones` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `funciones` AS (select `pelicula`.`titulo` AS `Película a proyectar`,`pelicula`.`duracion` AS `Duración de la película en minutos`,`sala`.`nombre` AS `Se exhibe en la sala`,`sala`.`capacidad` AS `Total de butacas`,`funcion`.`precio` AS `Precio`,`funcion`.`inicio` AS `Comienza a las`,`funcion`.`fin` AS `Finaliza a las` from ((`pelicula` join `funcion` on((`pelicula`.`id` = `funcion`.`pelicula_id`))) join `sala` on((`sala`.`id` = `funcion`.`sala_id`))) where (`pelicula`.`cartelera` = TRUE) order by `funcion`.`inicio`) */;

/*View structure for view peliculas */

/*!50001 DROP TABLE IF EXISTS `peliculas` */;
/*!50001 DROP VIEW IF EXISTS `peliculas` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `peliculas` AS (select `pelicula`.`imdb` AS `IMDB`,`pelicula`.`titulo` AS `Título`,`pelicula`.`director` AS `Director/es`,`pelicula`.`reparto` AS `Reparto principal`,`pelicula`.`fechaEstreno` AS `Fecha de estreno`,`pelicula`.`duracion` AS `Duración en minutos`,`pelicula`.`cartelera` AS `¿En cartelera?` from `pelicula` order by `pelicula`.`fechaEstreno` desc) */;

/*View structure for view salas */

/*!50001 DROP TABLE IF EXISTS `salas` */;
/*!50001 DROP VIEW IF EXISTS `salas` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `salas` AS (select `sala`.`nombre` AS `Nombre`,`sala`.`capacidad` AS `Capacidad Máx.`,`sala`.`habilitada` AS `¿Habilitada?` from `sala` order by `sala`.`nombre`) */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
