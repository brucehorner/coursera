object lectures63 {
  val fruit = Set ("apple", "banana", "pear")     //> fruit  : scala.collection.immutable.Set[java.lang.String] = Set(apple, banana
                                                  //| , pear)
  val s = (1 to 6).toSet                          //> s  : scala.collection.immutable.Set[Int] = Set(5, 1, 6, 2, 3, 4)
  
  s map (_ + 2)                                   //> res0: scala.collection.immutable.Set[Int] = Set(5, 6, 7, 3, 8, 4)
  s map (_ / 2)                                   //> res1: scala.collection.immutable.Set[Int] = Set(2, 0, 3, 1)
  s contains 5                                    //> res2: Boolean = true
  
  fruit filter (_.startsWith("app"))              //> res3: scala.collection.immutable.Set[java.lang.String] = Set(apple)
  
  def queens (n:Int) = {
    
    def isSafe (column:Int, queens:List[Int]): Boolean = {
      val row = queens.length
      val queensWithRows = (row-1 to 0 by -1) zip queens
      queensWithRows forall {
        case (r,c) => column!=c && math.abs(column-c)!=row-r	// different colum and not on diagonals
      }
    }
    
    
    def placeQueens(k:Int) : Set[List[Int]] = {
      if (k==0) Set(List())
      else
        for {
          queens <- placeQueens(k-1)
          column <- 0 until n
          if isSafe(column,queens)
        } yield column :: queens
    }
    
    placeQueens(n)
  
  }                                               //> queens: (n: Int)Set[List[Int]]
 
  def show(queens: List[Int]) = {
    val lines =
      for (column <- queens.reverse)
        yield Vector.fill(queens.length)("* ").updated(column,"Q ").mkString
    "\n" + (lines mkString "\n")
  }                                               //> show: (queens: List[Int])java.lang.String
  
  val q = queens(4)                               //> q  : Set[List[Int]] = Set(List(1, 3, 0, 2), List(2, 0, 3, 1))
  val qs = q map show                             //> qs  : scala.collection.immutable.Set[java.lang.String] = Set("
                                                  //| * * Q * 
                                                  //| Q * * * 
                                                  //| * * * Q 
                                                  //| * Q * * ", "
                                                  //| * Q * * 
                                                  //| * * * Q 
                                                  //| Q * * * 
                                                  //| * * Q * ")
  qs mkString "\n"                                //> res4: String = "
                                                  //| * * Q * 
                                                  //| Q * * * 
                                                  //| * * * Q 
                                                  //| * Q * * 
                                                  //| 
                                                  //| * Q * * 
                                                  //| * * * Q 
                                                  //| Q * * * 
                                                  //| * * Q * "
}