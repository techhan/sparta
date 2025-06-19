CREATE TABLE purchase
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id     BIGINT      not null,
    total_price BIGINT,
    status      varchar(20) not null,
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)