package recfun
import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
	def pascal(col: Int, row: Int): Int =
 		if (col==0 || col==row || col>row || col<0) 1
 		else pascal(col-1,row-1)+pascal(col,row-1)


  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean =
  {
	def trawl (chars: List[Char], depth: Int) : Boolean =
		if (chars.isEmpty) depth==0        // sunny day terminal check
		else if (depth<0) false                    // unbalanced
		else trawl(chars.tail,depth+direction(chars.head))

	def direction(letter: Char): Int =
  		if (letter=='(') 1
  		else if (letter==')') -1
  		else 0	
	
  	trawl(chars,0)
  }

 
  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int =
  {
    if (money==0) 1							// no money, one way to return nothing
    else if (money<0)  0					// got into trouble, no way to do anything
    else if (coins.isEmpty && money>0) 0	// no coins and have to pay something, but cannot
    else countChange(money, coins.tail) + countChange(money-coins.head, coins)  
  }
  
  
  
}
