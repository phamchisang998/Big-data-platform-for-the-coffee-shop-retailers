Create TB Orders: hive --hivevar input=/data -f ./hive\ scripts/create_orders.hql 

Top K products: hive --hivevar output=/out_hive --hivevar K=5 -f topK_computing.hql 

Top K products at time D: hive --hivevar output=/out_hive --hivevar K=5 --hivevar D=202009 -f topKatD_computing.hql 

Product sale computing: hive --hivevar output=/out_hive_Psales -f productsale_computing.hql 

Shop sale computing at time D: hive --hivevar output=/out_hive_Ssales --hivevar D=202009 -f shopsale_computing.hql 

