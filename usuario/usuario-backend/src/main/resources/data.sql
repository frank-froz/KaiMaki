-- ============================
-- 1. Insertar roles
-- ============================
INSERT INTO roles (nombre) VALUES
                               ('CLIENTE'),
                               ('TRABAJADOR'),
                               ('ADMINISTRADOR');

-- ============================
-- 2. Insertar estados
-- ============================
INSERT INTO estados_users (nombre) VALUES ('activo');
INSERT INTO estados_users (nombre) VALUES ('pendiente');
INSERT INTO estados_users (nombre) VALUES ('suspendido');

INSERT INTO estados_verificacion (nombre) VALUES ('rechasado');
INSERT INTO estados_verificacion (nombre) VALUES ('verificado');
INSERT INTO estados_verificacion (nombre) VALUES ('pendiente');

INSERT INTO estados_solicitudes (nombre) VALUES ('pendiente');
INSERT INTO estados_solicitudes (nombre) VALUES ('en proceso');
INSERT INTO estados_solicitudes (nombre) VALUES ('completada');
INSERT INTO estados_solicitudes (nombre) VALUES ('cancelada');

INSERT INTO estados_asignaciones (nombre) VALUES ('activa');
INSERT INTO estados_asignaciones (nombre) VALUES ('finalizada');
INSERT INTO estados_asignaciones (nombre) VALUES ('cancelada');

INSERT INTO estados_reclamos (nombre) VALUES ('pendiente');
INSERT INTO estados_reclamos (nombre) VALUES ('resuelto');
INSERT INTO estados_reclamos (nombre) VALUES ('rechazado');

-- ============================
-- 3. Insertar oficios
-- ============================
INSERT INTO oficios (nombre, descripcion) VALUES
                                              ('Electricista', 'Reparaciones eléctricas y cableado.'),
                                              ('Gasfitero', 'Instalaciones y reparaciones de gas y agua.'),
                                              ('Carpintero', 'Trabajos con madera y muebles.'),
                                              ('Pintor', 'Pintura de interiores y exteriores.'),
                                              ('Técnico de electrodomésticos', 'Reparación de artefactos del hogar.'),
                                              ('Soldador', 'Trabajos de soldadura y metales.'),
                                              ('Albañil', 'Construcción y remodelación.'),
                                              ('Jardinero', 'Mantenimiento de jardines y áreas verdes.'),
                                              ('Cerrajero', 'Instalación y reparación de cerraduras.'),
                                              ('Tapicero', 'Reparación y tapizado de muebles.');

-- ============================
-- 4. Insertar usuarios con rol trabajador (30 trabajadores)
-- ============================
INSERT INTO users (nombre, apellido, correo, contrasena, telefono, rol_id, estado_id, presentacion, foto_perfil) VALUES
                                                                                                                     ('Juan', 'Pérez', 'juan.perez@correo.com', '123456', '987654321', 2, 1, 'Soy un técnico electricista con 15 años de experiencia.', NULL),
                                                                                                                     ('Ana', 'García', 'ana.garcia@correo.com', '123456', '987654322', 2, 1, 'Especialista en plomería y gasfitería residencial.', NULL),
                                                                                                                     ('Luis', 'Ramírez', 'luis.ramirez@correo.com', '123456', '987654323', 2, 1, 'Pintor profesional y detallista en acabados.', NULL),
                                                                                                                     ('María', 'López', 'maria.lopez@correo.com', '123456', '987654324', 2, 1, 'Carpintera con 10 años de experiencia en muebles.', NULL),
                                                                                                                     ('Pedro', 'Torres', 'pedro.torres@correo.com', '123456', '987654325', 2, 1, 'Reparo electrodomésticos de todo tipo y marca.', NULL),
                                                                                                                     ('Carlos', 'Mendoza', 'carlos.mendoza@correo.com', '123456', '987654326', 2, 1, 'Soldador certificado en estructuras metálicas.', NULL),
                                                                                                                     ('Laura', 'Vargas', 'laura.vargas@correo.com', '123456', '987654327', 2, 1, 'Especialista en jardinería y paisajismo.', NULL),
                                                                                                                     ('Roberto', 'Silva', 'roberto.silva@correo.com', '123456', '987654328', 2, 1, 'Cerrajero con experiencia en seguridad.', NULL),
                                                                                                                     ('Carmen', 'Morales', 'carmen.morales@correo.com', '123456', '987654329', 2, 1, 'Tapicera profesional en muebles antiguos.', NULL),
                                                                                                                     ('Diego', 'Herrera', 'diego.herrera@correo.com', '123456', '987654330', 2, 1, 'Albañil especializado en construcción.', NULL),
                                                                                                                     ('Sandra', 'Castillo', 'sandra.castillo@correo.com', '123456', '987654331', 2, 1, 'Electricista con certificación industrial.', NULL),
                                                                                                                     ('Andrés', 'Ruiz', 'andres.ruiz@correo.com', '123456', '987654332', 2, 1, 'Gasfitero experto en instalaciones domésticas.', NULL),
                                                                                                                     ('Patricia', 'Jiménez', 'patricia.jimenez@correo.com', '123456', '987654333', 2, 1, 'Pintora especializada en decoración.', NULL),
                                                                                                                     ('Fernando', 'Guerrero', 'fernando.guerrero@correo.com', '123456', '987654334', 2, 1, 'Carpintero en muebles a medida.', NULL),
                                                                                                                     ('Rosa', 'Delgado', 'rosa.delgado@correo.com', '123456', '987654335', 2, 1, 'Técnica en reparación de electrodomésticos.', NULL),
                                                                                                                     ('Javier', 'Paredes', 'javier.paredes@correo.com', '123456', '987654336', 2, 1, 'Soldador especializado en tuberías.', NULL),
                                                                                                                     ('Mónica', 'Sánchez', 'monica.sanchez@correo.com', '123456', '987654337', 2, 1, 'Jardinera experta en plantas ornamentales.', NULL),
                                                                                                                     ('Raúl', 'Vega', 'raul.vega@correo.com', '123456', '987654338', 2, 1, 'Cerrajero con 8 años de experiencia.', NULL),
                                                                                                                     ('Elena', 'Romero', 'elena.romero@correo.com', '123456', '987654339', 2, 1, 'Tapicera especializada en restauración.', NULL),
                                                                                                                     ('Miguel', 'Flores', 'miguel.flores@correo.com', '123456', '987654340', 2, 1, 'Albañil con experiencia en remodelaciones.', NULL),
                                                                                                                     ('Beatriz', 'Aguilar', 'beatriz.aguilar@correo.com', '123456', '987654341', 2, 1, 'Electricista residencial certificada.', NULL),
                                                                                                                     ('Arturo', 'Medina', 'arturo.medina@correo.com', '123456', '987654342', 2, 1, 'Gasfitero especializado en gas natural.', NULL),
                                                                                                                     ('Lucía', 'Ramos', 'lucia.ramos@correo.com', '123456', '987654343', 2, 1, 'Pintora con técnicas especializadas.', NULL),
                                                                                                                     ('Enrique', 'Cabrera', 'enrique.cabrera@correo.com', '123456', '987654344', 2, 1, 'Carpintero en estructuras de madera.', NULL),
                                                                                                                     ('Silvia', 'Ortega', 'silvia.ortega@correo.com', '123456', '987654345', 2, 1, 'Técnica en electrodomésticos de línea blanca.', NULL),
                                                                                                                     ('Héctor', 'Navarro', 'hector.navarro@correo.com', '123456', '987654346', 2, 1, 'Soldador industrial con certificación.', NULL),
                                                                                                                     ('Gloria', 'Peña', 'gloria.pena@correo.com', '123456', '987654347', 2, 1, 'Jardinera especializada en hidroponía.', NULL),
                                                                                                                     ('Gonzalo', 'Rojas', 'gonzalo.rojas@correo.com', '123456', '987654348', 2, 1, 'Cerrajero experto en sistemas automáticos.', NULL),
                                                                                                                     ('Valeria', 'Herrera', 'valeria.herrera@correo.com', '123456', '987654349', 2, 1, 'Tapicera con 12 años de experiencia.', NULL),
                                                                                                                     ('Óscar', 'Mendoza', 'oscar.mendoza@correo.com', '123456', '987654350', 2, 1, 'Albañil especializado en acabados finos.', NULL);

-- ============================
-- 5. Insertar ubicaciones
-- ============================
INSERT INTO ubicaciones (user_id, direccion, distrito, provincia, departamento, latitud, longitud) VALUES
                                                                                                       (1, 'Av. Principal 123', 'San Isidro', 'Lima', 'Lima', -12.0975, -77.0333),
                                                                                                       (2, 'Jr. Secundario 456', 'Miraflores', 'Lima', 'Lima', -12.1200, -77.0300),
                                                                                                       (3, 'Calle Pintores 789', 'Surco', 'Lima', 'Lima', -12.1500, -76.9900),
                                                                                                       (4, 'Av. Carpinteros 321', 'La Molina', 'Lima', 'Lima', -12.0800, -76.9400),
                                                                                                       (5, 'Jr. Electricistas 654', 'Magdalena', 'Lima', 'Lima', -12.0900, -77.0600),
                                                                                                       (6, 'Av. Los Soldadores 111', 'Callao', 'Callao', 'Callao', -12.0500, -77.1200),
                                                                                                       (7, 'Calle Jardín 222', 'Pueblo Libre', 'Lima', 'Lima', -12.0700, -77.0700),
                                                                                                       (8, 'Jr. Cerrajeros 333', 'Jesús María', 'Lima', 'Lima', -12.0800, -77.0500),
                                                                                                       (9, 'Av. Tapiceros 444', 'Lince', 'Lima', 'Lima', -12.0900, -77.0400),
                                                                                                       (10, 'Calle Albañiles 555', 'Breña', 'Lima', 'Lima', -12.0600, -77.0500),
                                                                                                       (11, 'Av. Eléctrica 666', 'San Miguel', 'Lima', 'Lima', -12.0800, -77.0900),
                                                                                                       (12, 'Jr. Gasfiteros 777', 'Cercado', 'Lima', 'Lima', -12.0500, -77.0300),
                                                                                                       (13, 'Calle Colores 888', 'Barranco', 'Lima', 'Lima', -12.1400, -77.0200),
                                                                                                       (14, 'Av. Madera 999', 'Chorrillos', 'Lima', 'Lima', -12.1700, -77.0100),
                                                                                                       (15, 'Jr. Reparaciones 101', 'Villa El Salvador', 'Lima', 'Lima', -12.2100, -76.9400),
                                                                                                       (16, 'Calle Soldadura 102', 'San Juan de Miraflores', 'Lima', 'Lima', -12.1600, -76.9700),
                                                                                                       (17, 'Av. Verde 103', 'Surquillo', 'Lima', 'Lima', -12.1100, -77.0100),
                                                                                                       (18, 'Jr. Seguridad 104', 'La Victoria', 'Lima', 'Lima', -12.0700, -77.0200),
                                                                                                       (19, 'Calle Tapizado 105', 'Rímac', 'Lima', 'Lima', -12.0300, -77.0200),
                                                                                                       (20, 'Av. Construcción 106', 'Independencia', 'Lima', 'Lima', -12.0000, -77.0500),
                                                                                                       (21, 'Jr. Voltios 107', 'Los Olivos', 'Lima', 'Lima', -11.9800, -77.0700),
                                                                                                       (22, 'Calle Tuberías 108', 'Comas', 'Lima', 'Lima', -11.9300, -77.0500),
                                                                                                       (23, 'Av. Artística 109', 'Puente Piedra', 'Lima', 'Lima', -11.8800, -77.0700),
                                                                                                       (24, 'Jr. Estructuras 110', 'Carabayllo', 'Lima', 'Lima', -11.8600, -77.0300),
                                                                                                       (25, 'Calle Cocinas 111', 'Ancón', 'Lima', 'Lima', -11.7700, -77.1700),
                                                                                                       (26, 'Av. Industrial 112', 'Santa Rosa', 'Lima', 'Lima', -11.7800, -77.2000),
                                                                                                       (27, 'Jr. Naturaleza 113', 'Ventanilla', 'Callao', 'Callao', -11.8800, -77.1500),
                                                                                                       (28, 'Calle Automática 114', 'Bellavista', 'Callao', 'Callao', -12.0600, -77.1000),
                                                                                                       (29, 'Av. Telas 115', 'Carmen de la Legua', 'Callao', 'Callao', -12.0400, -77.0900),
                                                                                                       (30, 'Jr. Acabados 116', 'La Perla', 'Callao', 'Callao', -12.0700, -77.1100);

-- ============================
-- 6. Insertar trabajadores
-- ============================
INSERT INTO trabajadores (user_id, dni, antecedentes, verificacion_id, fecha_registro) VALUES
                                                                                           (1, '12345678', 'Sin antecedentes', 2, CURRENT_DATE),
                                                                                           (2, '23456789', 'Sin antecedentes', 2, CURRENT_DATE),
                                                                                           (3, '34567890', 'Sin antecedentes', 2, CURRENT_DATE),
                                                                                           (4, '45678901', 'Sin antecedentes', 2, CURRENT_DATE),
                                                                                           (5, '56789012', 'Sin antecedentes', 2, CURRENT_DATE),
                                                                                           (6, '67890123', 'Sin antecedentes', 2, CURRENT_DATE),
                                                                                           (7, '78901234', 'Sin antecedentes', 2, CURRENT_DATE),
                                                                                           (8, '89012345', 'Sin antecedentes', 2, CURRENT_DATE),
                                                                                           (9, '90123456', 'Sin antecedentes', 2, CURRENT_DATE),
                                                                                           (10, '01234567', 'Sin antecedentes', 2, CURRENT_DATE),
                                                                                           (11, '11234567', 'Sin antecedentes', 2, CURRENT_DATE),
                                                                                           (12, '12334567', 'Sin antecedentes', 2, CURRENT_DATE),
                                                                                           (13, '12344567', 'Sin antecedentes', 2, CURRENT_DATE),
                                                                                           (14, '12345567', 'Sin antecedentes', 2, CURRENT_DATE),
                                                                                           (15, '12345667', 'Sin antecedentes', 2, CURRENT_DATE),
                                                                                           (16, '12345677', 'Sin antecedentes', 2, CURRENT_DATE),
                                                                                           (17, '12345687', 'Sin antecedentes', 2, CURRENT_DATE),
                                                                                           (18, '12345697', 'Sin antecedentes', 2, CURRENT_DATE),
                                                                                           (19, '12345708', 'Sin antecedentes', 2, CURRENT_DATE),
                                                                                           (20, '12345718', 'Sin antecedentes', 2, CURRENT_DATE),
                                                                                           (21, '12345728', 'Sin antecedentes', 2, CURRENT_DATE),
                                                                                           (22, '12345738', 'Sin antecedentes', 2, CURRENT_DATE),
                                                                                           (23, '12345748', 'Sin antecedentes', 2, CURRENT_DATE),
                                                                                           (24, '12345758', 'Sin antecedentes', 2, CURRENT_DATE),
                                                                                           (25, '12345768', 'Sin antecedentes', 2, CURRENT_DATE),
                                                                                           (26, '12345778', 'Sin antecedentes', 2, CURRENT_DATE),
                                                                                           (27, '12345788', 'Sin antecedentes', 2, CURRENT_DATE),
                                                                                           (28, '12345798', 'Sin antecedentes', 2, CURRENT_DATE),
                                                                                           (29, '12345808', 'Sin antecedentes', 2, CURRENT_DATE),
                                                                                           (30, '12345818', 'Sin antecedentes', 2, CURRENT_DATE);

-- ============================
-- 7. Relacionar trabajadores con oficios
-- ============================
INSERT INTO trabajadores_oficio (trabajador_id, oficio_id) VALUES
                                                               (1, 1),  -- Juan Pérez - Electricista
                                                               (2, 2),  -- Ana García - Gasfitero
                                                               (3, 4),  -- Luis Ramírez - Pintor
                                                               (4, 3),  -- María López - Carpintero
                                                               (5, 5),  -- Pedro Torres - Técnico electrodomésticos
                                                               (6, 6),  -- Carlos Mendoza - Soldador
                                                               (7, 8),  -- Laura Vargas - Jardinero
                                                               (8, 9),  -- Roberto Silva - Cerrajero
                                                               (9, 10), -- Carmen Morales - Tapicero
                                                               (10, 7), -- Diego Herrera - Albañil
                                                               (11, 1), -- Sandra Castillo - Electricista
                                                               (12, 2), -- Andrés Ruiz - Gasfitero
                                                               (13, 4), -- Patricia Jiménez - Pintor
                                                               (14, 3), -- Fernando Guerrero - Carpintero
                                                               (15, 5), -- Rosa Delgado - Técnico electrodomésticos
                                                               (16, 6), -- Javier Paredes - Soldador
                                                               (17, 8), -- Mónica Sánchez - Jardinero
                                                               (18, 9), -- Raúl Vega - Cerrajero
                                                               (19, 10), -- Elena Romero - Tapicero
                                                               (20, 7), -- Miguel Flores - Albañil
                                                               (21, 1), -- Beatriz Aguilar - Electricista
                                                               (22, 2), -- Arturo Medina - Gasfitero
                                                               (23, 4), -- Lucía Ramos - Pintor
                                                               (24, 3), -- Enrique Cabrera - Carpintero
                                                               (25, 5), -- Silvia Ortega - Técnico electrodomésticos
                                                               (26, 6), -- Héctor Navarro - Soldador
                                                               (27, 8), -- Gloria Peña - Jardinero
                                                               (28, 9), -- Gonzalo Rojas - Cerrajero
                                                               (29, 10), -- Valeria Herrera - Tapicero
                                                               (30, 7); -- Óscar Mendoza - Albañil

-- ============================
-- 8. Insertar certificados (algunos trabajadores)
-- ============================
INSERT INTO certificados (trabajador_id, archivo_url, descripcion, fecha_subida) VALUES
                                                                                     (1, 'certificados/juan_perez_cert.pdf', 'Certificación eléctrica básica', NOW()),
                                                                                     (2, 'certificados/ana_garcia_cert.pdf', 'Plomería avanzada', NOW()),
                                                                                     (4, 'certificados/maria_lopez_cert.pdf', 'Carpintería profesional', NOW()),
                                                                                     (6, 'certificados/carlos_mendoza_cert.pdf', 'Soldadura estructural', NOW()),
                                                                                     (11, 'certificados/sandra_castillo_cert.pdf', 'Electricidad industrial', NOW()),
                                                                                     (14, 'certificados/fernando_guerrero_cert.pdf', 'Carpintería especializada', NOW()),
                                                                                     (16, 'certificados/javier_paredes_cert.pdf', 'Soldadura de tuberías', NOW()),
                                                                                     (21, 'certificados/beatriz_aguilar_cert.pdf', 'Electricidad residencial', NOW()),
                                                                                     (24, 'certificados/enrique_cabrera_cert.pdf', 'Estructuras de madera', NOW()),
                                                                                     (26, 'certificados/hector_navarro_cert.pdf', 'Soldadura industrial', NOW());