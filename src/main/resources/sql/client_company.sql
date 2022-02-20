DROP TABLE IF EXISTS `client_company`;

CREATE TABLE `client_company`
(
    `client_company_id`           BIGINT(20)   NOT NULL AUTO_INCREMENT,
    `name`                        VARCHAR(255) NOT NULL COLLATE 'utf8mb4_general_ci',
    `rank_id`                     BIGINT(20)   NOT NULL,
    `description`                 VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `business_number`             VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `business_name`               VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `business_type`               VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `city`                        VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `street_address`              VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `detail_address`              VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `zip_code`                    INT(11)      NULL DEFAULT NULL,
    `fax`                         VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `email`                       VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `representative`              VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `representative_phone_number` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `manager`                     VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `manager_phone_number`        VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `created_at`                  DATETIME     NOT NULL,
    `last_modified_at`            DATETIME     NOT NULL,
    `created_by`                  VARCHAR(50)  NOT NULL COLLATE 'utf8mb4_general_ci',
    `last_modified_by`            VARCHAR(50)  NOT NULL COLLATE 'utf8mb4_general_ci',
    PRIMARY KEY (`client_company_id`) USING BTREE,
    UNIQUE INDEX `name` (`name`) USING BTREE,
    INDEX `rank_id` (`rank_id`) USING BTREE
)
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB
;
