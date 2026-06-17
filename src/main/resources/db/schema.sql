-- Plataforma de Streaming Multimedia
-- Script de Base de Datos MySQL
-- Estructura completa para la aplicación

DROP DATABASE IF EXISTS streaming_platform;
CREATE DATABASE IF NOT EXISTS streaming_platform CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE streaming_platform;

-- Tabla de Suscripciones
CREATE TABLE subscriptions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    plan VARCHAR(50) NOT NULL UNIQUE,
    price DECIMAL(10, 2) NOT NULL,
    max_devices INT NOT NULL DEFAULT 1,
    max_quality VARCHAR(20) NOT NULL DEFAULT 'SD',
    ad_supported BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_plan (plan)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de Usuarios
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    subscription VARCHAR(50) NOT NULL DEFAULT 'Free',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (subscription) REFERENCES subscriptions(plan) ON DELETE RESTRICT,
    INDEX idx_username (username),
    INDEX idx_email (email),
    INDEX idx_subscription (subscription)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de Contenido
CREATE TABLE content (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    genre VARCHAR(50) NOT NULL,
    type VARCHAR(50) NOT NULL,
    duration BIGINT NOT NULL COMMENT 'Duration in seconds',
    url VARCHAR(500) NOT NULL,
    rating INT DEFAULT 0 COMMENT 'Rating from 1-5',
    release_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_genre (genre),
    INDEX idx_type (type),
    INDEX idx_title (title),
    FULLTEXT INDEX ft_title_desc (title, description)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de Streams (Sesiones de reproducción)
CREATE TABLE streams (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    content_id BIGINT NOT NULL,
    start_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    end_time TIMESTAMP NULL,
    watched_duration BIGINT DEFAULT 0 COMMENT 'Watched duration in seconds',
    quality VARCHAR(20) NOT NULL DEFAULT 'HD',
    device_type VARCHAR(50) NOT NULL,
    location VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (content_id) REFERENCES content(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_content_id (content_id),
    INDEX idx_start_time (start_time),
    INDEX idx_user_content (user_id, content_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de Historial de Cambios de Suscripción
CREATE TABLE subscription_history (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    old_plan VARCHAR(50),
    new_plan VARCHAR(50) NOT NULL,
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_changed_at (changed_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de Rating de Contenido por Usuario
CREATE TABLE user_ratings (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    content_id BIGINT NOT NULL,
    rating INT NOT NULL COMMENT 'Rating from 1-5',
    rated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY unique_user_content_rating (user_id, content_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (content_id) REFERENCES content(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_content_id (content_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de Notificaciones
CREATE TABLE notifications (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    type VARCHAR(50) NOT NULL,
    message TEXT NOT NULL,
    read_status BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_read_status (read_status),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de Dispositivos del Usuario
CREATE TABLE user_devices (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    device_type VARCHAR(50) NOT NULL,
    device_id VARCHAR(150) NOT NULL UNIQUE,
    active BOOLEAN DEFAULT TRUE,
    last_used TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_active (active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla de Estadísticas
CREATE TABLE streaming_stats (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    content_id BIGINT,
    total_streams INT DEFAULT 0,
    total_watched_seconds BIGINT DEFAULT 0,
    completion_rate DECIMAL(5, 2) DEFAULT 0.00 COMMENT 'Percentage 0-100',
    last_watched TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY unique_user_content_stats (user_id, content_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (content_id) REFERENCES content(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_completion_rate (completion_rate)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- INSERCIÓN DE DATOS INICIALES

-- Planes de Suscripción
INSERT INTO subscriptions (plan, price, max_devices, max_quality, ad_supported) VALUES
('Free', 0.00, 1, 'SD', TRUE),
('Basic', 9.99, 2, 'HD', FALSE),
('Standard', 15.99, 4, 'FHD', FALSE),
('Premium', 19.99, 6, '4K', FALSE);

-- Usuarios de ejemplo
INSERT INTO users (username, email, password, subscription) VALUES
('juan_perez', 'juan@example.com', 'hashed_pass123', 'Free'),
('maria_garcia', 'maria@example.com', 'hashed_pass456', 'Basic'),
('carlos_lopez', 'carlos@example.com', 'hashed_pass789', 'Premium'),
('ana_martinez', 'ana@example.com', 'hashed_passabc', 'Standard');

-- Contenido de ejemplo
INSERT INTO content (title, description, genre, type, duration, url, rating) VALUES
('Inception', 'A skilled thief steals corporate secrets through dream technology', 'Sci-Fi', 'movie', 148, 'https://stream.example.com/inception', 5),
('Breaking Bad', 'A chemistry teacher turns to cooking methamphetamine', 'Drama', 'series', 50, 'https://stream.example.com/breakingbad', 5),
('The Matrix', 'A computer hacker discovers reality is a simulation', 'Sci-Fi', 'movie', 136, 'https://stream.example.com/thematrix', 5),
('Stranger Things', 'A group of friends battle supernatural forces in the 1980s', 'Mystery', 'series', 42, 'https://stream.example.com/strangerthings', 4),
('Interstellar', 'Astronauts travel through a wormhole to save humanity', 'Sci-Fi', 'movie', 169, 'https://stream.example.com/interstellar', 5),
('The Crown', 'The biography of Queen Elizabeth II through decades', 'Drama', 'series', 60, 'https://stream.example.com/thecrown', 4),
('Oppenheimer', 'The story of the father of the atomic bomb', 'Drama', 'movie', 180, 'https://stream.example.com/oppenheimer', 5),
('Wednesday', 'A dark comedy about a teenage detective solving murders', 'Comedy', 'series', 50, 'https://stream.example.com/wednesday', 4);

-- Streams de ejemplo
INSERT INTO streams (user_id, content_id, watched_duration, quality, device_type) VALUES
(1, 1, 45, '4K', 'Smart TV'),
(2, 2, 120, 'HD', 'Laptop'),
(3, 3, 136, '4K', 'Smart TV'),
(4, 4, 42, 'FHD', 'Tablet'),
(1, 5, 0, 'HD', 'Mobile');

-- Ratings de ejemplo
INSERT INTO user_ratings (user_id, content_id, rating) VALUES
(1, 1, 5),
(2, 2, 5),
(3, 3, 5),
(4, 4, 4),
(1, 5, 5);

-- Dispositivos de ejemplo
INSERT INTO user_devices (user_id, device_type, device_id, active) VALUES
(1, 'Smart TV', 'samsung-tv-001', TRUE),
(1, 'Mobile', 'iphone-12-001', TRUE),
(2, 'Laptop', 'macbook-pro-001', TRUE),
(3, 'Smart TV', 'lg-tv-001', TRUE),
(3, 'Tablet', 'ipad-pro-001', TRUE),
(4, 'Mobile', 'pixel-6-001', TRUE);

-- STORED PROCEDURES

DELIMITER //

-- Obtener estadísticas de visualización de un usuario
CREATE PROCEDURE sp_get_user_stats(IN p_user_id BIGINT)
BEGIN
    SELECT 
        u.username,
        COUNT(DISTINCT s.id) as total_streams,
        SUM(s.watched_duration) as total_watched_seconds,
        AVG(ur.rating) as avg_rating_given,
        COUNT(DISTINCT s.content_id) as unique_content_watched
    FROM users u
    LEFT JOIN streams s ON u.id = s.user_id
    LEFT JOIN user_ratings ur ON u.id = ur.user_id
    WHERE u.id = p_user_id
    GROUP BY u.id, u.username;
END //

-- Obtener contenido popular (con más streams)
CREATE PROCEDURE sp_get_popular_content(IN p_limit INT DEFAULT 10)
BEGIN
    SELECT 
        c.id,
        c.title,
        c.genre,
        c.type,
        COUNT(s.id) as stream_count,
        AVG(ur.rating) as avg_rating,
        SUM(s.watched_duration) as total_watched_seconds
    FROM content c
    LEFT JOIN streams s ON c.id = s.content_id
    LEFT JOIN user_ratings ur ON c.id = ur.content_id
    GROUP BY c.id, c.title, c.genre, c.type
    ORDER BY stream_count DESC
    LIMIT p_limit;
END //

-- Actualizar estado de reproducción
CREATE PROCEDURE sp_update_stream_progress(
    IN p_stream_id BIGINT,
    IN p_watched_duration BIGINT,
    IN p_quality VARCHAR(20)
)
BEGIN
    UPDATE streams
    SET 
        watched_duration = p_watched_duration,
        quality = p_quality,
        updated_at = CURRENT_TIMESTAMP
    WHERE id = p_stream_id;
END //

-- Obtener historial de suscripciones de un usuario
CREATE PROCEDURE sp_get_subscription_history(IN p_user_id BIGINT)
BEGIN
    SELECT 
        u.username,
        sh.old_plan,
        sh.new_plan,
        sh.changed_at
    FROM subscription_history sh
    JOIN users u ON sh.user_id = u.id
    WHERE sh.user_id = p_user_id
    ORDER BY sh.changed_at DESC;
END //

DELIMITER ;

-- VISTAS

-- Vista: Información completa de reproducciones activas
CREATE VIEW vw_active_streams AS
SELECT 
    s.id as stream_id,
    u.username,
    c.title as content_title,
    c.genre,
    s.quality,
    s.device_type,
    s.start_time,
    TIMEDIFF(NOW(), s.start_time) as duration_so_far,
    s.watched_duration
FROM streams s
JOIN users u ON s.user_id = u.id
JOIN content c ON s.content_id = c.id
WHERE s.end_time IS NULL;

-- Vista: Estadísticas de contenido
CREATE VIEW vw_content_statistics AS
SELECT 
    c.id,
    c.title,
    c.genre,
    c.type,
    c.rating,
    COUNT(DISTINCT s.user_id) as unique_viewers,
    COUNT(s.id) as total_streams,
    AVG(s.watched_duration / c.duration * 100) as avg_completion_percentage,
    AVG(ur.rating) as avg_user_rating
FROM content c
LEFT JOIN streams s ON c.id = s.content_id
LEFT JOIN user_ratings ur ON c.id = ur.content_id
GROUP BY c.id, c.title, c.genre, c.type, c.rating;

-- Vista: Resumen de usuario
CREATE VIEW vw_user_summary AS
SELECT 
    u.id,
    u.username,
    u.email,
    u.subscription,
    COUNT(DISTINCT s.id) as total_streams,
    COUNT(DISTINCT s.content_id) as content_watched,
    COUNT(DISTINCT d.device_type) as connected_devices,
    MAX(s.start_time) as last_stream_time
FROM users u
LEFT JOIN streams s ON u.id = s.user_id
LEFT JOIN user_devices d ON u.id = d.user_id AND d.active = TRUE
GROUP BY u.id, u.username, u.email, u.subscription;

-- ÍNDICES ADICIONALES PARA OPTIMIZACIÓN

CREATE INDEX idx_streams_end_time ON streams(end_time);
CREATE INDEX idx_streams_quality ON streams(quality);
CREATE INDEX idx_content_genre_type ON content(genre, type);
CREATE INDEX idx_users_subscription ON users(subscription, id);

-- TRIGGERS

DELIMITER //

-- Trigger: Registrar cambios de suscripción
CREATE TRIGGER trg_subscription_change
AFTER UPDATE ON users
FOR EACH ROW
BEGIN
    IF OLD.subscription != NEW.subscription THEN
        INSERT INTO subscription_history (user_id, old_plan, new_plan)
        VALUES (NEW.id, OLD.subscription, NEW.subscription);
    END IF;
END //

-- Trigger: Actualizar estadísticas cuando termina un stream
CREATE TRIGGER trg_update_stats_on_stream_end
AFTER UPDATE ON streams
FOR EACH ROW
BEGIN
    IF OLD.end_time IS NULL AND NEW.end_time IS NOT NULL THEN
        INSERT INTO streaming_stats (user_id, content_id, total_streams, total_watched_seconds, completion_rate, last_watched)
        SELECT 
            NEW.user_id,
            NEW.content_id,
            1,
            NEW.watched_duration,
            (NEW.watched_duration / c.duration * 100),
            NOW()
        FROM content c
        WHERE c.id = NEW.content_id
        ON DUPLICATE KEY UPDATE
            total_streams = total_streams + 1,
            total_watched_seconds = total_watched_seconds + NEW.watched_duration,
            completion_rate = (total_watched_seconds + NEW.watched_duration) / 
                            (SELECT duration FROM content WHERE id = NEW.content_id) * 100,
            last_watched = NOW(),
            updated_at = CURRENT_TIMESTAMP;
    END IF;
END //

DELIMITER ;
