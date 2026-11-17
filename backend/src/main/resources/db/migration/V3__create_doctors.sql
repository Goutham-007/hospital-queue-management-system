CREATE TABLE doctors (
    id                 BIGINT        NOT NULL AUTO_INCREMENT,
    user_id            BIGINT        NOT NULL,
    specialization_id  BIGINT        NOT NULL,
    qualification      VARCHAR(150),
    experience_years   INT           NOT NULL DEFAULT 0,
    consultation_fee   DECIMAL(10,2) NOT NULL,
    is_active          BOOLEAN       NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id),
    UNIQUE KEY uk_doctors_user (user_id),
    CONSTRAINT fk_doctors_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_doctors_specialization FOREIGN KEY (specialization_id) REFERENCES specializations (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;