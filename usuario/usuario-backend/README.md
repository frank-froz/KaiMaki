# 📄 API - Registro de Usuario

## 📌 Descripción

Este backend permite registrar usuarios en el sistema KaiMaki.
Por ahora, solo está implementado el registro de usuario, pero más adelante se añadirá login, perfil, seguridad y más.

## 📍 URL - Endpoint de registro


POST `/api/usuarios/registro`

## 📥 Request Body (JSON)


```json

{
    "nombre": "Fulano",
    "correo":"fulanito@tecsup.edu.pe",
    "contrasena":"12345678"
}
```

## ✅ Campos obligatorios

- `nombre`: texto

- `correo`: formato válido de email

- `contrasena`: mínimo 8 caracteres

## 📤 Respuesta (201 - Created)

```json
{
    "id": 9,
    "nombre": "Fulano",
    "apellido": null,
    "correo": "fulanito@tecsup.edu.pe",
    "contrasena": "$2a$10$.n2RLrwjDi766Jgf.c5ECuut/FPOpoxI2Ypu/v0BRDAtxXiN.gcDG",
    "telefono": null,
    "rolId": 1,
    "estadoId": 1
}

```

## ❌ Errores posibles

- `400 Bad Request`: campos faltantes o mal formateados.

- `409 Conflict`: correo ya registrado.

- `500 Internal Server Error`: error inesperado del servidor.
