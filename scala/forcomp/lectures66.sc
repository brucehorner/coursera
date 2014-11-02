object lectures66 {

  val romanNumerals = Map ("I" -> 1, "V" -> 5, "X" -> 10)
                                                  //> romanNumerals  : scala.collection.immutable.Map[java.lang.String,Int] = Map(I
                                                  //|  -> 1, V -> 5, X -> 10)
  val capitalOfCountry = Map ("US" -> "Washington", "Switzerland" -> "Bern")
                                                  //> capitalOfCountry  : scala.collection.immutable.Map[java.lang.String,java.lan
                                                  //| g.String] = Map(US -> Washington, Switzerland -> Bern)

  capitalOfCountry("US")                          //> res0: java.lang.String = Washington
  capitalOfCountry get "US"                       //> res1: Option[java.lang.String] = Some(Washington)
  capitalOfCountry get "Europa"                   //> res2: Option[java.lang.String] = None
  
  val fruit = List ("apple", "pear", "orange", "pineapple")
                                                  //> fruit  : List[java.lang.String] = List(apple, pear, orange, pineapple)
  fruit sortWith (_.length < _.length)            //> res3: List[java.lang.String] = List(pear, apple, orange, pineapple)
  fruit sorted                                    //> res4: List[java.lang.String] = List(apple, orange, pear, pineapple)
  //fruit.groupBy(_.head)
  
  
// ** Polynomials
  class Poly(val terms0: Map[Int, Double])
  {
    def this (bindings: (Int,Double)*) = this(bindings.toMap)
  
    val terms = terms0 withDefaultValue 0.0
  
// Initial Version
//    def + (other: Poly) = new Poly(terms ++ (other.terms map adjustCoeffImproved))

// Improved Version
    def + (other: Poly) = new Poly(terms ++ (other.terms foldLeft terms)(addTerm))
    
    def addTerm(terms: Map[Int,Double], term: (Int,Double)): Map[Int,Double] = {
      val (exp,coeff) = term
      terms + (exp -> (coeff + terms(exp)))
    }
    
    def adjustCoeff(term: (Int,Double)): (Int,Double) = {
      val (exp,coeff) = term
      terms get exp match {
        case Some(coeff1) =>  exp -> (coeff + coeff1)
        case None => exp -> coeff
      }
    }

    def adjustCoeffImproved(term: (Int,Double)): (Int,Double) = {
      val (exp,coeff) = term
      exp -> (coeff + terms(exp))
    }
    
    override def toString =
      (for ((exp,coeff) <- terms.toList.sorted.reverse) yield coeff+"x^"+exp) mkString " + "
  }
  
  val p1 = new Poly (Map(1->2.0, 3->4.0, 5->6.2)) //> p1  : lectures66.Poly = 6.2x^5 + 4.0x^3 + 2.0x^1
  val p1a= new Poly (1->2.0, 3->4.0, 5->6.2)      //> p1a  : lectures66.Poly = 6.2x^5 + 4.0x^3 + 2.0x^1
  val p2 = new Poly (Map(0->3.0, 3->7.0))         //> p2  : lectures66.Poly = 7.0x^3 + 3.0x^0
  val p2a= new Poly (0->3.0, 3->7.0)              //> p2a  : lectures66.Poly = 7.0x^3 + 3.0x^0
  p1 + p2                                         //> res5: lectures66.Poly = 6.2x^5 + 11.0x^3 + 2.0x^1 + 3.0x^0
  p1a + p2a                                       //> res6: lectures66.Poly = 6.2x^5 + 11.0x^3 + 2.0x^1 + 3.0x^0
  p1.terms(7)                                     //> res7: Double = 0.0
}