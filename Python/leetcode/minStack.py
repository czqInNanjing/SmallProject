#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""#155 Min Stack"""
# Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.
#
# push(x) -- Push element x onto stack.
# pop() -- Removes the element on top of the stack.
# top() -- Get the top element.
# getMin() -- Retrieve the minimum element in the stack.
# Example:
# MinStack minStack = new MinStack();
# minStack.push(-2);
# minStack.push(0);
# minStack.push(-3);
# minStack.getMin();   --> Returns -3.
# minStack.pop();
# minStack.top();      --> Returns 0.
# minStack.getMin();   --> Returns -2.
__author__ = 'Qiang Chen'


class MinStack(object):
    def __init__(self):
        """
        initialize your data structure here.
        """
        self.__stack__ = []
        self.__min_stack__ = []

    def push(self, x):
        """
        :type x: int
        :rtype: void
        """
        if len(self.__stack__) == 0 or self.__min_stack__[-1] > x:
            self.__min_stack__.append(x)
        self.__stack__.append(x)

    def pop(self):
        """
        :rtype: void
        """
        if len(self.__stack__) != 0:
            if self.__min_stack__[-1] == self.__stack__[-1]:
                self.__min_stack__.pop()
            self.__stack__.pop()

    def top(self):
        """
        :rtype: int
        """
        if len(self.__stack__) != 0:
            return self.__stack__[-1]

    def getMin(self):
        """
        :rtype: int
        """
        if len(self.__min_stack__) != 0:
            return self.__min_stack__[-1]


# Your MinStack object will be instantiated and called as such:
# obj = MinStack()
# obj.push(x)
# obj.pop()
# param_3 = obj.top()
# param_4 = obj.getMin()

if __name__ == '__main__':
    min_stack = MinStack()
    min_stack.push(-2)
    min_stack.push(0)
    min_stack.push(-3)
    print(min_stack.getMin())
    min_stack.pop()
    min_stack.pop()
    min_stack.pop()
    print(min_stack.top())
    print(min_stack.getMin())
