object lectures72 {
 
  def isPrime(x: Int): Boolean =
    (2 until x) forall { d => x%d!=0 }            //> isPrime: (x: Int)Boolean
    
  Stream (1,2,3)                                  //> res0: scala.collection.immutable.Stream[Int] = Stream(1, ?)
  (1 to 1000).toStream                            //> res1: scala.collection.immutable.Stream[Int] = Stream(1, ?)

  def streamRange (lo: Int, hi: Int): Stream[Int] =
  {
    println (lo+" ")
    if (lo >= hi) Stream.empty
    else Stream.cons (lo, streamRange(lo+1,hi))
  }                                               //> streamRange: (lo: Int, hi: Int)Stream[Int]
    
  def listRange (lo: Int, hi: Int): List[Int] =
    if (lo >= hi) Nil
    else lo :: listRange(lo+1, hi)                //> listRange: (lo: Int, hi: Int)List[Int]

  isPrime(110)                                    //> res2: Boolean = false

  streamRange(1,10).take(3).toList                //> 1 
                                                  //| 2 
                                                  //| 3 
                                                  //| res3: List[Int] = List(1, 2, 3)

}