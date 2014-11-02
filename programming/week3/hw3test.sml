use "hw3.sml";
Control.Print.printDepth := 10;
val s_start = "StringAtTheStart";
val s_mid   = "StringIsAtTheMiddle";
val s_end   = "StringIsAtTheEnd";
val s1 = "junk";
val s2 = "funk";
val slist1 = [s_start, s1, s_mid, s2, s_end];
val slist2 = [s1, s_start, s2, s_end, s1];
val scaps = [s_start, s_mid, s_end];
val c7 = Const 7; (* the value of 7 *)
val pc7= ConstP 7;(* pattern to match the value of 7 *)
val c6 = Const 6;
val pc6= ConstP 6;
val sm1= "SM1_VAR";
val sm2= "sm2_var";
val sm3= "sm3_var";
val sm4= "sm4_var";
val sm5= "sm5_var";
val pat1 = TupleP [Wildcard, ConstP 2, Wildcard, Variable sm1];
val pat2 = TupleP [Variable s1, Variable s2, Variable sm2];
val pat3 = TupleP [Wildcard, TupleP[Wildcard, pc7], ConstructorP("", TupleP [Wildcard, Wildcard])];
val pat4 = TupleP [Variable s2, Variable s1, pc6, Variable s1];
val patdouble= TupleP [Variable s1, Variable s1];
val pat_empty = TupleP [];

fun is_even x = if x mod 2 = 0 then SOME([x]) else NONE
fun is_capitalized s = if Char.isUpper (String.sub(s,0)) then SOME([s]) else NONE
val ilist = [1, 2, 3, 4, 5, 6];
val ilist_evens = [2, 4, 6];

val check1_1 = only_capitals slist1 = scaps;
val check1_2 = only_capitals [] = [];
val check1_3 = only_capitals [s1] = [];
val check1_4 = only_capitals [s_start] = [s_start]; 

val check2_1 = longest_string1 slist1 = s_mid;
val check2_2 = longest_string1 [] = "";
val check2_3 = longest_string1 slist2 = s_start;
val check2_4 = longest_string1 [s1] = s1;

val check3_1 = longest_string2 slist1 = s_mid;
val check3_2 = longest_string2 [] = "";
val check3_3 = longest_string2 slist2 = s_end;
val check3_4 = longest_string2 [s1] = s1;

val check4_1 = longest_string3 slist1 = s_mid;
val check4_2 = longest_string3 [] = "";
val check4_3 = longest_string3 slist2 = s_start;
val check4_4 = longest_string3 [s1] = s1;
val check4_5 = longest_string4 slist1 = s_mid;
val check4_6 = longest_string4 [] = "";
val check4_7 = longest_string4 slist2 = s_end;
val check4_8 = longest_string4 [s1] = s1;

val check5_1 = longest_capitalized [] = "";
val check5_2 = longest_capitalized [s1] = "";
val check5_3 = longest_capitalized slist2 = s_end;
val check5_4 = longest_capitalized [s_start] = s_start;

val check6_1 = rev_string "" = "";
val check6_2 = rev_string "a" = "a";
val check6_3 = rev_string "abc" = "cba"; 

val check7_1 = first_answer is_even ilist = [hd ilist_evens];
val check7_2 = first_answer is_even [22] = [22];
val check7_3 = (first_answer is_even [1, 3, 5] handle NoAnswer => [~1])=[~1];
val check7_4 = (first_answer is_even [] handle NoAnswer => [~1])=[~1];
val check7_5 = first_answer is_capitalized slist1 = [s_start];
val check7_6 = first_answer is_capitalized slist2 = [s_start];
val check7_7 = (first_answer is_capitalized [] handle NoAnswer => [s1]) = [s1];
val check7_8 = (first_answer is_capitalized [s1,s2] handle NoAnwer => [s1]) = [s1];

val check8_1 = all_answers is_capitalized [] = SOME [];
val check8_2 = all_answers is_capitalized slist1 = NONE;
val check8_3 = all_answers is_capitalized scaps = SOME scaps;
val check8_3 = all_answers is_capitalized [s1,s2] = NONE;
val check8_4 = all_answers is_even ilist_evens = SOME ilist_evens; 

val check9a_1 = count_wildcards pat1 = 2;
val check9a_2 = count_wildcards pat_empty = 0;
val check9a_3 = count_wildcards (TupleP [Wildcard]) = 1;
val check9a_4 = count_wildcards Wildcard = 1;
val check9a_5 = count_wildcards (ConstP 4) = 0;
val check9a_6 = count_wildcards pat3 = 4;

val check9b_1 = count_wild_and_variable_lengths Wildcard = 1;
val check9b_2 = count_wild_and_variable_lengths (Variable "x") = 1;
val check9b_3 = count_wild_and_variable_lengths pat1 = 2+7;
val check9b_4 = count_wild_and_variable_lengths pat_empty = 0;
val check9b_5 = count_wild_and_variable_lengths pat2 = 4+4+7;

val check9c_1 = count_some_var (s1,pat_empty) = 0;
val check9c_2 = count_some_var (s1,pat1) = 0;
val check9c_3 = count_some_var (s1,pat2) = 1;
val check9c_4 = count_some_var (s1,(Variable s1))=1;
val check9c_5 = count_some_var (s1,pat4)=2;

val check10_1 = check_pat patdouble = false;
val check10_2 = check_pat pat3 = true;
val check10_3 = check_pat pat_empty = true;
val check10_4 = check_pat pat2 = true;
val check10_5 = check_pat pat4 = false;

val check11_1 = match(c7, Wildcard) = SOME [];
val check11_2 = match(Unit, Wildcard) = SOME [];
val check11_3 = match(Tuple[c7], Wildcard) = SOME [];
val check11_4 = match(Constructor(sm1,c7), Wildcard ) = SOME [];
val check11_5 = match(c7, Variable sm2) = SOME [(sm2,c7)];
val check11_6 = match(Unit, Variable sm1) = SOME [(sm1,Unit)];
val check11_7 = match(Unit, UnitP) = SOME [];
val check11_8 = match(c7, UnitP) = NONE;
val check11_9 = match(c7, pc7) = SOME [];
val check11_10= match(c7, pc6) = NONE;
val check11_11= match(Constructor(sm1,c7), ConstructorP(sm1,Wildcard)) = SOME[];
val check11_12= match(Constructor(sm2,c7), ConstructorP(sm1,Wildcard)) = NONE;
val check11_13= match(Constructor(sm1,c7), ConstructorP(sm1,UnitP)) = NONE;
val check11_14= match(Constructor(sm1,c7), ConstructorP(sm1,Variable sm4)) = SOME[(sm4,c7)];
val check11_15= match(Tuple[c7], TupleP[pc7]) = SOME [];
val check11_16= match(Tuple[c7], TupleP[pc7,pc7]) = NONE;
val check11_17= match(Tuple[c7,c6,Unit,c7], TupleP[pc7,Variable sm2,Wildcard,pc6]) = NONE;
val check11_17a=match(Tuple[c7,c6,Unit,c7], TupleP[pc7,Variable sm2,Wildcard,pc7]) = SOME [(sm2,c6)];
val check11_18= match(Tuple[c7,c6,Unit,c7], TupleP[Variable sm3,Variable sm2,Variable sm4,Variable sm5]) = 
		SOME (List.rev [(sm5,c7),(sm4,Unit),(sm2,c6),(sm3,c7)]);
val check11_19= match(Tuple[c7,Unit], TupleP[Variable s1,Variable s2]) = SOME [(s1,c7),(s2,Unit)];
val check11_20= match(c6, Wildcard) = SOME[];
val check11_21= match(c7, Variable sm1) = SOME [(sm1,c7)];
val check11_22= match(c7, UnitP) = NONE;
val check11_23= match(Unit,pc7) = NONE;
val check11_24= match(Constructor(sm3,c7), ConstructorP(sm3,Variable sm1)) = SOME [(sm1,c7)];
val check11_25= match(Tuple [c7,c6], TupleP [Variable sm1,Variable sm2]) = SOME [(sm1,c7),(sm2,c6)];
val check11_26= match (c7,TupleP[UnitP,UnitP]) = NONE;

val check12_1 = first_match c7 [ConstP 1, UnitP, pc7, Variable sm1] = SOME [];
val check12_2 = first_match c7 [ConstP 1, UnitP, Variable sm1, pc7] = SOME [(sm1,c7)];
val check12_3 = first_match c7 [ConstP 1, UnitP, TupleP[UnitP, UnitP]] = NONE;
val check12_4 = first_match c7 [UnitP, Wildcard, Variable s1] = SOME [];
val check12_5 = first_match c7 [UnitP, Variable s1, Wildcard] = SOME [(s1, c7)];
val check12_6 = first_match c7 [UnitP, pc6, ConstructorP(s2,Variable s1)] = NONE;
val check12_7 = first_match Unit [] = NONE;
val check12_8 = first_match (Constructor(s1,c7)) [] = NONE;
val check12_9 = first_match (Constructor(s1,c7)) [ConstructorP(s1,Variable sm1), ConstructorP(s1,Variable sm2)] = SOME [(sm1,c7)];
val check12_10= first_match (Constructor(s1,c7)) [ConstructorP(s2,Variable sm1), ConstructorP(s1,Variable sm2)] = SOME [(sm2,c7)];
val check12_11= first_match (Tuple[c6,c7]) [UnitP, TupleP[pc7,pc6]] = NONE;
val check12_11= first_match (Tuple[c6,c7]) [UnitP, TupleP[pc6,pc7]] = SOME [];
val check12_12= first_match (Tuple[c6,c7]) [UnitP, TupleP[Variable s1, Variable s2]] = SOME [(s1,c6),(s2,c7)];

val wildcard_twice = TupleP [Wildcard, Wildcard];
val tpat1 = [TupleP[Variable("x"), Variable("y")], wildcard_twice];
val check13_1 = typecheck_patterns([], tpat1) = SOME (TupleT [Anything,Anything]);
val check13_2 = typecheck_patterns([], [wildcard_twice, TupleP[Wildcard, wildcard_twice]])= SOME (TupleT [Anything,TupleT [Anything,Anything]]);
val check13_3 = typecheck_patterns([], [TupleP[pc6, pc7], TupleP[Wildcard,Wildcard],TupleP[Wildcard,wildcard_twice]]) = NONE;

val check13_4 = typecheck_patterns([("King", "Rank", UnitT), ("Queen", "Rank", UnitT), ("Jack", "Rank", UnitT),
				     ("Num", "Rank", IntT)], [ConstructorP("King", UnitP), ConstructorP("Num", TupleP[Wildcard])]) = NONE;
val check13_5 = typecheck_patterns([("King", "Rank", UnitT), ("Queen", "Rank", UnitT), ("Jack", "Rank", UnitT),
				     ("Num", "Rank", IntT)], [ConstructorP("King", UnitP), ConstructorP("Num", ConstP 1)]) = SOME (Datatype "Rank");
val check13_6 = typecheck_patterns([("c","t",TupleT[Anything,IntT])], [ConstructorP("c",TupleP[ConstP 4, Variable "x"])]) = NONE;

val check13_7 = typecheck_patterns ([("A", "A", IntT), ("B", "B", UnitT)], [TupleP [ConstructorP("A", pc6)], TupleP[ConstructorP("A", pc7)] ])
		= SOME (TupleT [Datatype "A"]);
val check13_8 = typecheck_patterns ([("A", "A", IntT), ("B", "AB", IntT)],
             [ TupleP [ConstructorP ("A", pc6)],
               TupleP [ConstructorP ("B", pc7)],
               TupleP [ConstructorP ("A", pc6), Wildcard],
               TupleP [Wildcard] ]) = NONE;
val check13_9 = typecheck_patterns ([],[ TupleP [pc6, Wildcard], TupleP [Wildcard, pc7] ]) = SOME (TupleT [IntT,IntT]);
val check13_10 = typecheck_patterns ([], [Variable "x"]) = SOME Anything;
val check13_11 = typecheck_patterns ([], [ConstructorP ("A", pc6)]) = NONE;
val check13_12 = typecheck_patterns ([], [Wildcard, TupleP [Wildcard]]) = SOME (TupleT [Anything]);
val check13_13 = typecheck_patterns ([("A", "ABCD", UnitT)], [Wildcard, ConstructorP ("A", UnitP)]) = SOME (Datatype "ABCD");
