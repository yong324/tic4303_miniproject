create table users (
	id int auto_increment not null,
	email varchar(255),
	name varchar(255),
	password varchar(255),
	role varchar(255),
	primary key (id)
);