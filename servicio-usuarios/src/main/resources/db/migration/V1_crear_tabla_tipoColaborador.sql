CREATE TABLE tipo_colaborador(
    id_tipo_colaborador INT AUTO_INCREMENT PRIMARY KEY,
    FOREIGN KEY (tipo_colaborador_id) REFERENCES tipo_colaborador(id_tipo_colaborador) ON DELETE CASCADE,
    FOREIGN KEY (id_colaborador) REFERENCES colaborador(id_colaborador) ON DELETE CASCADE
);