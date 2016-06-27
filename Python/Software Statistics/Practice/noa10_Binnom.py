# coding=utf-8
# 二项分布
import scipy.stats as stats
import matplotlib.pyplot as plt
import numpy as np

def binnom(n , p):
    rv = stats.binom(n , p)
    x = np.arange(0 , n , 1)
    y = rv.pmf(x)
    plt.bar(x, y , width=1,color = 'y')
    plt.show()

binnom(1000 , 0.5)