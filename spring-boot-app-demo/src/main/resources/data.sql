INSERT INTO COURSES(ID, NAME, CATEGORY, RATING, DESCRIPTION)  VALUES(100, 'Rapid Spring Boot Application Development', 'Spring', 4, 'Spring Boot gives all the power of the Spring Framework without all of the complexity');
INSERT INTO COURSES(ID, NAME, CATEGORY, RATING, DESCRIPTION)  VALUES(200, 'Getting Started with Spring Security DSL', 'Spring', 5, 'Learn Spring Security DSL in easy steps');
INSERT INTO COURSES(ID, NAME, CATEGORY, RATING, DESCRIPTION)  VALUES(300, 'Getting Started with Spring Cloud Kubernetes', 'Python', 3, 'Master Spring Boot application deployment with Kubernetes');


INSERT into USERS(username, password, enabled) values ('user','password', true);
INSERT into USERS(username, password, enabled) values ('admin','password', true);

INSERT into AUTHORITIES(username, authority) values ('user','USER');
INSERT into AUTHORITIES(username, authority) values ('admin','ADMIN');

INSERT INTO CT_USERS(ID, FIRST_NAME, LAST_NAME, USERNAME, PASSWORD, EMAIL, VERIFIED, LOCKED, ACC_CRED_EXPIRED) VALUES (1, 'John', 'Socket', 'jsocket', 'password', 'jsocket@example.com', TRUE, FALSE, FALSE);
INSERT INTO CT_USERS(ID, FIRST_NAME, LAST_NAME, USERNAME, PASSWORD, EMAIL, VERIFIED, LOCKED, ACC_CRED_EXPIRED) VALUES (2, 'Steve', 'Smith', 'smith', 'password', 'smith@example.com', FALSE, FALSE, FALSE);