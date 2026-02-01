-- Fitness Camp Registration System - MySQL Schema
-- Run this script to create all necessary tables

-- Create database (run separately if needed)
-- CREATE DATABASE IF NOT EXISTS fitness_camp;
-- USE fitness_camp;

-- Drop tables if exist (in correct order due to foreign keys)
DROP TABLE IF EXISTS participants;
DROP TABLE IF EXISTS camps;
DROP TABLE IF EXISTS users;

-- Users table for authentication
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Camps table
CREATE TABLE camps (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    location VARCHAR(200),
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    capacity INT NOT NULL DEFAULT 20,
    price DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_start_date (start_date),
    INDEX idx_active (active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Participants table
CREATE TABLE participants (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    camp_id BIGINT NOT NULL,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_participant_camp FOREIGN KEY (camp_id) REFERENCES camps(id) ON DELETE CASCADE,
    INDEX idx_email (email),
    INDEX idx_status (status),
    INDEX idx_camp_id (camp_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert default admin user (password: admin123)
-- BCrypt hash for 'admin123'
INSERT INTO users (username, password, role, enabled) VALUES 
('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MqrqBuBjZJ9YqCkeJepxAJhXUriqPLO', 'ADMIN', TRUE);

-- Insert sample camps
INSERT INTO camps (name, description, location, start_date, end_date, capacity, price, active) VALUES 
('Oboz Wytrzymalosciowy', 'Intensywny program treningowy skupiony na budowaniu wytrzymalosci i sily. Idealny dla osob chcacych przekroczyc swoje granice.', 'Centrum Sportowe Warszawa', DATE_ADD(CURDATE(), INTERVAL 30 DAY), DATE_ADD(CURDATE(), INTERVAL 37 DAY), 25, 899.00, TRUE),
('Oboz Jogi i Wellness', 'Znajdz wewnetrzny spokoj dzieki naszemu kompleksowemu programowi jogi i wellness. Obejmuje medytacje, cwiczenia oddechowe i holistyczne praktyki zdrowotne.', 'Osrodek Gorski Zakopane', DATE_ADD(CURDATE(), INTERVAL 45 DAY), DATE_ADD(CURDATE(), INTERVAL 52 DAY), 20, 749.00, TRUE),
('Oboz HIIT Extreme', 'Treningi interwalowe o wysokiej intensywnosci zaprojektowane tak, aby maksymalnie spalac kalorie i budowac miesnei w najkrotszym mozliwym czasie.', 'Fitness Arena Krakow', DATE_ADD(CURDATE(), INTERVAL 60 DAY), DATE_ADD(CURDATE(), INTERVAL 65 DAY), 30, 599.00, TRUE),
('Oboz Outdoor Adventure', 'Polacz fitness z przygoda! Wspinaczka, piesze wedrowki i cwiczenia na swiezym powietrzu w pieknych okolicznosciach przyrody.', 'Park Narodowy Bieszczady', DATE_ADD(CURDATE(), INTERVAL 90 DAY), DATE_ADD(CURDATE(), INTERVAL 97 DAY), 15, 1299.00, TRUE);

-- Insert sample participants
INSERT INTO participants (first_name, last_name, email, phone, status, camp_id) VALUES 
('Jan', 'Kowalski', 'jan.kowalski@email.pl', '501-234-567', 'CONFIRMED', 1),
('Anna', 'Nowak', 'anna.nowak@email.pl', '502-345-678', 'CONFIRMED', 1),
('Piotr', 'Wisniewski', 'piotr.wisniewski@email.pl', '503-456-789', 'PENDING', 2),
('Maria', 'Wojcik', 'maria.wojcik@email.pl', '504-567-890', 'CONFIRMED', 3);

-- Useful queries for verification
-- SELECT * FROM users;
-- SELECT * FROM camps;
-- SELECT * FROM participants;
-- SELECT c.name, COUNT(p.id) as participant_count FROM camps c LEFT JOIN participants p ON c.id = p.camp_id GROUP BY c.id;
