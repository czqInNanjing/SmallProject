# -*- coding: UTF-8 –*-
'''
Created on Jun 25, 2016

@author: Qiang
'''
# Rock-paper-scissors is a hand game played by two or more people where players choose to sign either rock, paper or scissors with their hands.
# For your statistics class project, you want to evaluate whether players choose between these three options randomly,
#  or if certain options are favoured above others. You ask two friends to play rock-paper-scissors 
#  and count the time each option is played. The following table summaries the data:
# Rock    Paper    Scissors
# 43    21    35
# 
# Use these data to evaluate whether players choose between these three options randomly, or if certain options are favored above others. alpha is 0.05.
# 
# [degree-of-freedom-of-distribution, statistical values, conclusion],'degree-of-freedom-of-distribution' and 'statistical values' are accurate to the second decimal place, 'conclusion' is True, which means the H0 is accepted, or False


import scipy.stats as stats

class Solution():
    def solve(self):
        a = [43,21,35]
        alpha = 0.05
        kafang = stats.chisquare(a)
        df = len(a) - 1
        
        if(kafang[1] < 1-alpha):
            result = False
        else:
            result = True
        print kafang
        print [df , round(kafang[0],2),result]
        return [df, round(kafang[0],2) ,result]

solu = Solution()
solu.solve()