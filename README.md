# Big data platform for the coffee-shop retailers
<img src="https://user-images.githubusercontent.com/83225697/121920357-28354180-cd62-11eb-9aef-2ae4b4986ad2.png" />

# What is this?
## Problem
Company A has a sales system consisting of N=60 retail stores (shops). Sales data of shops are sent to the central server system for analysis periodically after every T interval (eg T = 1 hour). The data each time each shop sends back is saved in a *.csv file (Shop-k-YYYYMMDD-hh.csv, k <=N). The structure of content in each file is as follows:\
*OrderID,ProductID,ProductName,Amount,Price,Discount*
## Solution
- This is a bundle of application and code that built upon hadoop ecosystem.
- This system is aimed to store, manage, manipulate and analyze big data of the coffee-shop retailers.
# Overview
- Assume that data will come to realtime-data folder in your pc (you can use DataMoving to move data any other place in you PC as you want).
- Use Nifi to transform and move data into hdfs.
- In HDFS you can use Hive for analytics or map reduce application according to your uses.
- Sqoop is used to export your computed results in hdfs into RDBMS (we use MySQL).
- You can use BI tools that connected to RDBMS data resource for visulization or decision making.
# Requirements
A hadoop cluter with configuration as below:
- Master (namenode): IP: 192.168.18.133 (master), CPU: 4 cores, RAM: 16 GB, Storage: 40 GB, OS: Ubuntu 20.04 (hduser@master).
- Slave01 (datanode): IP: 192.168.18.129 (slave01), CPU: 2 cores, RAM: 2 GB, Storage: 40 GB, OS: Ubuntu 20.04 (hduser@slave01).
- Slave02 (datanode): IP: 192.168.18.130 (slave02), CPU : 2 cores, RAM: 2 GB, Storage: 40 GB, OS: Ubuntu 20.04 (hduser@slave02).
## Hadoop ecosystem
- Hadoop ^3.3.0
- Apache Sqoop ^2.6.0
- Apache Nifi ^1.1.1
- Apache Hive ^2.3.8
## Programming Language
- Java 8
- Python 3.8
- NodeJS 12.22.1
- Shell script
- SQL
- HQL
# How to use
## Data generator
### Install requirements (terminal):
`python3.8 -m pip install requirements.txt`
### Open file generator.py config for simualiton your retailers
- At the opening of the file.
`self.rangeAvgOrderPerHour = [5,20]    # avarage oders every hours.`\
`self.rangeAvgProductPerOrder = [1,5]  # avarage products for every order.`\
`self.folders = '11-1'   #folder output.`\
`self.openTime = 8       # time that your shop open.`\
`self.closeTime = 23     # time that your shop will close.`
- At the end of the file config time range you want to generate.\
`fromDate = '2020-11-25 08:00:00'`\
`toDate = '2021-01-25 07:00:00'`
### Run file (terminal):
`python3 generator.py`
## Nifi
### Import xml file
- Import the flow in your Nifi.
- Config link dir and host in every processor that needed.
### Put the script file at the path you config in ExecuteScript Processor.
- You can change the script to transform data as your custom.
- The script can be replace by Python, Groovy,...
** Read file readme to know more about processors will be used.**
## MySQL
- Create DB **aiacad** and grant all privilege to user **hduser** (password: **hduser@123**, host: **%**).
- You can consult this one at your **root** user mysql:
`mysql> CREATE DATABASE aiacad;`\
`mysql> CREATE USER 'hduser'@'%' IDENTIFIED BY 'hduser@123';`\
`mysql> GRANT ALL PRIVILEGES ON hivedb.* TO 'hduser'@'%' WITH GRANT OPTION;`\
`mysql> FLUSH PRIVILEGES;`
## Hive
- Install and config Hive with schematool store metadata in mysql.
- Read file ReadMe to run Hive scripts.
## Sqoop
- Install sqoop in your cluster.
- Read file ReadMe to know how to run script at the right way.
## MapReduce
- You can use .jar file at every folder to run the app according to ReadMe file.
- Or you can change the process and build your own .jar file based on the source code.
# Support
*AI academy - Big data analytics*
