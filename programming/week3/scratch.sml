fun filter (f, xs) =
    case xs of
	[] => []
     | x::xs' => if f x then x::filter(f,xs') else filter(f,xs')

fun allGreaterThanSeven xs = filter (fn x => x>7, xs)
fun allGreaterThan (xs,n) = filter (fn x => x>n, xs)
fun allShorterThan1 (xs,s) = filter (fn x => String.size x < String.size s, xs)
fun allShorterThan2 (xs,s) =
    let
	val i = String.size s
    in
	filter (fn x => String.size x < i, xs)
    end

fun fold (f, acc, xs) = 
    case xs of
	[] => acc
     | x::xs' => fold (f, f(acc,x), xs')


fun areAllShorter (xs, s) =
    let
	val i = String.size s
    in
	fold ((fn(x,y) => x andalso String.size y < i), true, xs)
    end

val sorted3 = fn x => fn y => fn z => z>=y andalso y>=x

