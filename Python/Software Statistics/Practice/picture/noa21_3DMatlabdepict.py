#-*- coding:utf-8 -*-
from mpl_toolkits.mplot3d import Axes3D
from matplotlib import cm
from matplotlib.ticker import LinearLocator, FormatStrFormatter
import matplotlib.pyplot as plt
import numpy as np

# 下面为Matplotlib包进行3D作图的曲面图示例，请完成以下练习：
# （1）仔细阅读，理解代码含义，并运行代码，看看结果如何？
# （2）删掉负责缩放的代码，即23-26行，看看结果如何？
# （3）对于概率密度函数f(x,y)=x²+y²，怎么样绘制其曲面图？
# （4）对于概率密度函数f(x,y)=6e^(-2x-3y),x>0,y>0，怎么样绘制其曲面图？


# （1）此题为普通编程练习题
# （2）gca(**kwargs)返回当前Axes对象，如果没有则创建一个返回，当设定参数projection='3d'时返回Axes3D对象
# （3）numpy.meshgrid(*xi,**kwargs)利用坐标向量生成并返回坐标矩阵
# （4）Axes3D.plot_surface(X,Y,Z,*args,**kwargs)生成曲面图
# （5）Axes3D.set_zlim(bottom=None,top=None)设定Z轴下限、上限
# （6）mpl_toolkits.mplot3d.axis3d.Zxis.set_major_locator(Locator)设定Z轴locator
# （7）matplotlib.ticker.LinearLocator(numticks=None,presets=None)通过numticks进行数据切割
# （8）mpl_toolkits.mplot3d.axis3d.Zxis.set_major_formatter(Formatter)设定Z轴Formatter
# （9）matplotlib.ticker.FormatStrFormatter(fmt)利用'%操作符'格式对数据进行格式化
# （10）figure.colorbar(mappable,shrink=1.0,aspect=20)对mappable实例创建colorbar并返回，其中shrink参数调节缩放比例，aspect参数调节长短比例
def surface_3d():
    fig = plt.figure()
    #add a 3d subplot
    ax = fig.gca(projection='3d')
    #set X,Y,Z
    X = np.arange(-5, 5, 0.25)
    Y = np.arange(-5, 5, 0.25)
    X, Y = np.meshgrid(X, Y)
    R = np.sqrt(X**2 + Y**2)
    Z = np.sin(R)
    #create surface
    surf = ax.plot_surface(X, Y, Z, rstride=1, cstride=1, cmap=cm.coolwarm,
            linewidth=0, antialiased=False)

        ax.set_zlim(-1.01, 1.01)#set z limits
        ax.zaxis.set_major_locator(LinearLocator(10))#set tick locator to linear locator
        ax.zaxis.set_major_formatter(FormatStrFormatter('%.02f'))#set tick formatter to str formatter
        fig.colorbar(surf, shrink=0.5, aspect=5)#add a colorbar to plot

    plt.show()

#the code should not be changed
if __name__ == '__main__':
    surface_3d()