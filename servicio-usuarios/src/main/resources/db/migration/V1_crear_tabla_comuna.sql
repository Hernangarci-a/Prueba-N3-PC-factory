CREATE TABLE comuna(
    id_comuna INT AUTO_INCREMENT PRIMARY KEY,
    nombre_comuna VARCHAR(25) NOT NULL,
    id_region INT,
    CONSTRAINT fk_comuna_region FOREIGN KEY (id_region) REFERENCES region(id_region)
);