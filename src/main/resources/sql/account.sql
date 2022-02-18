DROP TABLE IF EXISTS `account`;

CREATE TABLE `account`
(
    `account_id`       BIGINT(20)   NOT NULL AUTO_INCREMENT,
    `username`         VARCHAR(50)  NOT NULL COLLATE 'utf8mb4_general_ci',
    `password`         VARCHAR(255) NOT NULL COLLATE 'utf8mb4_general_ci',
    `role`             VARCHAR(6)   NOT NULL COLLATE 'utf8mb4_general_ci',
    `description`      VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `name`             VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `email`            VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `phone_number`     VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `account_type`     VARCHAR(255) NOT NULL COLLATE 'utf8mb4_general_ci',
    `created_at`       DATETIME     NOT NULL,
    `last_modified_at` DATETIME     NOT NULL,
    `created_by`       VARCHAR(50)  NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `last_modified_by` VARCHAR(50)  NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    PRIMARY KEY (`account_id`) USING BTREE,
    UNIQUE INDEX `username` (`username`) USING BTREE
)
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB
;
