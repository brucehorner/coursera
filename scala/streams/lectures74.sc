object lectures74 {

  def from (n: Int): Stream[Int] = n #:: from(n+1)//> from: (n: Int)Stream[Int]
  
  from(4)                                         //> res0: Stream[Int] = Stream(4, ?)
  from(4)(1)                                      //> res1: Int = 5
  from(4)(2)                                      //> res2: Int = 6
  
  val nats = from(0)                              //> nats  : Stream[Int] = Stream(0, ?)
  val m4s = nats map (_ * 4)                      //> m4s  : scala.collection.immutable.Stream[Int] = Stream(0, ?)
  
  (m4s take 100).toList                           //> res3: List[Int] = List(0, 4, 8, 12, 16, 20, 24, 28, 32, 36, 40, 44, 48, 52, 
                                                  //| 56, 60, 64, 68, 72, 76, 80, 84, 88, 92, 96, 100, 104, 108, 112, 116, 120, 12
                                                  //| 4, 128, 132, 136, 140, 144, 148, 152, 156, 160, 164, 168, 172, 176, 180, 184
                                                  //| , 188, 192, 196, 200, 204, 208, 212, 216, 220, 224, 228, 232, 236, 240, 244,
                                                  //|  248, 252, 256, 260, 264, 268, 272, 276, 280, 284, 288, 292, 296, 300, 304, 
                                                  //| 308, 312, 316, 320, 324, 328, 332, 336, 340, 344, 348, 352, 356, 360, 364, 3
                                                  //| 68, 372, 376, 380, 384, 388, 392, 396)
  
  def sieve(s: Stream[Int]): Stream[Int] =
    s.head #:: sieve (s.tail filter (_%s.head!=0))//> sieve: (s: Stream[Int])Stream[Int]

  val primes = sieve(from(2))                     //> primes  : Stream[Int] = Stream(2, ?)
  (primes take 10).toList                         //> res4: List[Int] = List(2, 3, 5, 7, 11, 13, 17, 19, 23, 29)

  def sqrtStream(x: Double): Stream[Double] =
  {
    def improve (guess: Double) = (guess + x / guess) / 2
    lazy val guesses: Stream[Double] = 1 #:: (guesses map improve)
    guesses
  }                                               //> sqrtStream: (x: Double)Stream[Double]

  def isGoodEnough(guess:Double, x:Double) =
    math.abs((guess*guess - x) / x) < 0.0001      //> isGoodEnough: (guess: Double, x: Double)Boolean

  sqrtStream(4).take(10).toList                   //> res5: List[Double] = List(1.0, 2.5, 2.05, 2.000609756097561, 2.0000000929222
                                                  //| 947, 2.000000000000002, 2.0, 2.0, 2.0, 2.0)
  sqrtStream(4).filter(isGoodEnough(_,4)).take(10).toList
                                                  //> res6: List[Double] = List(2.0000000929222947, 2.000000000000002, 2.0, 2.0, 2
                                                  //| .0, 2.0, 2.0, 2.0, 2.0, 2.0)
  
}