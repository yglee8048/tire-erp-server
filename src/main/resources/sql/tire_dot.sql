DROP TABLE IF EXISTS `tire_dot`;

CREATE TABLE `tire_dot`
(
    `tire_dot_id`      BIGINT(20)   NOT NULL AUTO_INCREMENT,
    `tire_id`          BIGINT(20)   NOT NULL,
    `dot`              VARCHAR(255) NOT NULL COLLATE 'utf8mb4_general_ci',
    `created_at`       DATETIME     NOT NULL,
    `last_modified_at` DATETIME     NOT NULL,
    `created_by`       VARCHAR(50)  NOT NULL COLLATE 'utf8mb4_general_ci',
    `last_modified_by` VARCHAR(50)  NOT NULL COLLATE 'utf8mb4_general_ci',
    PRIMARY KEY (`tire_dot_id`) USING BTREE,
    UNIQUE INDEX `tire_id_dot` (`tire_id`, `dot`) USING BTREE,
    INDEX `tire_id` (`tire_id`) USING BTREE
)
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB
;
