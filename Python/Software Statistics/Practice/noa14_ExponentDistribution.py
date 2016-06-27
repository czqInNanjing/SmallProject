import numpy as np
import matplotlib.pyplot as plt
from scipy.stats import expon
def exponDistribution(lamda):
    x = np.linspace(0.01, 20, 1000)
    rv = expon(scale = lamda)
    y = rv.pdf(x)
    y2 = rv.cdf(x)
    plt.plot(x, y)
    plt.plot(x,y2 , color = 'r')
    plt.show()



exponDistribution(0.5)