SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

DROP database IF EXISTS internet_provider;
CREATE database internet_provider;
USE internet_provider;

--------------------------------
-- Tables
--------------------------------

DROP TABLE IF EXISTS roles;
CREATE TABLE IF NOT EXISTS roles (
    id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(10) NOT NULL UNIQUE
);

DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS users (
    id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(50) NOT NULL, 
	date_of_birth DATE NOT NULL,
	address VARCHAR(50) NOT NULL,
	passport VARCHAR(10) NOT NULL,
	login VARCHAR(30) NOT NULL UNIQUE,
	password VARCHAR(15) NOT NULL,
	role_id INT(11) DEFAULT NULL,
	FOREIGN KEY (role_id) REFERENCES roles(id)	
);

DROP TABLE IF EXISTS tariffs;
CREATE TABLE IF NOT EXISTS tariffs (
    id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE	
);

DROP TABLE IF EXISTS services;
CREATE TABLE IF NOT EXISTS services (
    id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE	
);

DROP TABLE IF EXISTS services_tariffs;
CREATE TABLE IF NOT EXISTS services_tariffs (
    id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	service_id INT(11) NOT NULL,
	tariff_id INT(11) NOT NULL,
	price DOUBLE NOT NULL,
	description VARCHAR(100) NOT NULL,
	FOREIGN KEY (service_id) REFERENCES services(id),	
	FOREIGN KEY (tariff_id) REFERENCES tariffs(id)	
);

DROP TABLE IF EXISTS contracts;
CREATE TABLE IF NOT EXISTS contracts (
    id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	user_id INT(11) NOT NULL,
	service_tariff_id INT(11) NOT NULL,
	date DATE NOT NULL,
	FOREIGN KEY (user_id) REFERENCES users(id),
	FOREIGN KEY (service_tariff_id) REFERENCES services_tariffs(id)	
);

DROP TABLE IF EXISTS account;
CREATE TABLE IF NOT EXISTS account (
    id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	user_id INT(11) NOT NULL,
	money DOUBLE NOT NULL DEFAULT '0.0',
	is_blocked VARCHAR(7) NOT NULL DEFAULT 'FREE',
	is_blocked_by_admin VARCHAR(7) NOT NULL DEFAULT 'FREE',
	FOREIGN KEY (user_id) REFERENCES users(id)
);

--------------------------------
-- Insert into tables
--------------------------------

INSERT INTO roles VALUES (0, 'admin');
INSERT INTO roles VALUES (1, 'subscriber');

INSERT INTO users VALUES (1, 'Пархоменко Татьяна Игоревна', '1996-03-02', 'Харьков', 'АБ123456', 'таня', 'таня123', '0');
INSERT INTO users VALUES (2, 'Kevin McCallister', '1980-08-26', 'SMALLSYS INC 795 E DRAGRAM TUCSON AZ 85705 USA', 'MN123456', 'kevin', 'kevin123', '1');
INSERT INTO users VALUES (3, 'Daniel Jacob Radcliffe', '1989-07-23', '132, My Street, Bigtown BG23 4YZ United States', 'RT123456', 'dan', 'dan123', '1');
INSERT INTO users VALUES (4, 'Benedict Timothy Carlton Cumberbatch', '1976-07-19', 'IMPASSE VIVALDI VAUCE 53300 COUESNES VAUCE FRANCE', 'WW123456', 'ben', 'ben123', '1');
INSERT INTO users VALUES (5, 'Leonardo Wilhelm DiCaprio', '1974-11-11', 'Gerold und Co Weihburggasse 26 A-1010 VIENNA AUSTRIA', 'AZ123456', 'leo', 'leo123', '1');
INSERT INTO users VALUES (6, 'Петров Петр Петрович', '1990-01-02', 'Киев', 'МК4432613', 'pivas47@mail.ru', 'петя', '1');

INSERT INTO tariffs VALUES (1, 'Basic');
INSERT INTO tariffs VALUES (2, 'Normal');
INSERT INTO tariffs VALUES (3, 'Super');
INSERT INTO tariffs VALUES (4, 'Mega');
INSERT INTO tariffs VALUES (5, 'Turbo');

INSERT INTO services VALUES (1, 'Internet');
INSERT INTO services VALUES (2, 'IP-TV');
INSERT INTO services VALUES (3, 'Cable TV');
INSERT INTO services VALUES (4, 'Local telephone');

INSERT INTO services_tariffs VALUES (1, 1, 1, 20.0, 'Speed: 25 Mbit/s');
INSERT INTO services_tariffs VALUES (2, 1, 2, 50.0, 'Speed: 50 Mbit/s');
INSERT INTO services_tariffs VALUES (3, 1, 3, 100.0, 'Speed: 100 Mbit/s');
INSERT INTO services_tariffs VALUES (4, 1, 4, 150.0, 'Speed: 300 Mbit/s');
INSERT INTO services_tariffs VALUES (5, 1, 5, 200.0, 'Speed: 500 Mbit/s');
INSERT INTO services_tariffs VALUES (6, 2, 1, 15.0, 'Channels: 25');
INSERT INTO services_tariffs VALUES (7, 2, 2, 25.0, 'Channels: 50');
INSERT INTO services_tariffs VALUES (8, 2, 3, 30.0, 'Channels: 100');
INSERT INTO services_tariffs VALUES (9, 2, 4, 40.0, 'Channels: 200');
INSERT INTO services_tariffs VALUES (10, 3, 1, 10.0, 'Channels: 15');
INSERT INTO services_tariffs VALUES (11, 3, 2, 20.0, 'Channels: 40');
INSERT INTO services_tariffs VALUES (12, 3, 5, 35.0, 'Channels: 90');
INSERT INTO services_tariffs VALUES (13, 4, 1, 20.0, 'Free: 30 mins');
INSERT INTO services_tariffs VALUES (14, 4, 3, 25.0, 'Free: 60 mins');
INSERT INTO services_tariffs VALUES (15, 4, 4, 35.0, 'Free: 3 hrs');

INSERT INTO contracts VALUES (1, 2, 1, '2016-01-11');
INSERT INTO contracts VALUES (2, 2, 8, '2016-02-09');
INSERT INTO contracts VALUES (3, 3, 14, '2016-05-05');
INSERT INTO contracts VALUES (4, 4, 3, '2016-07-07');
INSERT INTO contracts VALUES (5, 4, 9, '2016-07-07');
INSERT INTO contracts VALUES (6, 4, 15, '2016-10-13');
INSERT INTO contracts VALUES (7, 5, 2, '2016-11-01');
INSERT INTO contracts VALUES (8, 6, 4, '2016-11-01');

INSERT INTO account VALUES (1, 2, 200.0, 'FREE', 'FREE');
INSERT INTO account VALUES (2, 3, 0.0, 'FREE', 'FREE');
INSERT INTO account VALUES (3, 4, 20.0, 'FREE', 'FREE');
INSERT INTO account VALUES (4, 5, 100.0, 'FREE', 'FREE');
INSERT INTO account VALUES (5, 6, 1000.0, 'FREE', 'FREE');

--------------------------------
-- Triggers
--------------------------------

DROP TRIGGER IF EXISTS payment;

CREATE TRIGGER payment 
AFTER INSERT ON contracts 
FOR EACH ROW UPDATE account 
 SET money=money-(SELECT price from services_tariffs st JOIN contracts c ON c.service_tariff_id=st.id JOIN users u ON u.id=c.user_id WHERE c.user_id=NEW.user_id AND c.service_tariff_id=NEW.service_tariff_id) 
WHERE user_id=NEW.user_id;

DELIMITER $$

DROP TRIGGER IF EXISTS blocker $$
CREATE TRIGGER blocker 
BEFORE UPDATE ON account 
FOR EACH ROW 
BEGIN 
	IF NEW.money<0 
		THEN SET NEW.is_blocked="BLOCKED"; 
	ELSEIF NEW.money>=0 
		THEN SET NEW.is_blocked="FREE"; 
	END IF; 
END;$$

CREATE TRIGGER account_rules 
BEFORE INSERT ON account 
FOR EACH ROW 
BEGIN 
	IF (SELECT roles.id FROM roles JOIN users ON users.role_id=roles.id WHERE users.id=NEW.user_id)=0 
		THEN signal sqlstate '45000' SET MESSAGE_TEXT='ADMIN CANNOT HAVE AN ACCOUNT'; 
	END IF; 
END;$$

DELIMITER ;

--------------------------------
-- Events
--------------------------------

SET GLOBAL event_scheduler = ON;

DELIMITER $$

DROP EVENT IF EXISTS monthlyPayment $$

CREATE DEFINER=`root`@`localhost` EVENT `monthlyPayment` ON SCHEDULE EVERY 1 MONTH STARTS '2017-03-01 00:00:00' ON COMPLETION NOT PRESERVE ENABLE DO UPDATE account SET money=money-(SELECT SUM(services_tariffs.price) FROM contracts JOIN services_tariffs ON services_tariffs.id=service_tariff_id WHERE contracts.user_id=account.user_id) WHERE user_id IN (SELECT user_id FROM contracts)$$

DELIMITER ;
