import cats.data.Reader

case class Cat(name: String, favouriteFood: String)

val catName: Reader[Cat,String] = Reader( cat => cat.name)

catName.run(Cat("Garfield","lasagne"))

val greetKitty: Reader[Cat,String] = catName.map(name => s"Hello $name")

val feedKitty: Reader[Cat,String] = Reader(cat => s"Have a nice bowl of ${cat.favouriteFood}")

val greetAndFeed: Reader[Cat,String] = 
  for {
    greet <- greetKitty
    feed <- feedKitty
  } yield s"$greet. $feed"

val _greetAndFeed: Reader[Cat,String] =  greetKitty.flatMap{ g => feedKitty.map{ f => s"$g. $f"}}

greetAndFeed(Cat("Garfield","lasagne"))
greetAndFeed(Cat("Heathcliff","junk food"))

_greetAndFeed(Cat("Garfield","lasagne"))
_greetAndFeed(Cat("Heathcliff","junk food"))

