#-*- coding:utf-8 -*-
from mpl_toolkits.mplot3d import Axes3D
import matplotlib.pyplot as plt
import numpy as np
# （3）Axes3D.bar3d(x,y,z,dx,dy,dz,color=u'b',*args,**kwargs)绘制多直方图，其中x,y,z为数组
def random_variables():
    fig = plt.figure()
    #add a 3d subplot
    ax = fig.add_subplot(111, projection='3d')
    #set X,Y,Z
    dx=0.3
    dy=0.3
    dz=[0.02, 0.75, 0.35, 0.1, 0.15, 0.04, 0.25, 0.04, 0.025]
    i=0
    for xpos in [1, 2, 3]:
        for c, ypos, zpos in zip(['r','y','g'], [-1, 0, 1] , [-0.5 , 0 , 0.5]):
            ax.bar3d(xpos, ypos, zpos, dx, dy, dz[i], color=c)
            i=i+1

    plt.show()

#the code should not be changed
if __name__ == '__main__':
    random_variables()