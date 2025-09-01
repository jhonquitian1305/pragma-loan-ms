INSERT INTO states (name, description) VALUES ('Pendiente de Revisión', 'El préstamo fue creado y está en espera de revisión.');
INSERT INTO states (name, description) VALUES ('Aprobado', 'El préstamo fue aprobado.');
INSERT INTO states (name, description) VALUES ('Rechazado', 'El préstamo fue rechazado.');

INSERT INTO loan_types (name, min_amount, max_amount, interest_rate, automatic_validation) VALUES
       ('Préstamo Personal', 500.00, 20000.00, 12.5, true),
       ('Préstamo Hipotecario', 10000.00, 200000.00, 8.0, false),
       ('Préstamo Vehicular', 5000.00, 80000.00, 10.0, false);