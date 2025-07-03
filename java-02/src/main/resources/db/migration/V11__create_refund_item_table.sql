CREATE TABLE refund_item
(
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    refund_id           BIGINT         NOT NULL,
    purchase_product_id BIGINT         NOT NULL,
    refund_quantity     INT            NOT NULL,
    refund_amount       DECIMAL(15, 2) NOT NULL,
    created_at          DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at          DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)