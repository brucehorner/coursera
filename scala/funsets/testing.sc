import Math.abs
object testing {
  
  val tolerance = 0.0001                          //> tolerance  : Double = 1.0E-4
  def isCloseEnough (x:Double, y:Double) = (abs(x-y)/x) / x < tolerance
                                                  //> isCloseEnough: (x: Double, y: Double)Boolean
  def fixedPoint(f:Double=>Double)(firstGuess:Double) =
  {
    def iterate(guess:Double): Double =
    {
      val next = f(guess)
      println(next)
      if (isCloseEnough(guess, next)) next
      else iterate(next)
    }
    iterate(firstGuess)
  }                                               //> fixedPoint: (f: Double => Double)(firstGuess: Double)Double
  
  def averageDamp(f:Double=>Double)(x:Double) = (x+f(x))/2
                                                  //> averageDamp: (f: Double => Double)(x: Double)Double
  
  def sqrt(x:Double) = fixedPoint(averageDamp(y=>x/y))(1.0)
                                                  //> sqrt: (x: Double)Double
  
  sqrt(100.0)                                     //> 50.5
                                                  //| 26.24009900990099
                                                  //| 15.025530119986813
                                                  //| 10.840434673026925
                                                  //| 10.032578510960604
                                                  //| 10.000052895642693
                                                  //| 10.000000000139897
                                                  //| res0: Double = 10.000000000139897
  
}