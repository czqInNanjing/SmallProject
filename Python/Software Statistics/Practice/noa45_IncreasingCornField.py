# -*- coding: UTF-8 –*-
'''
Created on Jun 25, 2016

@author: Qiang
'''
# A large farm wants to try out a new type of fertilizer to evaluate whether it will improve the farm's corn production. 
# The land is broken into plots that produce an average of 1.215 pounds of corn with a standard deviation of 94 pounds per plot. 
# The owner is interested in detecting any average difference of at least 40 pounds per plot. 
# How many plots of land would be needed for the experiment if the desired power level is 90%? 
# Assume each plot of land gets treated with either the current fertilizer or the new fertilizer.
# output:
# plot_num, int type

# Difference we care about: 40. Single tail of 90%: 1.28 × SE.
#  Rejection region bounds: ±1.96 × SE (if 5% significance level). Setting
#   942 942 3.24×SE = 40, subbing in SE = n + n ,
# and solving for the sample size n gives 116 plots
# oflandforeachfertilizer.


##result is 116