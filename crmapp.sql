CREATE DATABASE crmapptest;
USE crmapptest;
SET TIME_ZONE="+0:00";
SHOW VARIABLES LIKE '%time_zone%';

CREATE TABLE roles(
	id int auto_increment,
	name varchar(50),
	description varchar(255),
	
	PRIMARY KEY(id)
);

CREATE TABLE users(
	id int auto_increment,
	password varchar(255),
	first_name varchar(50),
	last_name varchar(50),
	username varchar(255),
	phone varchar(12),
	id_role int,
	
	PRIMARY KEY(id)
);

CREATE TABLE task(
	id int auto_increment,
	id_user int,
	id_project int,
	id_status int,
	name varchar(255),
	start_date timestamp ,
	end_date timestamp,
	
	PRIMARY KEY(id)
);

CREATE TABLE project(
	id int auto_increment,
	name varchar(255),
	start_date timestamp,
	end_date timestamp,
	id_leader int,
	
	PRIMARY KEY(id)
);

CREATE TABLE status(
	id int auto_increment,
	name varchar(20),
	
	PRIMARY KEY(id)
);

-- All foreign key
ALTER TABLE users ADD CONSTRAINT FK_id_role_users FOREIGN KEY(id_role) REFERENCES roles(id);
ALTER TABLE task ADD CONSTRAINT FK_id_user_task FOREIGN KEY(id_user) REFERENCES users(id);
ALTER TABLE task ADD CONSTRAINT FK_id_project_task FOREIGN KEY(id_project) REFERENCES project(id);
ALTER TABLE task ADD CONSTRAINT FK_id_status_task FOREIGN KEY(id_status) REFERENCES status(id);
ALTER TABLE project ADD CONSTRAINT FK_id_leader_project FOREIGN KEY(id_leader) REFERENCES users(id);

-- Before start website, you need:
-- Add Role
INSERT INTO roles (name)
VALUES
('ADMIN'),
('USER'),
('LEADER');

-- add admin account
-- account: admin@gmail.com
-- password: admin
INSERT INTO users (password, first_name, last_name, username, phone, id_role)
VALUES
('jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg=', 'Admin', 'Tèo', 'admin@gmail.com', '0123456789', 1);

-- add task status
INSERT INTO status (name)
VALUES
('CHƯA BẮT ĐẦU'),
('ĐANG THỰC HIỆN'),
('ĐÃ HOÀN THÀNH');