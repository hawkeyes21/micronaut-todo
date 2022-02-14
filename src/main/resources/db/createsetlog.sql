--liquibase formatted sql

--changeset JayminSachin:2
INSERT INTO todo(status, title) VALUES('OPEN', 'Eat Food')