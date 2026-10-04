CREATE DATABASE IF NOT EXISTS visa_egipto;
USE visa_egipto;

CREATE TABLE IF NOT EXISTS solicitud (
    id_solicitud INT AUTO_INCREMENT PRIMARY KEY,
    nombre_solicitante VARCHAR(100) NOT NULL,
    numero_pasaporte VARCHAR(20) NOT NULL UNIQUE,
    tipo_visa VARCHAR(50) NOT NULL,
    fecha_solicitud DATE NOT NULL,
    estado VARCHAR(30) NOT NULL DEFAULT 'Pendiente'
);

INSERT INTO solicitud (nombre_solicitante, numero_pasaporte, tipo_visa, fecha_solicitud, estado)
VALUES ('Ejemplo Inicial', 'EG123456', 'Turismo', CURDATE(), 'Pendiente');