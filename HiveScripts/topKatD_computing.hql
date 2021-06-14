USE eshop;
INSERT OVERWRITE DIRECTORY '${output}' 
    ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
    SELECT ProductID, sum(Amount) AS Amount FROM Orders WHERE WorkDate LIKE '${D}%' GROUP BY ProductID order by Amount desc limit ${K};
