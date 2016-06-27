# -*- coding: UTF-8 –*-
'''
Created on Jun 25, 2016

@author: Qiang
'''
# KS检验通常用于检验单一样本是否服从某特定分布，或者两样本是否来自相同分布
# scipy.stats.ks_2samp(data1,data2)对给定样本data1及data2进行KS双边检验

from scipy import stats

def kstest():
    n1 = 200
    n2 = 300
    a = stats.norm.rvs(size=n1, loc=0, scale=1)
    b = stats.norm.rvs(size=n2, loc=0.5, scale=1.5)
    c = stats.norm.rvs(size=n2, loc=0.01, scale=1)
    
    print(stats.ks_2samp(a, b))
    print(stats.ks_2samp(a, c))
    print stats.kstest(a, 'norm')
#     stats.bin
                 # the code should not be changed
if __name__ == '__main__':
    kstest()
