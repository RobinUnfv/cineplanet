-- ============================================================
-- Script de inicialización para la base de datos dbhistorial
-- ============================================================

CREATE DATABASE IF NOT EXISTS dbhistorial
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE dbhistorial;

-- ============================================================
-- Tabla: hist_sts_socio
-- ============================================================
CREATE TABLE IF NOT EXISTS hist_sts_socio (
                                              id            CHAR(36)     NOT NULL DEFAULT (UUID()),
    dni           VARCHAR(8)   NOT NULL,
    sts_anterior  VARCHAR(8)   NOT NULL,
    sts_actual    VARCHAR(8)   NOT NULL,
    fec_cambio    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX idx_dni (dni),
    INDEX idx_fec_cambio (fec_cambio)
    ) ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_unicode_ci;

-- Datos de ejemplo (opcional)
INSERT INTO hist_sts_socio (dni, sts_anterior, sts_actual)
VALUES ('12345678', 'ACTIVO', 'INACTIVO');

-- Verificación
SELECT '✅ Base de datos dbhistorial y tabla hist_sts_socio creadas correctamente' AS mensaje;
DESCRIBE hist_sts_socio;