(* Coursera PL, HW2 Completed Code *)

(* if you use this function to compare two strings (returns true if the same
   string), then you avoid several of the functions in problem 1 having
   polymorphic types that may be confusing *)
fun same_string(s1 : string, s2 : string) =
    s1 = s2

(* put your solutions for problem 1 here *)

(* 1A Write a function all_except_option, which takes a string and a string
 list. Return NONE if the string is not in the list, else return SOME lst
 where lst is identical to the argument list except the string is not in
 it. You may assume the string is in the list at most once. Use same_string,
 provided to you, to compare strings *)
fun all_except_option (s, strs) = 
    let 
	fun build_reduced_list (_, []) = NONE
	  | build_reduced_list (already_seen, str::remaining) =  
            (* okay since order doesn't matter I shouldn't stick the
               "strhead" at the end of "left" for the next iteration,
               but it makes the test checking easier, so I'll take the hit
               on performance of "@" versus "::" for this exercise *)
	    if same_string (s,str)
	    then SOME (already_seen @ remaining)
	    else build_reduced_list (already_seen @ [str], remaining)

    in
	build_reduced_list ([], strs)
    end

(* 1B Write a function get_substitutions1, which takes a string list list
 (a list of list of strings, the substitutions) and a string s and returns
 a string list. The result has all the strings that are in some list in
 substitutions that also has s, but s itself should not be in the result.  
 Assume each list in substitutions has no repeats. The result will have
 repeats if s and another string are both in more than one list in
 substitutions.  Use part (a) and ML's list-append (@) but no other helper
 functions. Sample solution is around 6 lines. *)
fun get_substitutions1 ([],_) = []
  | get_substitutions1 ((lsthead::remainder),str) = 
    case all_except_option(str, lsthead) of	
	NONE => get_substitutions1(remainder, str)
     |  SOME(the_list) => the_list @ get_substitutions1(remainder, str)

(* 1C Write a function get_substitutions2, which is like get_substitutions1
 except it uses a tail-recursive local helper function. *)
fun get_substitutions2 (strlists, s) =
    let
       fun find_subs ([], accumulator) = accumulator
	 | find_subs ((firstlist::remainder), accumulator) = 
	   case all_except_option(s, firstlist) of
	       NONE => find_subs (remainder, accumulator)
	     | SOME(the_list) => find_subs (remainder, the_list@accumulator)				      
    in
	find_subs (strlists, [])
    end

(* 1D Write a function similar_names, which takes a string list list of
 substitutions (as in parts (b) and (c)) and a full name of type
 {first:string,middle:string,last:string} and returns a list of full
 names (type {first:string,middle:string,last:string} list). The result
 is all the full names you can produce by substituting for the first name
 (and only the first name) using substitutions and parts (b) or (c). The
 answer should begin with the original name (then have 0 or more other
 names) *)
fun similar_names (substitutions,{first=f,middle=m,last=l}) =
    let
	fun build_alt_name_list (list_of_first_names) = 
	    case list_of_first_names of
		[] => []
	      | (first_name::list_of_first_names') => 
		{first=first_name,middle=m,last=l} :: 
		    build_alt_name_list (list_of_first_names') 
    in
	{first=f,middle=m,last=l} :: 
	build_alt_name_list(get_substitutions1(substitutions,f))
    end


(* =========================== PART 2 ===================================== *)

(* you may assume that Num is always used with values 2, 3, ..., 9
   though it will not really come up *)
datatype suit = Clubs | Diamonds | Hearts | Spades
datatype rank = Jack | Queen | King | Ace | Num of int 
type card = suit * rank

datatype color = Red | Black
datatype move = Discard of card | Draw 

exception IllegalMove

(* put your solutions for problem 2 here *)

(* 2A Write a function card_color, which takes a card and returns its color
 (spades and clubs are black, diamonds and hearts are red). 
 Note: One case-expression is enough *)
fun card_color (Clubs,_) = Black
  | card_color (Spades,_) =  Black
  | card_color (Hearts,_) = Red
  | card_color (Diamonds,_) = Red

(* 2B Write a function card_value, which takes a card and returns its value
 (numbered cards have their number as the value, aces are 11, everything
 else is 10) *)
fun card_value (_,Num n) = n
  | card_value (_,Ace) = 11
  | card_value (_,_) = 10 


(* 2C Write a function remove_card, which takes a list of cards cs, a card c,
 and an exception e. It returns a list that has all the elements of cs
 except c. If c is in the list more than once, remove only the first one.
 If c is not in the list, raise the exception e. 
 You can compare cards with = *)
fun remove_card (cs, c, e) =
    let 
	fun build_new_list (_, []) = raise e
	  | build_new_list (already_seen, (card_to_test::remaining)) = 
	    (* for this function will take order preservation as not
               important so cons the card to the part of the list 
               already seen rather than keep at the same position *)
	    if card_to_test=c
	    (* we're done, ignore c and eval to what we've seen joined
               to the what's left to see *)
  	    then already_seen @ remaining
	    else build_new_list (card_to_test::already_seen, remaining)
    in
	build_new_list ([], cs)
    end

(* 2D Write a function all_same_color, which takes a list of cards and
 returns true if all the cards in the list are the same color *)
fun all_same_color [] = true
  | all_same_color (_::[]) = true
  | all_same_color (card1::(card2::cards')) = 
	card_color(card1)=card_color(card2) andalso
	all_same_color(card2::cards')

(* 2E Write a function sum_cards, which takes a list of cards and returns
 the sum of their values. Use a locally defined helper function that
 is tail recursive *)
fun sum_cards (cards) = 
    let
	fun add_up ([], running_total) = running_total
	  | add_up (card1::remainder, running_total) =
	        add_up (remainder, running_total+card_value(card1))

    in
	add_up (cards,0)
    end

(* 2F Write a function score, which takes a card list (the held-cards) and
 an int (the goal) and computes the score as: Let sum be the sum of the
 values of the held-cards. If sum is greater than goal, the preliminary
 score is three times (sum - goal), else the preliminary score is goal - sum.
 The score is the preliminary score unless all the held-cards are the same
 color, in which case the score is the preliminary score divided by 2
 (and rounded down as usual with integer division; use ML's div operator) *)

(* taken as external function to use later *)
fun preliminary_score (sum,goal) =
    if (sum > goal) then 3*(sum - goal) else goal - sum

fun score (held_cards, goal) = 
    let
	val sum = sum_cards held_cards
	val ps = preliminary_score (sum,goal)
    in
	if all_same_color held_cards
	then ps div 2 else ps	    
    end

(* 2G Write a function officiate, which "runs a game." It takes a card list
 (the card-list) a move list (what the player "does" at each point), and
 an int (the goal) and returns the score at the end of the game after
 processing (some or all of) the moves in the move list in order. Use a
 locally defined recursive helper function that takes several arguments
 that together represent the current state of the game *)
fun officiate (available_cards, moves, goal) =
    let
	fun play_game (_, [], cards_held) = score (cards_held, goal)
	  | play_game (cards_avail, move::remain_moves, cards_held) = 
	    case move of
		(* remove card from held list, keep same available and moves *)
		Discard c => play_game (cards_avail, remain_moves,
					remove_card(cards_held,c,IllegalMove))
	      | Draw =>
		case cards_avail of
		    [] => raise IllegalMove (* no more cards to Draw: error *)
		  | drawn_card::remain_avail => 
		    let
			val new_held = drawn_card::cards_held
			val s = sum_cards (new_held)
		    in
			if s > goal then score (new_held,goal)
			else play_game (remain_avail, remain_moves, new_held)
		    end


    in
	play_game (available_cards, moves, [])
    end

(* 3A Write score_challenge and officiate_challenge to be like their
 non-challenge counterparts except each ace can have a value of 1 or 11
 and score_challenge should always return the least (i.e., best)
 possible score *)
fun generate_all_sums (original_sum, 0) = [original_sum]
  | generate_all_sums (original_sum, num_aces) = 
    (original_sum-(num_aces*10)) :: generate_all_sums(original_sum, num_aces-1)

fun min_list [] = raise Empty
  | min_list [i] = i
  | min_list (top::rest) = 
    let
	val minrest = min_list rest
    in
	if top < minrest then top else minrest
    end

fun count_aces [] = 0
  | count_aces (c::remainder) = 
    let val count_rest = count_aces remainder
    in case c of
	   (_,Ace) => 1 + count_rest
	 | (_,_) => count_rest
    end

fun score_challenge (held_cards, goal) = 
    let
	fun generate_all_scores [] = [] 
	  | generate_all_scores (sum::remainder) = 
	    let
		val ps = preliminary_score(sum, goal)
		val score = if all_same_color held_cards
			    then ps div 2 else ps	    
	    in
		score :: generate_all_scores (remainder)
	    end
    in
	min_list (generate_all_scores(
		    generate_all_sums(
                      sum_cards held_cards,count_aces held_cards)))
    end

fun officiate_challenge (available_cards, moves, goal) =
    let
	fun play_game (_, [], cards_held) = score_challenge (cards_held, goal)
	  | play_game (cards_avail, move::remain_moves, cards_held) = 
	    case move of
		(* remove card from held list, keep same available and moves *)
		Discard c => play_game (cards_avail, remain_moves,
					remove_card(cards_held,c,IllegalMove))
	      | Draw =>
		case cards_avail of
		    [] => raise IllegalMove (* no more cards to Draw: error *)
		  | drawn_card::remain_avail => 
		    let
			val new_held = drawn_card::cards_held
			val num_aces = count_aces new_held
			val sum_new = sum_cards new_held
			val min_score = min_list(
				generate_all_sums(sum_new,num_aces))
		    in
			if min_score > goal then score_challenge (new_held,goal)
			else play_game (remain_avail, remain_moves, new_held)
		    end
    in
	play_game (available_cards, moves, [])
    end
