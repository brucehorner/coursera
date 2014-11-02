package patmat

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import patmat.Huffman._

@RunWith(classOf[JUnitRunner])
class HuffmanSuite extends FunSuite {
  trait TestTrees {
    val t1 = Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5)
    val t2 = Fork(Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5), Leaf('d',4), List('a','b','d'), 9)
  }
  
  test("weight of a larger tree") {
    new TestTrees {
      assert(weight(t1) === 5)
    }
  }

  test("chars of a larger tree") {
    new TestTrees {
      assert(chars(t2) === List('a','b','d'))
    }
  }

  test("string2chars(\"hello, world\")") {
    assert(string2Chars("hello, world") === List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'))
  }

  test("makeOrderedLeafList for some frequency table") {
    assert(makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))) === List(Leaf('e',1), Leaf('t',2), Leaf('x',3)))
  }

  test("combine of some leaf list") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assert(combine(leaflist) === List(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3), Leaf('x',4)))
  }

  test("decode and encode a very short text should be identity") {
    new TestTrees {
      assert(decode(t1, encode(t1)("ab".toList)) === "ab".toList)
    }
  }
  
  // BH -> new tests
  
  test("times")
  {
    val testStr = "this is not easy but it is great fun"
    val testList = testStr.toList 
    val counted = times(testList)
    assert ( counted.find(e=>e._1=='i').get._2 === 4 )		// there are four 'i' characters	  
    assert ( counted.find(e=>e._1==' ').get._2 === 8 )		// there are eight space characters
    assert ( totalCount(counted)===testStr.length() )	    // total count of chars must match length of string 
  }
  
  test("decode 2")
  {
	val testStr = "helloworld".toList
    val tree = createCodeTree(testStr)
    val bits = List(0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0)
    assert (decode(tree,bits)==="hell".toList)  
  }
  
  test("compare encode with quick encode")
  {
    val tree = createCodeTree("hello, world, this is not easy but it is great fun".toList)
    val test = "hell".toList 
    assert ( encode(tree)(test) === quickEncode(tree)(test))
    
  }
  
}
