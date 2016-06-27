class Solution():
    def describe(self, a):
        print (a)
        result = []
        length = float(len(a))
        sum = 0
        for i in a:
            sum += i
        result.append(round(sum/len(a) , 6))
        b2 = b3 = b4 = 0
        jianfang = []
        for i in a:
            jianfang.append(i - result[0])

        for i in jianfang:
            b2 += i*i
            b3 += i**3
            b4 += i**4

        b2with = b2/length
        b3 /= length
        b4 /= length
        print jianfang
        print b2
        print b3
        print b4
        if length == 1:
            result.append(None)
            result.append(-3)
            result.append(0)
            print result
            return result
        else:
            result.append(round(b2 / (length - 1), 6))
            result.append(round( (b3/b2with**1.5) , 6))
            result.append(round( b4/(b2with**2) - 3, 6))



        print 'result '
        print (result)
        return result


solu = Solution()
# solu.describe(a=[1, 2, 3, 4, 5, 6, 7, 8, 8, 8, 8, 8, 8, 9, 10])
solu.describe(a=[1, 2, 3, 4, 5, 6, 7, 8, 8, 8, 8, 8, 8, 9, 10])