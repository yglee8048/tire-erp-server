DROP TABLE IF EXISTS `stock`;

CREATE TABLE `stock`
(
    `stock_id`         BIGINT(20)  NOT NULL AUTO_INCREMENT,
    `tire_dot_id`      BIGINT(20)  NOT NULL,
    `warehouse_id`     BIGINT(20)  NOT NULL,
    `nickname`         VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `quantity`         BIGINT(20)  NOT NULL,
    `is_lock`          TINYINT(1)  NOT NULL,
    `created_at`       DATETIME    NULL DEFAULT NULL,
    `last_modified_at` DATETIME    NULL DEFAULT NULL,
    `created_by`       VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `last_modified_by` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    PRIMARY KEY (`stock_id`) USING BTREE,
    UNIQUE INDEX `stock_unique_dot_warehouse_nickname` (`tire_dot_id`, `warehouse_id`, `nickname`) USING BTREE,
    INDEX `stock_fk_tire_dot_id` (`tire_dot_id`) USING BTREE,
    INDEX `stock_fk_warehouse_id` (`warehouse_id`) USING BTREE
)
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB
;
