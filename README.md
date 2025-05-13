# KaiMaki 🛠️

**KaiMaki** es un marketplace de oficios que conecta a clientes con trabajadores especializados. Su objetivo es facilitar la búsqueda, contratación y comunicación entre ambas partes de forma segura, rápida y eficiente.

---

## 🚀 Arquitectura General

El sistema está dividido en dos grandes secciones:

### 1. **Administración**
- **Frontend**: React.js
- **Backend**: Django (REST Framework)
- Uso principal: gestión de usuarios, oficios, postulaciones, reclamos, y administración del sistema.

### 2. **Usuario (Cliente & Trabajador)**
- **Frontend Móvil**: Kotlin (Android)
- **Frontend Web**: React.js
- **Backend**: Spring Boot (REST API)
- Funcionalidades: registro, solicitud de servicios, negociación, mensajería, evaluación, etc.

### 🔗 Base de datos
- **Gestor**: MySQL
- Base de datos central compartida por todos los servicios.

---

## 📂 Estructura del Repositorio

```
/kaimaki/
│
├── backend-admin-django/ # Django REST para administración
├── backend-user-spring/ # Spring Boot para clientes y trabajadores
├── frontend-admin-react/ # Panel de control para administradores
├── frontend-web-user-react/ # Plataforma web para usuarios
├── mobile-user-kotlin/ # App móvil para usuarios
├── db/ # Scripts SQL y modelo E/R
├── docs/ # Documentación técnica y de diseño
└── README.md # Este archivo

```


---

## 🔒 Roles en el sistema

- **Administrador**: Gestiona usuarios, oficios, postulaciones, y atiende reclamos.
- **Trabajador**: Ofrece servicios, negocia y acepta solicitudes.
- **Cliente**: Solicita servicios, negocia y califica trabajadores.

---

## ⚙️ Funcionalidades Principales

- Registro y autenticación con roles diferenciados.
- Sistema de solicitudes y negociación entre clientes y trabajadores.
- Mensajería en tiempo real.
- Gestión de oficios y postulaciones con revisión de documentos.
- Módulo de reclamos y resolución de conflictos.

---

## 🛠️ En desarrollo

Actualmente nos encontramos trabajando en:

- [ ] Crear el modelo E/R de la BBDD.
- [ ] Desarrollo de los prototipos en figma de la página de registro.
- [ ] Desarrollo del backend para el registro del usuario.
- [ ] Desarrollo del frontend para el registro del usuario.
- [ ] Desarrollo de los prototipos de la página de vista de los trabajadores disponibles.

---

## 👥 Equipo

| Nombre              | Rol                  | 
|---------------------|----------------------|
| Franklin Huaytalla  | Scrum Master         | 
|   Hector Castro     | Scrum team           | 
|   Kenedy Ramos      | Scrum team           | 

---


## 📄 Licencia

MIT License © 2025 KaiMaki Dev Team
