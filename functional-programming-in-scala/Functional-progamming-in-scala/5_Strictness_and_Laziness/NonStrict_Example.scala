
/*
* Define a function add that logs every time it is called
*/
def add(x: Int, y: Int) = { 
  println(s"add called x: $x, y: $y") 
  x + y
}

/*
* This is the normal, strict version of double
*/
def doubleStrict(n: Int) = n + n

/*
* This is a non-strict version of double that uses
* 'thunks' to delay the evaluation of the two arguments
*/
def doubleNonStrictThunk(n: () => Int) = n() + n()

/*
* This uses the Scala non-strict syntax for the parameter,
* the scala compiler will translate this into a doubleNonStrictThunk
*/
def doubleNonStrict(n: => Int) = n + n

/*
* Demo use of lazy keyword to ensure the value is only evaluated when
* needed and cached for re-use
*/
def doubleNonStrictLazy(n: => Int) = {
  lazy val v = n
  v + v
}


println("This will evaluate n once")
doubleStrict(add(1,1))

println("This will evaluate n twice")
doubleNonStrictThunk( () => add(1,1))

println("This will evaluate n twice")
doubleNonStrict(add(1,1))

println("This will evaluate n once (due to lazy val")
doubleNonStrictLazy(add(1,1))
