;; Programming Languages, Homework 5

#lang racket
(provide (all-defined-out)) ;; so we can put tests in a second file

;; definition of structures for MUPL programs - Do NOT change
(struct var  (string) #:transparent)  ;; a variable, e.g., (var "foo")
(struct int  (num)    #:transparent)  ;; a constant number, e.g., (int 17)
(struct add  (e1 e2)  #:transparent)  ;; add two expressions
(struct ifgreater (e1 e2 e3 e4)    #:transparent) ;; if e1 > e2 then e3 else e4
(struct fun  (nameopt formal body) #:transparent) ;; a recursive(?) 1-argument function
(struct call (funexp actual)       #:transparent) ;; function call
(struct mlet (var e body) #:transparent) ;; a local binding (let var = e in body) 
(struct apair (e1 e2)     #:transparent) ;; make a new pair
(struct fst  (e)    #:transparent) ;; get first part of a pair
(struct snd  (e)    #:transparent) ;; get second part of a pair
(struct aunit ()    #:transparent) ;; unit value -- good for ending a list
(struct isaunit (e) #:transparent) ;; evaluate to 1 if e is unit else 0

;; a closure is not in "source" programs; it is what functions evaluate to
(struct closure (env fun) #:transparent) 

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Problem 1
#|
(a) Write a Racket function racketlist->mupllist that takes a Racket list
    and produces an analogous mupl list with the same elements in the
    same order.
(b) Write a Racket function mupllist->racketlist that takes a mupl list
    and produces an analogous Racket list (of mupl values) with the same
    elements in the same order.
|#
(define (racketlist->mupllist rlist)
  (letrec [(fn (lambda(lst)
                 (if (null? lst) (aunit)
                     (apair (car lst) (fn (cdr lst))))))]
    (fn rlist)))

(define (mupllist->racketlist mpair)
  (letrec [(fn (lambda(pr)
                 (if (aunit? pr) null
                     (cons (apair-e1 pr) (fn (apair-e2 pr))))))]
    (fn mpair)))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Problem 2
#|
Write a mupl interpreter, i.e., a Racket function eval-exp that takes a mupl
expression e and either returns the mupl value that e evaluates to under the
empty environment or calls Racket's error if evaluation encounters a
run-time mupl type error or unbound mupl variable.
|#

;; lookup a variable in an environment
;; Do NOT change this function
(define (envlookup env str)
  (cond [(null? env) (error "unbound variable during evaluation" str)]
        [(equal? (car (car env)) str) (cdr (car env))]
        [#t (envlookup (cdr env) str)]))

;; Do NOT change the two cases given to you.  
;; DO add more cases for other kinds of MUPL expressions.
;; We will test eval-under-env by calling it directly even though
;; "in real life" it would be a helper function of eval-exp.
(define (eval-under-env e env)
  (cond [(var? e) 
         (envlookup env (var-string e))]
        [(add? e) 
         (let ([v1 (eval-under-env (add-e1 e) env)]
               [v2 (eval-under-env (add-e2 e) env)])
           (if (and (int? v1)
                    (int? v2))
               (int (+ (int-num v1) 
                       (int-num v2)))
               (error "MUPL addition applied to non-number")))]
        ;; CHANGE add more cases here
        ; First the simple 'values'
        [(int? e) e]
        [(fun? e) (closure env e)]                    
        [(aunit? e) e]
        [(closure? e) e]
        
        ; ifgreater
        [(ifgreater? e) (let ([v1 (eval-under-env (ifgreater-e1 e) env)]
                              [v2 (eval-under-env (ifgreater-e2 e) env)])
                          (if (and (int? v1) (int? v2))
                             (if (> (int-num v1) (int-num v2))
                                (eval-under-env (ifgreater-e3 e) env)
                                (eval-under-env (ifgreater-e4 e) env))
                             (error "IF> must have first two integer terms")))]
        
        ; call
        [(call? e) (let ([cls (eval-under-env (call-funexp e) env)]
                         [argvalue (eval-under-env (call-actual e) env)])
                      (if (closure? cls)
                        (let ([local-env (closure-env cls)]
                              [fn (closure-fun cls)])
                          (if (fun? fn)
                            (let* ([name (fun-nameopt fn)]
                                   [argname (fun-formal fn)]
                                   [body (fun-body fn)]
                                   [argument (cons argname argvalue)]
                                   [new-env (cons argument local-env)])
                               (if name
                                  (eval-under-env body (cons (cons name cls) new-env))
                                  (eval-under-env body new-env)))
                           (error "Didn't find a function in the closure")))
                         (error "First argument to CALL must be a function closure")))]
                               
        
        ; mlet
        [(mlet? e) (let [(v (eval-under-env (mlet-e e) env))]
                     (eval-under-env (mlet-body e) (cons (cons (mlet-var e) v) env)))]
         
        ; apair
        [(apair? e) (let ([v1 (eval-under-env (apair-e1 e) env)]
                          [v2 (eval-under-env (apair-e2 e) env)])
                      (apair v1 v2))]
        
        ; fst
        [(fst? e) (let ([v (eval-under-env (fst-e e) env)])
                    (if (apair? v)
                      (apair-e1 v)
                      (error "FST requires a pair")))]
        
        ; snd
        [(snd? e) (let ([v (eval-under-env (snd-e e) env)])
                    (if (apair? v)
                      (apair-e2 v)
                      (error "SND requires a pair")))]
        
        ; isaunit
        [(isaunit? e) (let ([v (eval-under-env (isaunit-e e) env)])
                        (if (aunit? v) (int 1) (int 0)))]
        
        [#t (error "bad MUPL expression")]))

;; Do NOT change
(define (eval-exp e)
  (eval-under-env e null))
        
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Problem 3
#|
(a) Write a Racket function ifaunit that takes three mupl expressions e1, e2,
and e3. It returns a mupl expression that when run evaluates e1 and if the
result is mupl's aunit then it evaluates e2 and that is the overall result,
else it evaluates e3 and that is the overall result.
|#
(define (ifaunit e1 e2 e3)
  (ifgreater (isaunit e1) (int 0) e2 e3))

#|
(b) Write a Racket function mlet* that takes a Racket list of Racket pairs
'((s1 . e1) . . . (si . ei). . . (sn . en)) and a final mupl expression
en+1. In each pair, assume si is a Racket string and ei is a mupl expression.
mlet* returns a mupl expression whose value is en+1 evaluated in an
environment where each si is a variable bound to the result of evaluating
the corresponding ei for 1 <= i <= n. The bindings are done sequentially, so
that each ei is evaluated in an environment where s1 through si-1 have been
previously bound to the values e1 through ei-1.
|#
(define (mlet* lstlst e2)
  (if (null? lstlst)
    e2
    (let* ([head (car lstlst)]    ; name all the parts for documentation :)
           [var-name (car head)]	   
           [var-exp (cdr head)]
           [tail (mlet* (cdr lstlst) e2)]) 
	   (mlet var-name var-exp tail))))
   
#|
(c) Write a Racket function ifeq that takes four mupl expressions e1, e2, e3,
and e4 and returns a mupl expression that acts like ifgreater except e3 is
evaluated if and only if e1 and e2 are equal integers. Assume none of the
arguments to ifeq use the mupl variables _x or _y. Use this assumption so that
when an expression returned from ifeq is evaluated, e1 and e2 are evaluated
exactly once each.

We only have mlet* to deal with the setting of environment and ifgreater to
do comparisons, so ... if x>y they are not equal (e4) and if also y>x they
are still not equal (e4), otherwise (e3)
|#   
(define (ifeq e1 e2 e3 e4)
  (mlet* (list (cons "_x" e1) (cons "_y" e2))
    (ifgreater (var "_x") (var "_y") e4
      (ifgreater (var "_y") (var "_x") e4 e3))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Problem 4
#|
(a) Bind to the Racket variable mupl-map a mupl function that acts like map.
Function should be curried: it should take a mupl function and return a mupl
function that takes a mupl list and applies the function to every element of
the list returning a new mupl list. A mupl list is aunit or a pair where the
second component is a mupl list.

A curried anon function returns a function taking a MUPL list
A curried map looks like this in racket, so 'just' need to translate
to MUPL :)

(define xcmap (lambda (fn-to-apply)
  (lambda (thelist)
    (if (null? thelist)
      null
      (let ([head (fn-to-apply (car thelist))]
            [tail ((xcmap fn-to-apply) (cdr thelist))])
        (cons head tail))))))

((mupl-map fn-to-apply) mupl-list)
|#
(define mupl-map 
  (fun #f "fn-to-apply"
    (fun "iterator" "mupl-list" 
      (ifaunit (var "mupl-list")
        (aunit) 
        (mlet*
          (list
            (cons "head" (call (var "fn-to-apply") (fst (var "mupl-list"))))
            (cons "tail" (call (var "iterator") (snd (var "mupl-list")))))
         (apair (var "head") (var "tail")))))))

#|
(b) Bind to the Racket variable mupl-mapAddN a mupl function that takes a 
mupl integer 'i' and returns a mupl function that takes a mupl list of mupl
integers and returns a new mupl list of mupl integers that adds 'i' to
every element of the list. Use mupl-map.
		 
curried function taking an int, returning a function which takes a mupl
list and then adds 'i' to each element of that list
((mupl-mapAddN i) mupl-list)
|#
(define mupl-mapAddN 
  (mlet "map" mupl-map
    (fun #f "i"
      (call (var "map")
        (fun #f "element" (add (var "i") (var "element")))))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Challenge Problem

(struct fun-challenge (nameopt formal body freevars) #:transparent) ;; a recursive(?) 1-argument function

;; We will test this function directly, so it must do
;; as described in the assignment
(define (compute-free-vars e) "CHANGE")

;; Do NOT share code with eval-under-env because that will make
;; auto-grading and peer assessment more difficult, so
;; copy most of your interpreter here and make minor changes
(define (eval-under-env-c e env) "CHANGE")

;; Do NOT change this
(define (eval-exp-c e)
  (eval-under-env-c (compute-free-vars e) null))
