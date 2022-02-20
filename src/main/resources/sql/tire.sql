DROP TABLE IF EXISTS `tire`;

CREATE TABLE `tire`
(
    `tire_id`                BIGINT(20)   NOT NULL AUTO_INCREMENT,
    `pattern_id`             BIGINT(20)   NOT NULL,
    `tire_code`              VARCHAR(32)  NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `width`                  INT(3)       NOT NULL,
    `flatness_ratio`         INT(2)       NOT NULL,
    `inch`                   INT(2)       NOT NULL,
    `size`                   VARCHAR(16)  NOT NULL COLLATE 'utf8mb4_general_ci',
    `oe`                     VARCHAR(64)  NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `load_index`             INT(5)       NULL DEFAULT NULL,
    `speed_index`            VARCHAR(8)   NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `run_flat`               TINYINT(1)   NULL DEFAULT NULL,
    `sponge`                 TINYINT(1)   NULL DEFAULT NULL,
    `sealing`                TINYINT(1)   NULL DEFAULT NULL,
    `factory_price`          BIGINT(20)   NULL DEFAULT NULL,
    `country_of_manufacture` VARCHAR(256) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `created_at`             DATETIME     NOT NULL,
    `last_modified_at`       DATETIME     NOT NULL,
    `created_by`             VARCHAR(50)  NOT NULL COLLATE 'utf8mb4_general_ci',
    `last_modified_by`       VARCHAR(50)  NOT NULL COLLATE 'utf8mb4_general_ci',
    PRIMARY KEY (`tire_id`) USING BTREE,
    UNIQUE INDEX `tire_code` (`tire_code`) USING BTREE,
    INDEX `pattern_id` (`pattern_id`) USING BTREE
)
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB
;
