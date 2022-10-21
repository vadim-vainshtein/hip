CREATE DATABASE hip_users;
USE hip_users;

CREATE TABLE users
(
	user_id	INT PRIMARY KEY AUTO_INCREMENT NOT NULL;
	login		VARCHAR(20),
	hash		INT
);
