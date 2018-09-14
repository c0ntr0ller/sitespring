CREATE SEQUENCE hibernate_sequence
  START 1
  INCREMENT 1;
CREATE TABLE messages (
  id       INT8 NOT NULL,
  filename VARCHAR(255),
  tag      VARCHAR(255),
  text     VARCHAR(255),
  user_id  INT8,
  PRIMARY KEY (id)
);
CREATE TABLE userroles (
  user_id INT8 NOT NULL,
  roles   VARCHAR(255)
);
CREATE TABLE usr (
  id              INT8    NOT NULL,
  activation_code VARCHAR(255),
  active          BOOLEAN NOT NULL,
  email           VARCHAR(255),
  password        VARCHAR(255),
  username        VARCHAR(255),
  PRIMARY KEY (id)
);
ALTER TABLE IF EXISTS messages
  ADD CONSTRAINT FK5f19dxsguyxb310o6hn9ccmbt FOREIGN KEY (user_id) REFERENCES usr;
ALTER TABLE IF EXISTS userroles
  ADD CONSTRAINT FKgvaw2g8ci5y7tpq9bmpcypahp FOREIGN KEY (user_id) REFERENCES usr;