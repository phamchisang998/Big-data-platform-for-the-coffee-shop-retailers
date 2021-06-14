#!/bin/bash
mysql -u hduser -p -h master aiacad < createTables.sql
sqoop export --connect jdbc:mysql://master:3306/aiacad --username hduser --password hduser@123 --table TopProducts --input-fields-terminated-by "," --export-dir /cau5a
sqoop export --connect jdbc:mysql://master:3306/aiacad --username hduser --password hduser@123 --table TopProductsAtD --input-fields-terminated-by "," --export-dir /cau5b
sqoop export --connect jdbc:mysql://master:3306/aiacad --username hduser --password hduser@123 --table ProductSale --input-fields-terminated-by "," --export-dir /cau5c
sqoop export --connect jdbc:mysql://master:3306/aiacad --username hduser --password hduser@123 --table ShopSaleAtD --input-fields-terminated-by "," --export-dir /cau5d
