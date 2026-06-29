CREATE TABLE sucursal(
    id_sucursal INT AUTO_INCREMENT PRIMARY KEY,
    nombre_sucursal VARCHAR(30) NOT NULL,
    direccion_sucursal VARCHAR(60) NOT NULL,
    id_comuna INT,
    CONSTRAINT fk_sucursal_comuna FOREIGN KEY (id_comuna) REFERENCES comuna(id_comuna)
);