INSERT INTO vag_logs_viewer.users (email, enabled, name, password) VALUES ('admin@gmail.com', 1, 'coderslab', 'coderslab');
INSERT INTO vag_logs_viewer.users (email, enabled, name, password) VALUES ('user1@gmail.com', 1, 'user1', 'user1');

INSERT INTO vag_logs_viewer.role (name) VALUES ('ADMIN');
INSERT INTO vag_logs_viewer.role (name) VALUES ('USER');

INSERT INTO vag_logs_viewer.user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO vag_logs_viewer.user_role (user_id, role_id) VALUES (2, 2);
