# -*- coding: UTF-8 –*-
'''
Created on Jun 25, 2016

@author: Qiang
'''
from scipy import stats

def ttest():
    x = stats.norm.rvs(loc=5, scale=1, size=50)
    y = stats.norm.rvs(loc=5.01, scale=1, size=50)

    print(stats.ttest_ind(x, y))
    print(stats.ttest_ind(x, y, equal_var = True))

#the code should not be changed
if __name__ == '__main__':
    ttest()