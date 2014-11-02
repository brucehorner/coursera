package patmat

import common._

/**
 * Assignment 4: Huffman coding
 *
 */
object Huffman {

  /**
   * A huffman code is represented by a binary tree.
   *
   * Every `Leaf` node of the tree represents one character of the alphabet that the tree can encode.
   * The weight of a `Leaf` is the frequency of appearance of the character.
   *
   * The branches of the huffman tree, the `Fork` nodes, represent a set containing all the characters
   * present in the leaves below it. The weight of a `Fork` node is the sum of the weights of these
   * leaves.
   */
  abstract class CodeTree
  case class Fork(left: CodeTree, right: CodeTree, chars: List[Char], weight: Int) extends CodeTree
  case class Leaf(char: Char, weight: Int) extends CodeTree

  // Part 1: Basics

  def weight(tree: CodeTree): Int = tree match			// BH -> just pull data from the class constructor itself
  {
    case Leaf(char,weight) => weight
    case Fork(left,right,chars,weight) => weight
  }

  def chars(tree: CodeTree): List[Char] = tree match	// BH -> just pull data from the class constructor itself
  {
    case Leaf(char,weight) => List(char)
    case Fork(left,right,chars,weight) => chars
  }
  
  def makeCodeTree(left: CodeTree, right: CodeTree) =
    Fork(left, right, chars(left) ::: chars(right), weight(left) + weight(right))



  // Part 2: Generating Huffman trees

  /**
   * In this assignment, we are working with lists of characters. This function allows
   * you to easily create a character list from a given string.
   */
  def string2Chars(str: String): List[Char] = str.toList

  /**
   * This function computes for each unique character in the list `chars` the number of
   * times it occurs. For example, the invocation
   *
   *   times(List('a', 'b', 'a'))
   *
   * should return the following (the order of the resulting list is not important):
   *
   *   List(('a', 2), ('b', 1))
   *
   * The type `List[(Char, Int)]` denotes a list of pairs, where each pair consists of a
   * character and an integer. Pairs can be constructed easily using parentheses:
   *
   *   val pair: (Char, Int) = ('c', 1)
   *
   * In order to access the two elements of a pair, you can use the accessors `_1` and `_2`:
   *
   *   val theChar = pair._1
   *   val theInt  = pair._2
   *
   * Another way to deconstruct a pair is using pattern matching:
   *
   *   pair match {
   *     case (theChar, theInt) =>
   *       println("character is: "+ theChar)
   *       println("integer is  : "+ theInt)
   *   }
   */
  def times(chars: List[Char]): List[(Char, Int)] =
  {
     def times0(str:List[Char], accu:List[(Char,Int)]): List[(Char,Int)] = str match
     {
       case Nil => accu								// end of the string, return what we've accumulated
       case head::tail =>							// pull head and tail from the string list
       {
         val found = accu.find(e=>e._1==head)		// find an element which has the same character  
         found match								// as the current string's first
         {
           // didn't find anything, so tack a new pair to the front of the accumulator and continue
           case None => times0(tail, (head,1) :: accu)	
           
           // found a match, so add a new pair with count+1 to the front of a new accumulator 
           // where we've removed the existing matching element
           case _ => times0(tail,(head,found.get._2+1) :: accu.filterNot(y=>y._1==head))
         }
       }
     }
       
    times0(chars,List())
  }
  
  /*
   * support function to allow testing of the 'times' function
   * cycle down the whole list generating the sum of the 'times'
   */
  def totalCount (list:List[(Char, Int)]):Int =
    {
      def total0 (list:List[(Char, Int)], accu:Int):Int =
  		list match
  		{
  		  case Nil => accu
  		  case head::tail => total0 (list.tail,head._2+accu)
  		}
  	  total0 (list,0)
    }


  /**
   * Returns a list of `Leaf` nodes for a given frequency table `freqs`.
   *
   * The returned list should be ordered by ascending weights (i.e. the
   * head of the list should have the smallest weight), where the weight
   * of a leaf is the frequency of the character.
   */
  def makeOrderedLeafList(freqs: List[(Char, Int)]): List[Leaf] =
  {
  	def orderLeaf(counted: List[(Char, Int)], accu:List[Leaf]): List[Leaf] =
  	{
  	  counted match
  	  {
  	    // no entries left, so just return the accumulator
  	    case Nil => accu
  	    
  	    // match the head as a pair, and the tail, then construct a Leaf from
  	    // the head , prepend to the accumulator and recurse with the tail of
  	    // the list
  	    case (char,times)::tail => orderLeaf (tail, Leaf(char,times) :: accu)
  	  }
  	}
  
  	// start recursion with an empty Leaf list, then sort in ascending order
  	// of weight
  	orderLeaf(freqs,List[Leaf]()).sortWith(
  	    (x,y)=>(if (x.weight==y.weight) x.char<y.char else x.weight<y.weight))
  }     

  /**
   * Checks whether the list `trees` contains only one single code tree.
   */
  def singleton(trees: List[CodeTree]): Boolean = trees.length==1

  /**
   * The parameter `trees` of this function is a list of code trees ordered
   * by ascending weights.
   *
   * This function takes the first two elements of the list `trees` and combines
   * them into a single `Fork` node. This node is then added back into the
   * remaining elements of `trees` at a position such that the ordering by weights
   * is preserved.
   *
   * If `trees` is a list of less than two elements, that list should be returned
   * unchanged.
   */
  def combine(trees: List[CodeTree]): List[CodeTree] = 
    trees match
    {
      case (first :: (second :: tail)) => 
        (makeCodeTree(first,second) :: tail).sortWith(
            (x,y)=>(if (weight(x)==weight(y))  chars(x).head<chars(y).head else weight(x)<weight(y)
            ))
      case _ => trees
    }

  /**
   * This function will be called in the following way:
   *
   *   until(singleton, combine)(trees)
   *
   * where `trees` is of type `List[CodeTree]`, `singleton` and `combine` refer to
   * the two functions defined above.
   *
   * In such an invocation, `until` should call the two functions until the list of
   * code trees contains only one single tree, and then return that singleton list.
   *
   * Hint: before writing the implementation,
   *  - start by defining the parameter types such that the above example invocation
   *    is valid. The parameter types of `until` should match the argument types of
   *    the example invocation. Also define the return type of the `until` function.
   *  - try to find sensible parameter names for `xxx`, `yyy` and `zzz`.
   */
  def until(isComplete: List[CodeTree]=>Boolean, op: List[CodeTree]=>List[CodeTree])(tree: List[CodeTree]): CodeTree =
  {
    def until0 (t:List[CodeTree]) : List[CodeTree] =
      if (isComplete(t)) t
      else until0(op(t))
    
	until0(tree).head
  }

  /**
   * This function creates a code tree which is optimal to encode the text `chars`.
   *
   * The parameter `chars` is an arbitrary text. This function extracts the character
   * frequencies from that text and creates a code tree based on them.
   */
  def createCodeTree(chars: List[Char]): CodeTree = until(singleton,combine)(makeOrderedLeafList(times(chars)))
 

  // Part 3: Decoding

  type Bit = Int

  /**
   * This function decodes the bit sequence `bits` using the code tree `tree` and returns
   * the resulting list of characters.
   */
  def decode(tree: CodeTree, bits: List[Bit]): List[Char] =
  {
    // walk down the tree , turning left for a "0" and right for a "1" at every fork
    // when we get to the leaf, tack the character there to the accumulator
    // and also return the current bitlist which has been reduced as we went down the tree
    def getNextLetter (codes:List[Bit],t:CodeTree,accu:List[Char]) : (List[Char],List[Bit]) =
      t match
      {
        case Leaf(char,_) => (char::accu, codes)
        case Fork(left,right,_,_) =>
          codes match
    	  {
    	    case 0::tail => getNextLetter (tail, left, accu)
    	    case 1::tail => getNextLetter (tail, right, accu)
    	    case Nil => throw new IllegalArgumentException ("invalid code, found empty list during decode")
    	    case x => throw new IllegalArgumentException ("invalid code during decode:'"+x+"'")
          }
      }
    
    // iterate on the letter decoding until there are no entries in the bitlist to decode
    // kind of messy returning two values, one for the increasing list or characters 
    // and the other for the decreasing list of bits
    def decode0 (codes:List[Bit],t:CodeTree,accu:List[Char]) : (List[Char],List[Bit]) =
    	if (codes.length==0) (accu,codes)
    	else
    	{
          val x = getNextLetter (codes,tree,accu)
          decode0(x._2,t,x._1)
    	}

    // the algorithm adds decoded characters to the front of the accumulator, so we need
    // to reverse once everything has been found
    decode0(bits,tree,List[Char]())._1.reverse
  }

  /**
   * A Huffman coding tree for the French language.
   * Generated from the data given at
   *   http://fr.wikipedia.org/wiki/Fr%C3%A9quence_d%27apparition_des_lettres_en_fran%C3%A7ais
   */
  val frenchCode: CodeTree = Fork(Fork(Fork(Leaf('s',121895),Fork(Leaf('d',56269),Fork(Fork(Fork(Leaf('x',5928),Leaf('j',8351),List('x','j'),14279),Leaf('f',16351),List('x','j','f'),30630),Fork(Fork(Fork(Fork(Leaf('z',2093),Fork(Leaf('k',745),Leaf('w',1747),List('k','w'),2492),List('z','k','w'),4585),Leaf('y',4725),List('z','k','w','y'),9310),Leaf('h',11298),List('z','k','w','y','h'),20608),Leaf('q',20889),List('z','k','w','y','h','q'),41497),List('x','j','f','z','k','w','y','h','q'),72127),List('d','x','j','f','z','k','w','y','h','q'),128396),List('s','d','x','j','f','z','k','w','y','h','q'),250291),Fork(Fork(Leaf('o',82762),Leaf('l',83668),List('o','l'),166430),Fork(Fork(Leaf('m',45521),Leaf('p',46335),List('m','p'),91856),Leaf('u',96785),List('m','p','u'),188641),List('o','l','m','p','u'),355071),List('s','d','x','j','f','z','k','w','y','h','q','o','l','m','p','u'),605362),Fork(Fork(Fork(Leaf('r',100500),Fork(Leaf('c',50003),Fork(Leaf('v',24975),Fork(Leaf('g',13288),Leaf('b',13822),List('g','b'),27110),List('v','g','b'),52085),List('c','v','g','b'),102088),List('r','c','v','g','b'),202588),Fork(Leaf('n',108812),Leaf('t',111103),List('n','t'),219915),List('r','c','v','g','b','n','t'),422503),Fork(Leaf('e',225947),Fork(Leaf('i',115465),Leaf('a',117110),List('i','a'),232575),List('e','i','a'),458522),List('r','c','v','g','b','n','t','e','i','a'),881025),List('s','d','x','j','f','z','k','w','y','h','q','o','l','m','p','u','r','c','v','g','b','n','t','e','i','a'),1486387)

  /**
   * What does the secret message say? Can you decode it?
   * For the decoding use the `frenchCode' Huffman tree defined above.
   */
  val secret: List[Bit] = List(0,0,1,1,1,0,1,0,1,1,1,0,0,1,1,0,1,0,0,1,1,0,1,0,1,1,0,0,1,1,1,1,1,0,1,0,1,1,0,0,0,0,1,0,1,1,1,0,0,1,0,0,1,0,0,0,1,0,0,0,1,0,1)

  /**
   * Write a function that returns the decoded secret
   */
  def decodedSecret: List[Char] = decode(frenchCode,secret)



  // Part 4a: Encoding using Huffman tree

  /**
   * This function encodes `text` using the code tree `tree`
   * into a sequence of bits.
   */
  def encode(tree: CodeTree)(text: List[Char]): List[Bit] =
  {

    // take a single character, and walk the tree taking a left or right turn at  
    // every fork depending on which substring contains it, adding a zero or one to the 
    // accumlating bits string.
    // once we match a Leaf then stop recursing and return the accumulated bitlist (reversing it since
    //  we built up the list by preprending the bit values)
    def encodeCharacter(tree:CodeTree, c:Char, bits:List[Bit]) : List[Bit] =
	  tree match
	  {
	    case Fork(left,right,nodeString,_) =>
	      if (chars(left) contains c) encodeCharacter(left,c,0::bits)
	      else if (chars(right) contains c) encodeCharacter(right,c,1::bits)
	      else throw new Exception("Cannot encode character '"+c+"' not in the tree at node: "+nodeString)
	    case Leaf(_,_) => bits.reverse
	  }
  
    // go along the list of characters in the list, encoding one at a time
    // and adding each character's bitlist to the accumulated list from the 'tail'
    // of the string list.  This list is built up from the bottom to the top
    // so no need to reverse
    def encode0(tree:CodeTree, cs:List[Char], bits:List[Bit]) : List[Bit] =
      cs match
      {
        case Nil => bits
   	    case head::tail => encodeCharacter(tree,head,bits) ::: encode0(tree,tail,bits)
      }
    
     encode0(tree,text,List[Bit]())
  }
 


  // Part 4b: Encoding using code table

  type CodeTable = List[(Char, List[Bit])]

  /**
   * This function returns the bit sequence that represents the character `char` in
   * the code table `table`.
   */
  def codeBits(table: CodeTable)(char: Char): List[Bit] = 
    table.find((a:(Char,List[Bit]))=>a._1==char) match
    {
      case Some(arg) => arg._2
      case None      => Nil
    }

  /**
   * Given a code tree, create a code table which contains, for every character in the
   * code tree, the sequence of bits representing that character.
   *
   * Hint: think of a recursive solution: every sub-tree of the code tree `tree` is itself
   * a valid code tree that can be represented as a code table. Using the code tables of the
   * sub-trees, think of how to build the code table for the entire tree.
   */
  def convert(tree: CodeTree): CodeTable =
  {
    def encodeCharacter(tree:CodeTree, c:Char, bits:List[Bit]) : List[Bit] =
	  tree match
	  {
	    case Fork(left,right,nodeString,_) =>
	      if (chars(left) contains c) encodeCharacter(left,c,0::bits)
	      else if (chars(right) contains c) encodeCharacter(right,c,1::bits)
	      else throw new Exception("Cannot encode character '"+c+"' not in the tree at node: "+nodeString)
	    case Leaf(_,_) => bits.reverse
	  }
    
    
    def iterateOverCharList(list:List[Char],accu:CodeTable):CodeTable =
    {
      list match
      {
        case Nil => accu
        case head::tail => iterateOverCharList(tail,
            // some fairly ugly hoop-jumping to build a dummy code table just to use merge as instructed
            // this can be done better by prepending the single pair to accu
            mergeCodeTables (List[(Char,List[Bit])]((head,encodeCharacter(tree,head,Nil))), accu)
            )
      }
    }
    
    tree match
    {
      case Fork(l,r,c,w) => iterateOverCharList(c,Nil)
      case _ => Nil
    } 
    
  }

  /**
   * This function takes two code tables and merges them into one. Depending on how you
   * use it in the `convert` method above, this merge method might also do some transformations
   * on the two parameter code tables.
   */
  def mergeCodeTables(a: CodeTable, b: CodeTable): CodeTable = a ::: b

  /**
   * This function encodes `text` according to the code tree `tree`.
   *
   * To speed up the encoding process, it first converts the code tree to a code table
   * and then uses it to perform the actual encoding.
   */
  def quickEncode(tree: CodeTree)(text: List[Char]): List[Bit] =
  {
    val table = convert(tree)
    
    def iterateOverCharList(list:List[Char],accu:List[Bit]):List[Bit] =
    {
      list match
      {
        case Nil => accu
        case head::tail => iterateOverCharList(tail,accu:::(codeBits(table)(head)))
      }
    }

    iterateOverCharList(text, Nil)
    
  }
}
