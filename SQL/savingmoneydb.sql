/***************************************************** ENUMS *************************************************************/

CREATE TYPE direction AS enum('income','expense');

/***************************************************** TABLES *************************************************************/

CREATE TABLE family
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,

    CONSTRAINT valid_name CHECK
    (
        LENGTH(name) >= 1 AND
        name ~ '[A-Za-z]'
    )
);

CREATE TABLE "user"
(
    username VARCHAR(30) PRIMARY KEY,
    password VARCHAR(30) NOT NULL,
    family_id INTEGER NOT NULL,

    CONSTRAINT fk_family_id FOREIGN KEY (family_id) REFERENCES family(id) ON DELETE CASCADE ON UPDATE CASCADE,

    CONSTRAINT valid_username CHECK
    (
        LENGTH(username) >= 4 AND
        username ~ '[A-Za-z]'
    ),

    CONSTRAINT valid_password CHECK
    (    
        LENGTH(password) >= 8 AND
        password ~ '[A-Z]' AND
        password ~ '[a-z]' AND
        password ~ '[0-9]'
    )
);

CREATE TABLE payment_card
(
    card_number VARCHAR PRIMARY KEY,
    cvv VARCHAR(4) NOT NULL, 
    pin VARCHAR(6) NOT NULL, 
    expiration_date DATE NOT NULL,
    amount NUMERIC NOT NULL,
    owner_username VARCHAR NOT NULL,

    CONSTRAINT fk_username FOREIGN KEY (owner_username) REFERENCES "user"(username) ON DELETE CASCADE ON UPDATE CASCADE,

    CONSTRAINT valid_card_number CHECK (card_number ~ '^\d{13,19}$'),

    CONSTRAINT valid_cvv CHECK (cvv ~ '^\d{3,4}$'),

    CONSTRAINT valid_pin CHECK (pin ~ '^\d{4,6}$')
);

CREATE TABLE transaction
(
    id SERIAL PRIMARY KEY,
    amount NUMERIC NOT NULL,
    description VARCHAR(250) NOT NULL,
    date DATE NOT NULL,
    direction direction NOT NULL,
    card_number VARCHAR NOT NULL,

    CONSTRAINT fk_cardNumber FOREIGN KEY (card_number) REFERENCES payment_card(card_number) ON DELETE CASCADE ON UPDATE CASCADE,

    CONSTRAINT positive_amount CHECK (amount >= 0)
);

CREATE TABLE monthly_amount
(
    starting_amount NUMERIC NOT NULL,
    date DATE NOT NULL,
    card_number VARCHAR NOT NULL,
    
    CONSTRAINT fk_cardNumber FOREIGN KEY (card_number) REFERENCES payment_card(card_number) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE category
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    description VARCHAR(250) NOT NULL,
    owner_username VARCHAR NOT NULL,

    CONSTRAINT fk_username FOREIGN KEY (owner_username) REFERENCES "user"(username) ON DELETE CASCADE ON UPDATE CASCADE,

    CONSTRAINT valid_name CHECK
    (
        LENGTH(name) >= 3 AND
        name ~ '[A-Za-z]'
    )
);

CREATE TABLE keyword
(
    keyword VARCHAR(30) NOT NULL,
    category_id INTEGER NOT NULL,

    PRIMARY KEY (keyword, category_id),

    CONSTRAINT fk_category_id FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE ON UPDATE CASCADE,

    CONSTRAINT valid_keyword CHECK
    (
        LENGTH(keyword) >= 1 AND
        keyword ~ '[A-Za-z]'
    )
);
--missing bridge table
/***************************************************** DAO FUNCTIONS *************************************************************/


CREATE OR REPLACE FUNCTION get_monthly_expense_details(input_number VARCHAR, input_date DATE)
    RETURNS TABLE(max_expense NUMERIC, min_expense NUMERIC, avg_expense NUMERIC) AS $$
    BEGIN
        RETURN QUERY
            SELECT MAX(amount), MIN(amount), AVG(amount)
            FROM transaction AS t
            WHERE 
                t.card_number = input_number AND
                DATE_TRUNC('month', t.date) = DATE_TRUNC('month', input_date) AND 
                t.direction = 'expense';
    END;
    $$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION get_monthly_income_details(input_number VARCHAR, input_date DATE)
    RETURNS TABLE(max_income NUMERIC, min_income NUMERIC, avg_income NUMERIC) AS $$
    BEGIN
        RETURN QUERY
            SELECT MAX(amount), MIN(amount), AVG(amount)
            FROM transaction AS t
            WHERE 
                t.card_number = input_number AND
                DATE_TRUNC('month', t.date) = DATE_TRUNC('month', input_date) AND 
                t.direction = 'income';
    END;
    $$ LANGUAGE plpgsql;

--doesn't work (yet)
CREATE OR REPLACE FUNCTION get_monthly_amounts(input_number VARCHAR, input_date DATE)
    RETURNS TABLE(starting_amount NUMERIC, ending_amount NUMERIC) AS $$
    BEGIN
        RETURN QUERY WITH
            total_expense AS
            (
                SELECT SUM(amount)
                FROM transaction AS t
                WHERE 
                    t.card_number = input_number AND
                    DATE_TRUNC('month', t.date) = DATE_TRUNC('month', input_date) AND 
                    t.direction = 'expense'
            ),

            total_income AS
            (
                SELECT SUM(amount)
                FROM transaction AS t
                WHERE 
                    t.card_number = input_number AND
                    DATE_TRUNC('month', t.date) = DATE_TRUNC('month', input_date) AND 
                    t.direction = 'income'
            )

            SELECT m.starting_amount, m.starting_amount + total_income - total_expense
            FROM monthly_amount AS m
            WHERE 
                m.card_number = input_number AND
                DATE_TRUNC('month', m.date) = DATE_TRUNC('month', input_date);
    END;
    $$ LANGUAGE plpgsql;

/********************************************** POPULATION ************************************************/


INSERT INTO family (name)
VALUES
    ('Smith'),
    ('Johnson'),
    ('Brown');


INSERT INTO "user" (username, password, family_id)
VALUES
    ('alice', 'Password123', 1),
    ('boby', 'Password456', 2),
    ('charlie', 'Password789', 3);



INSERT INTO payment_card (card_number, cvv, pin, expiration_date, amount, owner_username)
VALUES
    ('1234567812345678', '123', '1234', '2029-12-01', 5000, 'alice'),
    ('8765432187654321', '456', '5678', '2029-06-01', 3000, 'boby'),
    ('1122334455667788', '789', '9101', '2029-08-01', 1000, 'charlie');



INSERT INTO transaction (amount, description, date, direction, card_number)
VALUES
    (200, 'Grocery Shopping', '2025-02-08', 'expense', '1234567812345678'),
    (500, 'Salary Payment', '2025-02-05', 'income', '1234567812345678'),
    (700, 'I scammed someone', '2025-02-03', 'income', '1234567812345678'),
    (400, 'Dinner', '2025-02-08', 'expense', '1234567812345678'),
    (150, 'I got scammed', '2025-01-01', 'expense', '1234567812345678');



INSERT INTO monthly_amount (starting_amount, date, card_number)
VALUES
    (5000, '2025-02-01', '1234567812345678'),
    (3000, '2025-02-01', '8765432187654321'),
    (1000, '2025-02-01', '1122334455667788');



INSERT INTO category (name, description, owner_username)
VALUES
    ('Food', 'Food and grocery expenses', 'alice'),
    ('Entertainment', 'Movies, music, and games', 'boby'),
    ('Utilities', 'Bills and subscriptions', 'charlie');


INSERT INTO keyword (keyword, category_id)
VALUES
    ('groceries', 1),
    ('movies', 2),
    ('electricity', 3);