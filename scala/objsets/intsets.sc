abstract class IntSet
{
  def contains(x: Int): Boolean
  def incl(x: Int): IntSet
  def union(other: IntSet): IntSet
  def filter(p: Int => Boolean): IntSet = filter0(p, new Empty)
  def filter0(p: Int => Boolean, accu: IntSet): IntSet
  def isEmpty: Boolean
  def head: Int
  def tail: IntSet
    def foreach(f: Int => Unit): Unit = {
    if (!this.isEmpty) {
      f(this.head)
      this.tail.foreach(f)
    }
  }

  def remove(tw: Int): IntSet

  def findMin0(curr: Int): Int =
    if (this.isEmpty) curr
    else if (this.head < curr) this.tail.findMin0(this.head)
    else this.tail.findMin0(curr)

  def findMin: Int =
    this.tail.findMin0(this.head)
  
  def ascendingByValue: Trending =
  {
    def ascending(set:IntSet, sequence:Trending): Trending =
      if (set.isEmpty) sequence
      else ascending (set.remove(set.findMin),sequence + set.findMin)
    
    ascending (this, new EmptyTrending)
  }
}

abstract class Trending {
  def + (tw: Int): Trending
  def head: Int
  def tail: Trending
  def isEmpty: Boolean
  def foreach(f: Int => Unit): Unit = {
    if (!this.isEmpty) {
      f(this.head)
      this.tail.foreach(f)
    }
  }
}

class EmptyTrending extends Trending {
  def + (tw: Int) = new NonEmptyTrending(tw, new EmptyTrending)
  def head: Int = throw new Exception
  def tail: Trending = throw new Exception
  def isEmpty: Boolean = true
  override def toString = "EmptyTrending"
}

class NonEmptyTrending(elem: Int, next: Trending) extends Trending
{
  def + (tw: Int): Trending = new NonEmptyTrending(elem, next + tw)
  def head: Int = elem
  def tail: Trending = next
  def isEmpty: Boolean = false
  override def toString = "NonEmptyTrending(" + elem + ", " + next + ")"
}

class NonEmpty(elem: Int, left: IntSet, right: IntSet) extends IntSet
{
  def union(other: IntSet): IntSet = ((left union right) union other) incl elem
  def contains(x: Int): Boolean = if (x < elem) left contains x else if (x > elem) right contains x else true
  def incl(x: Int): IntSet = if (x < elem) new NonEmpty(elem, left incl x, right) else if (x > elem) new NonEmpty(elem, left, right incl x) else this
  override def toString = "{" + left + elem + right + "}"
  def isEmpty: Boolean = false
  def head = if (left.isEmpty) elem else left.head
  def tail = if (left.isEmpty) right else new NonEmpty(elem, left.tail, right)
  
  def remove(tw: Int): IntSet =
    if (tw < elem) new NonEmpty(elem, left.remove(tw), right)
    else if (elem < tw) new NonEmpty(elem, left, right.remove(tw))
    else left.union(right)
    
  def filter0(p: Int => Boolean, accu: IntSet): IntSet =
  {
  	left.filter0(p, right.filter0 (p, if(p(elem)) accu incl elem else accu))
  }
}

class Empty extends IntSet
{
  def contains(x: Int): Boolean = false
  def incl(x: Int): IntSet = new NonEmpty(x, new Empty, new Empty)
  def union(other: IntSet): IntSet = other
  override def toString = "."
  def filter0(p: Int => Boolean, accu: IntSet): IntSet = accu
  def head = throw new Exception("Empty.head")
  def tail = throw new Exception("Empty.tail")
  def remove(tw: Int): IntSet = this
  def isEmpty: Boolean = true
}

object intsets
{
  val t1 = new NonEmpty(3, new Empty, new Empty)  //> t1  : NonEmpty = {.3.}
  val t2 = t1 incl 4                              //> t2  : IntSet = {.3{.4.}}
  val t3 = new NonEmpty(2, new Empty, new Empty)  //> t3  : NonEmpty = {.2.}
  val t4 = t3 incl 1                              //> t4  : IntSet = {{.1.}2.}
  val t5 = t2 union t4                            //> t5  : IntSet = {{.1.}2{{.3.}4.}}
  
  val s1 = t5.filter(x=>x%2==0)                   //> s1  : IntSet = {.2{.4.}}
  val s2 = t5.filter(x=>x%2!=0)                   //> s2  : IntSet = {{.1.}3.}
  val sort1 = s1 union s2                         //> sort1  : IntSet = {{.1{.2.}}3{.4.}}
  sort1.ascendingByValue foreach println          //> 1
                                                  //| 2
                                                  //| 3
                                                  //| 4
  
}