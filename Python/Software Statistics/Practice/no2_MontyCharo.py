import random


def getAPoint():
    x = random.uniform(-1, 1)
    y = random.uniform(-1, 1)
    return x, y

n = 1000000
inside = 0
for i in range(0, n):
    (x, y) = getAPoint()
    if x*x + y*y < 1:
        inside += 1

print float(inside)/float(n)*4
