#-*- coding:utf-8 -*-
#  (1）仔细阅读，理解代码含义，并运行代码，看看结果如何？
# （2）修改X、Y，使得X、Y位于长为2，宽为1的长方形G内
# （3）对f(x,y)=1/π,x²+y²<=1代表的二维均匀分布进行图形展现，注意X、Y处于什么区域范围内

# （2）gca(**kwargs)返回当前Axes对象，如果没有则创建一个返回，当设定参数projection='3d'时返回Axes3D对象
# （3）numpy.meshgrid(*xi,**kwargs)利用坐标向量生成并返回坐标矩阵
# （4）Axes3D.plot_surface(X,Y,Z,*args,**kwargs)生成曲面图

import random
import math
from mpl_toolkits.mplot3d import Axes3D
import matplotlib.pyplot as plt
import numpy as np

def uniform_distribution():
    fig = plt.figure()
    #add a 3d subplot
    ax = fig.gca(projection='3d')
    #set X,Y,Z
    X = np.arange(-1, 1, 0.01)
    Y=[]
    for i in X:
        low = -math.sqrt(1 - i**2)
        print low
        print np.arange(low , -low , 0.01)
        Y = np.append(Y, np.arange(low , -low , 0.01))
    print X
    print len(Y)
#     Y = np.arange(0, 2, 0.1)
    #create coordinate matrices
    X, Y = np.meshgrid(X, Y)
    print X
    print Y
    Z1 = 1
    Z2 = 0
    #create surface 1
    surf = ax.plot_surface(X, Y, Z1, color='b')
    #create surface 2
    surf = ax.plot_surface(X, Y, Z2, color='r')

    plt.show()

#the code should not be changed
if __name__ == '__main__':
    uniform_distribution()