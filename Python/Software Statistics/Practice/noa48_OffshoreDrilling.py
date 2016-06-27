# -*- coding: UTF-8 –*-
'''
Created on Jun 25, 2016

@author: Qiang
'''
# The table below summaries a data set that examines the response of a random sample of college graduates
#  and non-graduate on the topic of oil drilling. Complete a chi-square test for test data to check 
#  whether there is a statistically significant difference in responses from college graduates and non-graduates.

# College Grad?    Yes    No    Total
# Support    154    132    286
# Oppose    180    126    306
# Do not know    104    131    235
# Total    438    389    827

# [degree-of-freedom-of-distribution, statistical values, conclusion],'degree-of-freedom-of-distribution' and 'statistical values' are accurate to the second decimal place, 'conclusion' is True, which means the H0 is accepted, or False


import numpy as np
import scipy.stats as stats
class Solution():
    def solve(self):
        alpha = 0.05
        row1 = [154,132]
        row2 = [180,126]
        row3 = [104,131]
        data=[row1,row2,row3]
        kafang = stats.chi2_contingency(data)
        df = kafang[2]
        print kafang
        high = stats.chi.ppf(1 - alpha ,df)
        if  kafang[0] >= high:
            result = False
        else:
            result = True
        print [df , round(kafang[0],2) , result , high]
        return [df ,round(kafang[0],2) , result]
#         row1 = [186,38,35]
#         row2 = [227,54,45]
#         row3 = [219,78,78]
#         row4 = [355,112,140]
#         row5 = [653,285,259]
#         data = [row1,row2,row3,row4,row5]
#         kafang = stats.chi2_contingency(data)
#         print kafang
        
        
solu = Solution()
solu.solve()