object integertest
{
  // The neighbors of x are x + 1 and x - 1
  def neighbors(x: Int): Stream[Int] = Stream(x-1,x+1)
                                                  //> neighbors: (x: Int)Stream[Int]
  def newNeighborsOnly(s: Stream[Int], explored: Set[Int]): Stream[Int] =
    s filterNot (x=>explored contains x)          //> newNeighborsOnly: (s: Stream[Int], explored: Set[Int])Stream[Int]

  val n0 = neighbors(4)                           //> n0  : Stream[Int] = Stream(3, ?)
  val explored0 = Set(3)                          //> explored0  : scala.collection.immutable.Set[Int] = Set(3)
  val nn0=newNeighborsOnly (n0,explored0)         //> nn0  : Stream[Int] = Stream(5, ?)

	def from(s: Stream[Int], explored: Set[Int]): Stream[Int] =
	{
	  if (s.isEmpty) Stream.empty
	  else
	  {
	    val exploreStart = s.head
	
	    // get new neighbours of current top of the set
	    val more = newNeighborsOnly (neighbors(exploreStart), explored)
	
	    // put the head of the stream first, then follow with the
	    // set built from the remainder of this stream concatenated with the
	    // new locations to explore and with the prior visited locations
	    // combined with the locations just found
	    exploreStart #:: from(s.tail#:::more, explored++more)
	  }
	}                                         //> from: (s: Stream[Int], explored: Set[Int])Stream[Int]

	val fromZero = from(Stream(0), Set(0))    //> fromZero  : Stream[Int] = Stream(0, ?)
	fromZero.take(9).toList                   //> res0: List[Int] = List(0, -1, 1, -2, 2, -3, 3, -4, 4)

 }