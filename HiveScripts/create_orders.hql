USE eshop;
CREATE EXTERNAL TABLE IF NOT EXISTS 
    Orders_temp(ShopID integer, 
            WorkDate string,
            OrderID integer,
            ProductID integer, 
            ProductName string,
            Amount integer, 
            Price integer, 
            Discount integer)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE;
ALTER TABLE Orders_temp SET LOCATION '${input}';

DROP TABLE IF EXISTS Orders;
CREATE TABLE
    Orders(ShopID integer, 
            WorkDate string,
            OrderID integer,
            ProductID integer, 
            ProductName string,
            Amount integer, 
            Price integer, 
            Discount integer)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE;
INSERT INTO TABLE Orders SELECT * FROM Orders_temp;
