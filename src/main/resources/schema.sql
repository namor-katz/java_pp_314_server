create table IF NOT EXISTS t_user (id bigint not null auto_increment, email varchar(255), maxweight integer not null, password varchar(255), username varchar(255), primary key (id)) engine=InnoDB;
create table IF NOT EXISTS t_role (id bigint not null, name varchar(255), primary key (id)) engine=InnoDB;
create table IF NOT EXISTS t_user_roles (user_id bigint not null, roles_id bigint not null, primary key (user_id, roles_id)) engine=InnoDB;
