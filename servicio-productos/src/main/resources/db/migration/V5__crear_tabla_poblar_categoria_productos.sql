CREATE TABLE categoria_productos (
    producto_id INT NOT NULL,
    categoria_id INT NOT NULL,
    PRIMARY KEY (producto_id, categoria_id),
    FOREIGN KEY (producto_id) REFERENCES productos(id_productos),
    FOREIGN KEY (categoria_id) REFERENCES categorias(id_categoria)
);

INSERT INTO categoria_productos (producto_id, categoria_id) VALUES 
(1, 1), -- Producto 1 (MacBook) pertenece a la Categoría 1 (Notebooks)
(2, 1), -- Producto 2 (ASUS TUF) pertenece a la Categoría 1 (Notebooks)
(3, 3); -- Producto 3 (RTX 4060) pertenece a la Categoría 3 (Tarjetas de Video)