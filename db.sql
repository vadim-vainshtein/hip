CREATE DATABASE hip_concerts;
CREATE USER 'hip_user'@'localhost' IDENTIFIED BY hippie_forever;
GRANT SELECT,UPDATE,INSERT,DELETE ON hip_concerts.* TO 'hip_user'@'localhost';

USE hip_concerts;

CREATE TABLE concerts
(
	concert_id	INT NOT NULL AUTO_INCREMENT,
	date_time	DATETIME NOT NULL,
	place_id	INT NOT NULL,
	PRIMARY KEY (concert_id)
);

CREATE TABLE performers
(
	performer_id	INT NOT NULL AUTO_INCREMENT,
	performer_name	VARCHAR
	PRIMARY KEY (performer_id)
);

CREATE TABLE performers_concerts
(
	FOREIGN KEY (performer_id) REFERENCES (performers),
	FOREIGN KEY (concert_id) REFERENCES (concerts)
);

