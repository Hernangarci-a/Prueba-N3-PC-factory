CREATE TABLE categorias (
    idCategoria INT AUTO_INCREMENT PRIMARY KEY,
    nombreCategoria VARCHAR(25) NOT NULL UNIQUE
);

INSERT INTO categorias (idCategoria, nombreCategoria) VALUES (1, 'Notebook');
INSERT INTO categorias (idCategoria, nombreCategoria) VALUES (2, 'Procesadores');
INSERT INTO categorias (idCategoria, nombreCategoria) VALUES (3,'Tarjetas de video');
INSERT INTO categorias (idCategoria, nombreCategoria) VALUES (4,'Memorias ram');
INSERT INTO categorias (idCategoria, nombreCategoria) VALUES (5, 'Almacenamiento SSD');
INSERT INTO categorias (idCategoria, nombreCategoria) VALUES (6, 'Monitores');
INSERT INTO categorias (idCategoria, nombreCategoria) VALUES (7, 'Teclados gamer');
INSERT INTO categorias (idCategoria, nombreCategoria) VALUES (8, 'Mouse Gamer');
INSERT INTO categorias (idCategoria, nombreCategoria) VALUES (9, 'Audifonos');

