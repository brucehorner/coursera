(* University of Washington, Programming Languages, Homework 7
   hw7testsprovided.sml *)
(* Will not compile until you implement preprocess and eval_prog *)

(* These tests do NOT cover all the various cases, especially for intersection *)

use "hw7.sml";

(* Must implement preprocess_prog and Shift before running these tests *)

fun real_equal(x,y) = Real.compare(x,y) = General.EQUAL;

(* Preprocess tests *)
let
	val Point(a,b) = preprocess_prog(LineSegment(3.2,4.1,3.2,4.1))
	val Point(c,d) = Point(3.2,4.1)
in
	if real_equal(a,c) andalso real_equal(b,d)
	then (print "OK - preprocess converts a LineSegment to a Point successfully\n")
	else (print "ERR - preprocess does not convert a LineSegment to a Point succesfully\n")
end;

let 
	val LineSegment(a,b,c,d) = preprocess_prog (LineSegment(3.2,4.1,~3.2,~4.1))
	val LineSegment(e,f,g,h) = LineSegment(~3.2,~4.1,3.2,4.1)
in
	if real_equal(a,e) andalso real_equal(b,f) andalso real_equal(c,g) andalso real_equal(d,h)
	then (print "preprocess flips an improper LineSegment successfully\n")
	else (print "preprocess does not flip an improper LineSegment successfully\n")
end;

let val LineSegment(x1,y1,x2,y2) = preprocess_prog (LineSegment (3.2,4.1,3.2,4.0)) in
    if real_equal(x1,3.2) andalso
       real_equal(x2,3.2) andalso
       real_equal(y1,4.0) andalso
       real_equal(y2,4.1) then print "OK 1.1\n" else print "ERR 1.1\n"
end;

(* eval_prog tests with Shift*)
let 
	val Point(a,b) = (eval_prog (preprocess_prog (Shift(3.0, 4.0, Point(4.0,4.0))), []))
	val Point(c,d) = Point(7.0,8.0) 
in
	if real_equal(a,c) andalso real_equal(b,d)
	then (print "OK - eval_prog with empty environment worked\n")
	else (print "ERR - eval_prog with empty environment is not working properly\n")
end;

val LineSegment(a,b,c,d) = eval_prog(Shift(5.0,6.0,LineSegment(3.0,4.0,8.0,9.0)), []);
if real_equal(a,8.0) andalso real_equal(b,10.0) andalso real_equal(c,13.0) andalso real_equal(d,15.0) then print "OK 1.2\n" else print "ERR 1.2\n";

(* Using a Var *)
let 
	val Point(a,b) = (eval_prog (Shift(3.0,4.0,Var "a"), [("a",Point(4.0,4.0))]))
	val Point(c,d) = Point(7.0,8.0) 
in
	if real_equal(a,c) andalso real_equal(b,d)
	then (print "OK - eval_prog with 'a' in environment is working properly\n")
	else (print "ERR - eval_prog with 'a' in environment is not working properly\n")
end;


(* With Variable Shadowing *)
let 
	val Point(a,b) = (eval_prog (Shift(3.0,4.0,Var "a"), [("a",Point(4.0,4.0)),("a",Point(1.0,1.0))]))
	val Point(c,d) = Point(7.0,8.0) 
in
	if real_equal(a,c) andalso real_equal(b,d)
	then (print "OK - eval_prog with shadowing 'a' in environment is working properly\n")
	else (print "ERR - eval_prog with shadowing 'a' in environment is not working properly\n")
end;

(* eval_prog tests with Shift Line*)
let
    val Line(a,b) = (eval_prog (preprocess_prog (Shift(3.0, 4.0, Line(4.0,4.0))), []))
    val Line(c,d) = Line(4.0,~4.0) 
in
    if real_close(a,c) andalso real_close(b,d)
    then (print "shift works with Line\n")
    else (print "shift does NOT work with Line\n")
end;

(* eval_prog tests with Shift VerticalLine*)
let 
    val VerticalLine(a) = (eval_prog (preprocess_prog (Shift(3.0, 4.0, VerticalLine(4.0))), []))
    val VerticalLine(c) = VerticalLine(7.0) 
in
    if real_close(a,c)
    then (print "shift works with VerticalLine\n")
    else (print "shift does NOT work with VerticalLine\n")
end;
    
(* eval_prog tests with Shift LineSegment*)
let 
    val LineSegment(a, b, a1, b1) = (eval_prog (preprocess_prog (Shift(3.0, 4.0, LineSegment(4.0, 3.0, 12.0, ~2.0))), []))
    val LineSegment(c, d, c1, d1) = LineSegment(7.0, 7.0, 15.0, 2.0) 
in
    if real_close(a,c) andalso real_close(b, d) andalso real_close(a1, c1) andalso real_close(b1, d1)
    then (print "shift works with LineSegment\n")
    else (print "shift does NOT work with LineSegment\n")
end;
