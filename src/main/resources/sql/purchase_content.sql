DROP TABLE IF EXISTS `purchase_content`;

CREATE TABLE `purchase_content`
(
    `purchase_content_id` BIGINT(20)  NOT NULL AUTO_INCREMENT,
    `purchase_id`         BIGINT(20)  NOT NULL,
    `tire_dot_id`         BIGINT(20)  NOT NULL,
    `price`               BIGINT(20)  NOT NULL,
    `quantity`            INT(10)     NOT NULL,
    `warehouse_id`        BIGINT(20)  NULL DEFAULT NULL,
    `created_at`          DATETIME    NULL DEFAULT NULL,
    `last_modified_at`    DATETIME    NULL DEFAULT NULL,
    `created_by`          VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `last_modified_by`    VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    PRIMARY KEY (`purchase_content_id`) USING BTREE,
    INDEX `purchase_content_fk_purchase_id` (`purchase_id`) USING BTREE,
    INDEX `purchase_content_fk_tire_dot_id` (`tire_dot_id`) USING BTREE,
    INDEX `warehouse_id` (`warehouse_id`) USING BTREE
)
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB
;
