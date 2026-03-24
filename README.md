# ordonez-post2-u3 — TaskApp

**Aplicaciones Móviles · Unidad 3 · Post-Contenido 2**  
Universidad de Santander (UDES) · Ingeniería de Sistemas · 2026  
**Autor:** Ordonez Aguilar

---

## Descripción

Refactorización de TaskApp hacia **Clean Architecture**, migrando de Hilt a **Koin** como framework de inyección de dependencias e introduciendo un Use Case para encapsular la lógica de filtrado de tareas pendientes.

---

## Arquitectura — Clean Architecture

El proyecto está organizado en tres capas formales que respetan la **Regla de Dependencias**: las capas externas dependen de las internas, nunca al revés.

```
com.tuusuario.taskapp/
├── domain/                        ← Kotlin puro, sin imports Android
│   ├── model/
│   │   └── Task.kt
│   ├── repository/
│   │   └── TaskRepository.kt      ← Interfaz pura
│   └── usecase/
│       └── GetPendingTasksUseCase.kt
├── data/                          ← Implementaciones concretas
│   └── repository/
│       └── InMemoryTaskRepository.kt
├── presentation/                  ← UI y ViewModel
│   ├── viewmodel/
│   │   └── TaskViewModel.kt
│   └── ui/
│       └── TaskListScreen.kt
└── di/                            ← Inyección de dependencias con Koin
    └── AppModule.kt
```

### Descripción de cada capa

**domain/** — Núcleo de la aplicación. Contiene la lógica de negocio pura en Kotlin sin ninguna dependencia del SDK de Android. Define las entidades (`Task`), la interfaz del repositorio (`TaskRepository`) y los casos de uso (`GetPendingTasksUseCase`).

**data/** — Implementa las interfaces definidas en `domain`. `InMemoryTaskRepository` provee los datos en memoria y puede reemplazarse por una implementación con Room o Retrofit sin tocar las capas superiores.

**presentation/** — Contiene el `TaskViewModel` que consume el Use Case vía Koin y expone el estado de UI como `StateFlow`. La pantalla `TaskListScreen` es un Composable que observa el estado del ViewModel.

**di/** — Módulo Koin (`appModule`) que configura el grafo de dependencias: `single` para el repositorio, `factory` para el Use Case y `viewModel` para el ViewModel.

---

## Tecnologías utilizadas

| Tecnología | Versión | Uso |
|---|---|---|
| Kotlin | 1.9+ | Lenguaje principal |
| Jetpack Compose | BOM 2024.06 | UI declarativa |
| Koin | 3.5.6 | Inyección de dependencias |
| ViewModel + StateFlow | 2.8.3 | Estado de UI reactivo |
| Coroutines | 1.8.1 | Operaciones asíncronas |
| JUnit 4 + coroutines-test | 4.13.2 / 1.8.1 | Tests unitarios |

---

## Módulo Koin — AppModule

```kotlin
val appModule = module {
    single<TaskRepository> { InMemoryTaskRepository() }   // Singleton
    factory { GetPendingTasksUseCase(get()) }              // Nueva instancia por solicitud
    viewModel { TaskViewModel(get()) }                     // Scoped al ViewModel
}
```

---

## Checkpoints de verificación

### ✅ Checkpoint 1 — Capa domain sin imports Android
El paquete `domain/` es Kotlin puro. Verificado con búsqueda `Ctrl+Shift+F` → `import android` dentro de la carpeta `domain` → **0 coincidencias**.

> 📸 *Ver: `screenshots/checkpoint1-domain-sin-android.png`*

---

### ✅ Checkpoint 2 — App ejecuta y filtra tareas pendientes
La aplicación compila y ejecuta correctamente con Koin. La lista muestra únicamente las **4 tareas pendientes** de 5 totales, excluyendo correctamente la tarea con `completed = true` ("Leer documentación de Koin DSL").

> 📸 *Ver: `screenshots/checkpoint2-app-tareas-filtradas.png`*

---

### ✅ Checkpoint 3 — Tests unitarios del Use Case pasan
Los dos tests unitarios de `GetPendingTasksUseCaseTest` verifican:
- `retorna solo tareas pendientes` — filtra correctamente las completadas
- `retorna lista en orden descendente por ID` — ordena de mayor a menor ID

Ejecutados con `./gradlew test` → **BUILD SUCCESSFUL**, ambos tests **PASSED**.

> 📸 *Ver: `screenshots/checkpoint3-tests-passed.png`*

---

## Commits principales

```
1. Migra de Hilt a Koin y reorganiza en Clean Architecture
2. Agrega GetPendingTasksUseCaseTest con dos casos de prueba
3. Agrega README con documentacion de arquitectura y evidencias
```

---

## Cómo ejecutar

1. Clona el repositorio:
   ```bash
   git clone https://github.com/tuusuario/ordonez-post2-u3.git
   ```
2. Abre el proyecto en **Android Studio Hedgehog** o superior
3. Ejecuta en emulador con **API 36** (o superior)
4. Para correr los tests unitarios:
   ```bash
   ./gradlew test
   ```
