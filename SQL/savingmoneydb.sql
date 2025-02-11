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
        balance NUMERIC NOT NULL,
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

CREATE TABLE monthly_balances
    (
        starting_balance NUMERIC NOT NULL,
        ending_balance NUMERIC NOT NULL,
        date DATE NOT NULL,
        card_number VARCHAR NOT NULL,
        
        PRIMARY KEY (date, card_number),
        
        CONSTRAINT fk_cardNumber FOREIGN KEY (card_number) REFERENCES payment_card(card_number) ON DELETE CASCADE ON UPDATE CASCADE
    );

CREATE TABLE category
    (
        id SERIAL PRIMARY KEY,
        name VARCHAR(30) NOT NULL,
        description VARCHAR(250) NOT NULL,
        creator_username VARCHAR NOT NULL,

        UNIQUE (name, creator_username),

        CONSTRAINT fk_username FOREIGN KEY (creator_username) REFERENCES "user"(username) ON DELETE CASCADE ON UPDATE CASCADE,

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

CREATE TABLE transaction_category
    (
        transaction_id INTEGER NOT NULL,
        category_id INTEGER NOT NULL,

        PRIMARY KEY (transaction_id, category_id),

        CONSTRAINT fk_transaction_id FOREIGN KEY (transaction_id) REFERENCES transaction(id) ON DELETE CASCADE ON UPDATE CASCADE,
        CONSTRAINT fk_category_id FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE ON UPDATE CASCADE
    );

/**********************************************FUNZIONI AUSILIARIE************************************************/

CREATE OR REPLACE FUNCTION report_exists(input_card_number VARCHAR, report_date DATE)
	RETURNS BOOLEAN AS $$
	DECLARE  
	    report_found BOOLEAN;
	BEGIN 
	    SELECT EXISTS (
	        SELECT 1
	        FROM transaction AS t
	        WHERE 
	            t.card_number = input_card_number AND
	            DATE_TRUNC('month', t.date) = DATE_TRUNC('month', report_date)
	    ) INTO report_found;
	
	    RETURN report_found;
	END;
	$$ LANGUAGE plpgsql;
	
CREATE OR REPLACE FUNCTION get_latest_balance(input_card_number VARCHAR, input_date DATE)
    RETURNS NUMERIC AS $$

    DECLARE  
        latest_balance NUMERIC;
        BEGIN 
        SELECT ending_balance INTO latest_balance
        FROM monthly_balances AS m
        WHERE
            m.card_number = input_card_number AND
            date < input_date
        ORDER BY date DESC
        LIMIT 1;
            
        RETURN latest_balance;
    END;
    $$ LANGUAGE plpgsql;

/********************************************** TRIGGER FUNCTIONS ************************************************/
CREATE OR REPLACE FUNCTION update_report()
	RETURNS TRIGGER AS $$
	DECLARE  
	    exist BOOLEAN;
	    balance NUMERIC;
	BEGIN 
	    exist := report_exists(NEW.card_number, NEW.date);
	
	    IF NOT exist THEN
	        balance := get_latest_balance(NEW.card_number, NEW.date);
	        INSERT INTO monthly_balances 
	        VALUES (balance, balance, DATE_TRUNC('month', NEW.date), NEW.card_number);
	    END IF;
	    
	    IF NEW.direction = 'expense' THEN
	        UPDATE monthly_balances
	        SET ending_balance = ending_balance - NEW.amount
	        WHERE card_number = NEW.card_number AND date = DATE_TRUNC('month', NEW.date);
	    ELSE
	        UPDATE monthly_balances
	        SET ending_balance = ending_balance + NEW.amount
	        WHERE card_number = NEW.card_number AND date = DATE_TRUNC('month', NEW.date);
	    END IF;
	
	    RETURN NEW;
	END;
	$$ LANGUAGE plpgsql;
/********************************************** TRIGGERS ************************************************/
CREATE TRIGGER trigger_update_report
BEFORE INSERT ON transaction
FOR EACH ROW
EXECUTE FUNCTION update_report();


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

CREATE OR REPLACE FUNCTION get_monthly_balances(input_number VARCHAR, input_date DATE)
    RETURNS TABLE(starting_balance NUMERIC, ending_balance NUMERIC) AS $$
	DECLARE 
		exist BOOLEAN;
		balance NUMERIC;
    BEGIN
        exist := report_exists(input_number, input_date);
	
	    IF NOT exist THEN
	        balance := get_latest_balance(input_number, input_date);
	        INSERT INTO monthly_balances 
	        VALUES (balance, balance, DATE_TRUNC('month', input_date), input_number);
	    END IF;

        RETURN QUERY
            SELECT m.starting_balance, m.ending_balance 
            FROM monthly_balances AS m
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

INSERT INTO payment_card (card_number, cvv, pin, expiration_date, balance, owner_username)
    VALUES
    ('1234567812345678', '123', '1234', '2029-12-01', 5000, 'alice'),
    ('8765432187654321', '456', '5678', '2029-06-01', 3000, 'boby'),
    ('1122334455667788', '789', '9101', '2029-08-01', 1000, 'charlie');

INSERT INTO monthly_balances 
    VALUES
    (5000,5000, '2024-02-01', '1234567812345678'),
    (3000,3000, '2024-02-01', '8765432187654321'),
    (1000,1000, '2024-02-01', '1122334455667788');

INSERT INTO transaction (amount, description, date, direction, card_number)
    VALUES
    (400, 'Dinner', '2025-02-08', 'expense', '1234567812345678'),
    (200, 'Grocery Shopping', '2025-02-08', 'expense', '1234567812345678'),
    (500, 'Salary Payment', '2025-02-05', 'income', '1234567812345678'),
    (700, 'won a scratch ticket', '2025-02-03', 'income', '1234567812345678'),
    (400, 'Dinner', '2025-02-08', 'expense', '1234567812345678');

INSERT INTO category (name, description, creator_username)
    VALUES
    ('Food', 'Food and grocery expenses', 'alice'),
    ('Entertainment', 'Movies, music, and games', 'boby'),
    ('Utilities', 'Bills and subscriptions', 'charlie');

INSERT INTO keyword (keyword, category_id)
    VALUES
    ('groceries', 1),
    ('movies', 2),
    ('electricity', 3);

