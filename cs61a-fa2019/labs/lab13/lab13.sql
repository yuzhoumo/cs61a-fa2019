.read data.sql

-- QUESTIONS --

-------------------------------------------------------------------------
------------------------ Give Interest- ---------------------------------
-------------------------------------------------------------------------

UPDATE accounts SET amount = amount + 0.02 * amount;

create table give_interest_result as select * from accounts; -- just for tests

-------------------------------------------------------------------------
------------------------ Split Accounts ---------------------------------
-------------------------------------------------------------------------

CREATE TABLE temp(name, account);
INSERT INTO temp SELECT name || "'s Checking account", amount * 0.5 FROM accounts;
INSERT INTO temp SELECT name || "'s Savings account", amount * 0.5 FROM accounts;

DROP TABLE accounts;
CREATE TABLE accounts AS SELECT * FROM temp;

create table split_account_results as select * from accounts; -- just for tests

-------------------------------------------------------------------------
-------------------------------- Whoops ---------------------------------
-------------------------------------------------------------------------

DROP TABLE accounts;


CREATE TABLE average_prices AS
    SELECT category, AVG(MSRP) AS average_price FROM products GROUP BY category;

CREATE TABLE lowest_prices AS
    SELECT store, item, MIN(price) AS lowest_price FROM inventory GROUP BY item;

CREATE TABLE shopping_list AS
    SELECT name, store FROM lowest_prices, products WHERE name = item
        GROUP BY category HAVING MIN(MSRP/rating);

CREATE TABLE total_bandwidth AS
    SELECT SUM(Mbs) FROM shopping_list AS a, stores AS b
        WHERE a.store = b.store;
