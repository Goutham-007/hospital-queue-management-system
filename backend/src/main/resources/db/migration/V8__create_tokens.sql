CREATE TABLE tokens (
    id           BIGINT    NOT NULL AUTO_INCREMENT,
    booking_id   BIGINT    NOT NULL,
    doctor_id    BIGINT    NOT NULL,
    token_date   DATE      NOT NULL,
    token_number INT       NOT NULL,
    status       ENUM('WAITING','IN_CONSULTATION','DONE','SKIPPED') NOT NULL DEFAULT 'WAITING',
    created_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    completed_at TIMESTAMP NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_tokens_booking (booking_id),
    UNIQUE KEY uk_tokens_doctor_date_number (doctor_id, token_date, token_number),
    CONSTRAINT fk_tokens_booking FOREIGN KEY (booking_id) REFERENCES bookings (id),
    CONSTRAINT fk_tokens_doctor FOREIGN KEY (doctor_id) REFERENCES doctors (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;