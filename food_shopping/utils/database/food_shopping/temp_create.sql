CREATE TABLE device (
	device_id INT NOT NULL, 
	name varchar(50),
	location varchar(50),
	manufacturer varchar(10),
	PRIMARY KEY(device_id)
) ENGINE=INNODB;

CREATE TABLE code (
	code_id INT NOT NULL, 
	name varchar(50),
	size varchar(50),
	company varchar(50),
	PRIMARY KEY(code_id)
) ENGINE=INNODB;