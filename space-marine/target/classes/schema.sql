DROP TABLE IF EXISTS marine_ship;
DROP TABLE IF EXISTS ship;
DROP TABLE IF EXISTS marine;
DROP TABLE IF EXISTS chapter;




CREATE TABLE chapter
(
   chapter_id int NOT NULL AUTO_INCREMENT,
   chapter_name varchar (100) NOT NULL,
   tactics varchar (200),
   leader varchar (100),
   alignment varchar (100),
   description varchar (256),
   homeworld varchar (100),
   PRIMARY KEY (chapter_id)
);
CREATE TABLE marine
(
   marine_id int NOT NULL AUTO_INCREMENT,
   chapter_id int NOT NULL,
   marine_name varchar (256),
   weapon varchar (250),
   PRIMARY KEY (marine_id),
   FOREIGN KEY (chapter_id) REFERENCES chapter (chapter_id) ON DELETE CASCADE
);
CREATE TABLE ship
(
   ship_id int NOT NULL AUTO_INCREMENT,
   name varchar (256),
   PRIMARY KEY (ship_id)
);
CREATE TABLE marine_ship
(
   marine_id int NOT NULL,
   ship_id int NOT NULL,
   FOREIGN KEY (marine_id) REFERENCES marine (marine_id) ON DELETE CASCADE,
   FOREIGN KEY (ship_id) REFERENCES ship (ship_id) ON DELETE CASCADE
);
