#-*- coding:utf-8 -*-
import sys
import numpy as np
import matplotlib.pyplot as plt
from scipy.stats import geom as G

def geom_pmf(myLambda):
    rv=G(myLambda)#probability of success is 0.2
    x = np.arange(1, 100, 1)#return evenly spaced values within 1 interval between [1,11)
    print x
    y = rv.pmf(x)#probability mass 
    print y
    plt.bar(x, y, width=0.6,  color='grey')#make bar chart
    plt.plot(x,y , color='purple')
    plt.show()

#the code should not be changed
if __name__ == '__main__':
    geom_pmf(0.8)