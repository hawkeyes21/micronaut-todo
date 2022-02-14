--liquibase formatted sql
--changeset JayminSachin:1
create table todo (id bigint not null auto_increment, status integer, title varchar(255), primary key (id))


