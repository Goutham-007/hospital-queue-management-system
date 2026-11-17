CREATE TABLE bookings (
    id           BIGINT        NOT NULL AUTO_INCREMENT,
    patient_id   BIGINT        NOT NULL,
    doctor_id    BIGINT        NOT NULL,
    booking_date DATE          NOT NULL,
    slot_time    TIME          NOT NULL,
    status       ENUM('PENDING','CONFIRMED','CANCELLED','COMPLETED') NOT NULL DEFAULT 'PENDING',
    amount       DECIMAL(10,2) NOT NULL,
    created_at   TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    active_slot  TINYINT GENERATED ALWAYS AS
                 (CASE WHEN status IN ('PENDING','CONFIRMED') THEN 1 ELSE NULL END) STORED,
    PRIMARY KEY (id),
    UNIQUE KEY uk_booking_active_slot (doctor_id, booking_date, slot_time, active_slot),
    KEY idx_bookings_status_created (status, created_at),
    CONSTRAINT fk_bookings_patient FOREIGN KEY (patient_id) REFERENCES users (id),
    CONSTRAINT fk_bookings_doctor FOREIGN KEY (doctor_id) REFERENCES doctors (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;