create database tmc_access;
use tmc_access;

create table users
(
    user_name   VARCHAR(100) NOT NULL PRIMARY KEY,
    user_pass   VARCHAR(100) NOT NULL
);

create table user_roles
(
    user_name   VARCHAR(100) NOT NULL,
    role_name   VARCHAR(100) not NULL,
    PRIMARY KEY (user_name, role_name)
);

insert into users values ('admin', '12345');
insert into user_roles values ('admin', 'hip_admin');

create user 'tmc_access'@'localhost' identified by 'password';
grant select on tmc_access.* to 'tmc_access'@'localhost';