#lang racket

(struct const (int) #:transparent)
(struct negate (e) #:transparent)
(struct add (e1 e2) #:transparent)
(struct multiply (e1 e2) #:transparent)
(struct bool (b) #:transparent)
(struct if-then-else (e1 e2 e3) #:transparent)
(struct eq-num (e1 e2) #:transparent)

(define (eval-exp e)
  (cond [(const? e) e]
        [(negate? e) (const (- (const-int (eval-exp (negate-e e)))))]
        [(add? e)
         (let ([v1 (eval-exp (add-e1 e))]
               [v2 (eval-exp (add-e2 e))])
           (if (and (const? v1) (const? v2))
               (const (+ (const-int v1) (const-int v2)))
               (error "add applied to non-number")))]
        [(multiply? e)
         (let ([v1 (const-int (eval-exp (multiply-e1 e)))]
               [v2 (const-int (eval-exp (multiply-e2 e)))])
           (const (* v1 v2)))]
        [#t (error "eval-exp expects an exp")]))

(define a (negate (const 4)))
(define eval-a (eval-exp a))
(printf "eval-a is ~v~n" eval-a)

(define test-exp (multiply (negate (add (const 2) (const 2))) (const 7)))
(define test-ans (eval-exp test-exp))
(printf "test-ans is ~v~n" test-ans)

(define b (add (const 2) (bool #f)))
;(define eval-b (eval-exp b))
;(printf "eval-b is ~v~n" eval-b)

(define (double e)
  (multiply e (const 2)))

(define c (negate (double (negate (const 4)))))
(eval-exp c)

(define (list-product es)
  (if (null? es)
      (const 1)
      (multiply (car es) (list-product (cdr es)))))

