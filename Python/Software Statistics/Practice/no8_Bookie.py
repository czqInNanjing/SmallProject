# coding=utf-8
import random
from _random import Random
import numpy as np

# 下面为分赌本问题的python实现代码，规则如下：
# （1）赌徒甲、乙两赌徒赌技相同，每局无平局，他们约定，谁先赢得十局则得到全部赌本。
# （2）当前甲赢了5局，乙赢了2局，因故终止赌博，请问他们按照怎样的比例分赌本？
# 下面代码中已经给出此种情况下的实现代码，其中Bookie1函数模拟每次甲、乙赌博的情况，simulate1函数模拟进行10000次赌博后甲乙各自胜出概率
# （1）请仔细阅读，理解代码含义，并运行代码，看看结果如何？思考，如果甲、乙现在都是一局都没赢，Bookie1函数的参数应该怎么设置呢？
# （2）如果模拟5个人赌博的分赌本问题，Bookie2函数应该怎么写呢？
# （3）附加：如果甲乙赌技不相等情形下，每次比赛中甲胜出的概率为2/3，Bookie1函数又应该怎么写呢？
def bookie(n, na, nb, aRate):
    split = aRate*100

    for i in range( 0 , 2*n-na-nb-1):
        
        randomNum = random.randint(0, 100)
        whoWin = 1
        if (split > randomNum):
            whoWin = 0

        if(whoWin == 0):
            nb += 1
        else:
            na += 1
        if na == n:
            return 0
        elif nb == n:
            return 1
        

def bookie2(n, n1, n2, n3, n4, n5):
    a = [n1, n2,n3,n4,n5]
        
    for i in range(n**3):
        randnum = random.randint(1 ,5)
        a[randnum - 1] += 1
        if(a[randnum - 1] == n):
            return randnum
    
    return 0
            
def simulate1():
    n = 100000
    awin = 0
    for i in range(0, n):
        if bookie(3 , 1 , 2 , 0.5) == 0:
            awin += 1

    return awin/float(n)

def simulate2():
    n = 10000
    win1 = 0
    win2 = 0
    win3 = 0
    win4 = 0
    win5 = 0
    for i in range(n):#simulate 10000 games
        #simulate game whose total number is 10, and player1 and player3 won 1
        result = bookie2(10,1,0,1,0,0)
        if result==1:
            win1+=1
        elif result==2:
            win2+=1
        elif result==3:
            win3+=1
        elif result==4:
            win4+=1
        elif result==5:
            win5+=1
    print('player1 wins: ' + str(float(win1)/float(n)))
    print('player2 wins: ' + str(float(win2)/float(n)))
    print('player3 wins: ' + str(float(win3)/float(n)))
    print('player4 wins: ' + str(float(win4)/float(n)))
    print('player5 wins: ' + str(float(win5)/float(n)))

print simulate1()
simulate2()