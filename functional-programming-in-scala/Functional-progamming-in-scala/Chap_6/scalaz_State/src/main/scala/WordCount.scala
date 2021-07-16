

//import scalaz.State._
import scalaz.Scalaz._
import scalaz._

object WordCount {
  /*
  * Split a String into a List of words.
  */
  def words(s: String) = s.split("[ ]+") map {_.trim.toUpperCase }

  /*
  * Count the occurence of each word in the passed String
  * by folding a map of (word,count) over the word list.
  */
  def wordCount2(s: String) =
    words(s).foldLeft(Map.empty[String,Int]){ (map,word) => map + (word -> (map.getOrElse(word,0) + 1))}

  /*
  *  Same as above but a map is passed into the function to use
  *  in the foldLeft.
  */
  def wordCount(s: String, currMap: Map[String,Int]) = 
    words(s).foldLeft(currMap){ (map,word) => map + (word -> (map.getOrElse(word,0) + 1))}

  def wordCount3(s: String): State[Map[String,Int],Unit] =
    State{ (m: Map[String,Int]) => val m2 = wordCount(s,m)
                                   (m2,()) }

  def wordCount4(s: String): State[Map[String,Int],Unit] =
    for {
      a <- init[Map[String,Int]]				// Start with a State[Map[String,Int],Map[String,Int]]
      r <- modify[Map[String,Int]]{ m => wordCount(s,m) }       // Modify our State to contain (wordCount)
      } yield r

  def wordCounts(str: String) = modify { currMap: Map[String, Int] =>
    words(str).foldLeft(currMap) { (map, word) =>
      map + (word -> (map.getOrElse(word, 0) + 1))
    }
  }
}


import WordCount._

object WordCountExample extends App {
  val s1 = "The The Quick Quick Brown Brown Fox FOx Jumped Jumped Over the lazy dog"
  val s2 = "The The Quick Quick Brown Brown Fox FOx Jumped Jumped Over the lazy dog"
  val s3 = "The The Quick Quick Brown Brown Fox FOx Jumped Jumped Over the lazy dog"

  val tst = modify { s: String => s}

  val map1 = wordCount(s1,Map.empty[String,Int])
  val map2 = wordCount(s2,map1)
  val map3 = wordCount(s3,map2)
  println(map3)

  val l = List(s1,s2,s3)
  val m = l.foldLeft(Map.empty[String,Int]){ (map,s) => wordCount(s,map) }
  println(m)

  println("""wordCount3("The The The A")""" + wordCount3("The The The A").run(Map.empty[String,Int]))
  /* traverseS - Works like a map and then a fold.
                 it applys a function to each item in the list (wordCount4 in this case)
                 in the same way that map does. It also folds the resulting States into
                 a single State and then collects the A values and puts them into a list.
  */             
  val m2 = l traverseS wordCount4
  val (m3,_) = m2.run(Map.empty[String,Int])
  println(m3)

  val l1 = List.fill(1000)("The Quick Brown Fox jumped over the lazy dog")
  val (m4,_) = l1.traverseS(wordCount4).run(Map.empty[String,Int])
  println(m4)

}


