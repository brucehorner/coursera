package patmat
import patmat.Huffman._

object testing
{
    val tree = createCodeTree("hello, world, this is not easy but it is great fun".toList)
                                                  //> tree  : patmat.Huffman.CodeTree = Fork(Fork(Leaf( ,10),Fork(Leaf(t,5),Fork(F
                                                  //| ork(Leaf(w,1),Leaf(y,1),List(w, y),2),Leaf(e,3),List(w, y, e),5),List(t, w, 
                                                  //| y, e),10),List( , t, w, y, e),20),Fork(Fork(Fork(Leaf(l,3),Leaf(o,3),List(l,
                                                  //|  o),6),Fork(Fork(Leaf(,,2),Leaf(a,2),List(,, a),4),Fork(Fork(Leaf(b,1),Leaf(
                                                  //| d,1),List(b, d),2),Fork(Leaf(f,1),Leaf(g,1),List(f, g),2),List(b, d, f, g),4
                                                  //| ),List(,, a, b, d, f, g),8),List(l, o, ,, a, b, d, f, g),14),Fork(Fork(Fork(
                                                  //| Leaf(h,2),Leaf(n,2),List(h, n),4),Leaf(i,4),List(h, n, i),8),Fork(Fork(Leaf(
                                                  //| r,2),Leaf(u,2),List(r, u),4),Leaf(s,4),List(r, u, s),8),List(h, n, i, r, u, 
                                                  //| s),16),List(l, o, ,, a, b, d, f, g, h, n, i, r, u, s),30),List( , t, w, y, e
                                                  //| , l, o, ,, a, b, d, f, g, h, n, i, r, u, s),50)
    val test = "string".toList                    //> test  : List[Char] = List(s, t, r, i, n, g)
    encode(tree)(test)                            //> res0: List[patmat.Huffman.Bit] = List(1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0, 1,
                                                  //|  1, 0, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1)
    quickEncode(tree)(test)                       //> res1: List[patmat.Huffman.Bit] = List(1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0, 1,
                                                  //|  1, 0, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1)
}