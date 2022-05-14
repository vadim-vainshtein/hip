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

CREATE TABLE places
(
	place_id	INT NOT NULL AUTO_INCREMENT,
	place_name	VARCHAR(255),
	place_address	VARCHAR(255),
	PRIMARY KEY (place_id)
);

CREATE TABLE concerts
(
        concert_id      INT NOT NULL AUTO_INCREMENT,
        date_time       DATETIME,
        place_id        INT,
        program_name   VARCHAR(255),
        link		VARCHAR(1024),
        PRIMARY KEY (concert_id),
        FOREIGN KEY (place_id) REFERENCES places (place_id) ON DELETE SET NULL
);

CREATE TABLE instruments
(
	instrument_id		INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	instrument_name	VARCHAR(255) NOT NULL
);	

CREATE TABLE performers_concerts
(
	performer_id	INT,
	concert_id	INT,
	FOREIGN KEY (performer_id) REFERENCES performers (performer_id),
	FOREIGN KEY (concert_id) REFERENCES concerts (concert_id)
);

CREATE TABLE performers_instruments
(
	performer_id	INT,
	instrument_id	INT,
	FOREIGN KEY (performer_id) REFERENCES performers (performer_id),
	FOREIGN KEY (instrument_id) REFERENCES instruments (instrument_id)
);
