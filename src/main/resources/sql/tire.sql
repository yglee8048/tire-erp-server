DROP TABLE IF EXISTS `tire`;

CREATE TABLE `tire`
(
    `tire_id`                BIGINT(20)       NOT NULL AUTO_INCREMENT,
    `pattern_id`             BIGINT(20)       NOT NULL,
    `tire_code`              VARCHAR(255)     NOT NULL COLLATE 'utf8mb4_general_ci',
    `on_sale`                TINYINT(1)       NULL DEFAULT NULL,
    `width`                  INT(3) UNSIGNED  NOT NULL,
    `flatness_ratio`         INT(2) UNSIGNED  NOT NULL,
    `inch`                   INT(2) UNSIGNED  NOT NULL,
    `size`                   CHAR(11)         NOT NULL COLLATE 'utf8mb4_general_ci',
    `load_index`             INT(11) UNSIGNED NULL DEFAULT NULL,
    `speed_index`            VARCHAR(50)      NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `retail_price`           INT(11)          NULL DEFAULT NULL,
    `run_flat`               TINYINT(1)       NULL DEFAULT NULL,
    `sponge`                 TINYINT(1)       NULL DEFAULT NULL,
    `sealing`                TINYINT(1)       NULL DEFAULT NULL,
    `oe`                     VARCHAR(255)     NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `country_of_manufacture` VARCHAR(255)     NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `original_vehicle`       VARCHAR(255)     NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `note`                   VARCHAR(255)     NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `tire_group`             VARCHAR(255)     NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `pr`                     VARCHAR(255)     NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `lr`                     VARCHAR(255)     NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `tire_ro_id`             VARCHAR(255)     NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `created_at`             DATETIME         NULL DEFAULT NULL,
    `last_modified_at`       DATETIME         NULL DEFAULT NULL,
    `created_by`             VARCHAR(50)      NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `last_modified_by`       VARCHAR(50)      NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    PRIMARY KEY (`tire_id`) USING BTREE,
    UNIQUE INDEX `tire_unique_tire_code` (`tire_code`) USING BTREE,
    INDEX `tire_fk_pattern_id` (`pattern_id`) USING BTREE
)
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB
;
