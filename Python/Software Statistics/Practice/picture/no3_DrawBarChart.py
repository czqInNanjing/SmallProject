import numpy as np
import matplotlib.pyplot as plt


# you can write your code here
def drawBarChart():
    #get input data
    menMeans = (20, 35, 30, 35, 27)
    menStd = (2, 3, 4, 1, 2)
    womenMeans = (25, 32, 34, 20, 25)
    womenStd = (3, 5, 2, 3, 3)

    ind = np.arange(5)
    width = 0.35
    # the histogram of the data
    plt.bar(ind, menMeans, width, color='r')
    plt.bar(ind+width, womenMeans, width, color='y')
    
    #show image
    plt.show()

def get_data():
    # example data
    mu = 100 # mean of distribution
    sigma = 15 # standard deviation of distribution
    x = mu + sigma * np.random.randn(8)
    return x

def drawPieChart():
    #get input data
    x = get_data()
    c = ('b', 'g', 'r', 'c', 'm', 'y', 'k', 'w')
    # the pie chart of the data
    plt.pie(x, colors=c, shadow=False)
    plt.show()



drawBarChart()
# drawPieChart()

