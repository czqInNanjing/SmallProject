# -*- coding: UTF-8 –*-
'''
Created on Jun 25, 2016

@author: Qiang
'''
# Georgianna claims that in a small city renowned for its music school, the average child takes at least 5 years of piano lessons. 
# We have a random sample of 20 children from the city with a mean of 4.6 years of piano lessons
#  and a standard deviation of 2.2 years. Evaluate Georgianna's claims using a hypothesis test.
#   alpha is 0.05. 
# Extra:
# (1) Construct a 95% confidence interval for the number of years students in this city takes piano lessons and interpret it in context of the data.
# (2) Do your results from the hypothesis test and the confidence interval agree? Explain your reasoning.

# [degree-of-freedom-of-distribution, statistical values, conclusion],
# 'degree-of-freedom-of-distribution' and 'statistical values' are accurate to the second decimal place, 
# 'conclusion' is True, which means the H0 is accepted, or False
import numpy as np
import scipy.stats as stats

class Solution():
    def solve(self):
        mean = 4.6
        svar = 2.2
        allMean = 5
        n = 20
        df = n - 1
        # use t-distribution
        alpha = 0.05
        
        t = (mean - allMean)/svar*np.sqrt(n)
        judge = stats.t.ppf(alpha , df)
        if(t < judge):
            result = False
        else:
            result = True
        print [df, round(t,2) , result , judge]
        
        return [df, round(t,2) , result]

solu = Solution()
solu.solve()
        
    