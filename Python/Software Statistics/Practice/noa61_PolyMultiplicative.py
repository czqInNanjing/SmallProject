# -*- coding: UTF-8 –*-
'''
Created on Jun 25, 2016

@author: Qiang
'''
# 在Numpy中，多项式函数的系数可以用一维数组表示，例如对于f(x)=2x^3-x+1可表示为f=np.array([2.0,0.0,-1.0,1.0])，而np.poly1d()方法可以将多项式转换为poly1d(一元多项式)对象，
# 返回多项式函数的值，请利用poly1d()方法计算多项式g(x)(例如g(x)=x^2+2x+1)和f(x)的乘积并将结果返回。
# 输入说明:
# 
# 多项式g(x)的一维数组表示 
# 输出说明:
# 
# f(x)*g(x)的poly1d对象
# 样例输入:
# 
# np.array([1, 2, 1])
# 样例输出:
# 
# np.poly1d([2.0,4.0,1.0,-1.0,1.0,1.0])
# 提示:
# 
# 样式输出结果表示两个多项式f(x)*g(x)=2x^5+4x^4+x^3-x^2+x+1
import numpy as np

class Solution():
    def solve(self, A):
        f = np.array([2.0,0.0,-1.0,1.0])
        g = A
        return np.poly1d(np.polymul(f, g))
    

solu = Solution()
print solu.solve(np.array([1, 2, 1]))
        