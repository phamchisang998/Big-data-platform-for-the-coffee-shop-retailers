USE eshop;
INSERT OVERWRITE DIRECTORY '${output}' 
    ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
    SELECT ProductID, sum(Amount) AS Amount FROM Orders GROUP BY ProductID order by Amount desc limit ${K};
