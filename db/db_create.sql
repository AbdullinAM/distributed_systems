# drop all tables
DROP TABLE IF EXISTS bugreport_comments;
DROP TABLE IF EXISTS ticket_assignees;
DROP TABLE IF EXISTS ticket_comments;
DROP TABLE IF EXISTS project_developers;
DROP TABLE IF EXISTS project_testers;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS bugreport;
DROP TABLE IF EXISTS ticket;
DROP TABLE IF EXISTS milestone;
DROP TABLE IF EXISTS project;
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

CREATE TABLE project (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) UNIQUE NOT NULL,
  manager INT NOT NULL,
  teamLeader INT NOT NULL,
  FOREIGN KEY (manager) REFERENCES USERS(id),
  FOREIGN KEY (teamLeader) REFERENCES USERS(id)
);

CREATE TABLE project_developers (
  project INT NOT NULL,
  user INT NOT NULL,
  PRIMARY KEY (project, user),
  FOREIGN KEY (project) REFERENCES project(id),
  FOREIGN KEY (user) REFERENCES USERS(id)
);

CREATE TABLE project_testers (
  project INT NOT NULL,
  user INT NOT NULL,
  PRIMARY KEY (project, user),
  FOREIGN KEY (project) REFERENCES project(id),
  FOREIGN KEY (user) REFERENCES USERS(id)
);

CREATE TABLE bugreport (
  id INT AUTO_INCREMENT PRIMARY KEY,
  project INT NOT NULL,
  creator INT NOT NULL,
  developer INT,
  status ENUM("OPENED", "ACCEPTED", "FIXED", "CLOSED") NOT NULL,
  creationTime DATETIME NOT NULL,
  description VARCHAR(1000) NOT NULL,
  FOREIGN KEY (project) REFERENCES project(id),
  FOREIGN KEY (creator) REFERENCES USERS(id),
  FOREIGN KEY (developer) REFERENCES USERS(id)
);

CREATE TABLE comment (
  id INT AUTO_INCREMENT PRIMARY KEY,
  time DATETIME NOT NULL,
  commenter INT NOT NULL,
  description VARCHAR(1000) NOT NULL,
  FOREIGN KEY (commenter) REFERENCES USERS(id)
);

CREATE TABLE bugreport_comments (
  commentid INT PRIMARY KEY,
  bugreport INT NOT NULL,
  FOREIGN KEY (bugreport) REFERENCES bugreport (id),
  FOREIGN KEY (commentid) REFERENCES comment(id)
);

CREATE TABLE milestone (
  id INT AUTO_INCREMENT PRIMARY KEY,
  project INT NOT NULL,
  status ENUM("OPENED", "ACTIVE", "CLOSED") NOT NULL,
  startDate DATETIME NOT NULL,
  activeDate DATETIME,
  endDate DATETIME NOT NULL,
  closingDate DATETIME,
  FOREIGN KEY (project) REFERENCES project(id)
);

CREATE TABLE ticket (
  id INT AUTO_INCREMENT PRIMARY KEY,
  milestone INT NOT NULL,
  creator INT NOT NULL,
  status ENUM("NEW", "ACCEPTED", "IN_PROGRESS", "FINISHED", "CLOSED") NOT NULL,
  creationTime DATETIME NOT NULL,
  task VARCHAR(1000),
  FOREIGN KEY (milestone) REFERENCES milestone (id),
  FOREIGN KEY (creator) REFERENCES USERS(id)
);

CREATE TABLE ticket_assignees (
  ticket INT,
  user INT,
  PRIMARY KEY (ticket, user),
  FOREIGN KEY (ticket) REFERENCES ticket (id),
  FOREIGN KEY (user) REFERENCES USERS(id)
);

CREATE TABLE ticket_comments (
  commentid INT PRIMARY KEY,
  ticket INT NOT NULL,
  FOREIGN KEY (ticket) REFERENCES ticket (id),
  FOREIGN KEY (commentid) REFERENCES comment (id)
);
