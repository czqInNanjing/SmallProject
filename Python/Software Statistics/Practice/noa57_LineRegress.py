# -*- coding: UTF-8 –*-
'''
Created on Jun 25, 2016

@author: Qiang
'''
# （2）scipy.stats.Linregress(x,y=None)计算两组测量值X，Y之间的回归曲线，并返回曲线斜率slope、曲线截距intercept、相关系数r-value，检验值p-value，估计标准误差stderr
# 回归分析是确定两种或两种以上变量之间相互依赖的定量关系的方法，分为一元回归分析和多元回归分析、线性回归分析和非线性回归分析等。下面为回归分析的python实现，请完成以下练习：
# （1）仔细阅读，理解代码含义，并运行代码，看看结果如何？
# （2）假如X、Y之间满足y=a*x**2+b的关系，怎么随机生成X、Y变量并画出其回归曲线呢？
# （3）假如X、Y之间满足y=a*e**x+b的关系，怎么随机生成X、Y变量并画出其回归曲线呢？
import matplotlib.pyplot as plt
import numpy as np
from scipy import stats

def linregress1():
    x = np.linspace(-5, 5, num=150)
    y = x + np.random.normal(size=x.size)
    y[12:14] += 10 
    
    slope, intercept, r_value, p_value, std_err = stats.linregress(x,y)
    print(slope)
    print(intercept)
    print(r_value)
    print(p_value)
    print(std_err)
    
    plt.plot(x, y, 'b.')
    plt.plot(x, slope * x + intercept, 'r-')
    plt.show()
    
def linregress2():
    x = np.linspace(-5, 5, num=150)
#     y = 5*x**2 + np.random.normal(size=x.size)
    y = 3*np.e**x + np.random.normal(size=x.size) + 4
    y[12:14] += 10 
    y[137:141] -= 6
    x1=np.e**x
    slope, intercept, r_value, p_value, std_err = stats.linregress(x1,y)
    print(slope)
    print(intercept)
    print(r_value)
    print(p_value)
    print(std_err)
    
    plt.plot(x, y, 'b.')
    plt.plot(x, slope * x1 + intercept, 'r-')
    plt.show()

#the code should not be changed
if __name__ == '__main__':
#     linregress1()
    linregress2()