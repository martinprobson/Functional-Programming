
# Contents

## Default Package
This package contains the Scala implementations of the Monads as described in section 2 of the 
paper [Monads for functional programming](http://homepages.inf.ed.ac.uk/wadler/papers/marktoberdorf/baastad.pdf)
note that these monad implementations do not use the CATS library.

## `cats` Package
This package contains code that imports from the CATS library.

* `CatsWriter.scala` contains the CATS Writer Monad used to log factorials.
* `cats_monads.CatsReader.scala` contains example code using the CATS Reader Monad.