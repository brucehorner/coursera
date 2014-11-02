use "hw7.sml";

val p1_x = 11.0/3.0
val p1 = Point (p1_x, p1_x-1.0)
val p2 = Point (2.0, 1.0)
val p3 = Point (3.0, 5.0)
val l1 = Line (1.0, ~1.0)
val l2 = Line (~0.5, 4.5)
val l3 = VerticalLine (5.0)
val l4 = Line (1.0, 5.0)
val l5_setup = Shift (~6.0, 0.0, l3)
val l5 = eval_prog (preprocess_prog(l5_setup), [])

val Point(a1,b1) = eval_prog (preprocess_prog(Intersect(l1, l2)), [])
val Point(a2,b2) = eval_prog (preprocess_prog(Intersect(l2, l1)), [])
val test1 = real_close(a1,a2) andalso real_close(b1,b2) andalso real_close(a1,p1_x) andalso real_close(b1,p1_x-1.0)

