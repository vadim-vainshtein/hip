CREATE DATABASE hip_concerts;
CREATE USER 'hip_user'@'localhost' IDENTIFIED BY 'hippie_forever';
GRANT ALL PRIVILEGES ON hip_concerts.* TO 'hip_user'@'localhost';

USE hip_concerts;

CREATE TABLE performers
(
	performer_id	INT NOT NULL AUTO_INCREMENT,
	performer_name	VARCHAR(255) NOT NULL,
	PRIMARY KEY (performer_id)
);

CREATE TABLE concerts
(
        concert_id      INT NOT NULL AUTO_INCREMENT,
        date_time       DATETIME NOT NULL,
        place_id        INT NOT NULL,
        PRIMARY KEY (concert_id)
);


CREATE TABLE performers_concerts
(
	performer_id	INT,
	concert_id	INT,
	FOREIGN KEY (performer_id) REFERENCES performers (performer_id),
	FOREIGN KEY (concert_id) REFERENCES concerts (concert_id)
);

