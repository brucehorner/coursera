#lang racket
(provide (all-defined-out)) ;; so we can put tests in a second file

;; put your code below

; (1) Write a function sequence that takes 3 arguments low, high, and
; stride, all assumed to be numbers. Further assume stride is positive.
; sequence produces a list of numbers from low to high (including
; low and possibly high) separated by stride and in sorted order
(define (sequence low high stride)
  (if (> low high)
      null
      (cons low (sequence (+ low stride) high stride))))

; (2) Write a function string-append-map that takes a list of strings
; xs and a string suffix and returns a list of strings. Each element of
; the output should be the corresponding element of the input appended
; with suffix (with no extra space between the element and suffix).
; You must use Racket-library functions map and string-append.
(define (string-append-map xs suffix)
  (map (lambda (s) (string-append s suffix)) xs))

; 3. Write a function list-nth-mod that takes a list xs and a number n.
; If the number is negative, terminate the computation with
; (error "list-nth-mod: negative number"). Else if the list is empty, 
; terminate the computation with (error "list-nth-mod: empty list"). Else
; return the ith element of the list where we count from zero and i is
; the remainder produced when dividing n by the list's length. Library
; functions length, remainder, car, and list-tail are all useful
(define (list-nth-mod xs n)
  (cond [(< n 0) (error "list-nth-mod: negative number")]
        [(null? xs) (error "list-nth-mod: empty list")]
        [#t (let ([rem (remainder n (length xs))])
              (car (list-tail xs rem)))]))

; 4. Write a function stream-for-n-steps that takes a stream s and a
; number n. It returns a list holding the first n values produced by s
; in order.
(define (stream-for-n-steps s n)
  (letrec ([fn (lambda(s acclist)
                 (let ([node (s)])
                   (if (= (length acclist) n)
                       acclist
                       (fn (cdr node) (cons (car node) acclist)))))])
    (reverse (fn s null))))

; 5. Write a stream funny-number-stream that is like the stream of natural
; numbers (i.e., 1, 2, 3, ...) except numbers divisble by 5 are negated
; (i.e., 1, 2, 3, 4, -5, 6, 7, 8, 9, -10, 11, ...). Remember a stream
; is a thunk that when called produces a pair. Here the car of the pair will
; be a number and the cdr will be another stream.
(define funny-number-stream
  (letrec ([fn (lambda(x)
                 (cons
                  (if (= (remainder x 5) 0) (* x -1) x)
                  (lambda() (fn (+ x 1)))))])
    (lambda() (fn 1))))

; 6. Write a stream dan-then-dog, where the elements of the stream alternate
; between the strings "dan.jpg" and "dog.jpg" (starting with "dan.jpg"). 
; More specifically, dan-then-dog should be a thunk that when called produces
; a pair of "dan.jpg" and a thunk that when called produces a pair of
; "dog.jpg"and a thunk that when called... etc.
(define dan-then-dog
  (letrec ([fn
            (lambda(x)
              (cons (string-append x ".jpg") 
                    (lambda() (fn (if (equal? x "dan") "dog" "dan")))))])
    (lambda() (fn "dan"))))

; 7. Write a function stream-add-zero that takes a stream s and returns
; another stream. If s would produce v for its ith element, then
; (stream-add-zero s) would produce the pair (0 . v) for its ith element.
(define (stream-add-zero s)
  (letrec ([fn
            (lambda(str)
              (let ([node (str)])
                (cons (cons 0 (car node)) (lambda() (fn (cdr node))))))])
    (lambda() (fn s))))

; 8. Write a function cycle-lists that takes two lists xs and ys and
; returns a stream. The lists may or may not be the same length, but assume
; they are both non-empty. The elements produced by the stream are pairs
; where the first part is from xs and the second part is from ys. The
; stream cycles forever through the lists. For example, if xs is '(1 2 3)
; and ys is '("a" "b"), then the stream would produce, (1 . "a"), (2 . "b"),
; (3 . "a"), (1 . "b"), (2 . "a"), (3 . "b"), (1 . "a"), (2 . "b"), etc. 
(define (cycle-lists xs ys)
  (letrec ([fn (lambda(n)
                 (cons 
                  (cons (list-nth-mod xs n) (list-nth-mod ys n))
                  (lambda() (fn (+ n 1)))))])
    (lambda() (fn 0))))

; 9. Write a function vector-assoc that takes a value v and a vector vec.
; It should behave like Racket's assoc library function except
; (1) it processes a vector (Racket's name for an array) instead of a list
; and (2) it allows vector elements not to be pairs in which case it skips
; them.
(define (vector-assoc v vec)
  (letrec ([fn (lambda(n)
                 (if (= n (vector-length vec) )
                     #f
                     (let ([node (vector-ref vec n)])
                       (cond [(pair? node) 
                              (if (equal? v (car node)) node (fn (+ n 1)))]
                             [#t (fn (+ n 1))]))))])
    (fn 0)))

; 10. Write a function cached-assoc that takes a list xs and a number n and
; returns a function that takes one argument v and returns the same thing
; that (assoc v xs) would return. However, you should use an n-element cache
; of recent results to possibly make this function faster than just calling
; assoc (if xs is long and a few elements are returned often). 
(define (cached-assoc xs n)
  (let* ([cache (make-vector n #f)]
         [pos 0]
         [fn (lambda(v)
               (let ([result (vector-assoc v cache)])
                 (if result 
                     result
                     (let ([new-result (assoc v xs)])                       
                       (if new-result
                           (begin
                             (vector-set! cache pos new-result)
                             (set! pos (remainder (+ pos 1) n))
                             new-result
                             )
                           #f)))))])
    fn))

#|
11. Define a macro that is used like (while-less e1 do e2) where e1 and e2
 are expressions and while-less and do are syntax (keywords). The macro should
 do the following:
 * It evaluates e1 exactly once.
 * It evaluates e2 at least once.
 * It keeps evaluating e2 until and only until the result is not a number less
     than the result of the evaluation of e1.
 * Assuming evaluation terminates, the result is #t.
 * Assume e1 and e2 produce numbers; your macro can do anything or fail
      mysteriously otherwise.
|#

(define-syntax while-less
  (syntax-rules (do)
    [(while-less e1 do e2)
     (let ([stop-condition e1])
       (letrec ([loop
                 (lambda()
                   (let ([current e2])
                     (if (< current stop-condition) (loop) #t)))])
         (loop)))]))

