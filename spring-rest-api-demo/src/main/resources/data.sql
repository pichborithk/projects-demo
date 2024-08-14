INSERT INTO users (username, password)
    VALUES ('bo', '123'), ('nick', '123'), ('will', '123'), ('david', '123');


INSERT INTO items (name, quantity)
    VALUES ('t-Shirt', 50), ('dress', 40), ('jacket', 20), ('jean', 100), ('hat', 50);


INSERT INTO users_items ("user_id", "item_id")
    VALUES (1, 1), (1, 4), (1, 5), (2, 1), (2, 5), (3, 2);