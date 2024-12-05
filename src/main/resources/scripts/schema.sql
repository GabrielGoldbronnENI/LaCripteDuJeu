DROP TABLE IF EXISTS rental_status_location CASCADE;
DROP TABLE IF EXISTS copy_location CASCADE;
DROP TABLE IF EXISTS user_location CASCADE;
DROP TABLE IF EXISTS product_age_limit CASCADE;
DROP TABLE IF EXISTS product_genre CASCADE;
DROP TABLE IF EXISTS product_copy CASCADE;

DROP TABLE IF EXISTS rental_status CASCADE;
DROP TABLE IF EXISTS location CASCADE;
DROP TABLE IF EXISTS copy CASCADE;
DROP TABLE IF EXISTS genre CASCADE;
DROP TABLE IF EXISTS products CASCADE;
DROP TABLE IF EXISTS age_limit CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE IF NOT EXISTS users
(
    user_id    SERIAL PRIMARY KEY,
    first_name VARCHAR(50)  NOT NULL,
    last_name  VARCHAR(50)  NOT NULL,
    email      VARCHAR(255) NOT NULL UNIQUE,
    phone      VARCHAR(10)  NOT NULL UNIQUE,
    address    VARCHAR(255),
    createdAt  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS age_limit
(
    age_limit_id SERIAL PRIMARY KEY,
    label        VARCHAR(100) NOT NULL UNIQUE,
    createdAt    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS products
(
    product_id SERIAL PRIMARY KEY,
    title      VARCHAR(255)   NOT NULL,
    play_time  VARCHAR(255)   NOT NULL,
    tariff     DECIMAL(10, 2) NOT NULL,
    createdAt  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    age_limit  INT            NOT NULL,
    CONSTRAINT fk_product_age_limit FOREIGN KEY (age_limit) REFERENCES age_limit (age_limit_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS genre
(
    genre_id  SERIAL PRIMARY KEY,
    title     VARCHAR(255) NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS copy
(
    copy_id    SERIAL PRIMARY KEY,
    status     bool NOT NULL,
    createdAt  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    product_id INT  NOT NULL,
    CONSTRAINT fk_copy_product FOREIGN KEY (product_id) REFERENCES products (product_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS location
(
    location_id SERIAL PRIMARY KEY,
    price       DECIMAL(10, 2) NOT NULL,
    status      int            NOT NULL,
    createdAt   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    product_id  INT            NOT NULL,
    user_id     INT            NOT NULL,
    CONSTRAINT fk_location_product FOREIGN KEY (product_id) REFERENCES products (product_id) ON DELETE CASCADE,
    CONSTRAINT fk_location_user FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS rental_status
(
    rental_status_id SERIAL PRIMARY KEY,
    label            VARCHAR(255) NOT NULL,
    createdAt        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt        TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS user_location
(
    user_id     INT NOT NULL,
    location_id INT NOT NULL,
    PRIMARY KEY (user_id, location_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
    CONSTRAINT fk_location FOREIGN KEY (location_id) REFERENCES location (location_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS copy_location
(
    copy_id     INT NOT NULL,
    location_id INT NOT NULL,
    PRIMARY KEY (copy_id, location_id),
    CONSTRAINT fk_copy FOREIGN KEY (copy_id) REFERENCES copy (copy_id) ON DELETE CASCADE,
    CONSTRAINT fk_location FOREIGN KEY (location_id) REFERENCES location (location_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS rental_status_location
(
    rental_status_id INT NOT NULL,
    location_id      INT NOT NULL,
    PRIMARY KEY (rental_status_id, location_id),
    CONSTRAINT fk_copy FOREIGN KEY (rental_status_id) REFERENCES rental_status (rental_status_id) ON DELETE CASCADE,
    CONSTRAINT fk_location FOREIGN KEY (location_id) REFERENCES location (location_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS product_copy
(
    product_id INT NOT NULL,
    copy_id    INT NOT NULL,
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES products (product_id) ON DELETE CASCADE,
    CONSTRAINT fk_copy FOREIGN KEY (copy_id) REFERENCES copy (copy_id) ON DELETE CASCADE,
    PRIMARY KEY (product_id, copy_id)
);

CREATE TABLE IF NOT EXISTS product_genre
(
    product_id INT NOT NULL,
    genre_id   INT NOT NULL,
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES products (product_id) ON DELETE CASCADE,
    CONSTRAINT fk_genre FOREIGN KEY (genre_id) REFERENCES genre (genre_id) ON DELETE CASCADE,
    PRIMARY KEY (product_id, genre_id)
);

CREATE TABLE IF NOT EXISTS product_age_limit
(
    product_id   INT NOT NULL,
    age_limit_id INT NOT NULL,
    PRIMARY KEY (product_id, age_limit_id),
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES products (product_id) ON DELETE CASCADE,
    CONSTRAINT fk_age_limit FOREIGN KEY (age_limit_id) REFERENCES age_limit (age_limit_id) ON DELETE CASCADE
);
