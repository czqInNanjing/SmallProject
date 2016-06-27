# -*- coding: UTF-8 –*-
'''
Created on Jun 25, 2016

@author: Qiang
'''
import matplotlib.pyplot as plt
import numpy as np
from scipy import stats

def pearsonr():
    x = np.linspace(-5, 5, num=150)
    y = x + np.random.normal(size=x.size)
#     y[12:14] += 10
    
    print(stats.pearsonr(x, y))
    plt.scatter(x,y)
    plt.show()

#the code should not be changed
if __name__ == '__main__':
    pearsonr()