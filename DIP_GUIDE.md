# DIP (Dependency Inversion Principle) - Guía de Implementación

## Conceptos Fundamentales

### 1. Definición
El Dependency Inversion Principle (DIP) es el quinto principio de SOLID que establece:

**"Los módulos de alto nivel no deben depender de módulos de bajo nivel. Ambos deben depender de abstracciones."**

### 2. Problema sin DIP

```java
// ❌ MALO: Acoplamiento directo
public class PlayController {
    private UserRepository userRepo;
    private StreamRepository streamRepo;
    
    public PlayController() {
        this.userRepo = new UserRepository();      // Creación directa
        this.streamRepo = new StreamRepository();   // Creación directa
    }
    
    public void playContent(Long userId, Long contentId) {
        User user = userRepo.findById(userId);     // Dependencia fuerte
        Stream stream = streamRepo.startStream(userId, contentId);
    }
}
```

**Problemas:**
- PlayController está acoplado a implementaciones concretas
- Imposible cambiar UserRepository sin modificar PlayController
- Difícil de testear (no se pueden usar mocks)
- Cambio de base de datos requiere modificar todas las clases

### 3. Solución con DIP

```java
// ✅ BUENO: Inyección de dependencias + Interfaces
public interface UserManageable {
    Optional<User> findById(Long id);
}

public interface StreamPlayable {
    Stream startStream(Long userId, Long contentId, String quality, String deviceType);
}

public class PlayController {
    private final UserManageable userService;      // Interfaz
    private final StreamPlayable streamService;    // Interfaz
    
    // Constructor: Inversión de Control (IoC)
    public PlayController(UserManageable userService, 
                         StreamPlayable streamService) {
        this.userService = userService;
        this.streamService = streamService;
    }
    
    public void playContent(Long userId, Long contentId) {
        User user = userService.findById(userId).orElseThrow();  // A través de interfaz
        Stream stream = streamService.startStream(userId, contentId, "HD", "TV");
    }
}

// Implementación concreta
public class UserRepository implements UserManageable {
    @Override
    public Optional<User> findById(Long id) {
        // Implementación...
    }
}

public class StreamRepository implements StreamPlayable {
    @Override
    public Stream startStream(Long userId, Long contentId, String quality, String deviceType) {
        // Implementación...
    }
}
```

## Arquitectura de Capas con DIP

```
┌─────────────────────────────────────────────┐
│         CAPA DE PRESENTACIÓN                │
│  (PlayController, UserController, etc.)     │
│  [Alto Nivel - Lógica de Negocio]           │
└─────────────────────────────────────────────┘
                      ▲
                      │
            ╔═════════╩═════════╗
            │ DEPENDE DE        │
            ▼                   ▼
┌───────────────────────┐  ┌──────────────────┐
│   INTERFACES (DIP)    │  │   ABSTRACCIONES  │
├───────────────────────┤  ├──────────────────┤
│ UserManageable        │  │ - Define contrato│
│ ContentManageable     │  │ - Define métodos │
│ StreamPlayable        │  │ - Independiente  │
│ StreamValidator       │  │   de impl.       │
│ NotificationService   │  └──────────────────┘
└───────────────────────┘
         ▲
         │
    ╔════╩═════════════════════════════════╗
    │ IMPLEMENTADA POR                      │
    ▼                                       ▼
┌──────────────────┐        ┌──────────────────────┐
│    REPOSITORIOS  │        │  CAPA DE DATOS       │
│  (Bajo Nivel)    │        │  (Implementación)    │
├──────────────────┤        ├──────────────────────┤
│ UserRepository   │───────▶│  Base de Datos       │
│ ContentRepository│───────▶│  Cache               │
│ StreamRepository │───────▶│  APIs Externas       │
└──────────────────┘        └──────────────────────┘
```

## Patrón de Inyección de Dependencias

### Inyección por Constructor (RECOMENDADO)
```java
public class PlayController {
    private final UserManageable userService;
    private final StreamPlayable streamService;
    
    // Constructor Injection
    public PlayController(UserManageable userService, 
                         StreamPlayable streamService) {
        this.userService = userService;
        this.streamService = streamService;
    }
}
```

**Ventajas:**
- Dependencias explícitas
- Inmutabilidad
- Facilita testing

### Inyección por Setter (MENOS RECOMENDADO)
```java
public class PlayController {
    private UserManageable userService;
    
    public void setUserService(UserManageable userService) {
        this.userService = userService;
    }
}
```

**Problemas:**
- Dependencias no explícitas
- Objeto en estado inconsistente
- Difícil de testear

## Contenedor IoC (Dependency Injector)

```java
public class DependencyInjector {
    // Singleton instances
    private static final UserManageable userService = new UserRepository();
    private static final ContentManageable contentService = new ContentRepository();
    private static final StreamPlayable streamService = new StreamRepository();
    
    // Factory methods
    public static UserManageable getUserService() {
        return userService;
    }
    
    public static StreamPlayable getStreamService() {
        return streamService;
    }
}

// Uso
UserManageable userService = DependencyInjector.getUserService();
PlayController controller = new PlayController(userService, streamService);
```

## Beneficios del DIP

### 1. Flexibilidad
```java
// Cambiar implementación sin tocar PlayController
UserManageable userService = new DatabaseUserRepository();  // BD tradicional
// OR
UserManageable userService = new CachedUserRepository();    // Con cache
// OR
UserManageable userService = new MockUserRepository();      // Para testing
```

### 2. Testabilidad
```java
@Test
public void testPlayContent() {
    // Mock
    UserManageable mockUserService = mock(UserManageable.class);
    StreamPlayable mockStreamService = mock(StreamPlayable.class);
    
    PlayController controller = new PlayController(mockUserService, mockStreamService);
    
    when(mockUserService.findById(1L))
        .thenReturn(Optional.of(new User(1L, "juan", "juan@test.com", "pass", "Premium")));
    
    controller.playContent(1L, 1L);
    
    verify(mockStreamService).startStream(1L, 1L, "4K", "TV");
}
```

### 3. Mantenibilidad
- Código centralizado (DependencyInjector)
- Cambios localizados
- Fácil de entender el flujo

### 4. Escalabilidad
- Nuevas implementaciones sin modificar existentes
- Cumplimiento del Open/Closed Principle
- Arquitectura preparada para crecimiento

## Ejemplo Real de Cambio

### Situación 1: BD en memoria (Desarrollo)
```java
class DependencyInjector {
    public static UserManageable getUserService() {
        return new UserRepository();  // En memoria
    }
}
```

### Situación 2: BD real (Producción)
```java
class DependencyInjector {
    public static UserManageable getUserService() {
        return new DatabaseUserRepository();  // MySQL
    }
}
```

**PlayController no cambia en absoluto!**

## Antipatrones a Evitar

### ❌ Creación directa de objetos
```java
public class PlayController {
    private UserRepository userRepo = new UserRepository();  // MAL
}
```

### ❌ Service Locator
```java
public class PlayController {
    public void playContent(Long userId) {
        User user = ServiceLocator.getUserService().findById(userId);  // MAL
    }
}
```

### ❌ Dependencias globales
```java
public class PlayController {
    public void playContent(Long userId) {
        User user = GlobalServices.getUserService().findById(userId);  // MAL
    }
}
```

## Checklist DIP

- [ ] ¿Hay interfaces para cada servicio?
- [ ] ¿Los controladores dependen de interfaces, no de implementaciones?
- [ ] ¿Las dependencias se inyectan por constructor?
- [ ] ¿Existe un contenedor IoC centralizado?
- [ ] ¿Se pueden crear mocks de las interfaces fácilmente?
- [ ] ¿El cambio de implementación no requiere modificar controladores?
- [ ] ¿Hay bajo acoplamiento entre capas?
