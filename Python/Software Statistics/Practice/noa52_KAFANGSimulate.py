# -*- coding: UTF-8 –*-
'''
Created on Jun 25, 2016

@author: Qiang
'''
from scipy import stats
# 卡方拟合优度检验通常用于根据样本的频数分布来推断总体的分布。
def chisquare():
    A=[16, 18, 16, 14, 12, 12]
    B=[16, 16, 16, 16, 16, 8]
    C = stats.norm.rvs(loc = 0 , scale = 1, size = 100)
    D = stats.norm.rvs(loc = 0 , scale = 1.15 , size = 100)
#     print C
    print(stats.chisquare(A)) # f_exp = None 时期望为均匀分布 
    print(stats.chisquare(A, f_exp=B))
    print stats.chisquare(C, f_exp=D)

#the code should not be changed
if __name__ == '__main__':
    chisquare()