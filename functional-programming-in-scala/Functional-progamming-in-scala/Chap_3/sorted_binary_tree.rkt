#lang racket
; vim:ts=4:sw=4:sts=4:et:syntax=scheme
;

(require "listfuncs.rkt")
(require pict)
(require pict/tree-layout)


(define (val node) (car node))
(define (left node) (car (cdr node)))
(define (right node) (car (cdr (cdr node))))

(define (insert v tree)
  (cond ((eqv? tree '()) (list v '() '()))
        ((<  v (val tree)) (list (val tree) (insert v (left tree)) (right tree)))
        ((>= v (val tree)) (list (val tree) (left tree) (insert v (right tree))))
        )
  )

(define t (insert 74 (insert 59 (insert 22 (insert 24 (insert 88 (insert 34 (insert 56 '()))))))))
;

(define (inord t)
  (match t
    ['() '() ]
    [_ (append  (inord (left t))
                (list (val t))
                (inord (right t)))]))
              
; Drawing
;
; A function to build a tree-layout with a value
;
(define (tree-node value left right)
  (tree-layout #:pict (text value) left right))
;
; Draw the tree
(define (draw-tree b-tree)
  (cond [(empty? b-tree) #f]
        [(tree-node (number->string (val b-tree)) (draw-tree (left b-tree)) (draw-tree (right b-tree)))]))

(naive-layered (draw-tree t))
;
;
;
(inord t)

;
; Build a random list of integers and test our tree
;
(define t2 (foldRight (buildList 10 (lambda (x) (random 100))) '() (lambda (x y) (insert y x))))

(inord t2)
(naive-layered (draw-tree t2))