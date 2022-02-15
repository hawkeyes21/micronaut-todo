--liquibase formatted sql
--changeset JayminSachin:1
create table todos (id bigint not null auto_increment, status integer, title varchar(255), primary key (id))


