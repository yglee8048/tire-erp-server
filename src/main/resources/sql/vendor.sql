DROP TABLE IF EXISTS `vendor`;

CREATE TABLE `vendor`
(
    `vendor_id`                   BIGINT(20)   NOT NULL AUTO_INCREMENT,
    `name`                        VARCHAR(255) NOT NULL COLLATE 'utf8mb4_general_ci',
    `description`                 VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `business_number`             VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `business_name`               VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `business_type`               VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `city`                        VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `street_address`              VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `detail_address`              VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `zip_code`                    VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `fax`                         VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `email`                       VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `representative`              VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `representative_phone_number` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `manager`                     VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `manager_phone_number`        VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `created_at`                  DATETIME     NULL DEFAULT NULL,
    `last_modified_at`            DATETIME     NULL DEFAULT NULL,
    `created_by`                  VARCHAR(50)  NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `last_modified_by`            VARCHAR(50)  NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    PRIMARY KEY (`vendor_id`) USING BTREE,
    UNIQUE INDEX `vendor_unique_name` (`name`) USING BTREE
)
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB
;
