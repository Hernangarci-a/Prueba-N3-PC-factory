CREATE TABLE categorias (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre_categoria VARCHAR(25) NOT NULL UNIQUE
);

INSERT INTO categorias (id_categoria, nombre_categoria) VALUES (1, 'Notebook');
INSERT INTO categorias (id_categoria, nombre_categoria) VALUES (2, 'Procesadores');
INSERT INTO categorias (id_categoria, nombre_categoria) VALUES (3,"Tarjetas de video");
INSERT INTO categorias (id_categoria, nombre_categoria) VALUES (4,"Memorias ram");
INSERT INTO categorias (id_categoria, nombre_categoria) VALUES (5, 'Almacenamiento SSD');
INSERT INTO categorias (id_categoria, nombre_categoria) VALUES (6, 'Monitores');
INSERT INTO categorias (id_categoria, nombre_categoria) VALUES (7, "Teclados gamer");
INSERT INTO categorias (id_categoria, nombre_categoria) VALUES (8, "Mouse Gamer");
INSERT INTO categorias (id_categoria, nombre_categoria) VALUES (9, 'Audifonos');

