CREATE TABLE purchase_product
(
    id          BIGINT AUTO_INCREMENT primary key,
    purchase_id BIGINT not null,
    product_id  BIGINT not null,
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP
)