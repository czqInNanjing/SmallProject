# -*- coding: UTF-8 –*-
'''
Created on Jun 25, 2016

@author: Qiang
'''
# New York is known as "the city that never sleeps". 
# A random sample of 25 New Yorkers were asked how much sleep they get per night. 
# tatistical summaries of these data are shown below. 
#  Do these data provide strong evidence that New Yorkers sleep less than 8 hours per night on average? 
# Null-hypothesis is H0: u=8, and alpha is 0.05

# n    mean    stand-variance    min    max
# 25    7.73    0.77    6.17    9.7

# output
# [degree-of-freedom-of-distribution, statistical values, conclusion],
# 'degree-of-freedom-of-distribution' and 'statistical values' are accurate to the second decimal place,
#  'conclusion' is True, which means the H0 is accepted, or False

# If you were to construct a 90% confidence interval that corresponded to this hypothesis test, 
# would you expect 8 hours to be in the interval? Explain your reasoning.
import numpy as np
import scipy.stats as stats
###这道题是单边检验，所以拒绝域较小
## t-单边检验
class Solution():
    def solve(self):
        n = 25
        mean = 7.73
        svar = 0.77
        df = n - 1
        allMean = 8
        alpha = 0.05
        t = (mean - allMean)/svar*np.sqrt(n)
        judge = stats.t.ppf(1 - alpha,df)
        if(t < judge):
            result = True
        else:
            result = False
        
        print [df,round(t,2), result , judge]
        print judge
        return [df,round(t,2),result]
    
solu = Solution()
solu.solve()
        