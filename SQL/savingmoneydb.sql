/***************************************************** ENUMS *************************************************************/
CREATE TYPE direction AS enum('INCOME','EXPENSE');

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
        id NUMERIC PRIMARY KEY,
        amount NUMERIC NOT NULL,
        description VARCHAR(250) NOT NULL,
        date DATE NOT NULL,
        direction direction NOT NULL,
        card_number VARCHAR NOT NULL,
        counter_part VARCHAR NOT NULL,

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

CREATE OR REPLACE FUNCTION balance_saved(input_card_number VARCHAR, report_date DATE)
	RETURNS BOOLEAN AS $$
	DECLARE  
	    report_found NUMERIC;
	BEGIN 
	    SELECT COUNT(*) INTO report_found
        FROM monthly_balances AS m
        WHERE
            m.card_number = input_card_number AND
            date = DATE_TRUNC('month', report_date);

        IF report_found = 0 THEN
            RETURN FALSE;
        ELSE
            RETURN TRUE;
        END IF;
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

CREATE OR REPLACE FUNCTION update_monthly_balances()
	RETURNS TRIGGER AS $$
	DECLARE  
	    exist BOOLEAN;
	    balance NUMERIC;
	BEGIN 
	    exist := balance_saved(NEW.card_number, NEW.date);
	
	    IF NOT exist THEN
	        balance := get_latest_balance(NEW.card_number, NEW.date);
	        INSERT INTO monthly_balances 
	        VALUES (balance, balance, DATE_TRUNC('month', NEW.date), NEW.card_number);
	    END IF;
	    
	    IF NEW.direction = 'EXPENSE' THEN
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

CREATE TRIGGER trigger_update_monthly_balances
BEFORE INSERT ON transaction
FOR EACH ROW
EXECUTE FUNCTION update_monthly_balances();

/***************************************************** DAO FUNCTIONS *************************************************************/

CREATE OR REPLACE FUNCTION get_monthly_expense_details(input_number VARCHAR, input_date DATE)
    RETURNS TABLE(max_expense NUMERIC, min_expense NUMERIC, avg_expense NUMERIC, total_expense NUMERIC) AS $$
    BEGIN
        RETURN QUERY
            SELECT MAX(amount), MIN(amount), AVG(amount), SUM(amount)
            FROM transaction AS t
            WHERE 
                t.card_number = input_number AND
                DATE_TRUNC('month', t.date) = DATE_TRUNC('month', input_date) AND 
                t.direction = 'EXPENSE';
    END;
    $$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_monthly_income_details(input_number VARCHAR, input_date DATE)
    RETURNS TABLE(max_income NUMERIC, min_income NUMERIC, avg_income NUMERIC, total_income NUMERIC) AS $$
    BEGIN
        RETURN QUERY
            SELECT MAX(amount), MIN(amount), AVG(amount), SUM(amount)
            FROM transaction AS t
            WHERE 
                t.card_number = input_number AND
                DATE_TRUNC('month', t.date) = DATE_TRUNC('month', input_date) AND 
                t.direction = 'INCOME';
    END;
    $$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_monthly_balances(input_number VARCHAR, input_date DATE)
    RETURNS TABLE(starting_balance NUMERIC, ending_balance NUMERIC) AS $$
	DECLARE 
		exist BOOLEAN;
		balance NUMERIC;
    BEGIN
        exist := balance_saved(input_number, input_date);
	
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
    ('frankie', 'Password123', 1),
    ('boby', 'Password456', 1),
    ('charlie', 'Password789', 2);

INSERT INTO payment_card (card_number, cvv, pin, expiration_date, balance, owner_username)
    VALUES
    ('1234567812345678', '123', '1234', '2029-12-01', 20000, 'alice'),
    ('8765432187654321', '456', '5678', '2029-06-01', 9000, 'boby'),
    ('1122334455667788', '789', '9101', '2029-08-01', 1000, 'charlie'),
    ('3498734875349033', '123', '1234', '2029-12-01', 3000, 'alice'),
    ('3498734875349456', '456', '5678', '2029-06-01', 3000, 'frankie');

INSERT INTO monthly_balances 
    VALUES
    (20000, 20000, '2024-02-01', '1234567812345678'),
    (9000, 9000, '2024-02-01', '8765432187654321'),
    (1000, 1000, '2024-02-01', '1122334455667788'),
    (3000, 3000, '2024-02-01', '3498734875349033'),
    (3000, 3000, '2024-02-01', '3498734875349456');

INSERT INTO category (name, description, creator_username)
    VALUES
    ('Food', 'Food and grocery expenses', 'alice'),
    ('Income', 'Salary, freelance, and other earnings', 'alice'),
    ('Health', 'Medical and health-related expenses', 'alice'),
    ('Travel', 'Travel and vacation expenses', 'alice'),
    ('Electronics', 'Gadgets and electronic devices', 'alice'),
    
    ('Entertainment', 'Movies, music, and games', 'boby'),
    ('Shopping', 'Retail and online purchases', 'boby'),

    ('Utilities', 'Bills and subscriptions', 'charlie'),
    ('Housing', 'Rent and home-related expenses', 'charlie'),

    ('Transport', 'Expenses related to transportation', 'frankie'),

    ('Other', '', 'alice'),
    ('Other', '', 'boby'),
    ('Other', '', 'charlie'),
    ('Other', '', 'frankie');
    
INSERT INTO keyword (keyword, category_id)
    VALUES
    ('groceries', 1),      -- for Food (creator: alice)
    ('food', 1),
    ('supermarket', 1),
    ('restaurant', 1),
    ('diner', 1),
    ('coffee', 1),
    ('snacks', 1),

    ('salary', 2),        -- for Income (creator: alice)
    ('freelance', 2),
    ('bonus', 2),

    ('health', 3),        -- for Health (creator: alice)
    ('medical', 3),
    ('doctor', 3),
    ('hospital', 3),
    ('pharmacy', 3),

    ('travel', 4),        -- for Travel (creator: alice)
    ('vacation', 4),
    ('flight', 4),
    ('hotel', 4),
    ('tour', 4),

    ('gadgets', 5),       -- for Electronics (creator: alice)
    ('devices', 5),
    ('laptop', 5),
    ('phone', 5),
    ('tablet', 5),

    ('movies', 6),        -- for Entertainment (creator: boby)
    ('music', 6),
    ('games', 6),
    ('concert', 6),
    ('show', 6),

    ('shopping', 7),      -- for Shopping (creator: boby)
    ('clothes', 7),
    ('shoes', 7),
    ('accessories', 7),
    ('fashion', 7),

    ('bills', 8),         -- for Utilities (creator: charlie)
    ('subscriptions', 8),
    ('internet', 8),
    ('water', 8),
    ('gas', 8),

    ('rent', 9),          -- for Housing (creator: charlie)
    ('mortgage', 9),
    ('maintenance', 9),
    ('furniture', 9),
    ('appliances', 9),

    ('transport', 10),    -- for Transport (creator: frankie)
    ('bus', 10),
    ('train', 10),
    ('taxi', 10),
    ('fuel', 10),

