
class Solution:

    def solve(self, A):
        result = {}
        for str in A:
            if str in result.keys():
                temp = result[str]
                temp += 1
                result[str] = temp
            else:
                result[str] = 1
        return result


solu = Solution()
test = ['apple', 'banana', 'cherry', 'pineapple', 'banana', 'peach', 'pear','peach', 'cherry' ]
print solu.solve(A=test)