-- ==========================================
-- SCRIPT PARA CREAR BASE DE DATOS CINE_DB Y REGISTROS DE PRUEBA
-- ==========================================

-- Crear la base de datos solo si no existe
CREATE DATABASE IF NOT EXISTS Cine_DB;

-- Usar la base de datos
USE Cine_DB;

-- Crear la tabla Cartelera solo si no existe
CREATE TABLE IF NOT EXISTS Cartelera (
                                         id INT AUTO_INCREMENT PRIMARY KEY,
                                         titulo VARCHAR(150),
    director VARCHAR(50),
    anio INT NOT NULL,
    duracion INT,
    genero VARCHAR(30)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Verificar que la tabla se creó correctamente
DESCRIBE Cartelera;

-- Insertar registros de prueba en Cartelera
INSERT INTO Cartelera (titulo, director, anio, duracion, genero) VALUES
                                                                     ('Matrix', 'Lana Wachowski', 1999, 136, 'Ciencia Ficcion'),
                                                                     ('El Padrino', 'Francis Ford Coppola', 1972, 175, 'Drama'),
                                                                     ('Toy Story', 'John Lasseter', 1995, 81, 'Comedia'),
                                                                     ('Rapidos y Furiosos', 'Rob Cohen', 2001, 106, 'Accion'),
                                                                     ('El Conjuro', 'James Wan', 2013, 112, 'Suspenso'),
                                                                     ('Titanic', 'James Cameron', 1997, 195, 'Romance'),
                                                                     ('Interestelar', 'Christopher Nolan', 2014, 169, 'Ciencia Ficcion'),
                                                                     ('Joker', 'Todd Phillips', 2019, 122, 'Drama'),
                                                                     ('La La Land', 'Damien Chazelle', 2016, 128, 'Romance'),
                                                                     ('Avengers', 'Joss Whedon', 2012, 143, 'Accion'),
                                                                     ('Gladiador', 'Ridley Scott', 2000, 155, 'Accion'),
                                                                     ('Forrest Gump', 'Robert Zemeckis', 1994, 142, 'Drama'),
                                                                     ('Inception', 'Christopher Nolan', 2010, 148, 'Ciencia Ficcion'),
                                                                     ('El Rey Leon', 'Roger Allers', 1994, 88, 'Animacion'),
                                                                     ('Pulp Fiction', 'Quentin Tarantino', 1994, 154, 'Crimen'),
                                                                     ('Amelie', 'Jean-Pierre Jeunet', 2001, 122, 'Comedia'),
                                                                     ('El Laberinto del Fauno', 'Guillermo del Toro', 2006, 118, 'Fantasia'),
                                                                     ('La Lista de Schindler', 'Steven Spielberg', 1993, 195, 'Drama'),
                                                                     ('Jurassic Park', 'Steven Spielberg', 1993, 127, 'Aventura'),
                                                                     ('Buscando a Nemo', 'Andrew Stanton', 2003, 100, 'Animacion'),
                                                                     ('Los Increibles', 'Brad Bird', 2004, 115, 'Animacion'),
                                                                     ('El Gran Hotel Budapest', 'Wes Anderson', 2014, 99, 'Comedia'),
                                                                     ('Coco', 'Lee Unkrich', 2017, 105, 'Animacion'),
                                                                     ('La Forma del Agua', 'Guillermo del Toro', 2017, 123, 'Fantasia'),
                                                                     ('Parasite', 'Bong Joon-ho', 2019, 132, 'Drama');

-- Mostrar mensaje de confirmación
SELECT 'Base de datos Cine_DB, tabla Cartelera y registros de prueba creados exitosamente' AS Resultado;

