# coding=utf-8
# 创建一个ndarray数组对象，输入一组100以内的整数，数组shape为4*4，
# 将数组中所有元素的总和、每行的平均值以及每列的平均值共9个数存入一个列表中，并返回该列表。

import numpy as np

class Solution():
    def solve(self,A):
        a = np.array(A)
        sum = 0
        result = []
        for i in range(9):
            result.append(0)
        for i in range(len(a)):
            for j in range(len(a[0])):
                sum += a[i][j]
                result[i + 1] += a[i][j]
                result[j + 5] += a[i][j]
        
        for i in range(9):
            result[i] = result[i]/float(4)
        
        result[0] = sum
        return result



soult = Solution()
print soult.solve([[1 ,2 ,3, 4], [ 5, 6 ,7 ,8] ,[ 9, 10, 11, 12], [13, 14, 15, 16]] )