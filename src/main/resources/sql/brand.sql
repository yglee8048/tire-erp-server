DROP TABLE IF EXISTS `brand`;

CREATE TABLE `brand`
(
    `brand_id`         BIGINT(20)   NOT NULL AUTO_INCREMENT,
    `name`             VARCHAR(255) NOT NULL COLLATE 'utf8mb4_general_ci',
    `description`      VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `created_at`       DATETIME     NULL DEFAULT NULL,
    `last_modified_at` DATETIME     NULL DEFAULT NULL,
    `created_by`       VARCHAR(50)  NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `last_modified_by` VARCHAR(50)  NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    PRIMARY KEY (`brand_id`) USING BTREE,
    UNIQUE INDEX `brand_unique_name` (`name`) USING BTREE
)
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB
;
