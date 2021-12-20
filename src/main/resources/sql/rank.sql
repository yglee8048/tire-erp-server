DROP TABLE IF EXISTS `rank`;

CREATE TABLE `rank`
(
    `rank_id`          BIGINT(20)  NOT NULL AUTO_INCREMENT,
    `name`             VARCHAR(50) NOT NULL COLLATE 'utf8mb4_general_ci',
    `created_at`       DATETIME    NOT NULL,
    `last_modified_at` DATETIME    NOT NULL,
    `created_by`       VARCHAR(50) NOT NULL COLLATE 'utf8mb4_general_ci',
    `last_modified_by` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_general_ci',
    PRIMARY KEY (`rank_id`) USING BTREE,
    UNIQUE INDEX `name` (`name`) USING BTREE
)
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB
;
