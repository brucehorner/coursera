(* dates are (year, month, day) *)

(* Write a function is_older that takes two dates and evaluates to true or false. 
 It evaluates to true if the first argument is a date that comes before the 
 second argument. (If the two dates are the same,the result is false.) *)
fun is_older (dfirst: int*int*int, dsecond: int*int*int) = 
    if #1 dfirst < #1 dsecond then true else
    if #1 dfirst > #1 dsecond then false else
    if #2 dfirst < #2 dsecond then true else
    if #2 dfirst > #2 dsecond then false else
    if #3 dfirst < #3 dsecond then true else
    if #3 dfirst > #3 dsecond then false else
    false  (* they are the same *)

(* Write a function number_in_month that takes a list of dates and a month
 (i.e., an int) and returns how many dates in the list are in the given month. *)
fun number_in_month (dates: (int*int*int) list, month: int) =
    if null dates then 0
    else
	if #2 (hd dates) = month then
	    1 + number_in_month ((tl dates), month)
	else
	    number_in_month((tl dates), month)

(* Write a function number_in_months that takes a list of dates and a list of months
 (i.e., an int list) and returns the number of dates in the list of dates that are
 in any of the months in the list of months. Assume the list of months has no number
 repeated. Hint: Use your answer to the previous problem. *)
fun number_in_months (dates: (int*int*int) list, months: int list) =
    if null months then 0
    else
	number_in_month(dates,(hd months)) + number_in_months(dates,(tl months))

(* Write a function dates_in_month that takes a list of dates and a month (i.e., an
 int) and returns a list holding the dates from the argument list of dates that are
 in the month.  The returned list should contain dates in the order they were
 originally given. *)
fun dates_in_month (dates: (int*int*int) list, month: int) = 
    if null dates then []
    else
	if #2 (hd dates) = month then
	    (hd dates) :: dates_in_month ((tl dates), month)
	else
	    dates_in_month ((tl dates), month)			   

(* Write a function dates_in_months that takes a list of dates and a list of months
 (i.e., an int list) and returns a list holding the dates from the argument list of
 dates that are in any of the months in the list of months. Assume the list of months
 has no number repeated. Hint: Use your answer to the previous problem and SML's 
 list-append operator (@). *)
fun dates_in_months (dates: (int*int*int) list, months: int list ) = 
    if null months then []
    else
	dates_in_month(dates,(hd months)) @ dates_in_months(dates,(tl months))

(* Write a function get_nth that takes a list of strings and an int n and returns the
 nth element of the list where the head of the list is 1st. Do not worry about the
 case where the list has too few elements: your function may apply hd or tl to the
 empty list in this case, which is okay. *)
fun get_nth (strings: string list, position: int) =
    let
	fun offset (strs: string list, counter: int) = 
	    if counter=position then (hd strs)
	    else offset((tl strs), counter+1)
    in
	offset (strings,1)
    end

(* Write a function date_to_string that takes a date and returns a string of the form 
 January 20, 2013 (for example). Use the operator ^ for concatenating strings and the
 library function Int.toString for converting an int to a string. For producing the
 month part, do not use a bunch of conditionals. Instead, use a list holding 12
 strings and your answer to the previous problem. For consistency, put a comma
 following the day and use capitalized English month names *)
fun date_to_string (date: int*int*int) =
    let
	val months = ["January", "February", "March", "April",
		      "May", "June", "July", "August", "September",
		      "October", "November", "December"];
    in
	get_nth (months, #2 date) ^ " " ^ Int.toString (#3 date) ^ ", " 
	    ^ Int.toString (#1 date)
    end

(* Write a function number_before_reaching_sum that takes an int called sum, 
 which you can assume is positive, and an int list, which you can assume
 contains all positive numbers, and returns an int. You should return an int n
 such that the first n elements of the list add to less than sum, but the first
 n+1 elements of the list add to sum or more. Assume the entire list sums to
 more than the passed in value; it is okay for an exception to occur if this is
 not the case. *)
fun number_before_reaching_sum (sum: int, numbers: int list) = 
    let
	fun counter (priorSum: int, priorPosition: int, nums: int list) = 
	    let
		val newSum = priorSum + (hd nums);          
	    in
		if newSum >= sum then priorPosition
		else counter(newSum, priorPosition+1, (tl nums))
	    end

    in
	counter(0,0,numbers)
    end

(* Write a function what_month that takes a day of year (i.e., an int
 between 1 and 365) and returns what month that day is in (1 for January,
 2 for February, etc.). Use a list holding 12 integers and your answer to
 the previous problem. *)
fun what_month (day:int) =
    let
	val monthLengths = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    in
	1 + number_before_reaching_sum (day, monthLengths)
    end

(* Write a function month_range that takes two days of the year day1 and
 day2 and returns an int list [m1,m2,...,mn] where m1 is the month of day1,
 m2 is the month of day1+1, ..., and mn is the month of day day2. Note the
 result will have length day2 - day1 + 1 or length 0 if day1>day2. *)
fun month_range (day1: int, day2: int) = 
    let
	fun build_list (day: int, months: int list) =
	    let
		val newList = months @ [what_month(day)];
	    in
		if (day=day2) then newList
		else build_list (day+1, newList)
	    end
    in
	if day1 > day2 then []
	else build_list (day1, [])
    end


(* Write a function oldest that takes a list of dates and evaluates 
 to an (int*int*int) option. It evaluates to NONE if the list has no
 dates and SOME d if the date d is the oldest date in the list. *)
fun oldest (dates: (int*int*int) list) = 
    let
	fun find_oldest (currentOldest:(int*int*int), ds: (int*int*int) list) =
	    if null ds then currentOldest
	    else 
		if is_older(currentOldest,(hd ds)) then
		    find_oldest (currentOldest,(tl ds))
		else
		    find_oldest ((hd ds), (tl ds))
    in
	if null dates then NONE
	else SOME (find_oldest((hd dates),(tl dates)))
    end

(* Write functions number_in_months_challenge and dates_in_months_challenge
 that are like your solutions to problems 3 and 5 except having a month in 
 the second argument multiple times has no more effect than having it once.
 (Hint: Remove duplicates, then use previous work.) *)

fun remove_duplicates (nums: int list) = 
    let	
	fun contains (values: int list, number: int) = 
	    if null values then false
	    else
		if (hd values)=number then true
		else contains((tl values), number)

	fun de_dup (clean: int list, input: int list) =
	    if null input then clean
	    else
		if (contains(clean,(hd input))) then
		    de_dup (clean, (tl input))
		else
		    de_dup (clean @ [(hd input)], (tl input))
    in
	if null nums then []
	else
	    de_dup ([(hd nums)],(tl nums))
    end

fun number_in_months_challenge (dates: (int*int*int) list, months: int list) =
    number_in_months (dates,remove_duplicates(months))

fun dates_in_months_challenge (dates: (int*int*int) list, months: int list ) = 
    dates_in_months (dates,remove_duplicates(months))

(* Write a function reasonable_date that takes a date and determines if it
 describes a real date in the common era. A "real date" has a positive year
 (year 0 did not exist), a month between 1 and 12, and a day appropriate for
 the month. Solutions should properly handle leap years. Leap years are years
 that are either divisible by 400 or divisible by 4 but not divisible by 100.
 (Do not worry about days possibly lost in the conversion to the Gregorian
 calendar in the Late 1500s.) *)
fun is_leap_year (year: int) = 
    (year mod 400 = 0 orelse year mod 4 = 0) andalso not (year mod 100 = 0)

fun reasonable_date (date: int*int*int) = 
    let
	fun get_month_length (position: int, isLeap: bool) =
	    let
		val monthLengths = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
		fun offset (ns: int list, counter: int) = 
		    if counter=position then
			if position=2 then (* leap year check *)
			    if isLeap then 1+(hd ns) else
			    (hd ns)
			else
			    (hd ns)				
		    else
			offset((tl ns), counter+1)
	    in
		offset (monthLengths,1)
	    end

    in
	if #1 date < 1 then false
	else if #2 date < 1 orelse #2 date > 12 then false
	else if #3 date < 1 orelse #3 date > get_month_length(#2 date, is_leap_year(#1 date)) then false
	else
	    true (* whew! made it with no violations *)
    end					    
