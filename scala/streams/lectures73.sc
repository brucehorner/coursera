object lectures73 {

  def isPrime(x: Int): Boolean = (2 until x) forall { d => x%d!=0 }
                                                  //> isPrime: (x: Int)Boolean
  
  def expr =
  {
    val x = { println("x"); 1 }
    lazy val y = { println("y"); 2 }
    def z = { println("z"); 3 }
    z + y + x + z + y + y
  }                                               //> expr: => Int
  
  expr                                            //> x
                                                  //| z
                                                  //| y
                                                  //| z
                                                  //| res0: Int = 13
  
  def streamRange (lo: Int, hi: Int): Stream[Int] =
    if (lo >= hi) Stream.empty
    else Stream.cons (lo, streamRange(lo+1,hi))   //> streamRange: (lo: Int, hi: Int)Stream[Int]
   
  (streamRange(1000,10000) filter isPrime) apply 0//> res1: Int = 1009
  (streamRange(1000,10000) filter isPrime)(1)     //> res2: Int = 1013
  (streamRange(1000,10000) filter isPrime)(2)     //> res3: Int = 1019
}