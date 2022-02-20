DROP TABLE IF EXISTS `client`;

CREATE TABLE `client`
(
    `account_id`        BIGINT(20)   NOT NULL,
    `client_company_id` BIGINT(20)   NOT NULL,
    `city`              VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `street_address`    VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `detail_address`    VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
    `zip_code`          INT(11)      NULL DEFAULT NULL,
    PRIMARY KEY (`account_id`) USING BTREE,
    INDEX `client_company_id` (`client_company_id`) USING BTREE
)
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB
;

