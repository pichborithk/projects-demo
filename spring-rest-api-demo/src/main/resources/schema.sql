DROP TABLE IF EXISTS users_items;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS users;

--DROP TYPE IF EXISTS role_enum;

--CREATE TYPE role_enum AS ENUM ('USER', 'ADMIN');

CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR NOT NULL DEFAULT 'USER'
);


CREATE TABLE items (
    item_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    quantity INT DEFAULT 0
);


CREATE TABLE users_items (
    "user_id" INT REFERENCES users(user_id) ON UPDATE CASCADE ON DELETE CASCADE,
    "item_id" INT REFERENCES items(item_id) ON UPDATE CASCADE ON DELETE CASCADE,
    UNIQUE ("user_id", "item_id")
);