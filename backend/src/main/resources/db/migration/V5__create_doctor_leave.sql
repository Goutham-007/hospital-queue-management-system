CREATE TABLE doctor_leave (
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    doctor_id  BIGINT       NOT NULL,
    leave_date DATE         NOT NULL,
    reason     VARCHAR(255),
    PRIMARY KEY (id),
    UNIQUE KEY uk_leave_doctor_date (doctor_id, leave_date),
    CONSTRAINT fk_leave_doctor FOREIGN KEY (doctor_id) REFERENCES doctors (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;