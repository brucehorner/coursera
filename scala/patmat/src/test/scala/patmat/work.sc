import patmat.Huffman._

object work {

  val list1 = string2Chars("abc")                 //> list1  : List[Char] = List(a, b, c)
  val list2 = string2Chars("hello")               //> list2  : List[Char] = List(h, e, l, l, o)
  val list3 = string2Chars("this is a sentence")  //> list3  : List[Char] = List(t, h, i, s,  , i, s,  , a,  , s, e, n, t, e, n, c
                                                  //| , e)
  val list4 = string2Chars("telephone")           //> list4  : List[Char] = List(t, e, l, e, p, h, o, n, e)
  val list5 = string2Chars("california")          //> list5  : List[Char] = List(c, a, l, i, f, o, r, n, i, a)
  val list6 = string2Chars("mississippi state college")
                                                  //> list6  : List[Char] = List(m, i, s, s, i, s, s, i, p, p, i,  , s, t, a, t, e
                                                  //| ,  , c, o, l, l, e, g, e)
  list1.size                                      //> res0: Int = 3
  list2.size                                      //> res1: Int = 5
  list3.size                                      //> res2: Int = 18
  list4.size                                      //> res3: Int = 9
  list5.size                                      //> res4: Int = 10
  list6.size                                      //> res5: Int = 25
  val times1 = times(list1)                       //> times1  : List[(Char, Int)] = List((c,1), (b,1), (a,1))
  val times2 = times(list2)                       //> times2  : List[(Char, Int)] = List((o,1), (l,2), (e,1), (h,1))
  val times3 = times(list3)                       //> times3  : List[(Char, Int)] = List((e,3), (c,1), (n,2), (t,2), (s,3), ( ,3),
                                                  //|  (a,1), (i,2), (h,1))
  val times4 = times(list4)                       //> times4  : List[(Char, Int)] = List((e,3), (n,1), (o,1), (h,1), (p,1), (l,1),
                                                  //|  (t,1))
  val times5 = times(list5)                       //> times5  : List[(Char, Int)] = List((a,2), (i,2), (n,1), (r,1), (o,1), (f,1),
                                                  //|  (l,1), (c,1))
  val times6 = times(list6)                       //> times6  : List[(Char, Int)] = List((e,3), (g,1), (l,2), (o,1), (c,1), ( ,2),
                                                  //|  (t,2), (a,1), (s,5), (i,4), (p,2), (m,1))
  val oll1 = makeOrderedLeafList(times1)          //> oll1  : List[patmat.Huffman.Leaf] = List(Leaf(a,1), Leaf(b,1), Leaf(c,1))
  val oll2 = makeOrderedLeafList(times2)          //> oll2  : List[patmat.Huffman.Leaf] = List(Leaf(e,1), Leaf(h,1), Leaf(o,1), Le
                                                  //| af(l,2))
  val oll3 = makeOrderedLeafList(times3)          //> oll3  : List[patmat.Huffman.Leaf] = List(Leaf(a,1), Leaf(c,1), Leaf(h,1), Le
                                                  //| af(i,2), Leaf(n,2), Leaf(t,2), Leaf( ,3), Leaf(e,3), Leaf(s,3))
  val oll4 = makeOrderedLeafList(times4)          //> oll4  : List[patmat.Huffman.Leaf] = List(Leaf(h,1), Leaf(l,1), Leaf(n,1), Le
                                                  //| af(o,1), Leaf(p,1), Leaf(t,1), Leaf(e,3))
  val oll5 = makeOrderedLeafList(times5)          //> oll5  : List[patmat.Huffman.Leaf] = List(Leaf(c,1), Leaf(f,1), Leaf(l,1), Le
                                                  //| af(n,1), Leaf(o,1), Leaf(r,1), Leaf(a,2), Leaf(i,2))
  val oll6 = makeOrderedLeafList(times6)          //> oll6  : List[patmat.Huffman.Leaf] = List(Leaf(a,1), Leaf(c,1), Leaf(g,1), Le
                                                  //| af(m,1), Leaf(o,1), Leaf( ,2), Leaf(l,2), Leaf(p,2), Leaf(t,2), Leaf(e,3), L
                                                  //| eaf(i,4), Leaf(s,5))

  combine(oll1)                                   //> res6: List[patmat.Huffman.CodeTree] = List(Leaf(c,1), Fork(Leaf(a,1),Leaf(b,
                                                  //| 1),List(a, b),2))
  combine(oll2)                                   //> res7: List[patmat.Huffman.CodeTree] = List(Leaf(o,1), Fork(Leaf(e,1),Leaf(h,
                                                  //| 1),List(e, h),2), Leaf(l,2))
  combine(oll3)                                   //> res8: List[patmat.Huffman.CodeTree] = List(Leaf(h,1), Fork(Leaf(a,1),Leaf(c,
                                                  //| 1),List(a, c),2), Leaf(i,2), Leaf(n,2), Leaf(t,2), Leaf( ,3), Leaf(e,3), Lea
                                                  //| f(s,3))
  combine(oll4)                                   //> res9: List[patmat.Huffman.CodeTree] = List(Leaf(n,1), Leaf(o,1), Leaf(p,1), 
                                                  //| Leaf(t,1), Fork(Leaf(h,1),Leaf(l,1),List(h, l),2), Leaf(e,3))
  combine(oll5)                                   //> res10: List[patmat.Huffman.CodeTree] = List(Leaf(l,1), Leaf(n,1), Leaf(o,1),
                                                  //|  Leaf(r,1), Leaf(a,2), Fork(Leaf(c,1),Leaf(f,1),List(c, f),2), Leaf(i,2))
  combine(oll6)                                   //> res11: List[patmat.Huffman.CodeTree] = List(Leaf(g,1), Leaf(m,1), Leaf(o,1),
                                                  //|  Leaf( ,2), Fork(Leaf(a,1),Leaf(c,1),List(a, c),2), Leaf(l,2), Leaf(p,2), Le
                                                  //| af(t,2), Leaf(e,3), Leaf(i,4), Leaf(s,5))

  val ct1 = createCodeTree(list1)                 //> ct1  : patmat.Huffman.CodeTree = Fork(Leaf(c,1),Fork(Leaf(a,1),Leaf(b,1),Lis
                                                  //| t(a, b),2),List(c, a, b),3)
  val ct2 = createCodeTree(list2)                 //> ct2  : patmat.Huffman.CodeTree = Fork(Leaf(l,2),Fork(Leaf(o,1),Fork(Leaf(e,1
                                                  //| ),Leaf(h,1),List(e, h),2),List(o, e, h),3),List(l, o, e, h),5)
  val ct3 = createCodeTree(list3)                 //> ct3  : patmat.Huffman.CodeTree = Fork(Fork(Leaf(s,3),Fork(Leaf(i,2),Leaf(n,2
                                                  //| ),List(i, n),4),List(s, i, n),7),Fork(Fork(Leaf(t,2),Leaf( ,3),List(t,  ),5)
                                                  //| ,Fork(Leaf(e,3),Fork(Leaf(h,1),Fork(Leaf(a,1),Leaf(c,1),List(a, c),2),List(h
                                                  //| , a, c),3),List(e, h, a, c),6),List(t,  , e, h, a, c),11),List(s, i, n, t,  
                                                  //| , e, h, a, c),18)
  val ct4 = createCodeTree(list4)                 //> ct4  : patmat.Huffman.CodeTree = Fork(Fork(Fork(Leaf(h,1),Leaf(l,1),List(h,
                                                  //|  l),2),Fork(Leaf(n,1),Leaf(o,1),List(n, o),2),List(h, l, n, o),4),Fork(Fork
                                                  //| (Leaf(p,1),Leaf(t,1),List(p, t),2),Leaf(e,3),List(p, t, e),5),List(h, l, n,
                                                  //|  o, p, t, e),9)
  val ct5 = createCodeTree(list5)                 //> ct5  : patmat.Huffman.CodeTree = Fork(Fork(Leaf(i,2),Fork(Leaf(l,1),Leaf(n,
                                                  //| 1),List(l, n),2),List(i, l, n),4),Fork(Fork(Leaf(o,1),Leaf(r,1),List(o, r),
                                                  //| 2),Fork(Leaf(a,2),Fork(Leaf(c,1),Leaf(f,1),List(c, f),2),List(a, c, f),4),L
                                                  //| ist(o, r, a, c, f),6),List(i, l, n, o, r, a, c, f),10)
  val ct6 = createCodeTree(list6)                 //> ct6  : patmat.Huffman.CodeTree = Fork(Fork(Leaf(s,5),Fork(Leaf(t,2),Leaf(e,
                                                  //| 3),List(t, e),5),List(s, t, e),10),Fork(Fork(Fork(Leaf(o,1),Leaf( ,2),List(
                                                  //| o,  ),3),Fork(Fork(Leaf(a,1),Leaf(c,1),List(a, c),2),Fork(Leaf(g,1),Leaf(m,
                                                  //| 1),List(g, m),2),List(a, c, g, m),4),List(o,  , a, c, g, m),7),Fork(Leaf(i,
                                                  //| 4),Fork(Leaf(l,2),Leaf(p,2),List(l, p),4),List(i, l, p),8),List(o,  , a, c,
                                                  //|  g, m, i, l, p),15),List(s, t, e, o,  , a, c, g, m, i, l, p),25)


  val e11 = encode(ct1)(string2Chars("a"))        //> e11  : List[patmat.Huffman.Bit] = List(1, 0)
  val e12 = encode(ct1)(string2Chars("b"))        //> e12  : List[patmat.Huffman.Bit] = List(1, 1)
  val e13 = encode(ct1)(string2Chars("c"))        //> e13  : List[patmat.Huffman.Bit] = List(0)
  val e14 = encode(ct1)(string2Chars("ab"))       //> e14  : List[patmat.Huffman.Bit] = List(1, 0, 1, 1)
  val e15 = encode(ct1)(string2Chars("bc"))       //> e15  : List[patmat.Huffman.Bit] = List(1, 1, 0)
  val e16 = encode(ct1)(string2Chars("abc"))      //> e16  : List[patmat.Huffman.Bit] = List(1, 0, 1, 1, 0)
  val e17 = encode(ct1)(string2Chars("abcabc"))   //> e17  : List[patmat.Huffman.Bit] = List(1, 0, 1, 1, 0, 1, 0, 1, 1, 0)

  decode(ct1, e11)                                //> res12: List[Char] = List(a)
  decode(ct1, e12)                                //> res13: List[Char] = List(b)
  decode(ct1, e13)                                //> res14: List[Char] = List(c)
  decode(ct1, e14)                                //> res15: List[Char] = List(a, b)
  decode(ct1, e15)                                //> res16: List[Char] = List(b, c)
  decode(ct1, e16)                                //> res17: List[Char] = List(a, b, c)
  decode(ct1, e17)                                //> res18: List[Char] = List(a, b, c, a, b, c)

  val e21 = encode(ct2)(string2Chars("h"))        //> e21  : List[patmat.Huffman.Bit] = List(1, 1, 1)
  val e22 = encode(ct2)(string2Chars("e"))        //> e22  : List[patmat.Huffman.Bit] = List(1, 1, 0)
  val e23 = encode(ct2)(string2Chars("l"))        //> e23  : List[patmat.Huffman.Bit] = List(0)
  val e24 = encode(ct2)(string2Chars("o"))        //> e24  : List[patmat.Huffman.Bit] = List(1, 0)
  val e25 = encode(ct2)(string2Chars("he"))       //> e25  : List[patmat.Huffman.Bit] = List(1, 1, 1, 1, 1, 0)
  val e26 = encode(ct2)(string2Chars("hello"))    //> e26  : List[patmat.Huffman.Bit] = List(1, 1, 1, 1, 1, 0, 0, 0, 1, 0)
  val e27 = encode(ct2)(string2Chars("hellohello"))
                                                  //> e27  : List[patmat.Huffman.Bit] = List(1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 
                                                  //| 1, 1, 1, 0, 0, 0, 1, 0)

  decode(ct2, e21)                                //> res19: List[Char] = List(h)
  decode(ct2, e22)                                //> res20: List[Char] = List(e)
  decode(ct2, e23)                                //> res21: List[Char] = List(l)
  decode(ct2, e24)                                //> res22: List[Char] = List(o)
  decode(ct2, e25)                                //> res23: List[Char] = List(h, e)
  decode(ct2, e26)                                //> res24: List[Char] = List(h, e, l, l, o)
  decode(ct2, e27)                                //> res25: List[Char] = List(h, e, l, l, o, h, e, l, l, o)


    convert(ct1)                                  //> res26: patmat.Huffman.CodeTable = List((b,List(1, 1)), (a,List(1, 0)), (c,L
                                                  //| ist(0)))
    convert(ct2)                                  //> res27: patmat.Huffman.CodeTable = List((h,List(1, 1, 1)), (e,List(1, 1, 0))
                                                  //| , (o,List(1, 0)), (l,List(0)))
    convert(ct3)                                  //> res28: patmat.Huffman.CodeTable = List((c,List(1, 1, 1, 1, 1)), (a,List(1, 
                                                  //| 1, 1, 1, 0)), (h,List(1, 1, 1, 0)), (e,List(1, 1, 0)), ( ,List(1, 0, 1)), (
                                                  //| t,List(1, 0, 0)), (n,List(0, 1, 1)), (i,List(0, 1, 0)), (s,List(0, 0)))
    convert(ct4)                                  //> res29: patmat.Huffman.CodeTable = List((e,List(1, 1)), (t,List(1, 0, 1)), (
                                                  //| p,List(1, 0, 0)), (o,List(0, 1, 1)), (n,List(0, 1, 0)), (l,List(0, 0, 1)), 
                                                  //| (h,List(0, 0, 0)))
    convert(ct5)                                  //> res30: patmat.Huffman.CodeTable = List((f,List(1, 1, 1, 1)), (c,List(1, 1, 
                                                  //| 1, 0)), (a,List(1, 1, 0)), (r,List(1, 0, 1)), (o,List(1, 0, 0)), (n,List(0,
                                                  //|  1, 1)), (l,List(0, 1, 0)), (i,List(0, 0)))
    convert(ct6)                                  //> res31: patmat.Huffman.CodeTable = List((p,List(1, 1, 1, 1)), (l,List(1, 1, 
                                                  //| 1, 0)), (i,List(1, 1, 0)), (m,List(1, 0, 1, 1, 1)), (g,List(1, 0, 1, 1, 0))
                                                  //| , (c,List(1, 0, 1, 0, 1)), (a,List(1, 0, 1, 0, 0)), ( ,List(1, 0, 0, 1)), (
                                                  //| o,List(1, 0, 0, 0)), (e,List(0, 1, 1)), (t,List(0, 1, 0)), (s,List(0, 0)))
                                                  //| 

  val e61 = quickEncode(ct6)(string2Chars("m"))   //> e61  : List[patmat.Huffman.Bit] = List(1, 0, 1, 1, 1)
  val e62 = quickEncode(ct6)(string2Chars("i"))   //> e62  : List[patmat.Huffman.Bit] = List(1, 1, 0)
  val e63 = quickEncode(ct6)(string2Chars("s"))   //> e63  : List[patmat.Huffman.Bit] = List(0, 0)
  val e64 = quickEncode(ct6)(string2Chars("miss"))//> e64  : List[patmat.Huffman.Bit] = List(1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0)
                                                  //| 
  val e65 = quickEncode(ct6)(string2Chars("llissm"))
                                                  //> e65  : List[patmat.Huffman.Bit] = List(1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 0, 
                                                  //| 0, 0, 0, 1, 0, 1, 1, 1)
  val e66 = quickEncode(ct6)(string2Chars("misisisis"))
                                                  //> e66  : List[patmat.Huffman.Bit] = List(1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 
                                                  //| 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0)
  val e67 = quickEncode(ct6)(string2Chars("mmmmmm"))
                                                  //> e67  : List[patmat.Huffman.Bit] = List(1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 
                                                  //| 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1)
  val e68 = quickEncode(ct6)(string2Chars("mississippi state college"))
                                                  //> e68  : List[patmat.Huffman.Bit] = List(1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 
                                                  //| 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 
                                                  //| 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1, 
                                                  //| 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 1)

  decode(ct6, e61)                                //> res32: List[Char] = List(m)
  decode(ct6, e62)                                //> res33: List[Char] = List(i)
  decode(ct6, e63)                                //> res34: List[Char] = List(s)
  decode(ct6, e64)                                //> res35: List[Char] = List(m, i, s, s)
  decode(ct6, e65)                                //> res36: List[Char] = List(l, l, i, s, s, m)
  decode(ct6, e66)                                //> res37: List[Char] = List(m, i, s, i, s, i, s, i, s)
  decode(ct6, e67)                                //> res38: List[Char] = List(m, m, m, m, m, m)
  decode(ct6, e68)                                //> res39: List[Char] = List(m, i, s, s, i, s, s, i, p, p, i,  , s, t, a, t, e,
                                                  //|   , c, o, l, l, e, g, e)

}