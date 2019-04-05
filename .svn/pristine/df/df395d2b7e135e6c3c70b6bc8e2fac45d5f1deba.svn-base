CREATE TABLE sakila.user (
  id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  user_name VARCHAR(50) NOT NULL,
  password VARCHAR(255) NOT NULL,
  last_update_date DATETIME DEFAULT NULL,
  remarks TEXT DEFAULT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 1
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

SET NAMES 'utf8';

INSERT INTO sakila.user(id, user_name, password, last_update_date, remarks) VALUES
(1, 'mary', 'abcd', '2016-05-09 09:18:25', 'test');
INSERT INTO sakila.user(id, user_name, password, last_update_date, remarks) VALUES
(2, 'peter', 'cdef', '2016-05-09 09:18:28', NULL);
INSERT INTO sakila.user(id, user_name, password, last_update_date, remarks) VALUES
(3, 'mario', 'eeff', '2016-05-09 09:18:40', NULL);


CREATE TABLE sakila.salary (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  user_id INT DEFAULT NULL,
  salary NUMERIC(10, 0) DEFAULT NULL,
  last_updated_date DATETIME DEFAULT NULL,
  remarks TEXT DEFAULT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB;


SET NAMES 'utf8';

INSERT INTO sakila.salary(id, user_id, salary, last_updated_date, remarks) VALUES
(1, 1, 5560, '2016-05-09 09:20:59', NULL);
INSERT INTO sakila.salary(id, user_id, salary, last_updated_date, remarks) VALUES
(2, 2, 5570, '2016-05-09 09:21:10', NULL);
INSERT INTO sakila.salary(id, user_id, salary, last_updated_date, remarks) VALUES
(3, 3, 12300, '2016-05-09 09:21:20', NULL);