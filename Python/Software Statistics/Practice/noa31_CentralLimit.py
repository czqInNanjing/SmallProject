#-*- coding:utf-8 -*-
import sys
import numpy as np
from scipy.stats import binom
import matplotlib.pyplot as plt
# 下面为独立同分布的中心极限定理，拉普拉斯定理的python实现，请完成以下练习：
# （1）仔细阅读，理解代码含义，并运行代码，看看结果如何？
# （2）试调整二项分布的参数，看看结果如何变化？
def central_limit_theorem():
    y = []
    n=1000
    p = 0.6
    for i in range(n):
        r = binom.rvs(n, p)
        rsum=np.sum(r)
        z=(rsum-n*p)/np.sqrt(n*p*(1-p))
        y.append(z)
    print y
    plt.hist(y,color='grey')
    plt.show()
    
#the code should not be changed
if __name__ == '__main__':
    central_limit_theorem()