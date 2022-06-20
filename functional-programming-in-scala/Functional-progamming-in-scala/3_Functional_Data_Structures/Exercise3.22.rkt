#! /usr/bin/env racket
#lang racket
; vim:ts=4:sw=4:sts=4:et:syntax=scheme
;
; Various list functions implemented in Racket
;
;
;
; Chapter 3 - Functional Data Structures
;
; Exercise 3.22 - Add two lists of integers
;

(require "listfuncs.rkt")

;
; tail-recursive version
;
(define (addLists l1 l2)
  (define (_addLists l1 l2 accum)
    (match (list l1 l2)
      [(list '() '()) '()]
      [(list '() (list-rest l2)) l2]
      [(list (list-rest l1) '()) l1]
      [(list (list h1) (list-rest h2 t2)) (append accum (flatten (list (+ h1 h2) t2)))]
      [(list (list-rest h1 t1) (list h2)) (append accum (flatten (list (+ h1 h2) t1)))]
      [(list (list-rest h1 t1) (list-rest h2 t2)) (_addLists t1 t2 (append accum (flatten (list (+ h1 h2)))))]
    )
    )
  (_addLists l1 l2 '())
  )
    
;
; Non tail-recursive version
;
(define (addLists_NTR l1 l2)
  (match (list l1 l2)
    [(list '() '()) '()]
    [(list '() (list-rest l2)) l2]
    [(list (list-rest l1) '()) l1]
    [(list (list h1) (list-rest h2 t2)) (flatten (list (+ h1 h2) t2))]
    [(list (list-rest h1 t1) (list h2)) (flatten (list (+ h1 h2) t1))]
    [(list (list-rest h1 t1) (list-rest h2 t2)) (flatten (list (+ h1 h2) (addLists_NTR t1 t2)))]
    ))


(define tconds (list (list (list)           (list)           (list))
                     (list (list)           (list 1 2 3)     (list 1 2 3))
                     (list (list 1 2 3)     (list)           (list 1 2 3))
                     (list (list 10)        (list 1 2 3)     (list 11 2 3))
                     (list (list 10)        (list 1)         (list 11))
                     (list (list 1 2 3)     (list 10)        (list 11 2 3))
                     (list (list 1)         (list 10)        (list 11))
                     (list (list 1 2)       (list 3 4)       (list 4 6))
                     (list (list 1 2)       (list 3 4 5)     (list 4 6 5))
                     (list (list 3 4 5)     (list 1 2)       (list 4 6 5))
                     (list (list 1 2 3)     (list 1 2 3)     (list 2 4 6))
                     (list (list 1 2 3 4 5) (list 1 2 3 4 5) (list 2 4 6 8 10))
                     )
  )


(define (run-tests tc)
  (for-each (lambda (l)
              (define actual (addLists (car l) (car (cdr l))))
              (printf "l1 = ~a l2 = ~a expected = ~a actual = ~a\n" (car l) (car (cdr l)) (car (cdr (cdr l))) actual))
            tc)
  )
                                   
(run-tests tconds)
(equal? (addLists (buildList 10000 '()) (buildList 10000 '())) (map (lambda (x) (+ x x)) (buildList 10000 '())))
              