CREATE TABLE payments (
    id                  BIGINT        NOT NULL AUTO_INCREMENT,
    booking_id          BIGINT        NOT NULL,
    razorpay_order_id   VARCHAR(64)   NOT NULL,
    razorpay_payment_id VARCHAR(64),
    amount              DECIMAL(10,2) NOT NULL,
    currency            CHAR(3)       NOT NULL DEFAULT 'INR',
    method              ENUM('UPI','CARD','NETBANKING'),
    status              ENUM('CREATED','SUCCESS','FAILED') NOT NULL DEFAULT 'CREATED',
    created_at          TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_payments_order (razorpay_order_id),
    UNIQUE KEY uk_payments_payment (razorpay_payment_id),
    CONSTRAINT fk_payments_booking FOREIGN KEY (booking_id) REFERENCES bookings (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;