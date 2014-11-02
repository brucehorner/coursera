object lectures61 {
  1 until 5                                       //> res0: scala.collection.immutable.Range = Range(1, 2, 3, 4)
  1 to 10 by 3                                    //> res1: scala.collection.immutable.Range = Range(1, 4, 7, 10)
  
  val v1 = Vector (1.0, 2.0, 7.5)                 //> v1  : scala.collection.immutable.Vector[Double] = Vector(1.0, 2.0, 7.5)
  val v2 = Vector (18.2, 7.34, -5.2)              //> v2  : scala.collection.immutable.Vector[Double] = Vector(18.2, 7.34, -5.2)
  
  def scalarProduct1(xs:Vector[Double], ys:Vector[Double]): Double =
    (xs zip ys).map(xy=>xy._1*xy._2).sum          //> scalarProduct1: (xs: Vector[Double], ys: Vector[Double])Double
  scalarProduct1(v1,v2)                           //> res2: Double = -6.1200000000000045
  
  def scalarProduct2 (xs:Vector[Double], ys:Vector[Double]): Double =
    (xs zip ys).map { case(x,y) => x*y }.sum      //> scalarProduct2: (xs: Vector[Double], ys: Vector[Double])Double
  scalarProduct2(v1,v2)                           //> res3: Double = -6.1200000000000045

  def isPrime(n:Int): Boolean = (2 until n) forall (number => n%number!=0 )
                                                  //> isPrime: (n: Int)Boolean
  
  isPrime(5)                                      //> res4: Boolean = true




}