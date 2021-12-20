DROP TABLE IF EXISTS `rank_dot_price`;

CREATE TABLE `rank_dot_price`
(
    `rank_dot_price_id` BIGINT(20)  NOT NULL AUTO_INCREMENT,
    `rank_id`           BIGINT(20)  NOT NULL,
    `tire_dot_id`       BIGINT(20)  NOT NULL,
    `price`             INT(11)     NOT NULL,
    `created_at`        DATETIME    NOT NULL,
    `last_modified_at`  DATETIME    NOT NULL,
    `created_by`        VARCHAR(50) NOT NULL COLLATE 'utf8mb4_general_ci',
    `last_modified_by`  VARCHAR(50) NOT NULL COLLATE 'utf8mb4_general_ci',
    PRIMARY KEY (`rank_dot_price_id`) USING BTREE,
    UNIQUE INDEX `rank_id_tire_dot_id` (`rank_id`, `tire_dot_id`) USING BTREE,
    INDEX `rank_id` (`rank_id`) USING BTREE,
    INDEX `tire_dot_id` (`tire_dot_id`) USING BTREE
)
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB
;
