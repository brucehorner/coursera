use "hw2.sml";
val s1 = "one";
val s2 = "two";
val s3 = "three";
val ss= [s1, s2, s3];

val check1a1 = all_except_option (s1, ss) = SOME [s2, s3];
val check1a2 = all_except_option (s2, ss) = SOME [s1, s3];
val check1a3 = all_except_option (s3, ss) = SOME [s1, s2];
val check1a4 = all_except_option (s1, []) = NONE;
val check1a5 = all_except_option ("invalid", ss) = NONE;

val check1b1 = get_substitutions1([["Fred","Fredrick"],
               ["Elizabeth","Betty"],["Freddie","Fred","F"]],
               "Fred")=["Fredrick","Freddie","F"];
val check1b2 = get_substitutions1([["Fred","Fredrick"],
               ["Jeff","Jeffrey"],["Geoff","Jeff","Jeffrey"]],
               "Jeff")=["Jeffrey","Geoff","Jeffrey"];
val check1b3 = get_substitutions1([["Fred","Fredrick"],
               ["Elizabeth","Betty"],["Freddie","Fred","F"]],
               "Betty")=["Elizabeth"];
val check1b4 = get_substitutions1([["Fred","Fredrick"],
               ["Elizabeth","Betty"],["Freddie","Fred","F"]],
               "Roger")=[];

val check1c1 = get_substitutions2([["Fred","Fredrick"],
               ["Elizabeth","Betty"],["Freddie","Fred","F"]],
               "Fred")=["Freddie","F","Fredrick"];
val check1c2 = get_substitutions2([["Fred","Fredrick"],
               ["Jeff","Jeffrey"],["Geoff","Jeff","Jeffrey"]],
               "Jeff")=["Geoff","Jeffrey","Jeffrey"];
val check1c3 = get_substitutions2([["Fred","Fredrick"],
               ["Elizabeth","Betty"],["Freddie","Fred","F"]],
               "Betty")=["Elizabeth"];
val check1c4 = get_substitutions2([["Fred","Fredrick"],
               ["Elizabeth","Betty"],["Freddie","Fred","F"]],
               "Roger")=[];

val check1d1 = similar_names([["Fred","Fredrick"],["Elizabeth","Betty"],
			      ["Freddie","Fred","F"]],
			     {first="Fred", middle="W", last="Smith"})=
	       [{first="Fred", last="Smith", middle="W"},
		{first="Fredrick", last="Smith", middle="W"},
		{first="Freddie", last="Smith", middle="W"},
		{first="F", last="Smith", middle="W"}];
val check1d2 = similar_names([["Fred","Fredrick"],["Elizabeth","Betty"],
			      ["Freddie","Fred","F"]],
			     {first="Roger", middle="W", last="Smith"})=
	       [{first="Roger", last="Smith", middle="W"}];
val check1d3 = similar_names([["Fred","Fredrick"],["Elizabeth","Betty"],
			      ["Freddie","Fred","F"]],
			     {first="Betty", middle="W", last="Smith"})=
	       [{first="Betty", last="Smith", middle="W"},
		{first="Elizabeth", last="Smith", middle="W"}];


val ace_spades = (Spades,Ace);
val queen_clubs = (Clubs,Queen);
val seven_hearts = (Hearts,Num 7);
val jack_diamonds = (Diamonds,Jack); 
val two_clubs = (Clubs, Num 2);

val check2a1 = card_color ace_spades=Black;
val check2a2 = card_color seven_hearts=Red;
val check2a3 = card_color queen_clubs=Black;
val check2a4 = card_color jack_diamonds=Red;

val check2b1 = card_value ace_spades=11;
val check2b2 = card_value seven_hearts=7;
val check2b3 = card_value queen_clubs=10;
val check2b4 = card_value jack_diamonds=10;

val card_list = [ace_spades,queen_clubs,seven_hearts,jack_diamonds];
val check2c1 = remove_card (card_list, ace_spades, IllegalMove)=[queen_clubs,seven_hearts,jack_diamonds];
val check2c2 = remove_card (card_list, seven_hearts, IllegalMove)=[queen_clubs,ace_spades,jack_diamonds];
val check2c3 = remove_card (card_list, jack_diamonds, IllegalMove)=[seven_hearts,queen_clubs,ace_spades];
val check2c4 = remove_card (card_list, two_clubs, IllegalMove)=[] handle IllegalMove => true;

val check2d1 = all_same_color [];
val check2d2 = all_same_color [two_clubs];
val check2d3 = all_same_color [queen_clubs,ace_spades,two_clubs];
val check2d4 = all_same_color [jack_diamonds,ace_spades]=false;

val check2e1 = sum_cards []=0;
val check2e2 = sum_cards [two_clubs]=2;
val check2e3 = sum_cards [jack_diamonds,ace_spades]=21;
val check2e4 = sum_cards [queen_clubs,queen_clubs,queen_clubs]=30;
val check2e5 = sum_cards card_list=38;
val goal=20;
val check2f1 = score ([],10)=5;
val check2f2 = score ([ace_spades],5)=9; (* (3x(11-5) / 2 = 9 *)
val check2f3 = score (card_list,sum_cards(card_list))=0;
val check2f4 = score (card_list,goal)=54;



(* Dan Grossman, Coursera PL, HW2 Provided Tests *)

(* These are just two tests for problem 2; you will want more.

   Naturally these tests and your tests will use bindings defined 
   in your solution, in particular the officiate function, 
   so they will not type-check if officiate is not defined.
 *)

fun provided_test1 () = (* correct behavior: raise IllegalMove *)
    let val cards = [(Clubs,Jack),(Spades,Num(8))]
	val moves = [Draw,Discard(Hearts,Jack)]
    in
	officiate(cards,moves,42)
    end

fun provided_test2 () = (* correct behavior: return 3 *)
    let val cards = [(Clubs,Ace),(Spades,Ace),(Clubs,Ace),(Spades,Ace)]
	val moves = [Draw,Draw,Draw,Draw,Draw]
    in
 	officiate(cards,moves,42)
    end

val check2g1 = provided_test1()=999 handle IllegalMove => true;
val check2g2 = provided_test2()=3;
val check2g3 = officiate(card_list,[],goal)=goal div 2;
val check2g4 = officiate([],[],goal)=goal div 2;
val check2g5 = officiate([],[Draw],goal)=goal handle IllegalMove => true;

val ace_hearts = (Hearts,Ace);
val check3a1 = score_challenge (card_list,goal)=24;
val check3a2 = score_challenge ([ace_hearts,ace_hearts],10)=3;
val check3a3 = score_challenge ([ace_hearts,ace_spades],10)=6;
val check3a4 = score_challenge ([ace_spades],goal)=9 div 2;

val cards_ch = [(Clubs,Ace),(Spades,Ace),(Clubs,Ace),(Spades,Ace),(Spades,Num 8)];
val check3a5 = officiate_challenge(cards_ch,[Draw,Draw,Draw,Draw,Draw],42)=0;
val check3b6 = officiate_challenge([ace_spades,ace_hearts],[Draw,Draw,Discard ace_spades],3)=1;

