DROP TABLE IF EXISTS `pattern`;

CREATE TABLE `pattern`
(
    `pattern_id`       BIGINT(20)   NOT NULL AUTO_INCREMENT,
    `brand_id`         BIGINT(20)   NOT NULL,
    `name`             VARCHAR(128) NOT NULL COLLATE 'utf8mb4_general_ci',
    `english_name`     VARCHAR(256) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `description`      VARCHAR(512) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `season`           CHAR(10)     NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `quietness`        TINYINT(1)   NULL DEFAULT NULL,
    `ride_quality`     TINYINT(1)   NULL DEFAULT NULL,
    `mileage`          TINYINT(1)   NULL DEFAULT NULL,
    `handling`         TINYINT(1)   NULL DEFAULT NULL,
    `breaking_power`   TINYINT(1)   NULL DEFAULT NULL,
    `wet_surface`      TINYINT(1)   NULL DEFAULT NULL,
    `snow_performance` TINYINT(1)   NULL DEFAULT NULL,
    `created_at`       DATETIME     NOT NULL,
    `last_modified_at` DATETIME     NOT NULL,
    `created_by`       VARCHAR(50)  NOT NULL COLLATE 'utf8mb4_general_ci',
    `last_modified_by` VARCHAR(50)  NOT NULL COLLATE 'utf8mb4_general_ci',
    PRIMARY KEY (`pattern_id`) USING BTREE,
    UNIQUE INDEX `brand_id_name` (`brand_id`, `name`) USING BTREE,
    INDEX `brand_id` (`brand_id`) USING BTREE
)
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB
;
