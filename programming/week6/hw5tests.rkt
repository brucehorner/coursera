#lang racket

(require rackunit "hw5.rkt")

"Tests for #1a"
(define v1a (var "test"))
(check-equal? (aunit) (racketlist->mupllist (list)) "1a1")
(check-equal? (apair v1a (aunit)) (racketlist->mupllist (list v1a)) "1a2")

(define v1b (int 20))
(define l1b (list v1a v1b))
(define m1b (apair v1a (apair v1b (aunit))))
(check-equal? m1b (racketlist->mupllist l1b) "1a3")

(define l1c (list (var "test") (int 30) (var "third") (int -39)))
(define m1c (apair v1a (apair (int 30) (apair (var "third") (apair (int -39) (aunit))))))
(check-equal? m1c (racketlist->mupllist l1c) "1a4")

"Tests for #1b"
(check-equal? (list) (mupllist->racketlist (aunit)) "1b1")
(check-equal? (list v1a) (mupllist->racketlist (apair v1a (aunit))) "1b2")
(check-equal? l1b (mupllist->racketlist m1b) "1b3")
(check-equal? l1c (mupllist->racketlist m1c) "1b4")

; (check-exn (regexp "error message") (fn-call)  "message")
; (check-false (fn-call) "Returns false")

"Tests for #2"
(check-equal? v1b (eval-exp v1b) "2-1")
(check-equal? (int 9) (eval-exp (add (int 5) (int 4))) "2-2")
(check-equal? (int -99) (eval-exp (ifgreater (int 4) (int 5) (int 99) (int -99))) "2-3")
(check-equal? (int 99) (eval-exp (ifgreater (int 4) (int 3) (int 99) (int -99))) "2-4")
(check-equal? (int 15) (eval-exp (mlet "test" (int 3) (add (var "test") (int 12)))) "2-5")
(define forty-two (int 42))
(check-equal? forty-two (eval-exp (mlet "test" forty-two (ifgreater (int 934) (int 4) (var "test") (add (var "test") forty-two)))) "2-6")
(check-equal? (int 84) (eval-exp (mlet "test" forty-two (ifgreater (int 3) (int 4) (var "test") (add (var "test") forty-two)))) "2-7")
(check-equal? forty-two (eval-exp (mlet "test" forty-two (ifgreater (var "test") (int 4) (var "test") (add (var "test") forty-two)))) "2-8")
(check-equal? (int 84) (eval-exp (mlet "test" forty-two (ifgreater (var "test") (int 400) (var "test") (add (var "test") forty-two)))) "2-9")

(check-equal? (apair (int 3) (int 99)) (eval-exp (apair (int 3) (add (int 6) (int 93)))) "2-10")
(check-equal? (apair (int 0) (int 99)) (eval-exp (apair (add (int 4) (int -4)) (add (int 6) (int 93)))) "2-11")
(check-equal? (apair (int 14) (int 103)) (eval-exp (mlet "weight" (int 10) (apair (add (int 4) (var "weight")) (add (var "weight") (int 93))))) "2-12")

(check-equal? (int 4) (eval-exp (fst (apair (int 4) (int 99)))) "2-13")
(check-equal? (int 8) (eval-exp (fst (apair (add (int 4) (int 4)) (int 99)))) "2-13a")
(check-equal? (int 99) (eval-exp (snd (apair (int 4) (int 99)))) "2-14")
(check-equal? (int 109) (eval-exp (mlet "adder" (int 10) (snd (apair (add (int 4) (int 4)) (add (var "adder") (int 99)))))) "2-14a")

(check-equal? (aunit) (eval-exp (aunit)) "2-15")
(check-equal? (int 1) (eval-exp (isaunit (aunit))) "2-16")
(check-equal? (int 0) (eval-exp (isaunit (int 5))) "2-17")
(check-equal? (int 0) (eval-exp (isaunit (apair (int 5) (aunit)))) "2-17a")
(check-equal? (int 1) (eval-exp (isaunit (snd(apair (int 5) (aunit))))) "2-17a")

(define plus1 (fun "plus1" "x" (add (int 1) (var "x")))) ; function to add 1 to its argument
(define double-if-positive (fun "double-if-positive" "y" (ifgreater (var "y") (int 0) (add (var "y") (var "y")) (var "y"))))
(check-equal? (int 26) (eval-exp (call plus1 (int 25))) "2-18")
(check-equal? (int -25) (eval-exp (call double-if-positive (int -25))) "2-19")
(check-equal? (int 50) (eval-exp (call double-if-positive (int 25))) "2-20")

(check-equal? (int 2) 
              (eval-exp
               (mlet "f1"
                     (fun "f1" "a"
                          (mlet "x" (var "a")
                                (fun "f2" "z" (add (var "x") (int 1)))))
                     (mlet "f3"
                           (fun "f3" "f"
                                (mlet "x" (int 1729)
                                      (call (var "f") (aunit))))
                           (call (var "f3") (call (var "f1") (int 1)))))) "2-Pavel")
(check-equal? (int 13) (eval-exp (mlet "add3" (fun #f "x" (add (var "x") (int 3))) (call (var "add3") (int 10)))) "Charlie" )

"Tests for #3"
(check-equal? (int 99) (ifaunit (int 3) (int -99) (int 99)) "3-1")
(check-equal? (int -99) (ifaunit (aunit) (int -99) (int 99)) "3-2")
(check-equal? (int 49) (ifaunit (snd (apair (int 3) (aunit))) (add (int 40) (int 9)) (int 17)) "3-3")
(check-equal? (int 17) (ifaunit (fst (apair (int 3) (aunit))) (add (int 40) (int 9)) (int 17)) "3-4")

(define mlet*-l1 (list (cons "A" (int 1)) (cons "M" (int 13)) (cons "Z" (int 26))))
(define mlet*-l2 (list (cons "TOTAL" (add (var "A") (add (var "Z") (var "M"))))))
(check-equal? (int 40) (mlet* mlet*-l1 (add (var "A") (add (var "M") (var "Z")))) "3-5")
(check-equal? (int 67) (mlet* (append mlet*-l1 mlet*-l2) (add (var "TOTAL") (add (var "A") (var "Z")))) "3-6")

(check-equal? (int 99) (ifeq (int 4) (int 4) (int 99) (int -99)) "3-7")
(check-equal? (int 99) (eval-exp (ifeq (add (int 2) (int 2)) (int 4) (add (int 90) (int 9)) (int -99))) "3-8")
(check-equal? (int -99) (ifeq (add (int 2) (int 1)) (int 4) (add (int 90) (int 9)) (add (int 1) (int -100))) "3-9")


"Tests for #4"
(define curry-add (fun #f "x" (fun #f "y" (add (var "x") (var "y")))))
(define curry-add2 (fun "f1" "x" (fun "f2" "y" (add (var "x") (var "y")))))
(check-equal? (int 99) (eval-exp (call (call curry-add (int 90)) (int 9))) "4-1")
(check-equal? (int 99) (eval-exp (call (call curry-add2 (int 90)) (int 9))) "4-2")





; THESE SHOULD WORK
#|


(define sum-from-to
  (fun "sum-from-to" "from-to" 
       (mlet* (list (cons "from" (fst (var "from-to"))) (cons "to" (snd (var "from-to"))))
              (ifgreater (var "from") (var "to")
                         (int 0)
                         (add (var "from") 
                              (call (var "sum-from-to") 
                                    (apair (add (var "from") (int 1)) (var "to"))))))))

(check-equal? (int 55) (eval-exp (call (fun "fibo" "n" 
                                            (ifgreater (var "n") (int 1)
                                                       (add (call (var "fibo") 
                                                                  (add (var "n") (int -1)))
                                                            (call (var "fibo")
                                                                  (add (var "n") (int -2))))                                                    
                                                       (var "n"))) 
                                       (int 10)))
              "Juan")
|#




#|
; a test case that uses problems 1, 2, and 4
; should produce (list (int 10) (int 11) (int 16))
(define test1
  (mupllist->racketlist
   (eval-exp (call (call mupl-mapAddN (int 7))
                   (racketlist->mupllist 
                    (list (int 3) (int 4) (int 9)))))))
|#

