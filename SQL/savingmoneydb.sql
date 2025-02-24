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

INSERT INTO category (name, creator_username)
    VALUES
    ('Food', 'alice'),
    ('Entertainment', 'boby'),
    ('Utilities', 'charlie'),
    ('Transport', 'frankie'),
    ('Income', 'alice'),
    ('Shopping', 'boby'),
    ('Housing', 'charlie'),

    ('Other', 'alice'),
    ('Other', 'boby'),
    ('Other', 'charlie'),
    ('Other', 'frankie');
    
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

    -- Cycle 9
    (33, 250, 'Dividends from investments in groceries stock market', '2025-01-05', 'income', '3498734875349033', 'Investment Firm'),
    (34, 35, 'Gift purchase for birthday', '2025-01-06', 'expense', '8765432187654321', 'Gift Shop'),
    (35, 640, 'Monthly rent installment', '2025-01-07', 'expense', '1122334455667788', 'Landlord'),
    (36, 13, 'Bus trip for city tour', '2025-01-08', 'expense', '3498734875349456', 'Tour Bus Service'),

    -- Cycle 10
    (37, 35, 'Random shopping expense', '2025-01-09', 'expense', '1234567812345678', 'Retail Store'),
    (38, 22, 'Casual outing expense', '2025-01-10', 'expense', '8765432187654321', 'Cafe'),
    (39, 35, 'Miscellaneous utility expense', '2025-01-11', 'expense', '1122334455667788', 'Service Provider'),
    (40, 18, 'Unplanned expense', '2025-01-12', 'expense', '3498734875349456', 'General Vendor'),

    -- Extra 10 for Alice
    (41, 65, 'Groceries for family dinner', '2025-01-13', 'expense', '1234567812345678', 'Supermarket'),
    (42, 2100, 'Salary deposit', '2025-01-14', 'income', '3498734875349033', 'Employer'),
    (43, 70, 'Fresh groceries purchase', '2025-01-15', 'expense', '1234567812345678', 'Grocery Store'),
    (44, 550, 'Freelance income from design work', '2025-01-16', 'income', '3498734875349033', 'Client'),
    (45, 30, 'Uncategorized expense at restaurant', '2025-01-17', 'expense', '1234567812345678', 'Restaurant'),
    (46, 50, 'Groceries from organic store', '2025-01-18', 'expense', '3498734875349033', 'Organic Store'),
    (47, 320, 'Bonus received after performance review', '2025-01-19', 'income', '1234567812345678', 'Employer'),
    (48, 60, 'Groceries at supermarket', '2025-01-20', 'expense', '3498734875349033', 'Supermarket'),
    (49, 260, 'Dividends paid quarterly', '2025-01-21', 'income', '1234567812345678', 'Investment Firm'),
    (50, 45, 'Miscellaneous expense for office supplies', '2025-01-22', 'expense', '3498734875349033', 'Office Supplies Store');

INSERT INTO transaction_category (transaction_id, category_id)
    VALUES
  (1, 1),    -- Transaction 1: alice – Food (contains "groceries")
  (2, 2),    -- Transaction 2: boby – Entertainment (contains "movies")
  (3, 3),    -- Transaction 3: charlie – Utilities (contains "electricity")
  (4, 4),    -- Transaction 4: frankie – Transport (contains "taxi")
  
  (5, 5),    -- Transaction 5: alice – Income (contains "salary")
  (6, 6),    -- Transaction 6: boby – Shopping (contains "electronics")
  (7, 7),    -- Transaction 7: charlie – Housing (contains "rent")
  (8, 4),    -- Transaction 8: frankie – Transport (contains "bus")
  
  (9, 1),    -- Transaction 9: alice – Food (contains "groceries")
  (10, 2),   -- Transaction 10: boby – Entertainment (contains "concert")
  (11, 3),   -- Transaction 11: charlie – Utilities (contains "electricity")
  (12, 4),   -- Transaction 12: frankie – Transport (contains "taxi")
  
  (13, 5),   -- Transaction 13: alice – Income (contains "freelance")
  (14, 6),   -- Transaction 14: boby – Shopping (contains "gift")
  (15, 7),   -- Transaction 15: charlie – Housing (contains "rent")
  (16, 4),   -- Transaction 16: frankie – Transport (contains "bus")
  
  (17, 8),   -- Transaction 17: alice – non-matching → Other (ID 8)
  (18, 9),   -- Transaction 18: boby – non-matching → Other (ID 9)
  (19, 10),  -- Transaction 19: charlie – non-matching → Other (ID 10)
  (20, 11),  -- Transaction 20: frankie – non-matching → Other (ID 11)
  
  (21, 1),   -- Transaction 21: alice – Food (contains "groceries")
  (22, 2),   -- Transaction 22: boby – Entertainment (contains "movies")
  (23, 3),   -- Transaction 23: charlie – Utilities (contains "electricity")
  (24, 4),   -- Transaction 24: frankie – Transport (contains "taxi")
  
  (25, 5),   -- Transaction 25: alice – Income (contains "bonus")
  (26, 6),   -- Transaction 26: boby – Shopping (contains "book")
  (27, 7),   -- Transaction 27: charlie – Housing (contains "rent")
  (28, 4),   -- Transaction 28: frankie – Transport (contains "bus")
  
  (29, 1),   -- Transaction 29: alice – Food (contains "groceries")
  (30, 2),   -- Transaction 30: boby – Entertainment (contains "concert")
  (31, 3),   -- Transaction 31: charlie – Utilities (contains "electricity")
  (32, 4),   -- Transaction 32: frankie – Transport (contains "taxi")
  
  (33, 5),   -- Transaction 33: alice – Income (contains "dividends")
  (33, 1),   -- Transaction 33: alice – Food (contains "groceries")
  (34, 6),   -- Transaction 34: boby – Shopping (contains "gift")
  (35, 7),   -- Transaction 35: charlie – Housing (contains "rent")
  (36, 4),   -- Transaction 36: frankie – Transport (contains "bus")
  
  (37, 8),   -- Transaction 37: alice – non-matching → Other (ID 8)
  (38, 9),   -- Transaction 38: boby – non-matching → Other (ID 9)
  (39, 10),  -- Transaction 39: charlie – non-matching → Other (ID 10)
  (40, 11),  -- Transaction 40: frankie – non-matching → Other (ID 11)
  
  (41, 1),   -- Transaction 41: alice – Food (contains "groceries")
  (42, 5),   -- Transaction 42: alice – Income (contains "salary")
  (43, 1),   -- Transaction 43: alice – Food (contains "groceries")
  (44, 5),   -- Transaction 44: alice – Income (contains "freelance")
  (45, 8),   -- Transaction 45: alice – non-matching → Other (ID 8)
  (46, 1),   -- Transaction 46: alice – Food (contains "groceries")
  (47, 5),   -- Transaction 47: alice – Income (contains "bonus")
  (48, 1),   -- Transaction 48: alice – Food (contains "groceries")
  (49, 5),   -- Transaction 49: alice – Income (contains "dividends")
  (50, 8);   -- Transaction 50: alice – non-matching → Other (ID 8)

