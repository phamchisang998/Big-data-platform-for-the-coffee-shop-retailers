Create TB Orders: hive --hivevar input=/data -f ./hive\ scripts/create_orders.hql 

Top K products: hive --hivevar output=/out_hive --hivevar K=5 -f topK_computing.hql 

Sale computing: hive --hivevar output=/out_hive_sales -f sale_computing.hql 
