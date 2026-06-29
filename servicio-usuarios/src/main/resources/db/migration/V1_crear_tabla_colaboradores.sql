CREATE TABLE colaboradores(
    id_colaborador INT AUTO_INCREMENT PRIMARY KEY,
    nombre_colaborador VARCHAR(25) NOT NULL,
    rut_colaborador VARCHAR(9) NOT NULL UNIQUE,
    correo VARCHAR(50) NOT NULL UNIQUE,
    telefono VARCHAR(9) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);