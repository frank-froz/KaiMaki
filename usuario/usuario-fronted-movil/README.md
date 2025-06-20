MVVM + Clean Architecture  para trabajar con Retrofit y ViewModels.

📁 Estructura recomendada del proyecto

com/
└── kaimaki/
└── usuario/
└── usuario_fronted_movil/
├── data/
│   ├── api/               ← Aquí va todo lo relacionado con Retrofit (API)
│   │   ├── ApiService.kt
│   │   └── RetrofitInstance.kt
│   ├── repository/           ← Implementación de repositorios
│   │   ├── UserRepositoryImpl.kt
│   │   └── ... (otros repos si tienes)
│   └── local/                ← (opcional) Para base de datos local, Room, etc.
│       └── ...
│
├── domain/
│   ├── model/                ← Modelos de datos que usas en tu app (User, AuthResponse)
│   │   ├── Usuario.kt
│   │   └── AuthResponse.kt
│   └── repository/           ← Interfaces de repositorios (contratos)
│       └── UserRepository.kt
│
├── ui/
│   ├── login/
│   │   ├── LoginActivity.kt
│   │   └── LoginViewModel.kt
│   ├── home/
│   │   ├── HomeActivity.kt
│   │   └── HomeViewModel.kt (opcional)
│   └── components/           ← (opcional) Para views reutilizables
│
└── util/                     ← Utilidades (clases helper, constantes, etc.)
└── Resource.kt, TokenManager.kt, Const.kt, etc.