DROP TABLE IF EXISTS `warehouse`;

CREATE TABLE `warehouse`
(
    `warehouse_id`     BIGINT(20)   NOT NULL AUTO_INCREMENT,
    `name`             VARCHAR(255) NOT NULL COLLATE 'utf8mb4_general_ci',
    `description`      VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `city`             VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `street_address`   VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `detail_address`   VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `zip_code`         INT(11)      NULL DEFAULT NULL,
    `created_at`       DATETIME     NOT NULL,
    `last_modified_at` DATETIME     NOT NULL,
    `created_by`       VARCHAR(50)  NOT NULL COLLATE 'utf8mb4_general_ci',
    `last_modified_by` VARCHAR(50)  NOT NULL COLLATE 'utf8mb4_general_ci',
    PRIMARY KEY (`warehouse_id`) USING BTREE,
    UNIQUE INDEX `name` (`name`) USING BTREE
)
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB
;
