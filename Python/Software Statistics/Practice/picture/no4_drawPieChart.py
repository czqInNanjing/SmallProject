# -*- coding: UTF-8 –*-
'''
Created on Jun 24, 2016

@author: Qiang
'''

import numpy as np
import matplotlib.pyplot as plt

def get_data():
    # example data
    mu = 100 # mean of distribution
    sigma = 15 # standard deviation of distribution
    x = mu + sigma * np.random.randn(8)
    return x

#you can write your code here
def draw():
    #get input data
    x = get_data()
    c = ('b', 'g', 'r', 'c', 'm', 'y', 'k', 'w')
    # the pie chart of the data
    
    
    
    ################ important arguments ###################
    plt.pie(x, colors=c, shadow=False)  
    #show image
#     plt.savefig('fig.png')
    plt.show()

#the code should not be changed
if __name__ == '__main__':
    draw()