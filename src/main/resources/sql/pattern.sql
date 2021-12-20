DROP TABLE IF EXISTS `pattern`;

CREATE TABLE `pattern`
(
    `pattern_id`       BIGINT(20)   NOT NULL AUTO_INCREMENT,
    `brand_id`         BIGINT(20)   NOT NULL,
    `name`             VARCHAR(255) NOT NULL COLLATE 'utf8mb4_general_ci',
    `pattern_code`     VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `car_type`         VARCHAR(50)  NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `rank`             VARCHAR(50)  NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `season`           VARCHAR(50)  NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `quietness`        TINYINT(1)   NULL DEFAULT NULL,
    `ride_quality`     TINYINT(1)   NULL DEFAULT NULL,
    `mileage`          TINYINT(1)   NULL DEFAULT NULL,
    `handling`         TINYINT(1)   NULL DEFAULT NULL,
    `breaking_power`   TINYINT(1)   NULL DEFAULT NULL,
    `sports`           TINYINT(1)   NULL DEFAULT NULL,
    `wet_surface`      TINYINT(1)   NULL DEFAULT NULL,
    `created_at`       DATETIME     NULL DEFAULT NULL,
    `last_modified_at` DATETIME     NULL DEFAULT NULL,
    `created_by`       VARCHAR(50)  NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `last_modified_by` VARCHAR(50)  NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    PRIMARY KEY (`pattern_id`) USING BTREE,
    UNIQUE INDEX `pattern_unique_name` (`brand_id`, `name`) USING BTREE,
    INDEX `pattern_fk_brand_id` (`brand_id`) USING BTREE
)
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB
;
