DROP TABLE IF EXISTS `delivery`;

CREATE TABLE `delivery`
(
    `delivery_id`            BIGINT(20)  NOT NULL AUTO_INCREMENT,
    `recipient_name`         VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `city`                   VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `street_address`         VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `detail_address`         VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `zip_code`               INT(11)     NULL DEFAULT NULL,
    `recipient_phone_number` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `delivery_option`        VARCHAR(10) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `delivery_company`       VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `invoice_number`         VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `created_at`             DATETIME    NOT NULL,
    `last_modified_at`       DATETIME    NOT NULL,
    `created_by`             VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `last_modified_by`       VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    PRIMARY KEY (`delivery_id`) USING BTREE
)
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB
;
