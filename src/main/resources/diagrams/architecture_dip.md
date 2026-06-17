graph TB
    subgraph Controllers["🎮 CONTROLLERS LAYER<br/>(High-level)"]
        UserCtrl["UserController"]
        ContentCtrl["ContentController"]
        PlayCtrl["PlayController"]
        SubCtrl["SubscriptionController"]
    end

    subgraph Interfaces["🔌 INTERFACES LAYER<br/>(Abstractions - DIP)"]
        UserMgmt["UserManageable"]
        ContentMgmt["ContentManageable"]
        StreamPlay["StreamPlayable"]
        StreamVal["StreamValidator"]
        NotifSvc["NotificationService"]
        SubMgmt["SubscriptionManageable"]
    end

    subgraph Repositories["📦 REPOSITORIES LAYER<br/>(Low-level implementations)"]
        UserRepo["UserRepository"]
        ContentRepo["ContentRepository"]
        StreamRepo["StreamRepository"]
        StreamValImpl["StreamValidatorImpl"]
        NotifImpl["ConsoleNotificationService"]
        SubRepo["SubscriptionRepository"]
    end

    subgraph DI["⚙️ DEPENDENCY INJECTION<br/>(IoC Container)"]
        Injector["DependencyInjector"]
    end

    subgraph Models["📊 MODELS<br/>(Domain Objects)"]
        User["User"]
        Content["Content"]
        Stream["Stream"]
        Subscription["Subscription"]
    end

    UserCtrl -->|depends on| UserMgmt
    ContentCtrl -->|depends on| ContentMgmt
    PlayCtrl -->|depends on| StreamPlay
    PlayCtrl -->|depends on| StreamVal
    PlayCtrl -->|depends on| UserMgmt
    PlayCtrl -->|depends on| ContentMgmt
    PlayCtrl -->|depends on| SubMgmt
    PlayCtrl -->|depends on| NotifSvc
    SubCtrl -->|depends on| SubMgmt

    UserMgmt -->|implemented by| UserRepo
    ContentMgmt -->|implemented by| ContentRepo
    StreamPlay -->|implemented by| StreamRepo
    StreamVal -->|implemented by| StreamValImpl
    NotifSvc -->|implemented by| NotifImpl
    SubMgmt -->|implemented by| SubRepo

    Injector -->|provides| UserMgmt
    Injector -->|provides| ContentMgmt
    Injector -->|provides| StreamPlay
    Injector -->|provides| StreamVal
    Injector -->|provides| NotifSvc
    Injector -->|provides| SubMgmt

    UserRepo -->|works with| User
    ContentRepo -->|works with| Content
    StreamRepo -->|works with| Stream
    SubRepo -->|works with| Subscription

    Controllers -->|instantiated by| Injector

    classDef controller fill:#FF6B6B,stroke:#C92A2A,stroke-width:2px,color:#fff
    classDef interface fill:#4ECDC4,stroke:#0A8080,stroke-width:2px,color:#fff
    classDef repository fill:#95E1D3,stroke:#38a169,stroke-width:2px,color:#000
    classDef model fill:#FFE66D,stroke:#D4A017,stroke-width:2px,color:#000
    classDef injector fill:#A8E6CF,stroke:#56C596,stroke-width:2px,color:#000

    class UserCtrl,ContentCtrl,PlayCtrl,SubCtrl controller
    class UserMgmt,ContentMgmt,StreamPlay,StreamVal,NotifSvc,SubMgmt interface
    class UserRepo,ContentRepo,StreamRepo,StreamValImpl,NotifImpl,SubRepo repository
    class User,Content,Stream,Subscription model
    class Injector injector
