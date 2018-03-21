CREATE TABLE IF NOT EXISTS `todo` (
  `id`          BIGINT       AUTO_INCREMENT PRIMARY KEY,
  `owner`       VARCHAR(255) NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  `completed`   BOOLEAN      NOT NULL DEFAULT false
);

CREATE TABLE IF NOT EXISTS `users` (
    `id`                BIGINT         AUTO_INCREMENT,
    `username`          VARCHAR(50)    NOT NULL UNIQUE,
    `password`          VARCHAR(60)    NOT NULL,
    `email`             VARCHAR(100)   NOT NULL,
    `enabled`           BOOLEAN NOT NULL  DEFAULT false,
    `activation_key`    VARCHAR(20),
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `authority` (
    `name`    VARCHAR(50)  NOT NULL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS `user_authority` (
    `username`         VARCHAR(50)    NOT NULL,
    `authority_name`   VARCHAR(50)    NOT NULL,
    PRIMARY KEY (`username`, `authority_name`),
    FOREIGN KEY (`username`)        REFERENCES `users`(`username`),
    FOREIGN KEY (`authority_name`)  REFERENCES `authority`(`name`)
);
