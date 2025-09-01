CREATE TABLE IF NOT EXISTS loan_types (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    min_amount NUMERIC(15,2) NOT NULL,
    max_amount NUMERIC(15,2) NOT NULL,
    interest_rate NUMERIC(5,2) NOT NULL,
    automatic_validation BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS states (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS loans (
       id BIGSERIAL PRIMARY KEY,
       amount NUMERIC(15,2) NOT NULL,
       term INT NOT NULL,
       dni_user VARCHAR(50) NOT NULL,
       id_loan_type INT NOT NULL,
       id_state INT NOT NULL,
       CONSTRAINT fk_loan_type FOREIGN KEY (id_loan_type) REFERENCES loan_types(id),
       CONSTRAINT fk_state FOREIGN KEY (id_state) REFERENCES states(id)
);
