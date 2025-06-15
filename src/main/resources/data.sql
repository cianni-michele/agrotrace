INSERT INTO users (id, username, name, password, user_type)
VALUES (0, 'curator', 'curator', '{noop}password', 'curator'),
       (1, 'transformer', 'transformer', '{noop}password', 'transformer'),
       (2, 'producer', 'producer', '{noop}password', 'producer'),
       (3, 'distributor', 'distributor', '{noop}password', 'distributor');

INSERT INTO processes (id, title, description, validation_status, author_id)
VALUES ('00000000-0000-0000-0000-000000000001', 'Process One', 'Description for Process One', 'PENDING', 1);

INSERT INTO products (id, title, description, validation_status, author_id, price, quantity)
VALUES ('00000000-0000-0000-0000-000000000002', 'Product One', 'Description for Product One', 'PENDING', 2, 29.99, 100);

INSERT INTO images (id, product_id, path, description)
VALUES ('00000000-0000-0000-0000-000000000010', '00000000-0000-0000-0000-000000000002', '/images/product1.jpg', 'Immagine principale prodotto'),
       ('00000000-0000-0000-0000-000000000011', '00000000-0000-0000-0000-000000000002', '/images/product1_2.jpg', 'Immagine secondaria prodotto');

INSERT INTO certifications (id, product_id, type, path)
VALUES ('00000000-0000-0000-0000-000000000020', '00000000-0000-0000-0000-000000000002', 'BIO', '/certifications/bio_cert.pdf'),
       ('00000000-0000-0000-0000-000000000021', '00000000-0000-0000-0000-000000000002', 'DOP', '/certifications/dop_cert.pdf');

INSERT INTO bundles (id, title, description, validation_status, author_id)
VALUES ('00000000-0000-0000-0000-000000000003', 'Bundle One', 'Description for Bundle One', 'PENDING', 3);