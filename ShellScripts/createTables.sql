DROP TABLE IF EXISTS TopProducts;
CREATE TABLE TopProducts(ProductID int, Amount int);

DROP TABLE IF EXISTS TopProductsAtD;
CREATE TABLE TopProductsAtD(ProductID int, Amount int);

DROP TABLE IF EXISTS ProductSale;
CREATE TABLE ProductSale(ProductID int, Total bigint);

DROP TABLE IF EXISTS ShopSaleAtD;
CREATE TABLE ShopSaleAtD(ShopID int, Total bigint);
