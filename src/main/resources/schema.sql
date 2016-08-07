/*Activities*/
CREATE TABLE activities (
  id INT NOT NULL AUTO_INCREMENT,
  title VARCHAR(45) NOT NULL,
  date_start DATE NOT NULL,
  date_end DATE NOT NULL,
  desc longtext,
  PRIMARY KEY (id)
);

/*Departments*/
CREATE TABLE departments (
  id VARCHAR(10) NOT NULL,
  name VARCHAR(100) DEFAULT NULL,
  PRIMARY KEY (id)
);

/*Involvements*/
CREATE TABLE involvement_types (
  name VARCHAR(45) NOT NULL,
  desc VARCHAR(100) DEFAULT NULL,
  PRIMARY KEY (name)
);

/*Members*/
CREATE TABLE members (
  id INT NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(45) NOT NULL,
  last_name VARCHAR(45) NOT NULL,
  dpt_id VARCHAR(45) DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT member_dpt_id FOREIGN KEY (dpt_id) REFERENCES departments (id) ON DELETE SET NULL ON UPDATE CASCADE
);

/*Participation*/
CREATE TABLE participations (
  id INT NOT NULL AUTO_INCREMENT,
  member_id INT NOT NULL,
  activity_id INT NOT NULL,
  date DATE NOT NULL,
  remarks longtext,
  PRIMARY KEY (member_id,date,activity_id),
  CONSTRAINT participation_activity_id FOREIGN KEY (activity_id) REFERENCES activities (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT participation_member_id FOREIGN KEY (member_id) REFERENCES members (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE participation_involvements (
  participation_id INT NOT NULL,
  type VARCHAR(45) NOT NULL,
  CONSTRAINT involvement_participation_id FOREIGN KEY (participation_id) REFERENCES participations (id) ON DELETE CASCADE ON UPDATE CASCADE
);

/*Users*/
CREATE TABLE users (
  username VARCHAR(45) NOT NULL,
  password VARCHAR(45) NOT NULL,
  enabled TINYINT NOT NULL,
  date_created datetime NOT NULL,
  member_id INT,
  PRIMARY KEY (username),
  CONSTRAINT user_member_id FOREIGN KEY (member_id) REFERENCES members (id) ON DELETE SET NULL
);

/*User roles*/
CREATE TABLE  user_roles (
  user_role_id INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(45) NOT NULL,
  role VARCHAR(45) NOT NULL,
  PRIMARY KEY (user_role_id),
  CONSTRAINT user_role_username FOREIGN KEY (username) REFERENCES users (username) ON DELETE SET NULL
);