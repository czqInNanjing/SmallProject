# -*- coding: UTF-8 –*-
'''
Created on Jun 25, 2016

@author: Qiang
'''
# Is there strong evidence of global warming? 
# Let's consider a small scale example, comparing how temperatures have changed is the US from 1968 to 2008. 
# The daily high temperature reading on January 1 was collected in 1968 and 2008 for 51 randomly selected locations in the continental US. 
# Then the difference between the two readings (temperature in 2008 - temperature in 1968) was calculated 
# for each of the 51 values was 1.1 degree with a standard deviation of 4.9 degrees. 
# We are interested in determining whether these data provide strong evidence of temperature warming in the continental US.
# (1) Is there a relationship between the observations collected in 1968 and 2008? Or are the observations in the two groups independent? Explain
# (2) What's the hypothesis for this research?
# (3) Check the conditions required to complete this test.
# (4) Calculate the freedom, test statistical value and give the conclusion. alpha is 0.05, coding this part
# (5) What type of error might we have made?
# (6) Based on the results of this hypothesis test, would you expect a confidence interval for the average difference between the temperature measurements from 1968 and 2008 to include 0? Explain

# [degree-of-freedom-of-distribution, statistical values, conclusion],'degree-of-freedom-of-distribution' and 'statistical values' are accurate to the second decimal place, 'conclusion' is True, which means the H0 is accepted, or False
import numpy as np
import scipy.stats as stats
class Solution():
    def solve(self):
        mean = 1.1
        svar = 4.9
        allMean = 0
        n = 51
        df = n - 1
        # use t-distribution
        alpha = 0.05
        
        t = (mean - allMean)/svar*np.sqrt(n)
        judge = stats.t.ppf(1 - alpha , df)
        if(t < judge):
            result = True
        else:
            result = False
        print [df, round(t,2) , result , judge]
        
        return [df, round(t,2) , result]

solu = Solution()
solu.solve()