DROP TABLE IF EXISTS `sale_content`;

CREATE TABLE `sale_content`
(
    `sale_content_id`  BIGINT(20)  NOT NULL AUTO_INCREMENT,
    `sale_id`          BIGINT(20)  NOT NULL,
    `tire_dot_id`      BIGINT(20)  NOT NULL,
    `price`            BIGINT(20)  NOT NULL,
    `quantity`         INT(11)     NOT NULL,
    `stock_id`         BIGINT(20)  NULL DEFAULT NULL,
    `created_at`       DATETIME    NULL DEFAULT NULL,
    `last_modified_at` DATETIME    NULL DEFAULT NULL,
    `created_by`       VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `last_modified_by` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    PRIMARY KEY (`sale_content_id`) USING BTREE
)
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB
;
