USE eshop;
INSERT OVERWRITE DIRECTORY '${output}'
    ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
    SELECT ProductID, sum(Amount*Price - Discount) AS Total FROM Orders GROUP BY ProductID order by Total desc;
