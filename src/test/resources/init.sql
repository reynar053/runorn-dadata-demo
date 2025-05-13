CREATE SCHEMA kzn;

CREATE TABLE kzn.users (
    id INT GENERATED ALWAYS AS IDENTITY,
    login VARCHAR(255) NOT NULL UNIQUE,
    created_at DATE NOT NULL,
    CONSTRAINT bogdan PRIMARY KEY(id)
);

CREATE TABLE kzn.addresses (
    id INT GENERATED ALWAYS AS IDENTITY,
    user_id INT NOT NULL,
    source VARCHAR(255),
    country VARCHAR(100),
    postal_code VARCHAR(20),
    region VARCHAR(100),
    region_type VARCHAR(50),
    qc VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT bog PRIMARY KEY(id)
--     CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES kzn.users(id) ON DELETE CASCADE
);