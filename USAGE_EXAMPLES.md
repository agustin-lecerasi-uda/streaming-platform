# Ejemplos de Uso - DIP en Acción

## 1. Ejemplo: PlayController con DIP

### Inicio Simple
```java
// Obtener servicios del inyector de dependencias
UserManageable userService = DependencyInjector.getUserService();
StreamPlayable streamService = DependencyInjector.getStreamService();
StreamValidator streamValidator = DependencyInjector.getStreamValidator();
ContentManageable contentService = DependencyInjector.getContentService();
SubscriptionManageable subscriptionService = DependencyInjector.getSubscriptionService();
NotificationService notificationService = DependencyInjector.getNotificationService();

// Inyectar dependencias en el controlador
PlayController playController = new PlayController(
    streamService, 
    streamValidator,
    userService, 
    contentService, 
    subscriptionService, 
    notificationService
);

// Usar el controlador
Stream stream = playController.playContent(1L, 1L, "4K", "Smart TV");
```

### Cómo funciona internamente

```java
// PlayController.java
public class PlayController {
    // Todas las dependencias son abstracciones (Interfaces)
    private final StreamPlayable streamService;
    private final StreamValidator streamValidator;
    private final UserManageable userService;
    private final ContentManageable contentService;
    private final SubscriptionManageable subscriptionService;
    private final NotificationService notificationService;

    // Constructor con inyección
    public PlayController(StreamPlayable streamService, 
                         StreamValidator streamValidator,
                         UserManageable userService,
                         ContentManageable contentService,
                         SubscriptionManageable subscriptionService,
                         NotificationService notificationService) {
        this.streamService = streamService;
        this.streamValidator = streamValidator;
        this.userService = userService;
        this.contentService = contentService;
        this.subscriptionService = subscriptionService;
        this.notificationService = notificationService;
    }

    public Stream playContent(Long userId, Long contentId, 
                             String requestedQuality, String deviceType) {
        
        // Consultar usuario a través de interfaz
        Optional<User> userOpt = userService.findById(userId);
        if (userOpt.isEmpty()) {
            notificationService.notifyError(userId, "User not found");
            throw new IllegalArgumentException("User not found");
        }

        User user = userOpt.get();
        
        // Consultar contenido a través de interfaz
        Optional<Content> contentOpt = contentService.findById(contentId);
        if (contentOpt.isEmpty()) {
            notificationService.notifyError(userId, "Content not found");
            throw new IllegalArgumentException("Content not found");
        }

        // Validar a través de interfaz
        streamValidator.validateStreamRequest(userId, contentId, requestedQuality);
        
        // Iniciar stream a través de interfaz
        Stream stream = streamService.startStream(userId, contentId, 
                                                 requestedQuality, deviceType);
        
        // Notificar a través de interfaz
        notificationService.notifyStreamStart(userId, user.getUsername());
        
        return stream;
    }
}
```

## 2. Cambio de Implementación sin Modificar Controladores

### Escenario 1: Desarrollo con memoria

```java
public class DependencyInjector {
    private static final UserManageable userService = new UserRepository();
    // En memoria, perfecto para desarrollo
}
```

### Escenario 2: Producción con BD real

```java
public class DependencyInjector {
    private static final UserManageable userService = new DatabaseUserRepository();
    // Conecta a MySQL
}
```

**El PlayController funciona exactamente igual en ambos casos!**

### Implementación alternativa: DatabaseUserRepository

```java
// Nueva implementación sin tocar la interfaz
public class DatabaseUserRepository implements UserManageable {
    private Connection connection;
    
    public DatabaseUserRepository() {
        // Conectar a BD
        this.connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/streaming_platform", 
            "root", 
            "password"
        );
    }
    
    @Override
    public Optional<User> findById(Long id) {
        String query = "SELECT * FROM users WHERE id = ?";
        // Ejecutar query SQL...
        return Optional.of(user);
    }
    
    // Otros métodos de la interfaz...
}
```

## 3. Testing con Mocks

```java
// Test sin modificar código original
@Test
public void testPlayContentWithValidUser() {
    // Crear mocks
    UserManageable mockUserService = mock(UserManageable.class);
    StreamPlayable mockStreamService = mock(StreamPlayable.class);
    StreamValidator mockValidator = mock(StreamValidator.class);
    ContentManageable mockContentService = mock(ContentManageable.class);
    SubscriptionManageable mockSubService = mock(SubscriptionManageable.class);
    NotificationService mockNotification = mock(NotificationService.class);
    
    // Configurar comportamiento
    User testUser = new User(1L, "juan", "juan@test.com", "pass", "Premium");
    when(mockUserService.findById(1L)).thenReturn(Optional.of(testUser));
    
    Content testContent = new Content("Movie", "Desc", "Sci-Fi", "movie", 120L, "url");
    when(mockContentService.findById(1L)).thenReturn(Optional.of(testContent));
    
    Stream testStream = new Stream(1L, 1L, "4K", "TV");
    when(mockStreamService.startStream(1L, 1L, "4K", "TV")).thenReturn(testStream);
    
    // Crear controlador con mocks
    PlayController controller = new PlayController(
        mockStreamService,
        mockValidator,
        mockUserService,
        mockContentService,
        mockSubService,
        mockNotification
    );
    
    // Ejecutar test
    Stream result = controller.playContent(1L, 1L, "4K", "TV");
    
    // Verificar
    assertNotNull(result);
    assertEquals(1L, result.getId());
    verify(mockNotification).notifyStreamStart(1L, "Movie");
}
```

## 4. Múltiples Implementaciones

### Interfaz: NotificationService

```java
public interface NotificationService {
    void notifyStreamStart(Long userId, String contentTitle);
    void notifyStreamEnd(Long userId, String contentTitle);
    void notifyError(Long userId, String errorMessage);
}
```

### Implementación 1: Consola

```java
public class ConsoleNotificationService implements NotificationService {
    @Override
    public void notifyStreamStart(Long userId, String contentTitle) {
        System.out.println("[Notification] User " + userId + 
                          " started streaming: " + contentTitle);
    }
}
```

### Implementación 2: Email

```java
public class EmailNotificationService implements NotificationService {
    private EmailService emailService;
    
    @Override
    public void notifyStreamStart(Long userId, String contentTitle) {
        String message = "You started streaming: " + contentTitle;
        emailService.sendEmail(userId, "Stream Started", message);
    }
}
```

### Implementación 3: SMS

```java
public class SMSNotificationService implements NotificationService {
    private SMSService smsService;
    
    @Override
    public void notifyStreamStart(Long userId, String contentTitle) {
        String message = "Now streaming: " + contentTitle;
        smsService.sendSMS(userId, message);
    }
}
```

### Cambiar entre implementaciones

```java
// Desarrollo: Consola
NotificationService notifs = new ConsoleNotificationService();

// Producción: Email
NotificationService notifs = new EmailNotificationService();

// Con alertas: SMS
NotificationService notifs = new SMSNotificationService();

// PlayController funciona con cualquiera!
PlayController controller = new PlayController(..., notifs);
```

## 5. Patrón Strategy con DIP

```java
// Diferentes estrategias de validación
public interface ValidationStrategy {
    boolean validate(Stream stream, User user, Subscription subscription);
}

// Estrategia 1: Validación básica
public class BasicValidation implements ValidationStrategy {
    @Override
    public boolean validate(Stream stream, User user, Subscription subscription) {
        return user.getId() != null && stream.getContentId() != null;
    }
}

// Estrategia 2: Validación estricta
public class StrictValidation implements ValidationStrategy {
    @Override
    public boolean validate(Stream stream, User user, Subscription subscription) {
        return user.getId() != null 
            && stream.getContentId() != null 
            && subscription.getMaxDevices() > 0;
    }
}

// Usar en controller
public class PlayController {
    private final ValidationStrategy validationStrategy;
    
    public PlayController(ValidationStrategy validationStrategy) {
        this.validationStrategy = validationStrategy;
    }
    
    public void playContent(Stream stream, User user, Subscription subscription) {
        if (!validationStrategy.validate(stream, user, subscription)) {
            throw new IllegalStateException("Validation failed");
        }
        // Continuar...
    }
}

// Uso
ValidationStrategy strategy = new StrictValidation();  // O BasicValidation
PlayController controller = new PlayController(strategy);
```

## 6. Composición de Servicios

```java
// Combinar múltiples servicios
public class EnhancedStreamService implements StreamPlayable {
    private final StreamPlayable baseStreamService;
    private final StreamValidator validator;
    private final NotificationService notifications;
    
    public EnhancedStreamService(StreamPlayable baseService,
                                StreamValidator validator,
                                NotificationService notifications) {
        this.baseStreamService = baseService;
        this.validator = validator;
        this.notifications = notifications;
    }
    
    @Override
    public Stream startStream(Long userId, Long contentId, 
                             String quality, String deviceType) {
        // Validar
        validator.validateStreamRequest(userId, contentId, quality);
        
        // Ejecutar servicio base
        Stream stream = baseStreamService.startStream(userId, contentId, 
                                                     quality, deviceType);
        
        // Notificar
        notifications.notifyStreamStart(userId, contentId.toString());
        
        return stream;
    }
    
    // Otros métodos...
}
```

## 7. Ejemplo de Flujo Completo

```java
// 1. Obtener servicios
UserManageable userService = DependencyInjector.getUserService();
ContentManageable contentService = DependencyInjector.getContentService();

// 2. Crear controlador
UserController userController = new UserController(userService, notificationService);
ContentController contentController = new ContentController(contentService);

// 3. Registrar usuario
User user = userController.registerUser("juan", "juan@email.com", "pass123");
System.out.println("Usuario creado: " + user.getId());

// 4. Añadir contenido
Content movie = contentController.addContent(
    "Inception",
    "A mind-bending thriller",
    "Sci-Fi",
    "movie",
    148L,
    "https://stream.example.com/inception"
);
System.out.println("Contenido añadido: " + movie.getId());

// 5. Mejorar suscripción
userController.upgradeSubscription(user.getId(), "Premium");

// 6. Reproducir
PlayController playController = new PlayController(...);
Stream stream = playController.playContent(user.getId(), movie.getId(), "4K", "SmartTV");
System.out.println("Stream iniciado: " + stream.getId());

// 7. Actualizar progreso
playController.updateWatchProgress(stream.getId(), 50L);

// 8. Calificar
contentController.rateContent(movie.getId(), 5);

// 9. Detener
playController.stopPlayback(stream.getId());
```

## Beneficios Observados

✅ **Cambio fácil de implementaciones**: De memoria a BD sin tocar código
✅ **Testing simplificado**: Mocks inyectados directamente  
✅ **Código desacoplado**: Cambios localizados
✅ **Escalabilidad**: Nuevos servicios sin modificar existentes
✅ **Mantenibilidad**: Código limpio y predecible
