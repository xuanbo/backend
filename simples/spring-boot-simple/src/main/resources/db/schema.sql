CREATE TABLE city
(
  id      INT PRIMARY KEY auto_increment,
  name    VARCHAR(50),
  state   VARCHAR(20),
  country VARCHAR(20)
);

CREATE TABLE file
(
  id      VARCHAR(50) PRIMARY KEY,
  name    VARCHAR(50),
  size    INT(20),
  createTime DATETIME
);