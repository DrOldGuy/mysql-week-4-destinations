DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS destinations;
DROP TABLE IF EXISTS members;

CREATE TABLE members (
  member_id int NOT NULL AUTO_INCREMENT,
  first_name varchar(40) NOT NULL,
  last_name varchar(40) NOT NULL,
  email varchar(64) NOT NULL,
  phone_number varchar(20),
  password varchar(40) NOT NULL,
  PRIMARY KEY (member_id),
  UNIQUE KEY (email)
);

CREATE TABLE destinations (
  destination_id int NOT NULL AUTO_INCREMENT,
  member_id int NOT NULL,
  name varchar(128) NOT NULL,
  description text NOT NULL,
  geolocation varchar(64) NOT NULL,
  time_to_traverse time,
  difficulty int,
  publish_timestamp timestamp not null DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (destination_id),
  FOREIGN KEY (member_id) REFERENCES members (member_id)
);

CREATE TABLE comments (
  comment_id int NOT NULL AUTO_INCREMENT,
  member_id int NOT NULL,
  destination_id int NOT NULL,
  comment text NOT NULL,
  publish_timestamp timestamp not null DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (comment_id),  
  FOREIGN KEY (member_id) REFERENCES members (member_id),
  FOREIGN KEY (destination_id) REFERENCES destinations (destination_id) ON DELETE CASCADE
);