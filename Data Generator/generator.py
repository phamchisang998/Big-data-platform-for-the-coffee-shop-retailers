import numpy as np
from datetime import datetime
import pandas as pd
from random import randint

class generator():
    def __init__(self, resource, numberOfShop = 60) -> None:
        self.resource = resource
        self.roulette = self.createRoulette(resource)
        self.infors = {}
        self.rangeAvgOrderPerHour = [5,20]
        self.rangeAvgProductPerOrder = [1,5]
        self.folders = '11-1'
        self.openTime = 8
        self.closeTime = 23
        for i in range(numberOfShop):
            self.infors[str(i+1)] = 100000000


    def createRoulette(self,data):
        lastPoint = 0
        output = pd.DataFrame(
            columns=['ProductID', 'ProductName', 'From', 'To'])
        for index, row in data.iterrows():
            if(np.isnan(row['ProductID'])):
                fromPoint = np.NaN
                toPoint = np.NaN
            else:
                fromPoint = lastPoint
                toPoint = fromPoint + row["Probability"]
            lastPoint = toPoint
            output = output.append({'ProductID': row['ProductID'], 'ProductName': row['ProductName'], 'From': fromPoint, 'To': toPoint}, ignore_index=True)
        return output
    
    def takeValue(self):
        randNum = randint(0,99)
        val = self.resource[(self.roulette.From <= randNum) & (self.roulette.To > randNum)]
        return val

    def order(self,numberOfProduct):
        alreadyP = {}
        discountPercent = randint(0,15)
        for j in range(numberOfProduct):
            val = self.takeValue()
            productID = val['ProductID'].values[0]
            productName = val['ProductName'].values[0]
            price = val['Price'].values[0]
            discount = -int(price*discountPercent/100)
            if productName not in alreadyP:
                alreadyP[productName] = {'ProductID': productID, 'Price':price, 'Amount': 0, 'Discount': discount}
            alreadyP[productName]['Amount'] +=1
            alreadyP[productName]['Discount'] +=discount
        return alreadyP

    def generateInOneHour(self,dateData):
        for k in self.infors.keys():
            nameFileOutput = f'Shop-{k}-{dateData.strftime("%Y%m%d-%H")}.csv'
            print(nameFileOutput)

            data = pd.DataFrame(columns=['OrderID','ProductID','ProductName','Amount','Price'])
            numberOfOrder = randint(self.rangeAvgOrderPerHour[0],self.rangeAvgOrderPerHour[1])
            for i in range(numberOfOrder):
                orderID = self.infors[str(k)] + 1 
                self.infors[k] = orderID
                numberOfProduct = randint(self.rangeAvgProductPerOrder[0],self.rangeAvgProductPerOrder[1])
                orders = self.order(numberOfProduct)
                for productName in orders.keys():
                    productID = orders[productName]['ProductID']
                    amount = orders[productName]['Amount']
                    price = int(orders[productName]['Price'])
                    discount = int(orders[productName]['Discount'])
                    data = data.append({'OrderID': orderID,'ProductID': productID,'ProductName': productName,'Amount': amount,'Price': price, 'Discount': discount},ignore_index=True)
            data.to_csv(f'{self.folders}/{nameFileOutput}',  index=False,  header=False )


    def generateInAmountOfTime(self, fromDate, toDate):
        p = '%Y-%m-%d %H:%M:%S'
        epoch_time= datetime.strptime(fromDate, p).timestamp()
        epoch_time_to_date = datetime.strptime(toDate, p).timestamp()
        while epoch_time <= epoch_time_to_date:
            dateTemp = datetime.fromtimestamp(epoch_time)
            if self.openTime <= dateTemp.hour <= self.closeTime:
                self.generateInOneHour(dateTemp)
            epoch_time += 3600
                




if __name__ == '__main__':
    fromDate = '2020-11-25 08:00:00'
    toDate = '2021-01-25 07:00:00'
    data = pd.read_csv('./resource.csv')
    gen = generator(data)
    gen.generateInAmountOfTime(fromDate,toDate)
              
