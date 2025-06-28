# 🧱 MVVM + Clean Architecture con Retrofit y ViewModels

Este proyecto sigue una arquitectura limpia basada en el patrón **MVVM (Model-View-ViewModel)**, utilizando Retrofit para el consumo de APIs y separación clara de responsabilidades en cada capa.

---

## 📁 Estructura recomendada del proyecto

```
com/
└── kaimaki/
    └── usuario/
        └── usuario_fronted_movil/
            ├── data/
            │   ├── api/
            │   │   ├── AuthApi.kt
            │   │   └── RetrofitInstance.kt
            │   ├── repository/
            │   │   ├── UserRepositoryImpl.kt
            │   │   └── ...
            │
            ├── domain/
            │   ├── model/
            │   │   ├── Usuario.kt
            │   │   └── AuthResponse.kt
            │   └── repository/
            │       └── UserRepository.kt
            │
            ├── ui/
            │   ├── login/
            │   │   ├── LoginActivity.kt
            │   │   └── LoginViewModel.kt
            │   ├── home/
            │   │   ├── HomeActivity.kt
            │   │   └── HomeViewModel.kt
            │   └── components/
            │
            └── util/
                └── TokenManager.kt
```

---

## 🧩 Capas de la Arquitectura

### 🔹 data
Contiene implementaciones concretas para acceder a datos (API REST, local, etc.).


### 🔹 domain
Capa intermedia con las **interfaces** de los repositorios y los **modelos de negocio**.


### 🔹 ui
Contiene la lógica de presentación y pantallas de la app.


### 🔹 util
Utilidades generales como manejo de tokens, validaciones, helpers, etc.

---

## 🚀 Tecnologías utilizadas

- Kotlin
- Android Architecture Components (ViewModel, LiveData)
- Retrofit2
- Coroutines
- Clean Architecture
- MVVM


---