# Functional Programming

## Introduction
Documentation, code and learning material produced whilst learning FP (mainly in Scala and Haskell).

## Links

[Functional Programming in Scala](functional-programming-in-scala/README.md)

[Scala with CATS](scala-with-cats/README.md)

## External Links

[Learn you a Haskell for the Great Good!](http://learnyouahaskell.com) 

[A Gentle Introduction to Haskell](https://www.haskell.org/tutorial/index.html)

[Monads for functional programming](https://homepages.inf.ed.ac.uk/wadler/papers/marktoberdorf/baastad.pdf)

[Functional Progamming in Scala - "The Red Book"](https://www.manning.com/books/functional-programming-in-scala)

[Scala with CATS](https://underscore.io/books/scala-with-cats/)

## To use Gollum to view/edit the markdown documentation

### Build the docker image

```
docker build -t gollum .
```

### Start the container

```
docker run -v `pwd`:/wiki -p 4567:80 gollum
```

### View the Documentation
Go to `http://127.0.0.1:4567`
