USE eshop;
INSERT OVERWRITE DIRECTORY '${output}'
    ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
    SELECT ShopID, sum(Amount*Price - Discount) AS Total FROM Orders WHERE WorkDate LIKE '${D}%' GROUP BY ShopID order by Total desc;
