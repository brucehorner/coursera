(* Coursera Programming Languages, Homework 3, Provided Code *)
exception NoAnswer

datatype pattern = Wildcard
		 | Variable of string
		 | UnitP
		 | ConstP of int
		 | TupleP of pattern list
		 | ConstructorP of string * pattern

datatype valu = Const of int
	      | Unit
	      | Tuple of valu list
	      | Constructor of string * valu

fun g f1 f2 p =
    let 
	val r = g f1 f2 
    in
	case p of
	    Wildcard          => f1 ()
	  | Variable x        => f2 x
	  | TupleP ps         => List.foldl (fn (p,i) => (r p) + i) 0 ps
	  | ConstructorP(_,p) => r p
	  | _                 => 0
    end

(**** for the challenge problem only ****)

datatype typ = Anything
	     | UnitT
	     | IntT
	     | TupleT of typ list
	     | Datatype of string

(**** you can put all your code here ****)

(* 1. Write a function only_capitals that takes a string list and returns a
 string list that has only the strings in the argument that start with an
 uppercase letter. Assume all strings have at least 1 character. Use
 List.filter, Char.isUpper, and String.sub to make a 1-2 line solution *)
fun only_capitals slist = 
    List.filter (fn s => Char.isUpper(String.sub(s,0))) slist

(* 2. Write a function longest_string1 that takes a string list and returns
 the longest string in the list. If the list is empty, return "". In the
 case of a tie, return the string closest to the beginning of the list.
 Use foldl, String.size, and no recursion *)
(* foldl function initial_value list *)
fun longest_string1 slist = 
    List.foldl (fn (s1,s2) => if (String.size s1) > (String.size s2)
			      then s1 else s2)
               "" slist

(* 3. Write a function longest_string2 that is exactly like longest_string1
 except in the case of ties it returns the string closest to the end of
 the list. Your solution should be almost an exact copy of longest_string1.
 Still use foldl and String.size. *)
fun longest_string2 slist = 
    List.foldl (fn (s1,s2) => if (String.size s1) >= (String.size s2)
			      then s1 else s2)
               "" slist
(* 4. Write functions longest_string_helper, longest_string3, and longest_string4
 such that:
   * longest_string3 has the same behavior as longest_string1 and
     longest_string4 has the same behavior as longest_string2.
   * longest_string_helper has type (int * int -> bool) -> string list ->
     string (notice the currying). This function will look a lot like 
     longest_string1 and longest_string2 but is more general because it takes
     a function as an argument.
   * longest_string3 and longest_string4 are defined with val-bindings and
     partial applications of longest_string_helper. *)
fun longest_string_helper f slist = 
    List.foldl (fn (s1,s2) =>
		if f(String.size s1,String.size s2) then s1 else s2) "" slist

val longest_string3 = longest_string_helper (fn(n1,n2) => n1>n2)
val longest_string4 = longest_string_helper (fn(n1,n2) => n1>=n2)

(* 5. Write a function longest_capitalized that takes a string list and
 returns the longest string in the list that begins with an uppercase
 letter (or "" if there are no such strings). Use a val-binding and the
 ML library's o operator for composing functions. Resolve ties like
 in problem 2. *)
val longest_capitalized = longest_string4 o only_capitals

(* 6. Write a function rev_string that takes a string and returns the
 string that is the same characters in reverse order. Use ML's o
 operator, the library function rev for reversing lists, and two
 library functions in the String module. *)
val rev_string = String.implode o List.rev o String.explode

(* 7. Write a function first_answer of type ('a -> 'b option) -> 'a list ->
 'b (notice the 2 arguments are curried). The first argument should be
 applied to elements of the second argument in order until the first
 time it returns SOME v for some v and then v is the result of the call
 to first_answer. If the first argument returns NONE for all list elements,
 then first_answer should raise the exception NoAnswer *)
fun first_answer f [] = raise NoAnswer
  | first_answer f (x::remainder) = case f(x) of
					SOME v => v
				      | NONE => first_answer f remainder

(* 8. Write a function all_answers of type ('a -> 'b list option) -> 
 'a list -> 'b list option (notice the 2 arguments are curried). The first
 argument should be applied to elements of the second argument. If it
 returns NONE for any element, then the result for all_answers is NONE. 
 Else the calls to the first argument will have produced SOME lst1,
 SOME lst2, ... SOME lstn and the result of all_answers is SOME lst 
 where lst is lst1, lst2, ..., lstn appended together (order doesn't
 matter) *)
fun all_answers f thelist = 
    let
	fun helper ([], accu) = SOME(accu)
	  | helper (x::remainder, accu) =
	    case f(x) of
		NONE => NONE 
	      | SOME v => helper (remainder, (accu@v))
    in
	helper (thelist, [])
    end

(* 9. (a) Use g to define a function count_wildcards that takes a pattern
          and returns how many Wildcard patterns it contains.
 (b) Use g to define a function count_wild_and_variable_lengths that takes a
     pattern and returns the number of Wildcard patterns it contains plus the
     sum of the string lengths of all the variables in the variable patterns
     it contains. (Use String.size. We care only about variable names; the
     constructor names are not relevant.)
 (c) Use g to define a function count_some_var that takes a string and a
 pattern (as a pair) and returns the number of times the string appears as
 a variable in the pattern. We care only about variable names; the
 constructor names are not relevant. *)
(* fun g f1 f2 p ==> (unit -> int) -> (string -> int) -> pattern -> int *)
(* for every wildcard, g calls f1 *)
(* for every variable, g calls f2 with a parameter *)
val count_wildcards = g (fn _=>1) (fn _=>0)  
val count_wild_and_variable_lengths = g (fn _=>1) (fn s=>String.size s)
fun count_some_var (s,pat) = g (fn _=>0) (fn y=>if y=s then 1 else 0) pat 

(* 10. Write a function check_pat that takes a pattern and returns true if
 and only if all the variables appearing in the pattern are distinct from
 each other (i.e., use different strings). The constructor names are not
 relevant *)
fun get_all_variable_names (Variable x) = [x] 
  | get_all_variable_names (TupleP ps) =
    List.foldl (fn(pt,x) => (get_all_variable_names pt)@x) [] ps
  | get_all_variable_names (ConstructorP(_,pt)) = get_all_variable_names pt
  | get_all_variable_names (_) = []

(* compare head of list to each value in the tail, if ok then continue 
   with the tail, where we compare its head with its tail and so on *)
fun contains_duplicate [] = false
  | contains_duplicate (s::ss') =
    List.exists (fn x => x=s) ss' orelse contains_duplicate ss'

fun check_pat pat = not (contains_duplicate(get_all_variable_names(pat)))

(* 11. Write a function match that takes a valu * pattern and returns a
 (string * valu) list option, namely NONE if the pattern does not match
 and SOME lst where lst is the list of bindings if it does. Note that if
 the value matches but the pattern has no patterns of the form Variable s,
 then the result is SOME []. *)
fun match (value, Variable s) = SOME [(s,value)]
  | match (Unit, UnitP) = SOME []
  | match (Const v, ConstP n) = if v=n then SOME [] else NONE
  | match (Tuple vs, TupleP ps) =
    if List.length vs=List.length ps
    then all_answers match (ListPair.zip(vs,ps))
    else NONE
  | match (Constructor(s1,v), ConstructorP(s2,pt)) =
    if s1=s2 then match(v,pt) else NONE
  | match (_, Wildcard) = SOME []
  | match (_, _) = NONE 

(* 12. Write a function first_match that takes a value and a list of
 patterns and returns a (string * valu) list option, namely NONE if no
 pattern in the list matches or SOME lst where lst is the list of bindings
 for the first pattern in the list that matches. Use first_answer and a
 handle-expression.  *)
fun first_match value patterns =
    SOME (first_answer match (List.map (fn p=>(value,p)) patterns))
    handle NoAnswer => NONE

(* 13. Write a function typecheck_patterns that "type-checks" a
 pattern list.  typecheck_patterns should have type
 ((string * string * typ) list) * (pattern list) -> typ option.
 The first argument contains elements that look like ("foo","bar",IntT),
 which means constructor foo makes a value of type Datatype "bar" given a
 value of type IntT.  Assume list elements all have different
 first fields (the constructor name), but there are probably elements with
 the same second field (the datatype name). Under the assumptions this
 list provides, you "type-check" the pattern list to see if there exists
 some typ (call it t) that all the patterns in the list can have.
 If so, return SOME t, else return NONE.
*)


(* this is very much work in progress, some tests pass but I've run
  out of time :( *)
fun match_pat_to_typ pat = 
    case pat of
	Wildcard => Anything
      | Variable _ => Anything
      | UnitP => UnitT
      | ConstP x => IntT
      | TupleP patlist => TupleT (List.map match_pat_to_typ patlist)
      | ConstructorP (s, p) => Datatype s

fun samesize_tuple_pattern (TupleP [], TupleP []) = TupleP []
  | samesize_tuple_pattern (TupleP l1, TupleP l2) = 
    if List.length l1 = List.length l2
    then TupleP l1
    else raise NoAnswer
  | samesize_tuple_pattern (_,_) = raise NoAnswer


fun typecheck_patterns ([], []) = NONE
  | typecheck_patterns ([], (init::plist)) =
    (SOME (match_pat_to_typ (List.foldl samesize_tuple_pattern init plist)) handle NoAnswer => NONE)
  | typecheck_patterns ((("","",Anything)::_), _) = NONE
  | typecheck_patterns (_, _) = NONE
				    

	
