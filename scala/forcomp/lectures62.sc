
object lectures62 {
  
  val n = 7                                       //> n  : Int = 7
  val xss = (1 until n) map ( i=>(1 until i) map (j=>(i,j)) )
                                                  //> xss  : scala.collection.immutable.IndexedSeq[scala.collection.immutable.Index
                                                  //| edSeq[(Int, Int)]] = Vector(Vector(), Vector((2,1)), Vector((3,1), (3,2)), Ve
                                                  //| ctor((4,1), (4,2), (4,3)), Vector((5,1), (5,2), (5,3), (5,4)), Vector((6,1), 
                                                  //| (6,2), (6,3), (6,4), (6,5)))
  //(xss foldRight Seq[Int]()) (_ ++ _)
  val xssF = xss.flatten                          //> xssF  : scala.collection.immutable.IndexedSeq[(Int, Int)] = Vector((2,1), (3
                                                  //| ,1), (3,2), (4,1), (4,2), (4,3), (5,1), (5,2), (5,3), (5,4), (6,1), (6,2), (
                                                  //| 6,3), (6,4), (6,5))
  val yss = (1 until n) flatMap ( i=>(1 until i) map (j=>(i,j)) )
                                                  //> yss  : scala.collection.immutable.IndexedSeq[(Int, Int)] = Vector((2,1), (3,
                                                  //| 1), (3,2), (4,1), (4,2), (4,3), (5,1), (5,2), (5,3), (5,4), (6,1), (6,2), (6
                                                  //| ,3), (6,4), (6,5))
  
  def isPrime(n:Int): Boolean = (2 until n) forall (number => n%number!=0 )
                                                  //> isPrime: (n: Int)Boolean
  yss filter (pair => isPrime(pair._1+pair._2))   //> res0: scala.collection.immutable.IndexedSeq[(Int, Int)] = Vector((2,1), (3,2
                                                  //| ), (4,1), (4,3), (5,2), (6,1), (6,5))
    
    
  for {
    i <- 1 until n
    j <- 1 until i
    if isPrime(i+j)
  } yield (i,j)                                   //> res1: scala.collection.immutable.IndexedSeq[(Int, Int)] = Vector((2,1), (3,2
                                                  //| ), (4,1), (4,3), (5,2), (6,1), (6,5))
                                                  
  val v1 = Vector (1.0, 2.0, 7.5)                 //> v1  : scala.collection.immutable.Vector[Double] = Vector(1.0, 2.0, 7.5)
  val v2 = Vector (18.2, 7.34, -5.2)              //> v2  : scala.collection.immutable.Vector[Double] = Vector(18.2, 7.34, -5.2)
  
  def scalarProduct3 (xs:Vector[Double], ys:Vector[Double]): Double =
    ( for ( (x,y) <- xs zip ys) yield x*y ).sum   //> scalarProduct3: (xs: Vector[Double], ys: Vector[Double])Double
  scalarProduct3(v1,v2)                           //> res2: Double = -6.1200000000000045
}