# drop all tables
DROP TABLE IF EXISTS BUGREPORT_COMMENTS;
DROP TABLE IF EXISTS TICKET_ASSIGNEES;
DROP TABLE IF EXISTS TICKET_COMMENTS;
DROP TABLE IF EXISTS PROJECT_DEVELOPERS;
DROP TABLE IF EXISTS PROJECT_TESTERS;
DROP TABLE IF EXISTS COMMENT;
DROP TABLE IF EXISTS BUGREPORT;
DROP TABLE IF EXISTS TICKET;
DROP TABLE IF EXISTS MILESTONE;
DROP TABLE IF EXISTS PROJECT;
DROP TABLE IF EXISTS MESSAGE;
DROP TABLE IF EXISTS USERS;

# create tables
CREATE TABLE USERS (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  login VARCHAR(100) UNIQUE NOT NULL,
  password CHAR(40)   # sha1 hash
);

CREATE TABLE MESSAGE (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user INT NOT NULL,
  content VARCHAR(1000) NOT NULL,
  FOREIGN KEY (user) REFERENCES USERS(id)
);

CREATE TABLE PROJECT (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) UNIQUE NOT NULL,
  manager INT NOT NULL,
  teamLeader INT NOT NULL,
  FOREIGN KEY (manager) REFERENCES USERS(id),
  FOREIGN KEY (teamLeader) REFERENCES USERS(id)
);

CREATE TABLE PROJECT_DEVELOPERS (
  project INT NOT NULL,
  user INT NOT NULL,
  PRIMARY KEY (project, user),
  FOREIGN KEY (project) REFERENCES PROJECT(id),
  FOREIGN KEY (user) REFERENCES USERS(id)
);

CREATE TABLE PROJECT_TESTERS (
  project INT NOT NULL,
  user INT NOT NULL,
  PRIMARY KEY (project, user),
  FOREIGN KEY (project) REFERENCES PROJECT(id),
  FOREIGN KEY (user) REFERENCES USERS(id)
);

CREATE TABLE BUGREPORT (
  id INT AUTO_INCREMENT PRIMARY KEY,
  project INT NOT NULL,
  creator INT NOT NULL,
  developer INT,
  status ENUM("OPENED", "ACCEPTED", "FIXED", "CLOSED") NOT NULL,
  creationTime DATETIME NOT NULL,
  description VARCHAR(1000) NOT NULL,
  FOREIGN KEY (project) REFERENCES PROJECT(id),
  FOREIGN KEY (creator) REFERENCES USERS(id),
  FOREIGN KEY (developer) REFERENCES USERS(id)
);

CREATE TABLE COMMENT (
  id INT AUTO_INCREMENT PRIMARY KEY,
  time DATETIME NOT NULL,
  commenter INT NOT NULL,
  description VARCHAR(1000) NOT NULL,
  FOREIGN KEY (commenter) REFERENCES USERS(id)
);

CREATE TABLE BUGREPORT_COMMENTS (
  commentid INT PRIMARY KEY,
  bugreport INT NOT NULL,
  FOREIGN KEY (bugreport) REFERENCES BUGREPORT(id),
  FOREIGN KEY (commentid) REFERENCES COMMENT(id)
);

CREATE TABLE MILESTONE (
  id INT AUTO_INCREMENT PRIMARY KEY,
  project INT NOT NULL,
  status ENUM("OPENED", "ACTIVE", "CLOSED") NOT NULL,
  startDate DATETIME NOT NULL,
  activeDate DATETIME,
  endDate DATETIME NOT NULL,
  closingDate DATETIME,
  FOREIGN KEY (project) REFERENCES PROJECT(id)
);

CREATE TABLE TICKET (
  id INT AUTO_INCREMENT PRIMARY KEY,
  milestone INT NOT NULL,
  creator INT NOT NULL,
  status ENUM("NEW", "ACCEPTED", "IN_PROGRESS", "FINISHED", "CLOSED") NOT NULL,
  creationTime DATETIME NOT NULL,
  task VARCHAR(1000),
  FOREIGN KEY (milestone) REFERENCES MILESTONE(id),
  FOREIGN KEY (creator) REFERENCES USERS(id)
);

CREATE TABLE TICKET_ASSIGNEES (
  ticket INT,
  user INT,
  PRIMARY KEY (ticket, user),
  FOREIGN KEY (ticket) REFERENCES TICKET(id),
  FOREIGN KEY (user) REFERENCES USERS(id)
);

CREATE TABLE TICKET_COMMENTS (
  commentid INT PRIMARY KEY,
  ticket INT NOT NULL,
  FOREIGN KEY (ticket) REFERENCES TICKET(id),
  FOREIGN KEY (commentid) REFERENCES COMMENT(id)
);
