DROP TABLE IF EXISTS `sale`;

CREATE TABLE `sale`
(
    `sale_id`               BIGINT(20)  NOT NULL AUTO_INCREMENT,
    `client_company_id`     BIGINT(20)  NOT NULL,
    `delivery_id`           BIGINT(20)  NULL DEFAULT NULL,
    `source`                VARCHAR(50) NOT NULL COLLATE 'utf8mb4_general_ci',
    `status`                VARCHAR(50) NOT NULL COLLATE 'utf8mb4_general_ci',
    `transaction_date`      DATE        NOT NULL,
    `release_date`          DATE        NULL DEFAULT NULL,
    `desired_delivery_date` DATE        NULL DEFAULT NULL,
    `created_at`            DATETIME    NULL DEFAULT NULL,
    `last_modified_at`      DATETIME    NULL DEFAULT NULL,
    `created_by`            VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `last_modified_by`      VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    PRIMARY KEY (`sale_id`) USING BTREE
)
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB
;
