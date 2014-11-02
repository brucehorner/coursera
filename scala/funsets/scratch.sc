object scratch {
  val bound = 100                                 //> bound  : Int = 100
  type Set = Int => Boolean
  def contains(s: Set, elem: Int): Boolean = s(elem)
                                                  //> contains: (s: Int => Boolean, elem: Int)Boolean
  def singletonSet(elem: Int): Set = (x=>x==elem) //> singletonSet: (elem: Int)Int => Boolean
  def toString(s: Set): String = {
    val xs = for (i <- -bound to bound if contains(s, i)) yield i
    xs.mkString("{", ",", "}")
  }                                               //> toString: (s: Int => Boolean)String
    def printSet(s: Set) {
    println(toString(s))
  }                                               //> printSet: (s: Int => Boolean)Unit
  def evens = (x:Int)=>x%2==0                     //> evens: => Int => Boolean
  def forall(s: Set, p: Int => Boolean): Boolean = {
    def iter(a: Int): Boolean = {
      if (a>bound) false
      else if (contains(s,a)) p(a)
      else iter(a+1)
    }
    iter(-bound)
  }                                               //> forall: (s: Int => Boolean, p: Int => Boolean)Boolean

  /**
   * Returns whether there exists a bounded integer within `s`
   * that satisfies `p`.
   */
  def exists(s: Set, p: Int => Boolean): Boolean = forall(p,s) || forall(s,p)
                                                  //> exists: (s: Int => Boolean, p: Int => Boolean)Boolean
  
  def map(s: Set, f: Int => Int): Set = elem => exists(s,x => f(x) == elem)
                                                  //> map: (s: Int => Boolean, f: Int => Int)Int => Boolean
  
  def ss: Set = (i:Int)=>(i>1&&i<10)&&i%2==0      //> ss: => Int => Boolean
  def ff: Int=>Int = (i:Int)=>i*2                 //> ff: => Int => Int
  
  val sm = map(ss,ff)                             //> sm  : Int => Boolean = <function1>

  printSet(ss)                                    //> {2,4,6,8}
  printSet(sm)                                    //> {4,8,12,16}

}