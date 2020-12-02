INSERT INTO vag_logs_viewer.users (email, enabled, first_name, last_name, name, password) VALUES ('admin@gmail.com', 1, 'Admin', 'admin', 'admin', '$2y$12$tTjLBtK3dPvKT3wfoehaoe8/kWhS/CSXbvhVHagenBDAA7SHIPWwW');
INSERT INTO vag_logs_viewer.users (email, enabled, first_name, last_name, name, password) VALUES ('user1@gmail.com', 1, 'John', 'Kovalsky', 'user1' ,'$2y$12$XbQAparH.ENMWB17EdTN2OHJ4H5FaZFmSEEnGfVTlwh1Fvi90m4jG');

INSERT INTO vag_logs_viewer.role (name) VALUES ('ADMIN');
INSERT INTO vag_logs_viewer.role (name) VALUES ('USER');

INSERT INTO vag_logs_viewer.user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO vag_logs_viewer.user_role (user_id, role_id) VALUES (2, 2);

INSERT INTO vag_logs_viewer.cars (brand, engine_capacity, engine_code, engine_type,
mileage, model, model_type, production_year, user_id) VALUES ('Audi', 2700, 'BPP', 'Diesel', '210000', 'A6', 'C6', 2008, 2);

INSERT INTO vag_logs_viewer.cars (brand, engine_capacity, engine_code, engine_type,
                                  mileage, model, model_type, production_year, user_id) VALUES ('VW', 3200, 'CBRA', 'Benzin', '150000', 'Golf', 'V', 2007, 2);

INSERT INTO vag_logs_viewer.cars (brand, engine_capacity, engine_code, engine_type,
                                  mileage, model, model_type, production_year, user_id) VALUES ('Audi', 2700, 'BPP', 'Diesel', '210000', 'A6', 'C6', 2008, 1);

INSERT INTO vag_logs_viewer.cars (brand, engine_capacity, engine_code, engine_type,
                                  mileage, model, model_type, production_year, user_id) VALUES ('VW', 3200, 'CBRA', 'Benzin', '150000', 'Golf', 'V', 2007, 1);