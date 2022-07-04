-- CREO LA BASE DE DATOS
CREATE DATABASE IF NOT EXISTS cinemark_bd CHARACTER SET
'utf8mb4' COLLATE 'utf8mb4_general_ci' ;
-- USAR LA BASE DE DATOS EXISTENTE
USE cinemark_bd ;
-- TABLAS
CREATE TABLE IF NOT EXISTS pelicula (
  id INTEGER NOT NULL AUTO_INCREMENT,
  imdb VARCHAR (20) NOT NULL,
  titulo VARCHAR (180) NOT NULL,
  director VARCHAR (180) NOT NULL,
  reparto VARCHAR (256) NOT NULL,
  fechaEstreno DATE NOT NULL,
  duracion SMALLINT NOT NULL,
  cartelera BOOL NOT NULL DEFAULT TRUE,
  PRIMARY KEY (id),
  UNIQUE imdb (imdb),
  INDEX idx_fecha_estreno (fechaEstreno DESC)
) ENGINE = INNODB ;

CREATE TABLE IF NOT EXISTS sala (
  id INTEGER NOT NULL AUTO_INCREMENT,
  nombre VARCHAR (120) NOT NULL,
  capacidad SMALLINT NOT NULL,
  habilitada BOOL NOT NULL DEFAULT TRUE,
  PRIMARY KEY (id),
  UNIQUE nombre_sala (nombre)
) ENGINE = INNODB ;

CREATE TABLE IF NOT EXISTS funcion (
  id INTEGER NOT NULL AUTO_INCREMENT,
  pelicula_id INTEGER NOT NULL,
  sala_id INTEGER NOT NULL,
  inicio DATETIME NOT NULL,
  fin DATETIME NOT NULL,
  precio FLOAT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (pelicula_id) REFERENCES pelicula (id) ON
UPDATE
	CASCADE ON
	DELETE
		RESTRICT,
		FOREIGN KEY (sala_id) REFERENCES sala (id) ON
		UPDATE
			CASCADE ON
			DELETE
				RESTRICT
) ENGINE = INNODB ;

CREATE TABLE IF NOT EXISTS descuento (
  dia TINYINT NOT NULL DEFAULT 1,
  porcentaje FLOAT NOT NULL DEFAULT 0,
  modificado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (dia)
) ENGINE = INNODB ;

CREATE TABLE IF NOT EXISTS cliente (
  dni VARCHAR(10) NOT NULL,
  fechaNac DATE NOT NULL,
  alta DATE NOT NULL,
  activo BOOL NOT NULL DEFAULT TRUE,
  PRIMARY KEY (dni)
) ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS tarjeta (
  id INTEGER NOT NULL AUTO_INCREMENT,
  alta DATETIME NOT NULL,
  baja DATETIME NOT NULL,
  dni_cliente VARCHAR(10) NOT NULL,
  habilitada BOOL NOT NULL DEFAULT TRUE,
  PRIMARY KEY (id),
  FOREIGN KEY (dni_cliente) REFERENCES cliente(dni) ON
UPDATE
	CASCADE ON
	DELETE
		CASCADE
) ENGINE = INNODB ;

CREATE TABLE IF NOT EXISTS ticket (
  codigo VARCHAR(11) NOT NULL,
  funcion_id INTEGER NOT NULL,
  butaca VARCHAR (20) NOT NULL,
  fechaVenta TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  dni_cliente VARCHAR(10) NOT NULL,
  monto FLOAT NOT NULL,
  PRIMARY KEY (codigo),
  FOREIGN KEY (funcion_id) REFERENCES funcion (id) ON
UPDATE
	CASCADE ON
	DELETE
		RESTRICT,
		FOREIGN KEY (dni_cliente) REFERENCES cliente (dni) ON
		UPDATE
			CASCADE ON
			DELETE
				RESTRICT,
				INDEX indx_fecha_de_venta (fechaVenta DESC)
) ENGINE = INNODB ;
-- VISTAS
DROP VIEW IF EXISTS peliculas ;

CREATE VIEW peliculas AS 
(
SELECT
	imdb AS 'IMDB',
	titulo AS 'Título',
	director AS 'Director/es',
	reparto AS 'Reparto principal',
	fechaEstreno AS 'Fecha de estreno',
	duracion AS 'Duración en minutos'
FROM
	pelicula
WHERE
	cartelera = TRUE
ORDER BY
	fechaEstreno DESC) ;

DROP VIEW IF EXISTS salas ;

CREATE VIEW salas AS 
(
SELECT
	nombre AS 'Nombre',
	capacidad AS 'Capacidad Máx.'
FROM
	sala
WHERE
	habilitada = TRUE
ORDER BY
	nombre ASC) ;

DROP VIEW IF EXISTS funciones ;

CREATE VIEW funciones AS 
(
SELECT
	pelicula.titulo AS 'Película a proyectar',
	pelicula.duracion AS 'Duración de la película en minutos',
	sala.nombre AS 'Se exhibe en la sala',
	sala.capacidad AS 'Total de butacas',
	precio AS 'Precio',
	inicio AS 'Comienza a las',
	fin AS 'Finaliza a las'
FROM
	(
    (
      pelicula
INNER JOIN funcion 
        ON
	pelicula.id = pelicula_id
    )
INNER JOIN sala 
      ON
	sala.id = sala_id
  )
WHERE
	pelicula.cartelera = TRUE
	AND sala.habilitada = TRUE
	AND (DATE(NOW()) = DATE(funcion.fin)) = TRUE
ORDER BY
	inicio) ;

DROP VIEW IF EXISTS funcionesVendidas ;

CREATE VIEW funcionesVendidas AS 
(
SELECT
	pelicula.titulo AS 'Película',
	sala.nombre AS 'Sala',
	pelicula.duracion AS 'Duración de la película',
	sala.capacidad AS 'Total de butacas',
	inicio AS 'Comienza a las',
	fin AS 'Finaliza a las',
	(
	SELECT
		COUNT(*)
	FROM
		ticket
	WHERE
		funcion_id = funcion.id) AS 'Butacas vendidas',
	(
	SELECT
		sala.capacidad - 
    (
		SELECT
			COUNT(*)
		FROM
			ticket
		WHERE
			funcion_id = funcion.id)
	FROM
		sala
	WHERE
		sala.id = funcion.sala_id) AS 'Butacas disponibles',
	precio AS 'Precio'
FROM
	(
    (
      (
        sala
LEFT JOIN funcion 
          ON
	sala.id = funcion.sala_id
      )
INNER JOIN pelicula 
        ON
	pelicula.id = funcion.pelicula_id
    )
INNER JOIN ticket 
      ON
	ticket.funcion_id = funcion.id
  )
GROUP BY
	sala.nombre
ORDER BY
	funcion.inicio) ;
-- PROCEDIMIENTOS
DROP PROCEDURE IF EXISTS idPeliculaPorIMDB ;
DELIMITER //

CREATE PROCEDURE idPeliculaPorIMDB (IN arg0 VARCHAR (20), OUT arg1 INTEGER) 
BEGIN
  SELECT
	id
INTO
	arg1
FROM
	pelicula
WHERE
	imdb = arg0
LIMIT 1 ;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS idSalaPorNombre ;
DELIMITER //

CREATE PROCEDURE idSalaPorNombre (
  IN arg0 VARCHAR (120),
  OUT arg1 INTEGER
) 
BEGIN
  SELECT
	id
INTO
	arg1
FROM
	sala
WHERE
	nombre = arg0
LIMIT 1 ;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS idFuncionPorPeliculaYSala ;
DELIMITER //

CREATE PROCEDURE idFuncionPorPeliculaYSala (
  IN arg0 VARCHAR (20),
  IN arg1 VARCHAR (120),
  OUT arg2 INTEGER
) 
BEGIN
  CALL idPeliculaPorIMDB (arg0,
@_pelicula_id_) ;
  CALL idSalaPorNombre (arg1,
@_sala_id_) ;
  SELECT
	id
INTO
	arg2
FROM
	funcion
WHERE
	pelicula_id = @_pelicula_id_
	AND sala_id = @_sala_id_ ;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS listarPeliculas ;
DELIMITER //

CREATE PROCEDURE listarPeliculas (arg0 INTEGER,
arg1 INTEGER) 
BEGIN
  SELECT
	*
FROM
	peliculas
LIMIT arg0,
arg1 ;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS listarSalas ;
DELIMITER //

CREATE PROCEDURE listarSalas (arg0 INTEGER,
arg1 INTEGER) 
BEGIN
  SELECT
	*
FROM
	salas
LIMIT arg0,
arg1 ;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS listarFunciones ;
DELIMITER //

CREATE PROCEDURE listarFunciones (arg0 INTEGER,
arg1 INTEGER) 
BEGIN
  SELECT
	*
FROM
	funciones
LIMIT arg0,
arg1 ;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS agregarPelicula ;
DELIMITER //

CREATE PROCEDURE agregarPelicula (
  arg0 VARCHAR (20),
  arg1 VARCHAR (180),
  arg2 VARCHAR (180),
  arg3 VARCHAR (256),
  arg4 DATE,
  arg5 SMALLINT,
  arg6 BOOL
) 
BEGIN
  INSERT
	INTO
	pelicula (
    imdb,
	titulo,
	director,
	reparto,
	fechaEstreno,
	duracion,
	cartelera
  )
VALUES
    (arg0,
arg1,
arg2,
arg3,
arg4,
arg5,
arg6) ;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS agregarSala ;
DELIMITER //

CREATE PROCEDURE agregarSala (
  arg0 VARCHAR (120),
  arg1 SMALLINT,
  arg2 BOOL
) 
BEGIN
  INSERT
	INTO
	sala (nombre,
	capacidad,
	habilitada)
VALUES
    (arg0,
arg1,
arg2) ;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS agregarFuncion ;
DELIMITER //

CREATE PROCEDURE agregarFuncion (
  arg0 VARCHAR (20),
  arg1 VARCHAR (120),
  arg2 DATETIME,
  arg3 DATETIME,
  arg4 FLOAT
) 
BEGIN
  CALL idPeliculaPorIMDB (arg0,
@_pelicula_id_) ;
  CALL idSalaPorNombre (arg1,
@_sala_id_) ;
  INSERT
	INTO
	funcion (
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
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS agregarCliente ;
DELIMITER //
CREATE PROCEDURE agregarCliente (arg0 VARCHAR(10),
arg1 DATE,
arg2 DATE)
BEGIN
    INSERT
	INTO
	cliente(dni,
	fechaNac,
	alta)
VALUES (arg0,
arg1,
arg2);
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS agregarTicket ;
DELIMITER //

CREATE PROCEDURE agregarTicket (
  arg0 VARCHAR(11),
  arg1 VARCHAR (20),
  arg2 VARCHAR (120),
  arg3 VARCHAR (20),
  arg4 VARCHAR(10),
  arg5 FLOAT
) 
BEGIN
  DECLARE funcion_id_ INTEGER DEFAULT 0 ;
  CALL idFuncionPorPeliculaYSala (arg1,
arg2,
funcion_id_) ;
  INSERT
	INTO
	ticket (
    codigo,
	funcion_id,
	butaca,
	dni_cliente,
	monto
  )
VALUES
    (arg0,
funcion_id_,
arg3,
arg4,
arg5) ;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS agregarTarjeta ;
DELIMITER //

CREATE PROCEDURE agregarTarjeta (
  arg0 DATETIME,
  arg1 DATETIME,
  arg2 VARCHAR(10)
) 
BEGIN
  INSERT
	INTO
	tarjeta (alta,
	baja,
	dni_cliente)
VALUES
    (arg0,
arg1,
arg2) ;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS actualizarPelicula ;
DELIMITER //

CREATE PROCEDURE actualizarPelicula (
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
  CALL idPeliculaPorIMDB (arg0,
@_id_) ;
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
WHERE
	id = @_id_ ;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS actualizarFuncion ;
DELIMITER //

CREATE PROCEDURE actualizarFuncion (
  arg0 VARCHAR (20),
  arg1 VARCHAR (120),
  arg2 DATETIME,
  arg3 DATETIME,
  arg4 FLOAT
) 
BEGIN
  CALL idPeliculaPorIMDB (arg0,
@_pelicula_id_) ;
  CALL idSalaPorNombre (arg1,
@_sala_id_) ;
  UPDATE
	funcion
SET
	inicio = arg2,
	fin = arg3,
	precio = arg4
WHERE
	pelicula_id = @_pelicula_id_
	AND sala_id = @_sala_id_ ;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS actualizarSala ;
DELIMITER //

CREATE PROCEDURE actualizarSala (
  arg0 VARCHAR (120),
  arg1 VARCHAR (120),
  arg2 SMALLINT,
  arg3 BOOL
) 
BEGIN
  CALL idSalaPorNombre (arg0,
@_id_) ;
  UPDATE
	sala
SET
	nombre = arg1,
	capacidad = arg2,
	habilitada = arg3
WHERE
	id = @_id_ ;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS actualizarCliente ;
DELIMITER //
CREATE PROCEDURE actualizarCliente(arg0 VARCHAR(11),
arg1 VARCHAR(11),
arg2 DATE,
arg3 DATE,
arg4 BOOL)
BEGIN
	UPDATE
	cliente
SET
	dni = arg1,
	fechaNac = arg2,
	alta = arg3,
	activo = arg4
WHERE
	dni = arg0;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS actualizarTicket ;
DELIMITER //

CREATE PROCEDURE actualizarTicket (
  arg0 VARCHAR(11),
  arg1 VARCHAR (20),
  arg2 VARCHAR (120),
  arg3 VARCHAR (20),
  arg4 VARCHAR(10),
  arg5 FLOAT
) 
BEGIN
  CALL idFuncionPorPeliculaYSala (arg1,
arg2,
@_funcion_id_) ;
  UPDATE
	ticket
SET
	funcion_id = @_funcion_id_,
	butaca = arg3,
	dni_cliente = arg4,
	monto = arg5
WHERE
	id = arg0 ;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS borrarPelicula;
DELIMITER //
CREATE PROCEDURE borrarPelicula (arg0 VARCHAR(20))
BEGIN
	DELETE
FROM
	pelicula
WHERE
	imdb = arg0;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS borrarSala;
DELIMITER //
CREATE PROCEDURE borrarSala(arg0 VARCHAR(120))
BEGIN
	DELETE FROM sala WHERE nombre = arg0;
END //

DELIMITER ;

DROP PROCEDURE IF EXISTS borrarTicket;
DELIMITER //
CREATE PROCEDURE borrarTicket (arg0 VARCHAR(11))
BEGIN
	DELETE
FROM
	ticket
WHERE
	codigo = arg0;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS borrarFuncion;
DELIMITER //
CREATE PROCEDURE borrarFuncion(arg0 VARCHAR(20),
arg1 VARCHAR(120))
BEGIN
	CALL idFuncionPorPeliculaYSala(arg0,
arg1,
@_id_);
	DELETE
FROM
	funcion
WHERE
	id = @_id_;
END //
DELIMITER ;

-- POBLACION
-- CALL agregarPelicula('tt1745960', 'Top Gun: Maverick', 'Joseph Kosinski', 'Tom cruise, Jeniffer Connelly, Miles Teller, entre otros.', '2022-06-12', 130, TRUE);
-- CALL agregarSala('Sala A', 200, TRUE);
-- CALL agregarFuncion('tt1745960', 'Sala A', NOW(), ADDTIME(NOW(), '0 2:10:00.000'), 1799.99);
-- CALL agregarCliente('37023089', '1998-11-09', CURDATE());
-- CALL agregarTicket('20220623089', 'tt1745960', 'Sala A', 'F1C1', '37023089', 1799.99);
-- CALL borrarTicket('20220623089');
-- CALL borrarFuncion('tt1745960', 'Sala A');
-- CALL borrarPelicula ('tt1745960');
-- CALL borrarSala('Sala A');
