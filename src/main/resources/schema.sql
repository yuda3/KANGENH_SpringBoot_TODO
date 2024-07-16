CREATE TABLE tasks
(
    id BIGINT not null primary key auto_increment,
    summary varchar(256) not null ,
    description text,
    status varchar(256) not null
);