CREATE TABLE tasks(
id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
summary TEXT,
description VARCHAR(256) NOT NULL,
status VARCHAR(256) NOT NULL
);