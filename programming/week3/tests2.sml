"matching test";

val cons1  = Constructor("answer", Const(42));
val consp1 = ConstructorP("answer", Variable("the_answer"));
val cons2  = Constructor("marvin", Unit);
val consp2 = ConstructorP("marvin", Variable("depressing_robot"));
val vals_tuple = Tuple[Const(42), Constructor("How many roads must a man walk down?", Const(42)),
                       Unit, Tuple[Const(44), Unit], cons1, cons2];
val pats_tuple = TupleP[Wildcard, Variable("question"),
                        UnitP, TupleP[Wildcard, UnitP], consp1, consp2];

"Unit matching test";
match(Unit, Wildcard)   = SOME [];
match(Unit, Variable("Don't panic!")) = SOME [("Don't panic!",Unit)];
match(Unit, UnitP)      = SOME [];
match(Unit, ConstP(42)) = NONE;
match(Unit, consp1)     = NONE;
match(Unit, consp2)     = NONE;
match(Unit, pats_tuple) = NONE;

"Const matching test";
match(Const(42), Wildcard)   = SOME [];
match(Const(42), Variable("Don't panic!")) = SOME [("Don't panic!",Const 42)];
match(Const(42), UnitP)      = NONE;
match(Const(42), ConstP(42)) = SOME [];
match(Const(42), ConstP(43)) = NONE;
match(Const(42), consp1)     = NONE;
match(Const(42), consp2)     = NONE;
match(Const(42), pats_tuple) = NONE;

"Tuple matching test";
match(vals_tuple, Wildcard)   = SOME [];
match(vals_tuple, Variable("Don't panic!"))
 = SOME [("Don't panic!",vals_tuple)];
match(vals_tuple, UnitP)      = NONE;
match(vals_tuple, ConstP(42)) = NONE;
match(vals_tuple, consp1)     = NONE;
match(vals_tuple, consp2)     = NONE;
match(vals_tuple, pats_tuple)
 = SOME [("question",Constructor("How many roads must a man walk down?", Const 42)),
         ("the_answer",Const 42),("depressing_robot",Unit)];

"Constructor matching test1";
match(cons1, Wildcard)   = SOME [];
match(cons1, Variable("Don't panic!"))
 = SOME [("Don't panic!",Constructor ("answer", Const 42))];
match(cons1, UnitP)      = NONE;
match(cons1, ConstP(42)) = NONE;
match(cons1, consp1)     = SOME [("the_answer",Const 42)];
match(cons1, consp2)     = NONE;
match(cons1, pats_tuple) = NONE;

"Constructor matching test2";
match(cons2, Variable("Don't panic!"))
 =  SOME [("Don't panic!",Constructor ("marvin", Unit))];
match(cons2, UnitP)      = NONE;
match(cons2, ConstP(42)) = NONE;
match(cons2, consp1)     = NONE;
match(cons2, consp2)     = SOME [("depressing_robot",Unit)];
match(cons2, pats_tuple) = NONE;

"first_math test";
first_match cons1 [consp2,pats_tuple]
= NONE;
first_match cons1 [consp2,pats_tuple,consp1]
= SOME [("the_answer",Const 42)];
first_match cons1 [consp2,pats_tuple,Variable("an_answer")]
= SOME [("an_answer",Constructor ("answer",Const 42))];
