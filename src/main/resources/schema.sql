CREATE TABLE todo (
  id bigint AUTO_INCREMENT PRIMARY KEY ,
  owner VARCHAR(255) NOT NULL,
  description VARCHAR(255) NOT NULL,
  completed BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE users (
    username    VARCHAR(50)    NOT NULL,
    password    VARCHAR(60)    NOT NULL,
    enabled     SMALLINT,
    PRIMARY KEY (username)
);

CREATE TABLE authorities (
    username    VARCHAR(50)    NOT NULL,
    authority   VARCHAR(50)    NOT NULL,
    FOREIGN KEY (username) REFERENCES users(username)
);
