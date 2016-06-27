#-*- coding:utf-8 -*-
import numpy as np
import matplotlib.pyplot as plt

def get_data():
    # example data
    mu = 100 # mean of distribution
    sigma = 15 # standard deviation of distribution
    x = mu + sigma * np.random.randn(50)
    return x

#you can write your code here
def draw():
    #get input data
    x = get_data()
    # the line chart of the data
    plt.plot(x, 'r--')
    #show image
    plt.show()

#the code should not be changed
if __name__ == '__main__':
    draw()