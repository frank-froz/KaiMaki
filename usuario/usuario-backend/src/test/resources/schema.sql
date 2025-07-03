CREATE TABLE IF NOT EXISTS estados_verificacion (
                                                    id BIGINT PRIMARY KEY,
                                                    nombre VARCHAR(50)
    );

CREATE TABLE IF NOT EXISTS trabajadores (
                                            id BIGINT PRIMARY KEY,
                                            user_id BIGINT NOT NULL,
                                            dni VARCHAR(20),
    antecedentes VARCHAR(255),
    verificacion_id BIGINT,
    fecha_registro DATE,
    FOREIGN KEY (verificacion_id) REFERENCES estados_verificacion(id)
    );

CREATE TABLE IF NOT EXISTS trabajadores_oficios (
                                                    trabajador_id BIGINT,
                                                    oficio VARCHAR(100),
    FOREIGN KEY (trabajador_id) REFERENCES trabajadores(id)
    );
