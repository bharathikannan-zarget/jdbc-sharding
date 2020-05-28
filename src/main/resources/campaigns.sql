create database campaigns0;
use campaigns0;
create table contacts(id bigint primary key auto_increment, org_id bigint, email varchar(255) unique, created_at datetime, updated_at datetime) engine=innodb;
create table contacts_custom(id bigint primary key auto_increment, org_id bigint, contact_id bigint, cf_string_01 varchar(255), cf_string_02 varchar(255), created_at datetime, updated_at datetime,
foreign key(contact_id) references contacts(id)) engine=innodb;
create table campaigns(id bigint, org_id bigint, name varchar(255), created_at datetime, updated_at datetime);

insert into contacts values(1, 100, 'bk@fworks.com', now(), now());
insert into contacts values(2, 100, 'david@fworks.com', now(), now());
insert into contacts_custom values(1, 100, 1, 'Bharathi Kannan', 'Ravikumar', now(), now());
insert into contacts_custom values(2, 100, 2, 'David', 'Thompson', now(), now());

insert into campaigns values(1, 100, 'Sharding Campaign', now(), now());
insert into campaigns values(1, 101, 'Sharding Campaign2', now(), now());

create database campaigns1;
use campaigns1;

create table contacts(id bigint primary key auto_increment, org_id bigint, email varchar(255) unique, created_at datetime, updated_at datetime) engine=innodb;
create table contacts_custom(id bigint primary key auto_increment, org_id bigint, contact_id bigint, cf_string_01 varchar(255), cf_string_02 varchar(255), created_at datetime, updated_at datetime,
foreign key(contact_id) references contacts(id)) engine=innodb;
create table campaigns(id bigint, org_id bigint, name varchar(255), created_at datetime, updated_at datetime);

insert into contacts values(1, 101, 'kannanrbk.r@gmail.com', now(), now());
insert into contacts values(2, 101, 'bill@gates.com', now(), now());
insert into contacts_custom values(1, 101, 1, 'Kannan', 'RBK', now(), now());
insert into contacts_custom values(2, 101, 2, 'Bill', 'Gates', now(), now());