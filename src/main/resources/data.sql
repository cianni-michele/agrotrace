INSERT INTO users (id, username, name, password, user_type)
VALUES (0, 'curator', 'curator', '{noop}password', 'curator'),
       (1, 'transformer', 'transformer', '{noop}password', 'transformer'),
       (2, 'producer', 'producer', '{noop}password', 'producer'),
       (3, 'distributor', 'distributor', '{noop}password', 'distributor');

INSERT INTO processes (id, title, description, validation_status, author_id)
VALUES ('00000000-0000-0000-0000-000000000001', 'Process One', 'Description for Process One', 'PENDING', 1);

INSERT INTO products (id, title, description, validation_status, author_id)
VALUES ('00000000-0000-0000-0000-000000000002', 'Product One', 'Description for Product One', 'PENDING', 2);

INSERT INTO bundles (id, title, description, validation_status, author_id)
VALUES ('00000000-0000-0000-0000-000000000003', 'Bundle One', 'Description for Bundle One', 'PENDING', 3);
