CREATE TABLE refund
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    purchase_id BIGINT         NOT NULL,
    user_id     BIGINT         NOT NULL,
    status      VARCHAR(20)    NOT NULL,
    reason      VARCHAR(255)   NOT NULL,
    total_price DECIMAL(15, 2) NOT NULL,
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP

)