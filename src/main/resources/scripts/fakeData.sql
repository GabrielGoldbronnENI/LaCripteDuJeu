INSERT INTO users (first_name, last_name, email, phone, address)
VALUES
    ('alice', 'smith', 'alice.smith@example.com', '0612345678', '123 Main St'),
    ('bob', 'brown', 'bob.brown@example.com', '0612345679', '456 Elm St'),
    ('charlie', 'brown', 'charlie.brown@example.com', '0612345680', '789 Oak St'),
    ('david', 'davis', 'david.davis@example.com', '0612345681', '321 Pine St'),
    ('eve', 'taylor', 'eve.taylor@example.com', '0612345682', '654 Cedar St');

-- Table age_limit
INSERT INTO age_limit (label)
VALUES
    ('3+'),
    ('7+'),
    ('12+'),
    ('16+'),
    ('18+');

-- Table genre
INSERT INTO genre (title)
VALUES
    ('Action'),
    ('Adventure'),
    ('Puzzle'),
    ('Horror'),
    ('Racing');

-- Table products
INSERT INTO products (title, play_time, tariff, age_limit)
VALUES
    ('Game 1', '2 hours', 19.99, 1),
    ('Game 2', '3 hours', 29.99, 2),
    ('Game 3', '1.5 hours', 9.99, 3),
    ('Game 4', '2.5 hours', 24.99, 4),
    ('Game 5', '1 hour', 14.99, 5);

-- Table copy
INSERT INTO copy (barcode,status, product_id)
VALUES
    ('1515151515151',TRUE, 1),
    ('1515151515152',FALSE, 2),
    ('1515151515153',TRUE, 3),
    ('1515151515154',TRUE, 4),
    ('1515151515155',FALSE, 5);

-- Table rental_status
INSERT INTO rental_status (label)
VALUES
    ('rented'),
    ('returned');

-- Table location
INSERT INTO locations (price, user_id, rental_status_id, copy_id)
VALUES
    (5.99, 1, 1, 1),
    (7.99, 2, 2, 2),
    (6.99, 1, 1, 3),
    (8.99, 2, 1, 4),
    (4.99, 5, 2, 5);



-- Table user_location
INSERT INTO user_location (user_id, location_id)
VALUES
    (1, 1),
    (2, 2),
    (1, 3),
    (2, 4),
    (5, 5);

-- Table copy_location
INSERT INTO copy_location (copy_id, location_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5);

-- Table product_copy
INSERT INTO product_copy (product_id, copy_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5);

-- Table product_genre
INSERT INTO product_genre (product_id, genre_id)
VALUES
    (1, 1),
    (2, 2),
    (2, 3),
    (2, 4),
    (3, 3),
    (4, 1),
    (4, 4),
    (4, 5),
    (5, 5);

-- Table product_age_limit
INSERT INTO product_age_limit (product_id, age_limit_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5);