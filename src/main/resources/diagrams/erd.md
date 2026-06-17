erDiagram
    USERS ||--o{ SUBSCRIPTIONS : has
    USERS ||--o{ STREAMS : initiates
    USERS ||--o{ USER_RATINGS : gives
    USERS ||--o{ USER_DEVICES : owns
    USERS ||--o{ NOTIFICATIONS : receives
    USERS ||--o{ SUBSCRIPTION_HISTORY : "changes to"
    
    CONTENT ||--o{ STREAMS : "is played in"
    CONTENT ||--o{ USER_RATINGS : "is rated by"
    CONTENT ||--o{ STREAMING_STATS : "has stats"
    
    STREAMS ||--|| CONTENT : references
    STREAMS ||--|| USERS : "belongs to"
    
    USER_RATINGS ||--|| USERS : rates
    USER_RATINGS ||--|| CONTENT : "rates"
    
    STREAMING_STATS ||--|| USERS : "tracks"
    STREAMING_STATS ||--|| CONTENT : "tracks"
    
    SUBSCRIPTION_HISTORY ||--|| USERS : "documents"
    SUBSCRIPTION_HISTORY ||--|| SUBSCRIPTIONS : "from-to"
    
    USER_DEVICES ||--|| USERS : "belongs to"
    NOTIFICATIONS ||--|| USERS : "targets"

    USERS {
        bigint id PK
        string username UK
        string email UK
        string password
        string subscription FK
        timestamp created_at
    }

    SUBSCRIPTIONS {
        bigint id PK
        string plan UK
        decimal price
        int max_devices
        string max_quality
        boolean ad_supported
    }

    CONTENT {
        bigint id PK
        string title
        text description
        string genre
        string type
        bigint duration
        string url
        int rating
        timestamp release_date
    }

    STREAMS {
        bigint id PK
        bigint user_id FK
        bigint content_id FK
        timestamp start_time
        timestamp end_time
        bigint watched_duration
        string quality
        string device_type
        string location
    }

    USER_RATINGS {
        bigint id PK
        bigint user_id FK
        bigint content_id FK
        int rating
        timestamp rated_at
    }

    USER_DEVICES {
        bigint id PK
        bigint user_id FK
        string device_type
        string device_id UK
        boolean active
        timestamp last_used
    }

    STREAMING_STATS {
        bigint id PK
        bigint user_id FK
        bigint content_id FK
        int total_streams
        bigint total_watched_seconds
        decimal completion_rate
        timestamp last_watched
    }

    SUBSCRIPTION_HISTORY {
        bigint id PK
        bigint user_id FK
        string old_plan
        string new_plan
        timestamp changed_at
    }

    NOTIFICATIONS {
        bigint id PK
        bigint user_id FK
        string type
        text message
        boolean read_status
        timestamp created_at
    }
