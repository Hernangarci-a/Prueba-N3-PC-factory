CREATE TABLE tipos_productos (
    id_tipos_productos INT AUTO_INCREMENT PRIMARY KEY,
    nombre_tipo_producto VARCHAR(50) NOT NULL
);

INSERT INTO tipos_productos (id_tipos_productos, nombre_tipo_producto) VALUES (1,'Computacion');
INSERT INTO tipos_productos (id_tipos_productos, nombre_tipo_producto) VALUES (2,'Componentes');
INSERT INTO tipos_productos (id_tipos_productos, nombre_tipo_producto) VALUES (3,'Perifericos');
INSERT INTO tipos_productos (id_tipos_productos, nombre_tipo_producto) VALUES (4,'Audio');
