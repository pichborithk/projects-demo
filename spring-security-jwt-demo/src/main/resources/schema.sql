DROP TABLE IF EXISTS users;

DROP TYPE IF EXISTS role_enum;

--CREATE TYPE role_enum AS ENUM ('USER', 'EMPLOYEE', 'ADMIN');

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    role VARCHAR NOT NULL DEFAULT 'USER'
);

--CREATE TABLE users (
--    id SERIAL PRIMARY KEY,
--    email VARCHAR(255) NOT NULL UNIQUE,
--    first_name VARCHAR(255),
--    last_name VARCHAR(255),
--    password VARCHAR(255) NOT NULL,
--    role role_enum NOT NULL DEFAULT 'USER'
--);