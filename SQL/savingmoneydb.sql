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
        name VARCHAR(30) NOT NULL,
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
        ),

        CONSTRAINT valid_name CHECK
        (
            LENGTH(name) >= 1 AND
            name ~ '[A-Za-z]'
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
        description VARCHAR(70) NOT NULL,
        date DATE NOT NULL,
        direction direction NOT NULL,
        card_number VARCHAR NOT NULL,

        CONSTRAINT fk_cardNumber FOREIGN KEY (card_number) REFERENCES payment_card(card_number) ON DELETE CASCADE ON UPDATE CASCADE,

        CONSTRAINT positive_amount CHECK (amount >= 0)
    );

CREATE TABLE monthly_balance
    (
        initial_balance NUMERIC NOT NULL,
        final_balance NUMERIC NOT NULL,
        date DATE NOT NULL,
        card_number VARCHAR NOT NULL,
        
        PRIMARY KEY (date, card_number),
        
        CONSTRAINT fk_cardNumber FOREIGN KEY (card_number) REFERENCES payment_card(card_number) ON DELETE CASCADE ON UPDATE CASCADE
    );

CREATE TABLE category
    (
        id SERIAL PRIMARY KEY,
        name VARCHAR(30) NOT NULL,
        creator_username VARCHAR NOT NULL,

        UNIQUE (name, creator_username),

        CONSTRAINT fk_username FOREIGN KEY (creator_username) REFERENCES "user"(username) ON DELETE CASCADE ON UPDATE CASCADE,

        CONSTRAINT valid_name CHECK
        (
            LENGTH(name) >= 1 AND
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

CREATE OR REPLACE FUNCTION exists_balance(input_card_number VARCHAR, report_date DATE)
	RETURNS BOOLEAN AS $$
	DECLARE  
	    report_found NUMERIC;
	BEGIN 
	    SELECT COUNT(*) INTO report_found
        FROM monthly_balance AS m
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
        SELECT final_balance INTO latest_balance
        FROM monthly_balance AS m
        WHERE
            m.card_number = input_card_number AND
            date < input_date
        ORDER BY date DESC
        LIMIT 1;
            
        RETURN latest_balance;
    END;
    $$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION is_date_valid(input_card_number VARCHAR, input_date DATE)
    RETURNS BOOLEAN AS $$
    DECLARE
        card_registration_date DATE;
    BEGIN
        SELECT MIN(date) INTO card_registration_date 
        FROM monthly_balance AS m
        WHERE m.card_number = input_card_number;

        RETURN DATE_TRUNC('month', input_date) >= DATE_TRUNC('month', card_registration_date) AND DATE_TRUNC('month', input_date) <= DATE_TRUNC('month', CURRENT_DATE);
    END;
    $$ LANGUAGE plpgsql;
/********************************************** TRIGGER FUNCTIONS ************************************************/

CREATE OR REPLACE FUNCTION update_monthly_balance()
	RETURNS TRIGGER AS $$
	DECLARE  
	    exist BOOLEAN;
	    balance NUMERIC;
	BEGIN 
	    exist := exists_balance(NEW.card_number, NEW.date);
	
	    IF NOT exist THEN
	        balance := get_latest_balance(NEW.card_number, NEW.date);
	        INSERT INTO monthly_balance 
	        VALUES (balance, balance, DATE_TRUNC('month', NEW.date), NEW.card_number);
	    END IF;
	    
	    IF NEW.direction = 'EXPENSE' THEN
	        UPDATE monthly_balance
	        SET final_balance = final_balance - NEW.amount
	        WHERE card_number = NEW.card_number AND date = DATE_TRUNC('month', NEW.date);
	    ELSE
	        UPDATE monthly_balance
	        SET final_balance = final_balance + NEW.amount
	        WHERE card_number = NEW.card_number AND date = DATE_TRUNC('month', NEW.date);
	    END IF;
	
	    RETURN NEW;
	END;
	$$ LANGUAGE plpgsql;

/********************************************** TRIGGERS ************************************************/

CREATE TRIGGER trigger_update_monthly_balance
BEFORE INSERT ON transaction
FOR EACH ROW
EXECUTE FUNCTION update_monthly_balance();

/***************************************************** DAO FUNCTIONS *************************************************************/

CREATE OR REPLACE FUNCTION get_card_monthly_expense_details(input_number VARCHAR, input_date DATE)
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

CREATE OR REPLACE FUNCTION get_card_monthly_income_details(input_number VARCHAR, input_date DATE)
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

CREATE OR REPLACE FUNCTION get_card_monthly_balance(input_number VARCHAR, input_date DATE)
    RETURNS TABLE(initial_balance NUMERIC, final_balance NUMERIC) AS $$
	DECLARE 
		exist BOOLEAN;
		balance NUMERIC;
    BEGIN
        exist := exists_balance(input_number, input_date);
	
	    IF NOT exist THEN
	        balance := get_latest_balance(input_number, input_date);
	        INSERT INTO monthly_balance 
	        VALUES (balance, balance, DATE_TRUNC('month', input_date), input_number);
	    END IF;

        RETURN QUERY
            SELECT m.initial_balance, m.final_balance 
            FROM monthly_balance AS m
            WHERE 
                m.card_number = input_number AND
                DATE_TRUNC('month', m.date) = DATE_TRUNC('month', input_date);
    END;
    $$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION get_family_monthly_income(input_family_id INTEGER, input_date DATE)
    RETURNS NUMERIC AS $$
    DECLARE
        result NUMERIC;
    BEGIN
        SELECT SUM(amount)
        INTO result
        FROM transaction AS t
        WHERE t.card_number IN 
            (
                SELECT card_number 
                FROM payment_card
                WHERE owner_username IN 
                    (
                        SELECT username 
                        FROM "user"
                        WHERE family_id = input_family_id
                    )
            )
        AND direction = 'INCOME'
        AND DATE_TRUNC('month', t.date) = DATE_TRUNC('month', input_date);

        RETURN COALESCE(result, 0.00);
    END;
    $$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_family_monthly_expense(input_family_id INTEGER, input_date DATE)
    RETURNS NUMERIC AS $$
    DECLARE
        result NUMERIC;
    BEGIN
        SELECT SUM(amount)
        INTO result
        FROM transaction AS t
        WHERE t.card_number IN 
            (
                SELECT card_number 
                FROM payment_card
                WHERE owner_username IN 
                    (
                        SELECT username 
                        FROM "user"
                        WHERE family_id = input_family_id
                    )
            )
        AND direction = 'EXPENSE'
        AND DATE_TRUNC('month', t.date) = DATE_TRUNC('month', input_date);

        RETURN COALESCE(result, 0.00);
    END;
    $$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_family_monthly_balance(input_family_id INTEGER, input_date DATE)
    RETURNS TABLE(initial_balance NUMERIC, final_balance NUMERIC) AS $$
    DECLARE
        initial_balance NUMERIC;
        final_balance NUMERIC;
    BEGIN

        SELECT SUM(m.initial_balance), SUM(m.final_balance) INTO initial_balance, final_balance
        FROM monthly_balance AS m
        WHERE m.card_number IN 
            (
                SELECT card_number 
                FROM payment_card
                WHERE owner_username IN 
                    (
                        SELECT username 
                        FROM "user"
                        WHERE family_id = input_family_id
                    )
            )
        AND DATE_TRUNC('month', m.date) = DATE_TRUNC('month', input_date);

        RETURN QUERY SELECT COALESCE(initial_balance, 0.00), COALESCE(final_balance, 0.00);
    END;
    $$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_user_monthly_income(input_username VARCHAR, input_date DATE)
    RETURNS NUMERIC AS $$

    DECLARE
        total_income NUMERIC;
    BEGIN 
        SELECT SUM(amount) INTO total_income
        FROM transaction AS t 
        WHERE
            direction = 'INCOME' AND
            DATE_TRUNC('month', t.date) = DATE_TRUNC('month', input_date) AND
            t.card_number in
                (
                    SELECT card_number FROM payment_card 
                    WHERE owner_username = input_username
                );

        RETURN COALESCE(total_income,0.00);

    END;
    $$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_user_monthly_expense(input_username VARCHAR, input_date DATE)
    RETURNS NUMERIC AS $$

    DECLARE
        total_expense NUMERIC;
    BEGIN 
        SELECT SUM(amount) INTO total_expense
        FROM transaction AS t 
        WHERE
            direction = 'EXPENSE' AND
            DATE_TRUNC('month', t.date) = DATE_TRUNC('month', input_date) AND
            t.card_number in
                (
                    SELECT card_number FROM payment_card 
                    WHERE owner_username = input_username
                );

        RETURN COALESCE(total_expense,0.00);

    END;
    $$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_user_monthly_balance(input_username VARCHAR, input_date DATE)
    RETURNS TABLE(initial_balance NUMERIC, final_balance NUMERIC) AS $$
    DECLARE
        initial_balance NUMERIC;
        final_balance NUMERIC;
    BEGIN

        SELECT SUM(m.initial_balance), SUM(m.final_balance) INTO initial_balance, final_balance
        FROM monthly_balance AS m
        WHERE m.card_number IN 
            (
                SELECT card_number 
                FROM payment_card
                WHERE owner_username = input_username
            )
        AND DATE_TRUNC('month', m.date) = DATE_TRUNC('month', input_date);

        RETURN QUERY SELECT COALESCE(initial_balance, 0.00), COALESCE(final_balance, 0.00);
    END;
    $$ LANGUAGE plpgsql;

/********************************************** POPULATION ************************************************/
INSERT INTO family (name)
    VALUES
    ('Smith'),
    ('Johnson'),
    ('Brown');

INSERT INTO "user" (username, password, name, family_id)
    VALUES
    ('alice104', 'Password123', 'Alice', 1),
    ('frankiewastaken', 'Password123', 'Frankie', 1),
    ('bobyyyy', 'Password456', 'Boby', 1),
    ('charlie_el_guapo', 'Password789', 'Charlie', 2);

INSERT INTO payment_card (card_number, cvv, pin, expiration_date, balance, owner_username)
    VALUES
    ('2402432187654321', '385', '2758', '2029-06-01', 9000.00, 'bobyyyy'),
    
    ('1003673846299564', '640', '4658', '2029-08-01', 1000.00, 'charlie_el_guapo'),

    ('3498567345687564', '904', '6349', '2029-06-01', 3000.00, 'frankiewastaken'),

    ('4021640965634942', '549', '5483', '2029-06-01', 0.00, 'alice104'),
    ('2402740937654901', '945', '2378', '2029-06-01', 1250.38, 'alice104'),
    ('3498734875349033', '648', '8906', '2029-12-01', 3000.00, 'alice104'),
    ('1003789453846743', '673', '5648', '2029-12-01', 20000.00, 'alice104');

INSERT INTO monthly_balance 
    VALUES
    (20000.00, 20000.00, '2024-02-01', '1003789453846743'),
    (9000.00, 9000.00, '2024-02-01', '2402432187654321'),
    (1000.00, 1000.00, '2024-02-01', '1003673846299564'),
    (3000.00, 3000.00, '2024-02-01', '3498734875349033'),
    (3000.00, 3000.00, '2024-02-01', '3498567345687564'),
    (0.00, 0.00, '2024-02-01', '4021640965634942'),
    (1250.38, 1250.38, '2024-02-01', '2402740937654901');

INSERT INTO category (name, creator_username)
    VALUES
    ('Food', 'alice104'),
    ('Paycheck', 'alice104'),
    ('Health', 'alice104'),
    ('Travel', 'alice104'),
    ('Electronics', 'alice104'),
    
    ('Entertainment', 'bobyyyy'),
    ('Shopping', 'bobyyyy'),

    ('Utilities', 'charlie_el_guapo'),
    ('Housing', 'charlie_el_guapo'),

    ('Transport', 'frankiewastaken'),

    ('Other', 'alice104'),
    ('Other', 'bobyyyy'),
    ('Other', 'charlie_el_guapo'),
    ('Other', 'frankiewastaken');
    
INSERT INTO keyword (keyword, category_id)
    VALUES
    ('groceries', 1),      -- for Food (creator: alice104)
    ('food', 1),
    ('supermarket', 1),
    ('restaurant', 1),
    ('diner', 1),
    ('coffee', 1),
    ('snacks', 1),

    ('salary', 2),        -- for Paycheck (creator: alice104)
    ('freelance', 2),
    ('bonus', 2),

    ('health', 3),        -- for Health (creator: alice104)
    ('medical', 3),
    ('doctor', 3),
    ('hospital', 3),
    ('pharmacy', 3),

    ('travel', 4),        -- for Travel (creator: alice104)
    ('vacation', 4),
    ('flight', 4),
    ('hotel', 4),
    ('tour', 4),

    ('gadgets', 5),       -- for Electronics (creator: alice104)
    ('devices', 5),
    ('laptop', 5),
    ('phone', 5),
    ('tablet', 5),

    ('movies', 6),        -- for Entertainment (creator: bobyyyy)
    ('music', 6),
    ('games', 6),
    ('concert', 6),
    ('show', 6),

    ('shopping', 7),      -- for Shopping (creator: bobyyyy)
    ('clothes', 7),
    ('shoes', 7),
    ('accessories', 7),
    ('fashion', 7),

    ('bills', 8),         -- for Utilities (creator: charlie_el_guapo)
    ('subscriptions', 8),
    ('internet', 8),
    ('water', 8),
    ('gas', 8),

    ('rent', 9),          -- for Housing (creator: charlie_el_guapo)
    ('mortgage', 9),
    ('maintenance', 9),
    ('furniture', 9),
    ('appliances', 9),

    ('transport', 10),    -- for Transport (creator: frankiewastaken)
    ('bus', 10),
    ('train', 10),
    ('taxi', 10),
    ('fuel', 10);