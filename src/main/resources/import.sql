/* Populate tables */
INSERT INTO customers (name, lastname, email, create_at, photo) VALUES('Andres', 'Guzman', 'profesor@bolsadeideas.com', '2017-08-01', '');
INSERT INTO customers (name, lastname, email, create_at, photo) VALUES('John', 'Doe', 'john.doe@gmail.com', '2017-08-02', '');
INSERT INTO customers (name, lastname, email, create_at, photo) VALUES('Linus', 'Torvalds', 'linus.torvalds@gmail.com', '2017-08-03', '');
INSERT INTO customers (name, lastname, email, create_at, photo) VALUES('Jane', 'Doe', 'jane.doe@gmail.com', '2017-08-04', '');
INSERT INTO customers (name, lastname, email, create_at, photo) VALUES('Rasmus', 'Lerdorf', 'rasmus.lerdorf@gmail.com', '2017-08-05', '');
INSERT INTO customers (name, lastname, email, create_at, photo) VALUES('Erich', 'Gamma', 'erich.gamma@gmail.com', '2017-08-06', '');
INSERT INTO customers (name, lastname, email, create_at, photo) VALUES('Richard', 'Helm', 'richard.helm@gmail.com', '2017-08-07', '');
INSERT INTO customers (name, lastname, email, create_at, photo) VALUES('Ralph', 'Johnson', 'ralph.johnson@gmail.com', '2017-08-08', '');
INSERT INTO customers (name, lastname, email, create_at, photo) VALUES('John', 'Vlissides', 'john.vlissides@gmail.com', '2017-08-09', '');
INSERT INTO customers (name, lastname, email, create_at, photo) VALUES('James', 'Gosling', 'james.gosling@gmail.com', '2017-08-10', '');
INSERT INTO customers (name, lastname, email, create_at, photo) VALUES('Bruce', 'Lee', 'bruce.lee@gmail.com', '2017-08-11', '');
INSERT INTO customers (name, lastname, email, create_at, photo) VALUES('Johnny', 'Doe', 'johnny.doe@gmail.com', '2017-08-12', '');
INSERT INTO customers (name, lastname, email, create_at, photo) VALUES('John', 'Roe', 'john.roe@gmail.com', '2017-08-13', '');
INSERT INTO customers (name, lastname, email, create_at, photo) VALUES('Jane', 'Roe', 'jane.roe@gmail.com', '2017-08-14', '');
INSERT INTO customers (name, lastname, email, create_at, photo) VALUES('Richard', 'Doe', 'richard.doe@gmail.com', '2017-08-15', '');
INSERT INTO customers (name, lastname, email, create_at, photo) VALUES('Janie', 'Doe', 'janie.doe@gmail.com', '2017-08-16', '');
INSERT INTO customers (name, lastname, email, create_at, photo) VALUES('Phillip', 'Webb', 'phillip.webb@gmail.com', '2017-08-17', '');
INSERT INTO customers (name, lastname, email, create_at, photo) VALUES('Stephane', 'Nicoll', 'stephane.ni, ''coll@gmail.com', '2017-08-18','');
INSERT INTO customers (name, lastname, email, create_at, photo) VALUES('Sam', 'Brannen', 'sam.brannen@gmail.com', '2017-08-19', '');  
INSERT INTO customers (name, lastname, email, create_at, photo) VALUES('Juergen', 'Hoeller', 'juergen.Hoe, ''ller@gmail.com', '2017-08-20', ''); 
INSERT INTO customers (name, lastname, email, create_at, photo) VALUES('Janie', 'Roe', 'janie.roe@gmail.com', '2017-08-21', '');
INSERT INTO customers (name, lastname, email, create_at, photo) VALUES('John', 'Smith', 'john.smith@gmail.com', '2017-08-22', '');
INSERT INTO customers (name, lastname, email, create_at, photo) VALUES('Joe', 'Bloggs', 'joe.bloggs@gmail.com', '2017-08-23', '');
INSERT INTO customers (name, lastname, email, create_at, photo) VALUES('John', 'Stiles', 'john.stiles@gmail.com', '2017-08-24', '');
INSERT INTO customers (name, lastname, email, create_at, photo) VALUES('Richard', 'Roe', 'stiles.roe@gmail.com', '2017-08-25', '');

INSERT INTO products (name, price, create_at) VALUES('Panasonic Pantalla LCD', 259990, NOW());
INSERT INTO products (name, price, create_at) VALUES('Sony Camara digital DSC-W320B', 123490, NOW());
INSERT INTO products (name, price, create_at) VALUES('Apple iPod shuffle', 1499990, NOW());
INSERT INTO products (name, price, create_at) VALUES('Sony Notebook Z110', 37990, NOW());
INSERT INTO products (name, price, create_at) VALUES('Hewlett Packard Multifuncional F2280', 69990, NOW());
INSERT INTO products (name, price, create_at) VALUES('Bianchi Bicicleta Aro 26', 69990, NOW());
INSERT INTO products (name, price, create_at) VALUES('Mica Comoda 5 Cajones', 299990, NOW());


INSERT INTO bills (description, observation, customer_id, create_at) VALUES ('Example bill', null, 1, NOW());
INSERT INTO bill_items (quantity, bill_id, product_id) VALUES (1, 1, 1);
INSERT INTO bill_items (quantity, bill_id, product_id) VALUES (2, 1, 4);
INSERT INTO bill_items (quantity, bill_id, product_id) VALUES (1, 1, 5);
INSERT INTO bill_items (quantity, bill_id, product_id) VALUES (1, 1, 7);

INSERT INTO bills (description, observation, customer_id, create_at) VALUES ('Example bill 2', 'some important note', 1, NOW());
INSERT INTO bill_items (quantity, bill_id, product_id) VALUES (1, 2, 6);

INSERT INTO users (username, password, enabled) VALUES ('dasetova', '$2a$10$EaGWMEj5CIOmDPPj66kzJODskyq/WWzqz2ZaZ3DNu9S5sBN46QzKi', 1);
INSERT INTO users (username, password, enabled) VALUES ('admin', '$2a$10$cDc4JJ/Oapr37pbQTac6XOPSx5SO4NsCF8m3P9zu14TdHmVItxpsi', 1);

INSERT INTO authorities (user_id, authority) VALUES (1, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (2, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (2, 'ROLE_ADMIN');
