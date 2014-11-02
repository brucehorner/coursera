use "hw2.sml";

fun looper() =
    let
	fun countdown (0) = true
	  | countdown (counter) =  
		let
		    val c1 = (Spades, Ace);
		    val c1a= (Spades, Num 1);
		    val c2 = (Hearts, Ace);
		    val c2a= (Hearts, Num 1);		   
		    val s1 = score ([c1,c2],counter);
		    val s2 = score ([c1a,c2],counter);
		    val s3 = score ([c1,c2a],counter);
		    val s4 = score ([c1a,c2a],counter);		    
		in
		    print (Int.toString(counter) ^ ": " ^ Int.toString (s1) ^ ", " ^ Int.toString(s2) ^ ", " ^ Int.toString(s3) ^ ", " ^ Int.toString(s4) ^ "\n");
		    countdown(counter-1)
		end
    in
	countdown(30)
    end





