-- ===============================
-- TABLAS BASE
-- ===============================

CREATE TABLE roles (
                       id BIGSERIAL PRIMARY KEY,
                       nombre VARCHAR(50) NOT NULL
);

CREATE TABLE estados_users (
                               id BIGSERIAL PRIMARY KEY,
                               nombre VARCHAR(50) NOT NULL
);

CREATE TABLE estados_verificacion (
                                      id BIGSERIAL PRIMARY KEY,
                                      nombre VARCHAR(50) NOT NULL
);

CREATE TABLE estados_solicitudes (
                                     id BIGSERIAL PRIMARY KEY,
                                     nombre VARCHAR(50) NOT NULL
);

CREATE TABLE estados_asignaciones (
                                      id BIGSERIAL PRIMARY KEY,
                                      nombre VARCHAR(50) NOT NULL
);

CREATE TABLE estados_reclamos (
                                  id BIGSERIAL PRIMARY KEY,
                                  nombre VARCHAR(50) NOT NULL
);

-- ===============================
-- USUARIOS Y RELACIONES
-- ===============================

CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       nombre VARCHAR(100) NOT NULL,
                       apellido VARCHAR(100) NOT NULL,
                       correo VARCHAR(150) NOT NULL UNIQUE,
                       contrasena VARCHAR(255),
                       telefono VARCHAR(20),
                       rol_id BIGINT,
                       estado_id BIGINT,
                       presentacion TEXT,
                       foto_perfil VARCHAR(255),
                       FOREIGN KEY (rol_id) REFERENCES roles(id),
                       FOREIGN KEY (estado_id) REFERENCES estados_users(id)
);

CREATE TABLE ubicaciones (
                             id BIGSERIAL PRIMARY KEY,
                             user_id BIGINT NOT NULL UNIQUE,
                             direccion VARCHAR(255),
                             distrito VARCHAR(100),
                             provincia VARCHAR(100),
                             departamento VARCHAR(100),
                             latitud DECIMAL(10, 8),
                             longitud DECIMAL(11, 8),
                             FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE trabajadores (
                              id BIGSERIAL PRIMARY KEY,
                              user_id BIGINT NOT NULL UNIQUE,
                              dni VARCHAR(20),
                              antecedentes TEXT,
                              verificacion_id BIGINT,
                              fecha_registro DATE,
                              FOREIGN KEY (user_id) REFERENCES users(id),
                              FOREIGN KEY (verificacion_id) REFERENCES estados_verificacion(id)
);

CREATE TABLE oficios (
                         id BIGSERIAL PRIMARY KEY,
                         nombre VARCHAR(100) NOT NULL,
                         descripcion TEXT
);

CREATE TABLE trabajadores_oficio (
                                     id BIGSERIAL PRIMARY KEY,
                                     trabajador_id BIGINT,
                                     oficio_id BIGINT,
                                     FOREIGN KEY (trabajador_id) REFERENCES trabajadores(id),
                                     FOREIGN KEY (oficio_id) REFERENCES oficios(id)
);

-- ===============================
-- FUNCIONALIDADES
-- ===============================

CREATE TABLE solicitudes (
                             id BIGSERIAL PRIMARY KEY,
                             cliente_id BIGINT NOT NULL,
                             oficio_id BIGINT NOT NULL,
                             descripcion TEXT,
                             imagen_url VARCHAR(255),
                             ubicacion_id BIGINT,
                             estado_id BIGINT,
                             fecha_creacion TIMESTAMP,
                             FOREIGN KEY (cliente_id) REFERENCES users(id),
                             FOREIGN KEY (oficio_id) REFERENCES oficios(id),
                             FOREIGN KEY (ubicacion_id) REFERENCES ubicaciones(id),
                             FOREIGN KEY (estado_id) REFERENCES estados_solicitudes(id)
);

CREATE TABLE postulaciones (
                               id BIGSERIAL PRIMARY KEY,
                               solicitud_id BIGINT,
                               trabajador_id BIGINT,
                               mensaje TEXT,
                               fecha_postulacion TIMESTAMP,
                               FOREIGN KEY (solicitud_id) REFERENCES solicitudes(id),
                               FOREIGN KEY (trabajador_id) REFERENCES trabajadores(id)
);

CREATE TABLE asignaciones (
                              id BIGSERIAL PRIMARY KEY,
                              solicitud_id BIGINT,
                              cliente_id BIGINT,
                              trabajador_id BIGINT,
                              descripcion_trabajo TEXT,
                              monto_acordado DECIMAL(10,2),
                              estado_id BIGINT,
                              fecha_asignacion TIMESTAMP,
                              FOREIGN KEY (solicitud_id) REFERENCES solicitudes(id),
                              FOREIGN KEY (cliente_id) REFERENCES users(id),
                              FOREIGN KEY (trabajador_id) REFERENCES trabajadores(id),
                              FOREIGN KEY (estado_id) REFERENCES estados_asignaciones(id)
);

CREATE TABLE mensajes (
                          id BIGSERIAL PRIMARY KEY,
                          asignacion_id BIGINT,
                          remitente_id BIGINT,
                          receptor_id BIGINT,
                          mensaje TEXT,
                          fecha_envio TIMESTAMP,
                          FOREIGN KEY (asignacion_id) REFERENCES asignaciones(id),
                          FOREIGN KEY (remitente_id) REFERENCES users(id),
                          FOREIGN KEY (receptor_id) REFERENCES users(id)
);

CREATE TABLE calificaciones (
                                id BIGSERIAL PRIMARY KEY,
                                asignacion_id BIGINT,
                                evaluador_id BIGINT,
                                evaluado_id BIGINT,
                                puntuacion INT,
                                comentario TEXT,
                                fecha TIMESTAMP,
                                FOREIGN KEY (asignacion_id) REFERENCES asignaciones(id),
                                FOREIGN KEY (evaluador_id) REFERENCES users(id),
                                FOREIGN KEY (evaluado_id) REFERENCES users(id)
);

CREATE TABLE certificados (
                              id BIGSERIAL PRIMARY KEY,
                              trabajador_id BIGINT,
                              archivo_url VARCHAR(255),
                              descripcion TEXT,
                              fecha_subida TIMESTAMP,
                              FOREIGN KEY (trabajador_id) REFERENCES trabajadores(id)
);

CREATE TABLE reclamos (
                          id BIGSERIAL PRIMARY KEY,
                          asignacion_id BIGINT,
                          usuario_id BIGINT,
                          mensaje TEXT,
                          estado_id BIGINT,
                          fecha TIMESTAMP,
                          FOREIGN KEY (asignacion_id) REFERENCES asignaciones(id),
                          FOREIGN KEY (usuario_id) REFERENCES users(id),
                          FOREIGN KEY (estado_id) REFERENCES estados_reclamos(id)
);
