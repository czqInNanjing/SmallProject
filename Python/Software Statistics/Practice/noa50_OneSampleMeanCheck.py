# -*- coding: UTF-8 –*-
'''
Created on Jun 25, 2016

@author: Qiang
'''
from scipy import stats

def ttest():
    x = stats.norm.rvs(loc=5, scale=10, size=50)

    print(stats.ttest_1samp(x,5.0))
    print(stats.ttest_1samp(x,1.0))

#the code should not be changed
if __name__ == '__main__':
    ttest()