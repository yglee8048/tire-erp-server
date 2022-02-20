DROP TABLE IF EXISTS `purchase_content`;

CREATE TABLE `purchase_content`
(
    `purchase_content_id` BIGINT(20)  NOT NULL AUTO_INCREMENT,
    `purchase_id`         BIGINT(20)  NOT NULL,
    `tire_dot_id`         BIGINT(20)  NOT NULL,
    `stock_id`            BIGINT(20)  NOT NULL,
    `price`               BIGINT(20)  NOT NULL,
    `quantity`            INT(11)     NOT NULL,
    `created_at`          DATETIME    NOT NULL,
    `last_modified_at`    DATETIME    NOT NULL,
    `created_by`          VARCHAR(50) NOT NULL COLLATE 'utf8mb4_general_ci',
    `last_modified_by`    VARCHAR(50) NOT NULL COLLATE 'utf8mb4_general_ci',
    PRIMARY KEY (`purchase_content_id`) USING BTREE,
    INDEX `tire_dot_id` (`tire_dot_id`) USING BTREE,
    INDEX `purchase_id` (`purchase_id`) USING BTREE,
    INDEX `stock_id` (`stock_id`) USING BTREE
)
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB
;
