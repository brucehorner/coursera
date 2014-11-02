#lang racket

(require "hw4.rkt") 

;; A simple library for displaying a 2x3 grid of pictures: used
;; for fun in the tests below (look for "Tests Start Here").

(require (lib "graphics.rkt" "graphics"))

(open-graphics)

(define window-name "Programming Languages, Homework 4")
(define window-width 700)
(define window-height 500)
(define border-size 100)

(define approx-pic-width 200)
(define approx-pic-height 200)
(define pic-grid-width 3)
(define pic-grid-height 2)

(define (open-window)
  (open-viewport window-name window-width window-height))

(define (grid-posn-to-posn grid-posn)
  (when (>= grid-posn (* pic-grid-height pic-grid-width))
    (error "picture grid does not have that many positions"))
  (let ([row (quotient grid-posn pic-grid-width)]
        [col (remainder grid-posn pic-grid-width)])
    (make-posn (+ border-size (* approx-pic-width col))
               (+ border-size (* approx-pic-height row)))))

(define (place-picture window filename grid-posn)
  (let ([posn (grid-posn-to-posn grid-posn)])
    ((clear-solid-rectangle window) posn approx-pic-width approx-pic-height)
    ((draw-pixmap window) filename posn)))

(define (place-repeatedly window pause stream n)
  (when (> n 0)
    (let* ([next (stream)]
           [filename (cdar next)]
           [grid-posn (caar next)]
           [stream (cdr next)])
      (place-picture window filename grid-posn)
      (sleep pause)
      (place-repeatedly window pause stream (- n 1)))))

;; Tests Start Here


"Tests for #1"
(equal? (list 0 1 2 3 4 5) (sequence 0 5 1))
(equal? (list 3 5 7 9 11) (sequence 3 11 2))
(equal? (list 3 6) (sequence 3 8 3))
(equal? null (sequence 3 2 1))


"Tests for #2"
(define tst-list (list "A" "B"))
(equal? (list "Atest" "Btest") (string-append-map tst-list "test"))
(equal? null (string-append-map null "test"))
(equal? tst-list (string-append-map tst-list ""))


"Tests for #3"
(define tst2-list (list "1" "2" "3" "4" "5" "6"))
(equal? "3" (list-nth-mod tst2-list 2))
(equal? "2" (list-nth-mod tst2-list 7))


"Tests for #4"
(define tst4-list (list 1 2 3 4 5 6))
(define powers-of-two
  (letrec ([f (lambda(x) (cons x (lambda() (f (* x 2)))))])
    (lambda() (f 2))))
(define nats
  (letrec ([f (lambda(x) (cons x (lambda() (f (+ x 1)))))])
    (lambda() (f 1))))
(equal? (list 2) (stream-for-n-steps powers-of-two 1))
(equal? tst4-list (stream-for-n-steps nats 6))

"Tests for #5"
(define tst5-list (list 1 2 3 4 -5 6 7 8 9 -10 11))
(equal? tst5-list (stream-for-n-steps funny-number-stream 11))

"Tests for #6"
(define dan-str "dan.jpg")
(define dog-str "dog.jpg")
(define tst6-list (list dan-str dog-str dan-str dog-str dan-str))
(equal? tst6-list (stream-for-n-steps dan-then-dog 5))
  
"Tests for #7"
(define tst7-list (list (cons 0 1) (cons 0 2)))
(equal? tst7-list (stream-for-n-steps (stream-add-zero nats) 2))
(equal? (list (cons 0 dan-str)) (stream-for-n-steps (stream-add-zero dan-then-dog) 1))
(equal? null (stream-for-n-steps (stream-add-zero dan-then-dog) 0))

"Tests for #8"
(define xs (list 1 2 3))
(define ys (list "a" "b" "c"))
(equal? '((1 . "a") (2 . "b") (3 . "c") (1 . "a")) (stream-for-n-steps (cycle-lists xs ys) 4))
(equal? '((10 . "a") (10 . "b") (10 . "c") (10 . "a")) (stream-for-n-steps (cycle-lists (list 10) ys) 4))
(equal? null (stream-for-n-steps (cycle-lists (list 10) ys) 0))
(equal? '((10 . "dan.jpg") (9 . "dog.jpg") (8 . "dan.jpg") (10 . "dog.jpg") (9 . "dan.jpg")) (stream-for-n-steps (cycle-lists (list 10 9 8) tst6-list) 5))

"Tests for #9"
(define vec (list->vector (list (cons "A" 23) "junk" (cons "B" 89) 45 37 (cons "C" 18) (cons "D" 4) (cons "E" -49) "hello" (cons "W" 19))))
(equal? (cons "B" 89) (vector-assoc "B" vec))
(equal? #f (vector-assoc "Z" vec))
(equal? (cons "A" 23) (vector-assoc "A" vec))
(equal? (cons "W" 19) (vector-assoc "W" vec))
(equal? #f (vector-assoc "B" (make-vector 1)))

"Tests for #10"
(define data '((1 . "A") (2 . "B") (3 . "C") (26 . "Z") (5 . "F") (4 . "D")))
(define fn (cached-assoc data 4))
(equal? '(4 . "D") (fn 4))
(equal? '(26 . "Z") (fn 26))
(equal? '(3 . "C") (fn 3))
(equal? '(1 . "A") (fn 1))
(equal? '(3 . "C") (fn 3))
(equal? '(1 . "A") (fn 1))
(equal? '(2 . "B") (fn 2))
(equal? '(5 . "F") (fn 5))


"Tests for #11"
(define a 2)
(while-less (begin (printf "This is e1~n") 7) do (begin (set! a (+ a 1)) (print "x") a))
(printf "Is 'a' now 7? ~v~n" (= a 7))
(while-less (begin (printf "This is e1~n") 7) do (begin (set! a (+ a 1)) (print "x") a))
(printf "Is 'a' now 8? ~v~n" (= a 8))

(define a2 11)
(while-less (begin (printf "This is e1~n") 7) do (begin (set! a2 (+ a2 1)) (print "x") a2))



"Predefined tests"

; These definitions will work only after you do some of the problems
; so you need to comment them out until you are ready.
; Add more tests as appropriate, of course.

(define nums (sequence 0 5 1))

(define files (string-append-map 
               (list "dan" "dog" "curry" "dog2") 
               ".jpg"))

(define funny-test (stream-for-n-steps funny-number-stream 16))

; a zero-argument function: call (one-visual-test) to open the graphics window, etc.
(define (one-visual-test)
  (place-repeatedly (open-window) 0.5 (cycle-lists nums files) 27))

; similar to previous but uses only two files and one position on the grid
(define (visual-zero-only)
  (place-repeatedly (open-window) 0.5 (stream-add-zero dan-then-dog) 27))
