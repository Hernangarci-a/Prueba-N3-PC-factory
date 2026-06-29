CREATE TABLE tipos_productos (
    idTipoProducto INT AUTO_INCREMENT PRIMARY KEY,
    nombreTipoProducto VARCHAR(50) NOT NULL
);

INSERT INTO tipos_productos (idTipoProducto, nombreTipoProducto) VALUES (1,'Computacion');
INSERT INTO tipos_productos (idTipoProducto, nombreTipoProducto) VALUES (2,'Componentes');
INSERT INTO tipos_productos (idTipoProducto, nombreTipoProducto) VALUES (3,'Perifericos');
INSERT INTO tipos_productos (idTipoProducto, nombreTipoProducto) VALUES (4,'Audio');
