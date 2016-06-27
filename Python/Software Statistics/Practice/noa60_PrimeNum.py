#-*- coding:utf-8 -*-
import math
class Solution():
    def solve(self, A):
        result = []
        for i in A:
            if self.prime(i):
                result.append(i)
        return result
    def prime(self, x):
        if x == 1 or x == 2 :
            return True
        largestNum = int(math.sqrt(x) + 1)
        print largestNum
        for i in range(2, largestNum):
            if x%i == 0:
                return False
        return True
solu = Solution()
testArray = [23, 45, 76, 67, 17]
print solu.solve(A=testArray)