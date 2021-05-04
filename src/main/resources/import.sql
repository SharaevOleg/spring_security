INSERT INTO roles (role) VALUES ('ROLE_ADMIN');
INSERT INTO roles (role) VALUES ('ROLE_USER');
INSERT INTO users (firstName, lastName, age, email, password) VALUES ('admin', 'admin', 11, 'admin@mail.ru', 'admin');
INSERT INTO users (firstName, lastName, age, email, password) VALUES ('user', 'user', 22, 'user@mail.ru', 'user');
INSERT INTO users (firstName, lastName, age, email, password) VALUES ('user2', 'user2', 33, 'user2@mail.ru', 'user2');
INSERT INTO users_roles (users_id, roles_id) VALUES (1, 1);
INSERT INTO users_roles (users_id, roles_id) VALUES (1, 2);
INSERT INTO users_roles (users_id, roles_id) VALUES (2, 2);
INSERT INTO users_roles (users_id, roles_id) VALUES (3, 2);