# Functional Programming in Scala

## Chapter 10 - Monoids

### Note 1
Note that [Excercise10_8](src/main/scala/Exercise10_8.scala) and the corresponding 
[Excercise10_8Test](src/test/scala/Exercise10_8Test.scala) requires the Par library from chapter 7.

### Note 2
[Excercise10_8Test](src/test/scala/Exercise10_8Test.scala) includes a test to exercise the `parFoldMap` function
by running sum against an indexed sequence containing 1,000,000 integers. The `ExcecutorService` configured uses
 Java 21 > virtual threads. If we used the `usePlatformThreads` instead then the code will probably crash with an 
OOM error.


