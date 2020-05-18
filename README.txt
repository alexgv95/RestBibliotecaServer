README Aplicación RESTfull GESTOR BIBLIOTECA

Se adjuntan los tres proyectos:
-RestBibliotecaCliente (Cliente Java que interactua con el servidor)
-RestBibliotecaServer (Con los recursos a utilizar)
-RestBibliotecaWeb (Cliente Web que interactua con el servidor)

NOTAS CLIENTE JAVA:
-Hemos encontrado problemas de compatibilidad entre Netbeans 8.2 y MySQL 8.0 así que utilizamos la versión 5.7 de MySQL y el conector de Java 5.23 para asegurarnos la compatibilidad.

NOTAS SERVIDOR:
Script de carga de BBDD:

create database bibliodb
use bibliodb

create table usuarios (usuarioId int auto_increment primary key,nombre varchar(255), password varchar(255),token varchar(255));
create table bibliotecas(bibliotecaId int auto_increment primary key, nombreFacultad varchar(255), nombreCiudad varchar(255), linkBiblioteca varchar(255),usuarioId int, foreign key (usuarioId) references usuarios(usuarioId));
create table libros(libroId int auto_increment primary key, tituloLibro varchar(255), autorLibro varchar(255), numPag int, bibliotecaId int, linkLibro varchar(255), foreign key (bibliotecaId) references bibliotecas(bibliotecaId));

-Respecto a al servidor, hemos advertido que la carpeta donde guarda exporta/importa ficheros depende del IDE, en nuestro caso estaba en una carpeta específica dentro de Documentos. Se podría alterar las rutas para que importe/exporte
en algún fichero más ergonómico pero dado que los archivos del servidor no son el objetivo, sino el de los clientes lo hemos dejado como está.
-Para importar una biblioteca esta debería estar en el fichero marcado por el IDE, sin embargo para evitar tener que buscarlo recomendamos exportar primero una biblioteca y después importarla para comprobar la funcionalidad.

NOTA CLIENTE WEB:
-Recomendamos usar una ventana del navegador que no guarde las cookies o datos del usuario ya que, aún teniendo la funcionalidad de cerrar sesión, en ocasiones genera error. 