# Plataforma de Streaming Multimedia - Backend

## Descripción
Backend de una plataforma de streaming multimedia implementada en Java aplicando **estrictamente el principio SOLID DIP (Dependency Inversion Principle)**.

## Estructura del Proyecto

```
src/main/java/com/streaming/
├── StreamingPlatformApp.java          # Clase main - punto de entrada
├── controllers/                        # Capa de Controladores (Alto nivel)
│   ├── UserController.java
│   ├── ContentController.java
│   ├── PlayController.java
│   └── SubscriptionController.java
├── interfaces/                         # Capa de Abstracciones (DIP)
│   ├── UserManageable.java
│   ├── ContentManageable.java
│   ├── StreamPlayable.java
│   ├── StreamValidator.java
│   ├── SubscriptionManageable.java
│   └── NotificationService.java
├── repositories/                       # Capa de Implementaciones (Bajo nivel)
│   ├── UserRepository.java
│   ├── ContentRepository.java
│   ├── StreamRepository.java
│   ├── SubscriptionRepository.java
│   ├── StreamValidatorImpl.java
│   └── ConsoleNotificationService.java
├── models/                             # Modelos de Dominio
│   ├── User.java
│   ├── Content.java
│   ├── Stream.java
│   └── Subscription.java
└── utils/                              # Utilidades
    └── DependencyInjector.java         # Inyector de Dependencias

src/main/resources/
├── db/
│   └── schema.sql                      # Script SQL completo para MySQL
└── diagrams/
    ├── erd.md                          # Diagrama Entidad-Relación (Mermaid)
    ├── playback_flow.md                # Diagrama de Flujo (Mermaid)
    └── architecture_dip.md             # Diagrama de Arquitectura DIP (Mermaid)
```

## Principio DIP Aplicado

### ¿Qué es DIP?
El Dependency Inversion Principle establece:
1. Los módulos de alto nivel NO deben depender de módulos de bajo nivel.
2. Ambos deben depender de abstracciones (interfaces).
3. Las abstracciones NO deben depender de detalles.
4. Los detalles deben depender de abstracciones.

### Aplicación en este proyecto

**Sin DIP (Acoplamiento fuerte):**
```java
public class PlayController {
    private UserRepository userRepo = new UserRepository();  // ❌ Acoplado
    private StreamRepository streamRepo = new StreamRepository();  // ❌ Acoplado
}
```

**Con DIP (Desacoplamiento):**
```java
public class PlayController {
    private final UserManageable userService;      // ✅ Depende de Interfaz
    private final StreamPlayable streamService;    // ✅ Depende de Interfaz
    
    public PlayController(UserManageable userService, 
                         StreamPlayable streamService) {
        this.userService = userService;
        this.streamService = streamService;
    }
}
```

## Componentes Principales

### 1. Interfaces (Abstracciones)
- `UserManageable`: Operaciones sobre usuarios
- `ContentManageable`: Operaciones sobre contenido
- `StreamPlayable`: Control de reproducción
- `StreamValidator`: Validación de streams
- `SubscriptionManageable`: Gestión de suscripciones
- `NotificationService`: Notificaciones

### 2. Repositorios (Implementaciones)
- `UserRepository`: Implementa `UserManageable`
- `ContentRepository`: Implementa `ContentManageable`
- `StreamRepository`: Implementa `StreamPlayable`
- `SubscriptionRepository`: Implementa `SubscriptionManageable`
- `StreamValidatorImpl`: Implementa `StreamValidator`
- `ConsoleNotificationService`: Implementa `NotificationService`

### 3. Controladores (Lógica de Negocio)
Reciben las dependencias por constructor e interactúan solo a través de interfaces.

### 4. DependencyInjector
Contenedor IoC que centraliza la creación de objetos.

## Base de Datos

### Tablas principales
- `users`: Información de usuarios
- `subscriptions`: Planes de suscripción
- `content`: Contenido multimedia
- `streams`: Sesiones de reproducción
- `user_ratings`: Calificaciones de usuarios
- `subscription_history`: Historial de cambios
- `user_devices`: Dispositivos conectados
- `streaming_stats`: Estadísticas de visualización

### Características
- Stored Procedures para operaciones complejas
- Vistas para reportes
- Triggers para actualización automática de estadísticas
- Índices optimizados para búsquedas

## Ejecución

### Compilación con Maven
```bash
cd /path/to/project
mvn clean compile
```

### Ejecutar aplicación
```bash
mvn exec:java -Dexec.mainClass="com.streaming.StreamingPlatformApp"
```

O compilar y ejecutar JAR:
```bash
mvn clean package
java -jar target/streaming-platform-1.0.0.jar
```

## Flujo Principal: Reproducir Contenido

1. Usuario solicita reproducción
2. PlayController valida usuario y contenido
3. Consulta plan de suscripción a través de `UserManageable`
4. Valida calidad con `StreamValidator`
5. Verifica límites de dispositivos con `SubscriptionManageable`
6. Inicia stream a través de `StreamPlayable`
7. Notifica al usuario con `NotificationService`

## Ventajas de DIP

✅ **Bajo acoplamiento**: Los cambios en repositorios no afectan controladores
✅ **Fácil testing**: Se pueden usar mocks de interfaces
✅ **Extensibilidad**: Nuevas implementaciones sin cambiar código existente
✅ **Mantenibilidad**: Código más limpio y predecible
✅ **Flexibilidad**: Cambiar de base de datos sin modificar lógica de negocio

## Tecnologías

- **Lenguaje**: Java 17
- **Gestor de dependencias**: Maven
- **Base de datos**: MySQL 8.0+
- **Patrones**: DIP, Inyección de Dependencias, Repositorio

## Diagramas Incluidos

### 1. ERD (Entity-Relationship Diagram)
Estructura completa de la base de datos con relaciones.

### 2. Playback Flow
Flujo completo del proceso de reproducción con validaciones.

### 3. Architecture DIP
Diagrama de capas mostrando cómo se aplica el DIP.
