package objsets

import common._
import TweetReader._

object part4 {
  val google = List("android", "Android", "galaxy", "Galaxy", "nexus", "Nexus")
                                                  //> google  : List[java.lang.String] = List(android, Android, galaxy, Galaxy, ne
                                                  //| xus, Nexus)
  
  google.exists(s => s.length==5)                 //> res0: Boolean = true
  google.exists(s=>s.contains("galaxy"))          //> res1: Boolean = true
  TweetReader.allTweets.filter (t=>google.exists(s=>t.toString.contains(s))).ascendingByRetweet.head
                                                  //> res2: objsets.Tweet = User: engadget
                                                  //| Text: IRL: Dyson DC44, NUU ClickMate PowerPlus and the Galaxy S III -  http:
                                                  //| //t.co/5Duf2aa5 [8]
}