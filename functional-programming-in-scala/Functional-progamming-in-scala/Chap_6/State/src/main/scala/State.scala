case class State[S,+A](run: S => (A,S)) {

  def modify[S](f: S => S) = State { s: S => ((), f(s)) }
  /*
  * Exercise 6.10 - Generalize the functions unit, map, map2, flatMap
  *                 and sequence.
  */
  def flatMap[B](f: A => State[S,B]): State[S,B] = 
     State( (s1) => { val (a,s2) = run(s1)
                      f(a).run(s2) 
                    } )
  def map[B](f: A => B): State[S,B] = 
     State( (s1) => { val (a,s2) = run(s1)
                      (f(a),s2) 
                    } )

  /*
  *  map in terms of flatMap
  */
  def map_FM[B](f: A => B): State[S,B] =
    flatMap{ a => State( (s1) => (f(a),s1))}


  def map2[B,C](rb: State[S,B])(f: (A,B) => C): State[S,C] =
     State( (s1) => { val (a,s2) = run(s1)
                      val (b,s3) = rb.run(s2)
                     (f(a,b),s3)
                    })
}

object State {

  def unit[S, A](a: A): State[S,A] =
    State(s => (a,s))


  def get[S] = State{  (s: S) => (s,s) } 
  def put[S](newState: S) = State{ s: S => ( (), newState ) }


  def sequence[S,A](fs: List[State[S,A]]): S => (List[A],S) =
     State( (s1: S) => {
         fs.foldRight( (Nil: List[A],s1))( (s: State[S,A], l: (List[A],S) ) => {
             val (a,s2) = s.run(l._2)
             (a :: l._1,s2) })
       }).run

}


import State._

object StateMain extends App {
  /*
  * Playing around with our State object
  */
  val m1 = State { s: String => (s.size, s) }
  def repeat(num: Int): State[String,Unit] = State { s: String => ( (),s * num) }

  val t = m1.flatMap(repeat).run("hello")
  println(s"t = $t")
  val t2 = m1.map(i => i * 2).map( i => i * 10).flatMap(repeat).run("hello")
  println(s"t2 = $t2")
  println(get[String].run("hello"))

  val t3 = get[String]
          .modify { s: String => s * s.size }
          .flatMap( { _  => get[String] } )
          .map( { s1 => s1.size } )
          .run("hello")
  println(s"t3 = $t3")

  val t4 = get[String]
          .flatMap( { s0 => put(s0 * s0.size) } )
          .flatMap( { _  => get[String] } )
          .map( { s1 => s1.size } )
          .run("hello")
  println(s"t4 = $t4")
  

  assert(t3 == t4 )

  val t5 = State{ s: String => (s.size,s) }.modify[String]( _.reverse)
    .modify[String]( s: String => s.toUpperCase).run("hello")

  println(s"t5 = $t5")

  val t6 = for {
    s0 <- get[String]                   // get[String]
    _  <- put( s0 * s0.size)            // flatMap( { s0 => put(s0 * s0.size) } )
    s1 <- get[String]                   // flatMap( { _  => get[String] } )
  } yield s1.size                       // map( { s1 => s1.size } )
  val t7 = t6.run("hello")
  println(s"t7 = $t7")

  val t8 = get[String]
          .modify( { s:String => s * s.size } )
          .flatMap( { _  => get[String] } )
          .map( { s1 => s1.size } )
          .run("hello")
  println(s"t8 = $t8")
}

