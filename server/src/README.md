# MySQL Database Initial Setup

CREATE DATABASE miniproject;

USE miniproject;

CREATE USER 'fred'@'localhost' IDENTIFIED BY 'Password@123';
GRANT ALL PRIVILEGES ON *.* TO 'fred'@'localhost' WITH GRANT OPTION;
FLUSH PRIVILEGES;

create table users (
	id int auto_increment not null,
	email varchar(255),
	name varchar(255),
	password varchar(255),
	role varchar(255),
	primary key (id)
);

insert into users (id, email, name, password, role) values ('1', 'admin@gmail.com', 'Administrator',  '$2a$12$122ZD9ZaPutMwU4XxT/.VOglGwSz6YzYVcw/gAmyRdQ7xLciCjThW', 'ADMIN');

create table interests (
	id int auto_increment not null,
    name varchar(255),
    email varchar(255),
    phone varchar(255),
    country varchar(255),
    gender varchar(255),
    qualification varchar(255),
    primary key (id)
);


# Commands to Run Project

open terminal 1:
navigate to the client directory of the miniprject: cd miniproject/client
run the following command: ng serve --proxy-config proxy-config.js
The angular project should be compiled successfully

open terminal 2:
navigate to the server directory of the miniprject: cd miniproject/server
run the following command: mvn spring-boot:run
The application should be started successfully

open chrome:
in the URL -> localhost:4200
right click, anywhere on screen, inspect, go to application to view JWT 