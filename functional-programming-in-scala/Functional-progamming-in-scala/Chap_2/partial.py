#
# Python version of partial function
#
from __future__ import print_function

def partial1(a,func):
    return lambda b: func(a,b)

add1 = partial1(1,lambda  a,b: a+b)

print(add1(1))

prefixHello = partial1("Hello ",lambda a,b: a+b)

print(prefixHello("Martin"))
