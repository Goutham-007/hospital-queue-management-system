CREATE TABLE doctor_availability (
    id                    BIGINT NOT NULL AUTO_INCREMENT,
    doctor_id             BIGINT NOT NULL,
    day_of_week           ENUM('MONDAY','TUESDAY','WEDNESDAY','THURSDAY','FRIDAY','SATURDAY','SUNDAY') NOT NULL,
    start_time            TIME   NOT NULL,
    end_time              TIME   NOT NULL,
    slot_duration_minutes INT    NOT NULL,
    max_patients_per_day  INT    NOT NULL,
    is_active             BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id),
    UNIQUE KEY uk_availability_range (doctor_id, day_of_week, start_time),
    CONSTRAINT fk_availability_doctor FOREIGN KEY (doctor_id) REFERENCES doctors (id),
    CONSTRAINT chk_availability_time CHECK (end_time > start_time),
    CONSTRAINT chk_availability_slot CHECK (slot_duration_minutes > 0),
    CONSTRAINT chk_availability_max CHECK (max_patients_per_day > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;