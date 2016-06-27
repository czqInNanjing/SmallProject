#-*- coding:utf-8 -*-
import sys
import numpy as np
import matplotlib.pyplot as plt
from scipy.stats import expon as E
from scipy.stats import norm as N

def expon_cdf_pdf():
    x = np.linspace(0, 5, 100)##return evenly spaced samples, calculated over the interval [0,5]
    rv=E(scale = 1)#the scale is 1
    plt.subplot(211)
    plt.plot(x, rv.pdf(x), color='blue')#make pdf chart 
    plt.plot(x, rv.cdf(x), color='red') #make cdf chart
    plt.draw()
    
    plt.subplot(212)
    nornRV = N(loc = 1, scale = 2)
    plt.plot(x, nornRV.pdf(x), color='azure')
    plt.plot(x, nornRV.cdf(x), color='r')
    plt.draw()
    
    plt.show()

#the code should not be changed
if __name__ == '__main__':
    expon_cdf_pdf()