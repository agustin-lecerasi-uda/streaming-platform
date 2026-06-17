flowchart TD
    Start([Usuario inicia reproducción]) --> CheckUser{¿Usuario existe?}
    
    CheckUser -->|No| ErrorUser["❌ Error: Usuario no encontrado"]
    ErrorUser --> NotifyError1["🔔 Notificación de Error"]
    NotifyError1 --> End1([Fin - Reproducción cancelada])
    
    CheckUser -->|Sí| CheckContent{¿Contenido existe?}
    
    CheckContent -->|No| ErrorContent["❌ Error: Contenido no encontrado"]
    ErrorContent --> NotifyError2["🔔 Notificación de Error"]
    NotifyError2 --> End2([Fin - Reproducción cancelada])
    
    CheckContent -->|Sí| GetPlan["📋 Obtener plan de suscripción"]
    GetPlan --> ValidateQuality{¿Calidad disponible<br/>para el plan?}
    
    ValidateQuality -->|No| Downgrade["⬇️ Reducir calidad al máximo<br/>permitido por plan"]
    ValidateQuality -->|Sí| ValidateLimit{¿Límite de dispositivos<br/>no excedido?}
    
    Downgrade --> NotifyDowngrade["🔔 Notificación: Calidad reducida"]
    NotifyDowngrade --> ValidateLimit
    
    ValidateLimit -->|Excedido| ErrorLimit["❌ Error: Límite de dispositivos excedido"]
    ErrorLimit --> NotifyError3["🔔 Notificación de Error"]
    NotifyError3 --> End3([Fin - Reproducción cancelada])
    
    ValidateLimit -->|OK| ValidateRequest["✓ Validar solicitud de reproducción"]
    ValidateRequest --> StartStream["▶️ Iniciar Stream"]
    StartStream --> CreateRecord["💾 Crear registro en BD"]
    CreateRecord --> NotifyStart["🔔 Notificación: Reproducción iniciada"]
    NotifyStart --> Playing["🎬 REPRODUCIENDO"]
    
    Playing --> UserAction{¿Acción del usuario?}
    
    UserAction -->|Pausa| Pause["⏸️ Pausar"]
    Pause --> UserAction
    
    UserAction -->|Reanudar| Resume["▶️ Reanudar"]
    Resume --> UserAction
    
    UserAction -->|Cambiar calidad| ChangeQuality{¿Nueva calidad<br/>válida?}
    ChangeQuality -->|Sí| UpdateQuality["🎨 Actualizar calidad"]
    UpdateQuality --> UserAction
    ChangeQuality -->|No| NotifyQualityError["🔔 Error: Calidad no válida"]
    NotifyQualityError --> UserAction
    
    UserAction -->|Actualizar progreso| UpdateProgress["📊 Actualizar progreso"]
    UpdateProgress --> UserAction
    
    UserAction -->|Detener| Stop["⏹️ Detener reproducción"]
    Stop --> FinalizeStream["💾 Finalizar registro en BD"]
    FinalizeStream --> UpdateStats["📈 Actualizar estadísticas"]
    UpdateStats --> NotifyStop["🔔 Notificación: Reproducción finalizada"]
    NotifyStop --> AskRating{¿Calificar contenido?}
    
    AskRating -->|Sí| Rating["⭐ Registrar calificación"]
    Rating --> End4([Fin - Sesión completada])
    
    AskRating -->|No| End4
    
    style Start fill:#4CAF50,color:#fff
    style End1 fill:#f44336,color:#fff
    style End2 fill:#f44336,color:#fff
    style End3 fill:#f44336,color:#fff
    style End4 fill:#4CAF50,color:#fff
    style Playing fill:#2196F3,color:#fff
    style ErrorUser fill:#f44336,color:#fff
    style ErrorContent fill:#f44336,color:#fff
    style ErrorLimit fill:#f44336,color:#fff
    style StartStream fill:#4CAF50,color:#fff
    style Stop fill:#FF9800,color:#fff
    style Pause fill:#FF9800,color:#fff
    style Resume fill:#4CAF50,color:#fff
