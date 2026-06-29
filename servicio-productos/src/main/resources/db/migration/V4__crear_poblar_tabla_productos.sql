CREATE TABLE productos (
    id_productos INT AUTO_INCREMENT PRIMARY KEY,
    nombre_producto VARCHAR(100) NOT NULL,
    precio_unitario DOUBLE NOT NULL,
    procesador VARCHAR(70),
    memoria_ram VARCHAR(30),
    almacenamiento VARCHAR(20),
    marca_id INT,
    tipo_producto_id INT,
    FOREIGN KEY (marca_id) REFERENCES marcas(id_marcas),
    FOREIGN KEY (tipo_producto_id) REFERENCES tipos_productos(id_tipos_productos)
);

INSERT INTO productos (id_productos, nombre_producto, precio_unitario, procesador, memoria_ram, almacenamiento, marca_id, tipo_producto_id) VALUES 
(1, "MacBook Air M2 Apple", 999990.00, "Apple M2", "8GB", "256GB SSD", 1, 1),
(2, "Notebook Gamer ASUS TUF A15", 749990.00, "AMD Ryzen 7", "16GB", "512GB SSD", 5, 1),
(3, "Tarjeta Video ASUS Dual RTX 4060", 349990.00, "NVIDIA RTX 4060", "8GB GDDR6", NULL, 5, 2);