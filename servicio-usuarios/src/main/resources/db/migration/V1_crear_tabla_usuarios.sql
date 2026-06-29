CREATE TABLE clientes(
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    rut VARCHAR(25) NOT NULL UNIQUE,
    nombre_cliente VARCHAR(25) NOT NULL,
    correo VARCHAR(50) NOT NULL,
    direccion VARCHAR(65) NOT NULL,
    telefono VARCHAR(9) NOT NULL,
    id_comuna INT,
    CONSTRAINT fk_cliente_comuna FOREIGN KEY (id_comuna) REFERENCES comuna(id_comuna)
);