# CineMark

página oficial https://www.cinemarkhoyts.com.ar

<img src="https://informatesalta.com.ar/download/multimedia.miniatura.a6732c436d10e576.6163746120646520696e6672616363696f6e20612063696e656d61726b5f6d696e6961747572612e6a7067.jpg">

# Planteo del problema: 

Los directivos de Cinemar comentaron a nuestro equipo que no cuentan con un control de los clientes, para realizar reservas de butacas y otorgarles descuentos para aquellos que son más recurrentes de forma automática.
Todo se efectúa mediante ventanilla y a mano, lo que provoca que en algunas salas a veces se terminan vendiendo más entradas que la capacidad de la sala, y perdiendo ventas en funciones por no contar con reservas por página web en horarios específicos.

# Contexto:

Cinemar es una empresa que se dedica a proyectar películas esencialmente dedicadas al público adolescente.
El cine cuenta con una cantidad de salas con diferentes capacidades (siendo esta capacidad la cantidad de butacas), también dispone de salas 2D como 3D variando el precio de las entradas.
Cuando un cliente se presenta en ventanilla muestra su tarjeta de descuento, si la tiene, se le efectúa un descuento en el valor de la entrada, sino pueden solicitar una sí acudieron al menos 6 veces en 3 meses, en caso contrario el precio de la entrada no tendrá descuento alguno.
Actualmente la tabla de descuentos para los que tienen la tarjeta de descuentos es la siguiente:
- Lunes y Miércoles: 20%
- Martes y Jueves: 15%
- Viernes, Sábados y Domingos: 10%
Siendo modificable según los directivos.

# Solución:

El sistema al final fue realizado con una interfaz via consola de comandos e ingresando mediante login para acceder a la info, almacenada en la base de datos.

<h3 align="center">Capturas:</h3>

<h4>Menú del Administrador en consola.</h4>
<img src="https://github.com/victorManuelMarquez/CineMark/blob/master/assets/snapshots/menu-admin.png" alt="Menú del Administrador en consola."/>
<h4>Menú del Gerente en consola.</h4>
<img src="https://github.com/victorManuelMarquez/CineMark/blob/master/assets/snapshots/menu-gerente.png" alt="Menú del Gerente en consola."/>
<h4>Menú del Boletero en consola.</h4>
<img src="https://github.com/victorManuelMarquez/CineMark/blob/master/assets/snapshots/menu-boletero.png" alt="Menú del Boletero en consola."/>
<h4>Menú del Cliente en consola.</h4>
<img src="https://github.com/victorManuelMarquez/CineMark/blob/master/assets/snapshots/menu-cliente.png" alt="Menú del Cliente en consola."/>

# Nota
Para acceder al sistema (los menús) debe existir previamente el usuario y cliente (persona) en la base de datos. De lo contrario jamás se podrá acceder.

## Erratas:
<ol>
  <li>Para que un usuario exista debe existir antes una persona, registrada en la base de datos como cliente. Sin importar si es un empleado o el administrador.
  Esto se hizo así para ahorrar tiempo y recursos en crear una tabla <code>persona</code> donde almacenar dicha información.</li>
  <li>Se omitió la solución via web para la parte de los usuarios y reservas en toda su extensión, por falta de tiempo. Aún así se desarrollo un mínimo de funciones para ello (experimental).</li>
  <li>La base de datos fue desarrollada teniendo en mente solo una única sucursal de cine, pero existe la posibilidad de expandirla o agregar más característcias para ello.</li>
  <li>La tabla de descuentos por día quedó inconclusa, simplemente por descudio. Pero puede ser implementable en una futura versión.</li>
  <li>Olvidé agregar la característica 2D/3D en la tabla de películas, además de que la misma repercuta en el precio del ticket.</li>
  <li>El cliente puede crearse su usuario, exista o no dicha opción permanecerá ahí. Esto se hizo así para la fase de producción y no se lo removió de la versión "final". Tendría más sentido que el usuario y contraseña sean el dni del cliente como valor por defecto para luego modificarlo (Para futuras versiones se corregirá).</li>
</ol>

&copy; 2022.<br>
Desarrollado por : Víctor Manuel Márquez.<br>
Este programa fue desarrollado sin fines de lucro y solo para presentación académica. No hay intención de su aplicación formal en un entorno real.
