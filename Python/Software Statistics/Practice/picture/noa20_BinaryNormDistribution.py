#-*- coding:utf-8 -*-
from mpl_toolkits.mplot3d import Axes3D
import matplotlib.pyplot as plt
import matplotlib.mlab as mlab
import numpy as np

def bivariate_normal():
    fig = plt.figure()
    #add a 3d subplot
    ax = fig.add_subplot(111, projection='3d')
    #set X,Y,Z
    x = np.linspace(-10, 10, 200)
    y = x
    X,Y = np.meshgrid(x, y)
    #create bivariate gaussian distribution for equal shape X,Y
    Z = mlab.bivariate_normal(X, Y, 1, 5, 0, -5, 0)
    #create surface
    ax.plot_surface(X, Y, Z, cmap='OrRd')

    plt.show()

#the code should not be changed
if __name__ == '__main__':
    bivariate_normal()