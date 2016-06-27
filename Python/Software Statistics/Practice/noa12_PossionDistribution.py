# coding=utf-8
import scipy.stats as stats
import matplotlib.pyplot as plt
import  numpy as np

#在泊松分布中使用λ描述单位时间中随机事件的平均发生率，其中λ=np
def possionDistribution(lam):
    x = np.arange(1,100,1)
    rv = stats.poisson(lam)
    y = rv.pmf(x)
    plt.bar(x, y , width=1 , color = 'r')
    plt.show()


possionDistribution(2)