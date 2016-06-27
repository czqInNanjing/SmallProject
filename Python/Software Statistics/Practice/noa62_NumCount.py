# -*- coding: UTF-8 –*-
'''
Created on Jun 25, 2016

@author: Qiang
'''
class Solution:
    
    def solve(self, A):
        result = {}
        for i in range(10):
            result[i] = 0
        for str in A:
            for i in str:
                result[int(i)] += 1
        return result


solu = Solution()
test = ['12','34','567', '36','809','120']
print solu.solve(A=test)