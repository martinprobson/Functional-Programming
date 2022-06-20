#lang racket
; vim:ts=4:sw=4:sts=4:et:syntax=scheme
;

;(require "listfuncs.rkt")

;
; Syntactic sugar
;
; empty tree?
(define (empty? node) (equal? node '()))
; leaf? - consists of single non-list value
(define (leaf? node) (not (list? node)))
; branch? - consists of a pair of lists
(define (branch? node) (pair? node))

; create leaf
(define (leaf n) n)
; create branch
(define (branch l r) (list l r))

; Left node of branch
(define (left b)
  (if (branch? b)
      (car b)
      (error "Not a branch")))

; right node of branch
(define (right b)
  (if (branch? b)
      (car (cdr b))
      (error "Not a branch")))

(define (maximum t)
  (cond [(empty? t) (error "max of an empty tree")]
        [(leaf? t) t]
        [(branch? t) (max (maximum (left t)) (maximum (right t)))]))

(define (size t)
  (cond [(empty? t) 0]
        [(leaf? t)  1]
        [(branch? t) (+ 1 (+  (size (left t)) (size (right t))))]))

(define (insert v tree)
  (match tree
    ['() (leaf v)]
    [a (branch (leaf a) (leaf v))]
    [(list a b) (branch a (branch tree (leaf v)))]))

(define tst  (branch (branch (branch (leaf 1) (leaf 3)) (branch (leaf 100) (leaf 45))) (branch (branch (leaf 90) (leaf 99)) (branch (leaf 35) (leaf 7890)))))
;
;(define (insert v tree)
;  (cond ((empty? tree '()) (list v '() '()))
;        ((<  v (val tree)) (list (val tree) (insert v (left tree)) (right tree)))
;        ((>= v (val tree)) (list (val tree) (left tree) (insert v (right tree))))
;        )
;  )