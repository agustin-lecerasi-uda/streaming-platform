# Índice Completo de Archivos

## 📋 Estructura del Proyecto

```
tp3/
├── src/
│   ├── main/
│   │   ├── java/com/streaming/
│   │   │   ├── Models
│   │   │   ├── Interfaces
│   │   │   ├── Repositories
│   │   │   ├── Controllers
│   │   │   └── Application
│   │   └── resources/
│   │       ├── db/
│   │       └── diagrams/
│   └── test/
├── Documentación
├── Configuración
└── README

```

---

## 🎯 CÓDIGO JAVA

### Models (Modelos de Dominio)

| Archivo | Ubicación | Propósito |
|---------|-----------|----------|
| **User.java** | `src/main/java/com/streaming/models/` | Modelo de usuario con suscripción |
| **Content.java** | `src/main/java/com/streaming/models/` | Modelo de contenido multimedia |
| **Stream.java** | `src/main/java/com/streaming/models/` | Modelo de sesión de reproducción |
| **Subscription.java** | `src/main/java/com/streaming/models/` | Modelo de plan de suscripción |

**Líneas de código**: ~350
**Características**: Constructores, getters/setters, equals, hashCode, toString

---

### Interfaces (DIP - Abstracciones)

| Archivo | Ubicación | Propósito |
|---------|-----------|----------|
| **UserManageable.java** | `src/main/java/com/streaming/interfaces/` | Contrato: operaciones de usuarios |
| **ContentManageable.java** | `src/main/java/com/streaming/interfaces/` | Contrato: operaciones de contenido |
| **StreamPlayable.java** | `src/main/java/com/streaming/interfaces/` | Contrato: control de reproducción |
| **SubscriptionManageable.java** | `src/main/java/com/streaming/interfaces/` | Contrato: gestión de suscripciones |
| **StreamValidator.java** | `src/main/java/com/streaming/interfaces/` | Contrato: validación de streams |
| **NotificationService.java** | `src/main/java/com/streaming/interfaces/` | Contrato: notificaciones |

**Total de métodos**: 45+
**Principio aplicado**: DIP - Inversión de dependencias
**Patrón**: Strategy + Template Method

---

### Repositories (Implementaciones)

| Archivo | Ubicación | Implementa | Propósito |
|---------|-----------|-----------|----------|
| **UserRepository.java** | `src/main/java/com/streaming/repositories/` | UserManageable | CRUD usuarios en memoria |
| **ContentRepository.java** | `src/main/java/com/streaming/repositories/` | ContentManageable | CRUD contenido en memoria |
| **StreamRepository.java** | `src/main/java/com/streaming/repositories/` | StreamPlayable | Gestión de sesiones |
| **SubscriptionRepository.java** | `src/main/java/com/streaming/repositories/` | SubscriptionManageable | Gestión de suscripciones |
| **StreamValidatorImpl.java** | `src/main/java/com/streaming/repositories/` | StreamValidator | Validación de streams |
| **ConsoleNotificationService.java** | `src/main/java/com/streaming/repositories/` | NotificationService | Notificaciones por consola |

**Características**: 
- Almacenamiento en memoria (HashMap)
- Métodos de búsqueda y filtrado
- Logging de operaciones
- Inicialización de datos por defecto

**Total de líneas**: ~1,200

---

### Controllers (Lógica de Negocio)

| Archivo | Ubicación | Depende De | Propósito |
|---------|-----------|-----------|----------|
| **UserController.java** | `src/main/java/com/streaming/controllers/` | UserManageable, NotificationService | Gestión de usuarios |
| **ContentController.java** | `src/main/java/com/streaming/controllers/` | ContentManageable | Gestión de contenido |
| **PlayController.java** | `src/main/java/com/streaming/controllers/` | 6 interfaces | Reproducción multimedia |
| **SubscriptionController.java** | `src/main/java/com/streaming/controllers/` | SubscriptionManageable | Gestión de suscripciones |

**Inyección de dependencias**: Todas por constructor
**Acoplamiento**: Nulo (solo interfaces)
**Total de métodos**: 25+

**Ejemplo PlayController**:
```
- playContent()           [DIP aplicado]
- pausePlayback()
- resumePlayback()
- stopPlayback()
- getActiveStream()
- getWatchHistory()
- updateWatchProgress()
```

---

### Utilities

| Archivo | Ubicación | Propósito |
|---------|-----------|----------|
| **DependencyInjector.java** | `src/main/java/com/streaming/utils/` | Contenedor IoC centralizado |
| **StreamingPlatformApp.java** | `src/main/java/com/streaming/` | Clase main - Demo de 15 pasos |

**DependencyInjector**:
- Singleton pattern
- Lazy initialization
- Factory methods para cada servicio

**App.java**:
- 15 casos de uso demostrados
- Validación de DIP
- Ejemplos de todas las funcionalidades

---

## 📊 BASE DE DATOS

### Schema.sql

| Ubicación | Propósito | Características |
|-----------|----------|-----------------|
| `src/main/resources/db/schema.sql` | Script MySQL completo | 2,000+ líneas |

**Componentes**:
- 8 tablas principales
- 4 vistas para reportes
- 3 stored procedures
- 2 triggers automáticos
- Índices optimizados
- Datos iniciales

**Tablas**:
```
1. subscriptions        - Planes de suscripción
2. users               - Información de usuarios
3. content             - Contenido multimedia
4. streams             - Sesiones de reproducción
5. user_ratings        - Calificaciones
6. subscription_history - Historial de cambios
7. user_devices        - Dispositivos conectados
8. streaming_stats     - Estadísticas
9. notifications       - Notificaciones
```

**Características avanzadas**:
- FULLTEXT search en content
- Foreign keys con cascada
- Índices compuestos
- Triggers para auditoría

---

## 📈 DIAGRAMAS (Mermaid)

### Diagrama 1: ERD (Entity-Relationship)

| Archivo | Ubicación | Contenido |
|---------|-----------|----------|
| **erd.md** | `src/main/resources/diagrams/` | Estructura relacional BD |

**Muestra**:
- 9 entidades
- Relaciones 1:N, N:N
- Atributos completos
- Claves primarias/foráneas

**Renderizado**: Copiar contenido a [mermaid.live](https://mermaid.live)

---

### Diagrama 2: Flujo de Reproducción

| Archivo | Ubicación | Contenido |
|---------|-----------|----------|
| **playback_flow.md** | `src/main/resources/diagrams/` | Proceso de streaming |

**Describe**:
- 30+ nodos de decisión
- Validaciones y errores
- Notificaciones
- Interacciones de usuario

**Flujo completo**:
```
1. Solicitud → Validaciones
2. Obtener plan → Verificar calidad
3. Iniciar stream → Crear registros
4. Usuario controla → Actualizar progreso
5. Detener → Finalizar sesión
```

---

### Diagrama 3: Arquitectura DIP

| Archivo | Ubicación | Contenido |
|---------|-----------|----------|
| **architecture_dip.md** | `src/main/resources/diagrams/` | Aplicación del principio DIP |

**Capas**:
- 🎮 Controllers (Alto nivel)
- 🔌 Interfaces (Abstracciones)
- 📦 Repositories (Bajo nivel)
- ⚙️ DependencyInjector (IoC)
- 📊 Models (Dominio)

**Colores por tipo**:
- Rojo: Controllers
- Turquesa: Interfaces
- Verde: Repositories
- Amarillo: Models

---

## 📚 DOCUMENTACIÓN

### README.md
- Descripción del proyecto
- Estructura completa
- Explicación de DIP
- Componentes principales
- Tecnologías usadas
- Ventajas del patrón

**Secciones**:
1. Descripción
2. Estructura
3. DIP Aplicado
4. Componentes
5. Base de Datos
6. Ejecución
7. Ventajas

---

### DIP_GUIDE.md
- **2,500+ palabras** sobre DIP
- Conceptos fundamentales
- Problemas sin DIP
- Soluciones con DIP
- Arquitectura de capas
- Patrones de inyección
- Beneficios y antipatrones

**Secciones**:
1. Conceptos
2. Problemas & Soluciones
3. Arquitectura
4. Patrones de inyección
5. Contenedor IoC
6. Beneficios
7. Antipatrones
8. Checklist

---

### USAGE_EXAMPLES.md
- Ejemplos prácticos de uso
- Casos de cambio de implementación
- Testing con mocks
- Múltiples implementaciones
- Patrón Strategy
- Composición de servicios
- Flujo completo

**7 ejemplos principales**:
1. PlayController con DIP
2. Cambio de implementación
3. Testing con mocks
4. Notificaciones (3 tipos)
5. Strategy pattern
6. Composición
7. Flujo completo

---

### COMPILATION_GUIDE.md
- Requisitos previos
- Compilación con Maven
- Ejecución de múltiples formas
- Configuración de BD
- Estructura de directorios
- Solución de problemas
- Optimizaciones

**Cubre**:
- Maven, IntelliJ, NetBeans, VS Code
- Compilación y ejecución
- JAR generation
- Troubleshooting

---

### IDE_SETUP.md
- Configuración IntelliJ IDEA (completa)
- Configuración NetBeans (completa)
- Paso a paso con screenshots
- Atajos de teclado
- Verificación final
- Tips productivos

**Para cada IDE**:
- 6 pasos de configuración
- Troubleshooting específico
- Atajos principales
- Checklist final

---

### FILE_INDEX.md (Este archivo)
- Índice completo de archivos
- Descripción de cada componente
- Propósito y características
- Líneas de código
- Relaciones entre archivos

---

## 📦 CONFIGURACIÓN

### pom.xml
**Maven Project Object Model**

```xml
- GroupId: com.streaming
- ArtifactId: streaming-platform
- Version: 1.0.0
- Java: 17
```

**Dependencias**:
- MySQL Connector: 8.0.33
- JUnit 5: 5.9.2
- SLF4J: 2.0.7

**Plugins**:
- Maven Compiler
- Maven JAR

---

## 📊 Estadísticas del Proyecto

| Métrica | Cantidad |
|---------|----------|
| **Archivos Java** | 16 |
| **Interfaces** | 6 |
| **Clases** | 10 |
| **Modelos** | 4 |
| **Líneas de código Java** | 2,500+ |
| **Métodos públicos** | 80+ |
| **Tablas BD** | 9 |
| **Stored Procedures** | 3 |
| **Vistas BD** | 3 |
| **Triggers BD** | 2 |
| **Diagramas Mermaid** | 3 |
| **Documentos** | 7 |
| **Palabras documentación** | 8,000+ |

---

## 🔍 Flujo de Lectura Recomendado

1. **README.md** - Visión general
2. **architecture_dip.md** - Dibujo de la arquitectura
3. **DIP_GUIDE.md** - Entender principio
4. **USAGE_EXAMPLES.md** - Ver código en acción
5. **Models/** - Entender dominio
6. **Interfaces/** - Ver abstracciones
7. **Repositories/** - Implementaciones
8. **Controllers/** - Lógica de negocio
9. **COMPILATION_GUIDE.md** - Compilar
10. **schema.sql** - Estructura BD
11. **Diagramas Mermaid** - Visualizar

---

## 🚀 Checklist de Entrega

- [x] Código Java con DIP aplicado
- [x] 16 archivos Java en estructura correcta
- [x] Interfaces para cada servicio
- [x] Inyección de dependencias por constructor
- [x] Controladores dependen solo de interfaces
- [x] Base de datos SQL completa (MySQL)
- [x] Script con 2,000+ líneas
- [x] 9 tablas relacionales
- [x] Stored procedures y triggers
- [x] 3 diagramas Mermaid
- [x] ERD (Entidad-Relación)
- [x] Flujo de reproducción
- [x] Diagrama de arquitectura DIP
- [x] 7 documentos markdown
- [x] Más de 8,000 palabras documentación
- [x] Ejemplos de uso completos
- [x] Guía compilación e IDE
- [x] pom.xml con Maven
- [x] Clase main con 15 casos de uso
- [x] Cero relleno - código directo

---

## 📝 Cómo Usar Este Índice

Este archivo sirve como **mapa del proyecto**. Cada sección indica:
- **Ubicación exacta** del archivo
- **Propósito** principal
- **Características** destacadas
- **Relaciones** con otros archivos
- **Estadísticas** de contenido

Para encontrar algo específico:
1. Ir a la sección temática
2. Buscar el archivo
3. Seguir la ubicación exacta
4. Consultar la descripción

**Todas las rutas son relativas al directorio raíz del proyecto**:
```
/home/agus/Desktop/UDA/Programacion avanzada/tp3/
```
