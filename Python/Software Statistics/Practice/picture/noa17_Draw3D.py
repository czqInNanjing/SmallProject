#-*- coding:utf-8 -*-
from mpl_toolkits.mplot3d import Axes3D
import matplotlib.pyplot as plt
import numpy as np

def bars_3d():
    fig = plt.figure()
    #add a 3d subplot
    ax = fig.add_subplot(111, projection='3d')
    for c, z in zip(['r', 'g', 'b', 'y'], [40, 20, 10, 0]):
        xs = np.arange(20)
        ys = np.random.rand(20)

        # cs is an array to set color
        cs = [c] * len(xs)
        # the first bar of each set will be colored cyan.
        cs[0] = 'c'
        ax.bar(xs, ys, zs=z, zdir='z', color=cs, alpha=0.8) # zdir

    # set label
    ax.set_xlabel('X')
    ax.set_ylabel('Y')
    ax.set_zlabel('Z')

    # for c,z in zip(['y','b','g','r'],[40,30,20,10]):
    #     xs = np.arange(20)
    #     ys = np.random.randint(30)




    plt.show()

#the code should not be changed
if __name__ == '__main__':
	bars_3d()