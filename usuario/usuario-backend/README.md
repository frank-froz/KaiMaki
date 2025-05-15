# 📄 API - Registro de Usuario

## 📌 Descripción

Este backend permite registrar usuarios en el sistema KaiMaki.
Por ahora, solo está implementado el registro de usuario, pero más adelante se añadirá login, perfil, seguridad y más.

## 📍 URL - Endpoint de registro


POST `/api/usuarios/registro`

## 📥 Request Body (JSON)


```json

{
  "nombre": "Carlos",
  "apellido": "Ramirez",
  "correo": "carlos@example.com",
  "contrasena": "12345678",
  "telefono": "987654321"
}
```

## ✅ Campos obligatorios

- `nombre`: texto

- `apellido`: texto

- `correo`: formato válido de email

- `contrasena`: mínimo 8 caracteres

## 📤 Respuesta (201 - Created)

```json
{
  "mensaje": "Usuario registrado correctamente",
  "usuario": {
    "id": 1,
    "nombre": "Carlos",
    "apellido": "Ramirez",
    "correo": "carlos@example.com",
    "telefono": "987654321"
  }
}

```

## ❌ Errores posibles

- `400 Bad Request`: campos faltantes o mal formateados.

- `409 Conflict`: correo ya registrado.

- `500 Internal Server Error`: error inesperado del servidor.
