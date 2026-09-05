-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS bdCustomer;
USE bdCustomer;

-- Crear la tabla Cliente
CREATE TABLE IF NOT EXISTS Cliente (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    nombre VARCHAR(50) NOT NULL,
    paterno VARCHAR(30) NOT NULL,
    materno VARCHAR(30) NOT NULL,
    correo VARCHAR(80) NOT NULL UNIQUE,
    telefono VARCHAR(9) NOT NULL,
    fechaIngreso DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    INDEX idx_correo (correo),
    INDEX idx_telefono (telefono)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insertar 20 clientes de ejemplo
INSERT INTO Cliente (id, nombre, paterno, materno, correo, telefono, fechaIngreso) VALUES
(UUID(), 'Juan', 'Perez', 'Garcia', 'juan.perez@email.com', '987654321', NOW()),
(UUID(), 'Maria', 'Lopez', 'Martinez', 'maria.lopez@email.com', '987654322', NOW()),
(UUID(), 'Carlos', 'Gonzalez', 'Rodriguez', 'carlos.gonzalez@email.com', '987654323', NOW()),
(UUID(), 'Ana', 'Fernandez', 'Sanchez', 'ana.fernandez@email.com', '987654324', NOW()),
(UUID(), 'Luis', 'Martínez', 'Diaz', 'luis.martinez@email.com', '987654325', NOW()),
(UUID(), 'Elena', 'Garcia', 'Perez', 'elena.garcia@email.com', '987654326', NOW()),
(UUID(), 'Miguel', 'Rodriguez', 'Lopez', 'miguel.rodriguez@email.com', '987654327', NOW()),
(UUID(), 'Carmen', 'Sanchez', 'Gonzalez', 'carmen.sanchez@email.com', '987654328', NOW()),
(UUID(), 'Javier', 'Diaz', 'Fernandez', 'javier.diaz@email.com', '987654329', NOW()),
(UUID(), 'Isabel', 'Perez', 'Martinez', 'isabel.perez@email.com', '987654330', NOW()),
(UUID(), 'Antonio', 'Lopez', 'Garcia', 'antonio.lopez@email.com', '987654331', NOW()),
(UUID(), 'Laura', 'Gonzalez', 'Rodriguez', 'laura.gonzalez@email.com', '987654332', NOW()),
(UUID(), 'Pablo', 'Rodriguez', 'Sanchez', 'pablo.rodriguez@email.com', '987654333', NOW()),
(UUID(), 'Sofía', 'Martinez', 'Diaz', 'sofia.martinez@email.com', '987654334', NOW()),
(UUID(), 'David', 'Fernandez', 'Perez', 'david.fernandez@email.com', '987654335', NOW()),
(UUID(), 'Patricia', 'Garcia', 'Lopez', 'patricia.garcia@email.com', '987654336', NOW()),
(UUID(), 'Jose', 'Sanchez', 'Gonzalez', 'jose.sanchez@email.com', '987654337', NOW()),
(UUID(), 'Marta', 'Diaz', 'Rodriguez', 'marta.diaz@email.com', '987654338', NOW()),
(UUID(), 'Francisco', 'Perez', 'Martinez', 'francisco.perez@email.com', '987654339', NOW()),,
(UUID(), 'Rosa', 'Lopez', 'Fernandez', 'rosa.lopez@email.com', '987654340', NOW());