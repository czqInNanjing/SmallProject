#-*- coding:utf-8 -*-

class Solution():
    def describe(self, a):
        sum = 0
        for num in a:
            sum+=num
        result = []
        avg = float(sum)/len(a)
        result.append(avg)
        if len(a)==1:
            result.append(None)
        else:
            sum2 = 0
            for num in a:
                sum2 += (num-avg)*(num-avg)
            s = float(sum2) / (len(a)-1)
            s = round(s,6)
            result.append(s)
        if len(a)==1:
            result.append(0)
        else:
            up = 0
            down = 0
            for num in a:
                up+=(num-avg)**3
                down+=(num-avg)**2
            up = float(up)/len(a)
            down = (float(down)/len(a))**1.5
            piandu = up/down
            piandu = round(piandu,6)
            result.append(piandu)
        if len(a)==1:
            result.append(-3)
        else:
            up = 0
            down = 0
            for num in a:
                up+=(num-avg)**4
                down+=(num-avg)**2
            up = float(up)/len(a)
            down = (float(down)/len(a))**2
            fendu = up/down
            fendu = round(fendu,6)-3
            result.append(fendu)
        return result
solu = Solution()
print solu.describe(a=[1, 2, 3, 4, 5, 6, 7, 8, 8, 8, 8, 8, 8, 9, 10])