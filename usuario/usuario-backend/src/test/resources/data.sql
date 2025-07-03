INSERT INTO estados_verificacion (id, nombre) VALUES (2, 'Verificado');

INSERT INTO trabajadores (id, user_id, dni, antecedentes, verificacion_id, fecha_registro)
VALUES (1, 10, '12345678', 'Sin antecedentes', 2, '2024-05-01');

INSERT INTO trabajadores_oficios (trabajador_id, oficio)
VALUES (1, 'Electricista'), (1, 'Gasfitero');
