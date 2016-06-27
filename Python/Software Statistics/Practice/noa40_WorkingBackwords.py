# -*- coding: UTF-8 –*-
'''
Created on Jun 25, 2016

@author: Qiang
'''
# A 95% confidence interval for a population mean, u, is given as (18.985, 21.015). 
# This confidence interval is based on a simple random samples of 36 observations. 
# Calculate the sample mean and standard deviation.
#  Assume that all conditions necessary for inference are satisfied.
#  Use the t-distribution in any calculations.
# Output:[mean, standard deviation], accurate to the second decimal place
import numpy as np
import scipy.stats as stats

class Solution():
    def solve(self):
        a = 18.985
        b = 21.015
        df = 36
        p = 0.95
        mean = b - (b - a)/2
        biggest = b - mean
        fenweidian = stats.t.ppf(0.975,df)
        sdev = biggest/fenweidian*np.sqrt(df)
        print [round(mean,2),round(sdev,2)]
        return [round(mean,2),round(sdev,2)]
        
solu = Solution()
solu.solve()
        
        
    


