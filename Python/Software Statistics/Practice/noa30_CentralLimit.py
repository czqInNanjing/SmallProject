#-*- coding:utf-8 -*-
import sys
import numpy as np
from scipy.stats import expon
from scipy.stats import poisson
import matplotlib.pyplot as plt
from numpy import size
# 为独立同分布的中心极限定理的python实现，请完成以下练习：
# （1）仔细阅读，理解代码含义，并运行代码，看看结果如何？
# （2）试利用其他分布函数验证此中心极限定理。
def central_limit_theorem():
    y = []
    n=100
    for i in range(1000):
        r = expon.rvs(scale=1, size=n)
        rsum=np.sum(r)
        z=(rsum-n)/np.sqrt(n)
        y.append(z)
    
    plt.subplot(211)
    print y
    plt.hist(y,color='grey')
    
    y2 = []
    for i in range(10000):
        r = poisson.rvs(3 , size = n)
        rsum = np.sum(r)
        z = (rsum - n)/np.sqrt(n)
        y2.append(z)
    plt.subplot(212)
    plt.hist(y2,color='r')
    plt.show()

#the code should not be changed
if __name__ == '__main__':
    central_limit_theorem()