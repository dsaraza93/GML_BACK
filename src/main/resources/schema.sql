-- schema.sql
DROP TABLE IF EXISTS clientes;

CREATE TABLE clientes (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          shared_key VARCHAR(255),
                          business_id VARCHAR(255),
                          email VARCHAR(255),
                          phone VARCHAR(255),
                          date_added DATE
);
