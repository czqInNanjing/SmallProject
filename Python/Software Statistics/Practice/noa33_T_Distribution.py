#-*- coding:utf-8 -*-
# （2）matplotlib.pyplot.subplot(nrows,ncols,plot_number)生成nrows * ncols 个subplot并返回plot_number所指定plot
# （3）numpy.linspace(start,end,num=50)返回start到end之间num个等间距数字
# （4）scipy.stats.t.pdf(x,df)概率密度函数
# （5）scipy.stats.norm.rvs()返回符合标准正态分布的随机变量
# （6）scipy.stats.chi2.rvs(df)返回自由度为df的卡方分布的随机变量

import sys
import numpy as np
from scipy.stats import norm
from scipy.stats import chi2
from scipy.stats import t
import matplotlib.pyplot as plt

def t_distribution():
    fig, ax = plt.subplots(1, 1)
    #display the probability density function
    df = 10
    x=np.linspace(-4, 4, 100)
    ax.plot(x, t.pdf(x,df))
    
    #simulate the t-distribution
    y = []
    for i in range(1000):
        rx = norm.rvs()
        #生成自由度为df的卡方分布
        ry = chi2.rvs(df)
        rt = rx/np.sqrt(ry/df)
        y.append(rt)

    ax.hist(y, normed=True, alpha=0.2)
    plt.show()

#the code should not be changed
if __name__ == '__main__':
    t_distribution()