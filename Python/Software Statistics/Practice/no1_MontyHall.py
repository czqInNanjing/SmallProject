import random


def montyHall(select , change):
    result = random.randint(1, 3)
    if select == result:
        if change == 0:
            return 1
        else:
            return 0
    else:
        if change == 1:
            return 1
        else:
            return 0

n = 100000
win = 0
select = 0
change = 0
for i in range(0, n):
    select = random.randint(1, 3)
    change = random.randint(0, 1)
    win += montyHall(select, change)
print float(win)/float(n)

win = 0
for i in range(0, n):
    select = random.randint(1, 3)
    change = 1
    win += montyHall(select , change)
print float(win)/float(n)

win = 0
for i in range(0, n):
    select = random.randint(1, 3)
    change = 0
    win += montyHall(select , change)
print float(win)/float(n)

