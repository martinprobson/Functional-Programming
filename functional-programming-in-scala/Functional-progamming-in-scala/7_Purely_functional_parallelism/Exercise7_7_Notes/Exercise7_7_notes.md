
# Working Notes on Exercise 7.7

## TODO
Complete this exercise once you are comfortable with Haskell.

## Notes 06-04-2018

[Theorems for Free!](http://ttic.uchicago.edu/~dreyer/course/papers/wadler.pdf)

### 1. Introduction

r in scala terms is a function of the type: -

```scala
def r[X](x: List[X]): List[X]
r: [X](x: List[X])List[X]
```

```scala
def map[A,B](x: List[A])(a: A => B): List[B] =
  x.foldRight(Nil:List[B]){ (a1,b) => a(a1) :: b }
```

```scala
map: [A, B](x: List[A])(a: A => B)List[B]
```

We can show that

map(r(a)) = r(map(a))

For example :-

r - reverse
map - a = _ + 1

```scala
map(List(1,2,3).reverse){_+1} = map(List(1,2,3)){_+1}.reverse
```


#### From Haskell wiki
Since a parametrically polymorphic value does not "know" anything about the unconstrained type variables, it must behave the same regardless of its type. 
This is a somewhat limiting but extremely useful property known as parametricity.
