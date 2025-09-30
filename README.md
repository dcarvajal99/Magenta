# Magenta - Sistema de Gestión de Cartelera de Cine

Este proyecto es una aplicación Java con interfaz gráfica (Swing) que permite gestionar películas en la base de datos `Cine_DB`. Utiliza el patrón MVC y conexión a MySQL.

## Descripción

La aplicación permite:
- Agregar, actualizar, eliminar y buscar películas.
- Validar datos y evitar duplicados de ID.
- Visualizar y seleccionar películas desde una tabla interactiva.
- Utilizar géneros como Comedia, Drama, Acción, Suspenso, Romance y Ciencia Ficción.

Incluye un script SQL para crear la base de datos y cargar registros de prueba.

## Requisitos
- Java 8 o superior
- MySQL Server
- Driver JDBC MySQL (mysql-connector-java)
- IDE recomendado: IntelliJ IDEA

## Instalación y uso
1. Clona el repositorio o descarga los archivos.
2. Ejecuta el script `SCRIPT_CINE_DB.sql` en tu servidor MySQL para crear la base de datos y la tabla con datos de prueba.
3. **IMPORTANTE:** Para conectarse a la base de datos, debes cambiar los datos de conexión (usuario, contraseña, URL) en el archivo:
   - `src/main/java/com/grupo7/magenta/dao/DBConnector.java`
   Modifica las variables `URL`, `USER`, y `PASS` según tu configuración local.
4. Compila y ejecuta la aplicación desde tu IDE.

## Estructura principal
- `src/main/java/com/grupo7/magenta/models/Pelicula.java`: Modelo de película.
- `src/main/java/com/grupo7/magenta/dao/PeliculaDAO.java`: Acceso a datos.
- `src/main/java/com/grupo7/magenta/controller/PeliculaController.java`: Lógica y validaciones.
- `src/main/java/com/grupo7/magenta/views/JFrameCRUD.java`: Interfaz gráfica principal.
- `SCRIPT_CINE_DB.sql`: Script para crear la base de datos y registros de prueba.

## Notas
- Si tienes problemas de conexión, revisa que el servidor MySQL esté activo y que los datos de usuario/contraseña sean correctos.
- El campo `genero` acepta hasta 30 caracteres para evitar errores de truncado.

## Autor
Grupo 7 - Duoc UC

---

¡Listo para gestionar tu cartelera de cine! 

