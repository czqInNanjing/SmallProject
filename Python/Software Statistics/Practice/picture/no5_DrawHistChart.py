#-*- coding:utf-8 -*-
import numpy as np
import matplotlib.pyplot as plt

def get_data():
    # example data
    mu = 100 # mean of distribution
    sigma = 1000 # standard deviation of distribution
    x = mu + sigma * np.random.randn(100000)

    return x

#you can write your code here
def draw():
    #get input data
    x = get_data()
    # the histogram of the data
    
    ###### bins means split the data into how many ############
    plt.hist(x, bins=10, color='g', alpha=0.5)
    #show image
    plt.show()

#the code should not be changed
if __name__ == '__main__':
    draw()