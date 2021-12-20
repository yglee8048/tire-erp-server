DROP TABLE IF EXISTS `purchase`;

CREATE TABLE `purchase`
(
    `purchase_id`      BIGINT(20)   NOT NULL AUTO_INCREMENT,
    `vendor_id`        BIGINT(20)   NOT NULL,
    `status`           VARCHAR(50)  NOT NULL COLLATE 'utf8mb4_general_ci',
    `transaction_date` DATE         NOT NULL,
    `description`      VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `created_at`       DATETIME     NOT NULL,
    `last_modified_at` DATETIME     NOT NULL,
    `created_by`       VARCHAR(50)  NOT NULL COLLATE 'utf8mb4_general_ci',
    `last_modified_by` VARCHAR(50)  NOT NULL COLLATE 'utf8mb4_general_ci',
    PRIMARY KEY (`purchase_id`) USING BTREE,
    INDEX `vendor_id` (`vendor_id`) USING BTREE
)
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB
;
