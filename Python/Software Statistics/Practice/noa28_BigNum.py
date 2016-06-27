#-*- coding:utf-8 -*-
import sys
import numpy as np
import matplotlib.pyplot as plt
from scipy.stats import bernoulli
# 伯努利大数定律
def law_of_large_numbers():
    x = np.arange(1, 1001, 1) 
#     scipy.stats.bernoulli.rvs(p,loc=0,size=1)返回size个符合泊松分布的随机变量
    r = bernoulli.rvs(0.3, size=1000)
    y = []
    rsum =0.0
    for i in range(1000):
        if r[i]==1:
            rsum=rsum+1
        y.append(rsum/(i+1))
    plt.plot(x, y, color='red')
    plt.show()

#the code should not be changed
if __name__ == '__main__':
    law_of_large_numbers()