# -*- coding: UTF-8 –*-
'''
Created on Jun 25, 2016

@author: Qiang
'''
# A group of researchers are interested in the possible effects of distracting stimuli during eating, 
# such as an increase or decrease in the amount of food consumption. 
# To test this hypothesis, they monitored food in take for a group of 44 patients who were randomised into two equal groups.
#  The treatment group ate lunch while playing solitaire, and the control group ate lunch without any added distractions.
#   Patients in the treatment group ate 52.1 grams of biscuits, with a standard deviation of 45.1 grams, 
#   and patients in the control group ate 27.1 grams of biscuits with a standard deviation of 26.4 grams.
#    Do these data provide convincing evidence that the average food intake is different for the patients in the treatment group? 
#    Assume the conditions for inference are satisfied.
# Null hypothesis is H0: u_t - u_c = 0, alpha is 0.05

import numpy as np
import scipy.stats as stats
class Solution():
    def solve(self):
        mean1 = 52.1
        mean2 = 27.1
        svar1 = 45.1
        svar2 = 26.4
        n = 44
        group1 = 22
        group2 = 22
        df = group1+ group2 -2
        henNaLetter = 0
        alpha = 0.05
        sw = ( (group1 - 1)*(svar1**2) + (group2 - 1)*(svar2**2)  )/df
        t = (mean1 - mean2 - henNaLetter) / (np.sqrt(sw)*np.sqrt(1.0/group1 + 1.0/group2))
        
        judge = stats.t.ppf(1- alpha/2 , df)
        
        if(abs(t) > judge):
            result = False
        else:
            result = True
        
        print [df , round(t,2), result , judge]
        return [df , round(t,2) , result]
        

solu = Solution()
solu.solve()