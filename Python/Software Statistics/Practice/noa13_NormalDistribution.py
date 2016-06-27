import numpy as np
import matplotlib.pyplot as plt
from scipy.stats import norm
from scipy.stats import expon
def normalDistribution(mean, var):
    x = np.linspace(-10 , 10 , 10000)
    rv = norm(mean, var)
    y = rv.pdf(x)
    y2 = rv.cdf(x)
    
    plt.plot(x, y)
    plt.plot(x, y2)
    plt.show()

def exponDistribution(lamda):
    x = np.linspace(0, 20, 1000)
    rv = expon(lamda)
    y = rv.pdf(x)
    plt.plot(x, y)
    plt.show()


normalDistribution(0, 1)
# exponDistribution(0.5)