#-*- coding:utf-8 -*-
import numpy as np
import matplotlib.pyplot as plt

def get_data():
    mu = 100
    sigma = 15
    x = mu + sigma * np.random.randn(100)
    return x

#you can write your code here
def draw():
    #get data from mooctest.net
    x = get_data()
    
    
    ########### bulid more data , to draw a beautiful box chart
    y = []
    for i in range(10):
        y.append(x)
    #create box plot chart
    plt.boxplot(y)
    #show image
    plt.show('fig.png')     

#the code should not be changed
if __name__ == '__main__':
    draw()