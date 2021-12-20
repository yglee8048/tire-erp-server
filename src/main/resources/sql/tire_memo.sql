DROP TABLE IF EXISTS `tire_memo`;

CREATE TABLE `tire_memo`
(
    `tire_memo_id`     BIGINT(20)   NOT NULL AUTO_INCREMENT,
    `tire_id`          BIGINT(20)   NOT NULL,
    `memo`             VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `is_lock`          TINYINT(1)   NOT NULL,
    `created_at`       DATETIME     NOT NULL,
    `last_modified_at` DATETIME     NOT NULL,
    `created_by`       VARCHAR(50)  NOT NULL COLLATE 'utf8mb4_general_ci',
    `last_modified_by` VARCHAR(50)  NOT NULL COLLATE 'utf8mb4_general_ci',
    PRIMARY KEY (`tire_memo_id`) USING BTREE,
    INDEX `tire_id` (`tire_id`) USING BTREE
)
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB
;
