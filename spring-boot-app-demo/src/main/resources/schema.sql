CREATE TABLE authors (
  id   BIGINT NOT NULL auto_increment,
  bio  VARCHAR(255),
  name VARCHAR(255),
  PRIMARY KEY (id)
);

CREATE TABLE authors_courses (
  author_id BIGINT NOT NULL,
  course_id BIGINT NOT NULL,
  PRIMARY KEY (author_id, course_id)
);

CREATE TABLE courses (
  id          BIGINT NOT NULL auto_increment,
  category    VARCHAR(255),
  description VARCHAR(255),
  name        VARCHAR(255),
  rating      INTEGER NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE authors_courses
  ADD CONSTRAINT course_id_fk FOREIGN KEY (course_id) REFERENCES courses (id);

ALTER TABLE authors_courses
  ADD CONSTRAINT author_id_fk FOREIGN KEY (author_id) REFERENCES authors (id);

create table users(
    username varchar(50) not null primary key,
    password varchar(500) not null,
    enabled boolean not null
);

create table authorities (
    username varchar(50) not null,
    authority varchar(50) not null,
    constraint fk_authorities_users foreign key(username) references users(username)
);

create unique index ix_auth_username on authorities (username,authority);

create table ct_users(
    ID int	NOT NULL auto_increment,
    EMAIL	VARCHAR(255)	NOT NULL,
    FIRST_NAME	VARCHAR(255) NOT NULL,
    LAST_NAME	VARCHAR(255) NOT NULL,
    PASSWORD	VARCHAR(255) NOT NULL,
    USERNAME	VARCHAR(255) NOT NULL,
    PRIMARY KEY (ID)
);