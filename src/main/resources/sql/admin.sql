DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin`
(
    `account_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`account_id`) USING BTREE
)
    COLLATE = 'utf8mb4_general_ci'
    ENGINE = InnoDB
;
