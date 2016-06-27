#-*- coding:utf-8 -*-

class Solution():
    def solve(self, A):
        result = []
        for i in A:
            if self.isPalindrome(A=i):
                result.append(i)
        return result



    def isPalindrome(self, A):
        strlen = len(A)
        for i in range(0,strlen/2):
            if(A[i] != A[strlen - 1 - i]):
                return False
        return True


solu = Solution()
testArray = ['123', '232' , '4556554' , '12123' , '3443' ,'1314131']
print solu.solve(A=testArray)