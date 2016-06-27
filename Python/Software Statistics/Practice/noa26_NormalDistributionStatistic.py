#-*- coding:utf-8 -*-
import numpy as np
from scipy.stats import norm
from scipy.stats.mstats_basic import moment
from scipy import integrate
from scipy.stats import expon
def nc_of_norm():
    f1 = lambda x: x**4
    f2 = lambda x: x**2-x+2
    
    rv = norm(loc = 2 , scale=20)
    print rv.mean()
    print rv.var()
    print rv.moment(1)
    print rv.moment(4)
    #     moments的参数可为m(均值),v(方差值),s(偏度),k(峰度),默认为mv
    print rv.stats(moments = 'mvsk')
# 3）scipy.stats.norm.expect(func,loc=0,scale=1)函数返回func函数相对于正态分布的期望值，其中函数f(x)相对于分布dist的期望值定义为E[x]=Integral(f(x) * dist.pdf(x))
    print(norm.expect(f1, loc=1, scale=2))
    print(norm.expect(f2, loc=2, scale=5))
    
    
    
# （2）lambda argument_list:expression用来表示匿名函数
# （3）numpy.exp(x)计算x的指数
# （4）numpy.inf表示正无穷大
# （5）scipy.integrate.quad(func,a,b)计算func函数从a至b的积分
# （6）scipy.stats.expon(scale)函数返回符合指数分布的参数为scale的随机变量rv
# （7）rv.moment(n,*args,*kwds)返回随机变量的n阶距
        #1st non-center moment of expon distribution whose lambda is 0.5
    E1 = lambda x: x*0.5*np.exp(-x/2)
    #2nd non-center moment of expon distribution whose lambda is 0.5
    E2 = lambda x: x**2*0.5*np.exp(-x/2)
    print(integrate.quad(E1, 0, np.inf))
    print(integrate.quad(E2, 0, np.inf))
    

#the code should not be changed
if __name__ == '__main__':
    nc_of_norm()