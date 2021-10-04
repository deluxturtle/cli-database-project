CREATE DATABASE IF NOT EXISTS `password_manager`;
USE `password_manager`;

-- Queries that create schema tables--
CREATE TABLE `user` (
  `username` varchar(75) NOT NULL,
  `master_password` varchar(120) NOT NULL,
  PRIMARY KEY (`username`)
);

CREATE TABLE `secure_note` (
  `secure_note_name` varchar(50) NOT NULL,
  `secure_note_text` text,
   PRIMARY KEY (`secure_note_name`)
);

CREATE TABLE `login` (
  `login_name` VARCHAR(50) NOT NULL,
  `login_username` VARCHAR(50) NOT NULL,
  `login_password` VARCHAR(120) NOT NULL,
  `login_url` VARCHAR(2083) NOT NULL,
  `login_note` text DEFAULT NULL,
  PRIMARY KEY (`login_name`)
  );
  
  CREATE TABLE `card` (
  `card_name` VARCHAR(50) NOT NULL,
  `cardholder_name` VARCHAR(120) NOT NULL,
  `card_number` BIGINT(16) NOT NULL,
  `card_type` VARCHAR(40) NOT NULL,
  `expiration_date` DATE NOT NULL,
  `security_code` SMALLINT(3)NOT NULL,
  PRIMARY KEY (`card_name`)
  );
  
CREATE TABLE `has_card` (
  `username` varchar(75) NOT NULL,
  `card_name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`username`,`card_name`),
  CONSTRAINT `fk_user_card_user` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON UPDATE CASCADE,
  CONSTRAINT `fk_user_card_card` FOREIGN KEY (`card_name`) REFERENCES `card` (`card_name`) ON UPDATE CASCADE
);

CREATE TABLE `has_note` (
  `username` varchar(75) NOT NULL,
  `secure_note_name` varchar(50) NOT NULL,
  PRIMARY KEY (`username`,`secure_note_name`),
  CONSTRAINT `fk_user_note_user` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON UPDATE CASCADE,
  CONSTRAINT `fk_user_note_note` FOREIGN KEY (`secure_note_name`) REFERENCES `secure_note` (`secure_note_name`) ON UPDATE CASCADE
);

CREATE TABLE `has_login` (
  `username` varchar(75) NOT NULL,
  `login_name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`username`,`login_name`),
  CONSTRAINT `fk_user_login_user` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON UPDATE CASCADE,
  CONSTRAINT `fk_user_login_login` FOREIGN KEY (`login_name`) REFERENCES `login` (`login_name`) ON UPDATE CASCADE
);

-- Queries that populate tables with data--
INSERT INTO `user` VALUES 
('Alice','Password123456'),
('Bob','Monkey123'),
('Charlie','qwerty123!');

INSERT INTO `secure_note` VALUES 
("Thoughts",'I hope Bob would never find my password'),
('To-Do','Find Alices password'),
('OMG','All my colleagues care about is their passwords..');

INSERT INTO `login` VALUES 
('Alice_office', 'alice@work.com', 'officepassword', 'http://work.com', 'Main thing'),
('Bob_office', 'alice@work.com', 'abcdefg', 'http://work.com', 'Remember this'),
('Charlie_office', 'charlie@work.com', 'monkey123', 'http://work.com', 'Dont forget'),
('Alice_chase_bank', 'aiceGotMoney', 'bankpassword', 'http://chase.com', 'Money here'),
('Bob_bank', 'bobsIncome', 'VeryImportantPassword!', 'http://bankofamerica.com', 'For paychecks');

INSERT INTO `card` VALUES 
('Chase card', 'Alice Smith', 1111222233334444, 'VISA', '2021-09-26', 123),
('BOA card', 'Bob Bobb', 1234567812345678, 'Master Card', '2021-09-26', 567),
('Black card', 'Charlie Brown', 9999000088887777, 'AmEx', '2021-09-26', 777);

INSERT INTO `has_card` VALUES
('Alice', 'Chase card'),
('Bob', 'BOA card'),
('Charlie', 'Black card'); 

INSERT INTO `has_login` VALUES
('Alice', 'Alice_office'),
('Bob', 'Bob_office'),
('Charlie', 'Charlie_office'),
('Alice', 'Alice_chase_bank'),
('Bob', 'Bob_bank');

INSERT INTO `has_note` VALUES
('Alice', 'Thoughts'),
('Bob', 'To-Do'),
('Charlie', 'OMG');


INSERT INTO `card` VALUES 
('Apple Card', 'Alice Smith', 1455666872222199, 'VISA', '2021-09-26', 545),
('Costco card', 'Bob Bobb', 1234567812345678, 'VISA', '2021-09-26', 999),
('Jetblue card', 'Charlie Brown', 9999000088887777, 'Master Card', '2021-09-26', 222);

INSERT INTO `has_card` VALUES
('Alice', 'Apple Card'),
('Bob', 'Costco card'),
('Charlie', 'Jetblue card'); 

INSERT INTO `login` VALUES 
('Alice_facebook', 'alice@facebook.com', 'alicecatname', 'https://www.facebook.com/', 'You need this'),
('Bob_facebook', 'bob@facebook.com', 'bobsdogname1', 'https://www.facebook.com/', 'Social Points!'),
('Charlie_twitter', 'charlie@twitter.com', 'dressrosa', 'https://twitter.com/', 'Bird App');

INSERT INTO `has_login` VALUES
('Alice', 'Alice_facebook'),
('Bob', 'Bob_facebook'),
('Charlie', 'Charlie_twitter');

-- Query that login names, passwords and urls for Alice--
SELECT 
	l.login_name, 
    l.login_password, 
    l.login_url 
FROM 
	login l 
    INNER JOIN has_login hl ON l.login_name = hl.login_name 
    INNER JOIN user u ON hl.username = u.username 
WHERE 
	u.username = 'Alice';

-- Query that deletes a record about Alice's Facebook acoount--
DELETE FROM login WHERE login_name = 'Alice_facebook';

-- Query that updates password for Bob's Facebook account--   
UPDATE login l SET l.login_password = 'testpassword123' WHERE l.login_name = 'Bob_facebook';

-- Query that gets all the users that have a login at work.com--   
SELECT user.username, login.login_username 
FROM user join has_login on user.username = has_login.username join login on login.login_name = has_login.login_name 
WHERE login.login_url = 'http://work.com';

-- Query that gets all the users that use Master Card--   
SELECT user.username, card.card_name 
FROM user join has_card on user.username = has_card.username join card on card.card_name = has_card.card_name 
WHERE card.card_type = 'Master Card';
