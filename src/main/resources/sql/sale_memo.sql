DROP TABLE IF EXISTS `sale_memo`;

CREATE TABLE `sale_memo`
(
    `sale_memo_id`     BIGINT(20)   NOT NULL AUTO_INCREMENT,
    `sale_id`          BIGINT(20)   NOT NULL,
    `memo`             VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `is_lock`          TINYINT(1)   NOT NULL,
    `is_admin`         TINYINT(1)   NOT NULL,
    `created_at`       DATETIME     NOT NULL,
    `last_modified_at` DATETIME     NOT NULL,
    `created_by`       VARCHAR(50)  NOT NULL COLLATE 'utf8mb4_general_ci',
    `last_modified_by` VARCHAR(50)  NOT NULL COLLATE 'utf8mb4_general_ci',
    PRIMARY KEY (`sale_memo_id`) USING BTREE,
    INDEX `sale_id` (`sale_id`) USING BTREE
)
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB
;
