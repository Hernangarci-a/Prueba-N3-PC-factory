CREATE TABLE categoria_productos (
    producto_id INT NOT NULL,
    categoria_id INT NOT NULL,
    PRIMARY KEY (producto_id, categoria_id),
    FOREIGN KEY (producto_id) REFERENCES productos(idProductos),
    FOREIGN KEY (categoria_id) REFERENCES categorias(idCategoria)
);

INSERT INTO categoria_productos (producto_id, categoria_id) VALUES (1, 1);
INSERT INTO categoria_productos (producto_id, categoria_id) VALUES (2, 1);
INSERT INTO categoria_productos (producto_id, categoria_id) VALUES (3, 3);