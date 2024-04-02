import Exercise10_10.WCMonoid
import Exercise10_8.*
import Par.*

import java.util.concurrent.{ExecutorService, Executors}
//
// Exercise10.11 - Use the WC Monoid to implement a function that counts words in a string by
//                 recursively splitting it into substrings and counting the word in those substrings.
//
object Exercise10_11 extends App with Logging {

  lazy val useVirtualThreads: ExecutorService =
    Executors.newThreadPerTaskExecutor(
      Thread.ofVirtual().name("virtual-", 0).factory()
    )

  def count(s: String): Int = {
    def unstub(s: String) = s.length.min(1)
    def toWC(c: Char): WC = {
      if (c.isWhitespace)
        Part("",0,"")
      else
        Stub(c.toString)
    }
    parFoldMap(s.toIndexedSeq, WCMonoid)(c => toWC(c))(useVirtualThreads).get match {
      case Stub(s) => unstub(s)
      case Part(l,w,r) => unstub(l) + w + unstub(r)
    }

  }
  val res = count("lorem ipsum dolor sit amet, ")
  logger.info(s"res = $res")

}
