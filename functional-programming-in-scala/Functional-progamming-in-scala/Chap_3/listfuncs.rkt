#! /usr/bin/env racket
#lang racket/base
; vim:ts=4:sw=4:sts=4:et:syntax=scheme
;
; Various list functions implemented in Racket
;
;

(provide reverse foldLeft foldRight append buildList map flatMap filter len front)


  
;
; reverse - tail recursive
;
(define (reverse l)
  (foldLeft l '() (lambda (a b) (cons b a)))
  )

;
; FoldLeft - tail recursive
;
(define (foldLeft l z fn)
  (if (null? l)
      z
      (foldLeft (cdr l) (fn z (car l)) fn)
      )
  )

;
; FoldLeft - non-tail recursive
;
(define (foldLeft_NTR l z fn)
  (if (null? l)
      z
      (fn (foldLeft_NTR (cdr l) z fn) (car l))
      )
  )
;
; FoldRight - tail recursive
;
; Implemented using reverse and foldLeft
;
(define (foldRight l z fn)
  (foldLeft (reverse l) z fn)
  )

;
; append - Append two lists using foldRight
;
(define (append2 l1 l2)
  (foldRight l1 l2 (lambda (b a) (cons a b)))
  )

;
; Append any number of lists - variadic function
;
(define (append . lsts)
  (foldRight lsts '() (lambda (sublist acc) (append2 acc sublist))))

;
; buildList - Build a list of length n, fn is called to generate each member
;
(define (buildList n fn)
  (define (_buildList n fn lst)
    (if (= n 0)
        lst
        (_buildList (- n 1) fn (cons (fn n) lst))))
  (_buildList n fn '()))

; buildIntList - Build a list of integers of length n - for testing
;
(define (buildIntList n)
  (buildList n (lambda (x) x)))

;
; map - non tail recursive version
;
(define (map_NTR fn lst)
  (if (null? lst)
      '()
      (cons (fn (car lst))
            (map_NTR fn (cdr lst)))))

;
; map - tail recursive version
; written in terms of foldRight
;
(define (map_FR fn lst)
  (foldRight lst '() (lambda (b a) (cons (fn a) b)))
  )

;
; flatMap
;
(define (flatMap fn lst)
  (if (null? lst)
      '()
      (append (fn (car lst))
              (flatMap fn (cdr lst)))
      )
  )
;
; Filter - non-tail recursive
;
(define (filter_NTR lst pred)
  (if (null? lst)
      '()
      (if (pred (car lst))
          (cons (car lst) (filter_NTR (cdr lst) pred))
          (filter_NTR (cdr lst) pred)
          )
      )
  )

;
; Filter - tail recursive
; written in terms of foldRight
;
(define (filter_FR lst pred)
  (foldRight lst '() (lambda (b a) (if (pred a) (cons a b) b)))
  )

;
; Filter - written in terms of flapMap
;
(define (filter_FM lst pred)
  (flatMap (lambda (x) (if (pred x) (list x) '())) lst)
  )

; length - using foldLeft
;
(define (len lst)
  (foldLeft lst 0 (lambda (x y) (+ x 1)))
  )

;
; front
; take the first n elements from the head of a list
;;
(define (front n lst)
  (define (_front n acc lst)
    (if (= n 0)
        acc
        (_front (- n 1) (cons (car lst) acc) (cdr lst))
        )
    )
  (reverse (_front n '() lst))
  )

(define filter filter_FR)
(define map map_FR)

;
; Test map implementations
; Note the map_NTR would cause a stack overflow in scala
;
(define (test) 
  (if (equal?
       (map_NTR (lambda (x) (+ x 1)) (buildIntList 10000))
       (map_FR  (lambda (x) (+ x 1)) (buildIntList 10000))
       )
      (displayln "pass")
      (displayln "fail")
      )
  (if (equal?
       (filter_FR  (buildIntList 10000) (lambda (x) (= (remainder x 2) 0)))
       (filter_NTR (buildIntList 10000) (lambda (x) (= (remainder x 2) 0)))
       )
      (displayln "pass")
      (displayln "fail")
      )
  ; first 1000 even numbers
  (front 1000 (filter_NTR (buildIntList 10000 ) (lambda (x) (= (remainder x 2) 0))))
  (front 1000 (filter_FM (buildIntList 10000 ) (lambda (x) (= (remainder x 2) 0))))
  (if (equal?
       (filter_FM (buildIntList 10000) (lambda (x) (= (remainder x 2) 0)))
       (filter_NTR (buildIntList 10000) (lambda (x) (= (remainder x 2) 0)))
       )
      (displayln "pass")
      (displayln "fail")
      )
  ; flatMap
  (flatMap (lambda (x) (list x (* x 100))) (buildIntList 1000))
  )
;
;
;
(test)