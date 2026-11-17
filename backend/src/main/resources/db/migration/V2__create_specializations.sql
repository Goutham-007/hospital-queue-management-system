CREATE TABLE specializations (
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    name        VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    is_active   BOOLEAN      NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id),
    UNIQUE KEY uk_specializations_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;