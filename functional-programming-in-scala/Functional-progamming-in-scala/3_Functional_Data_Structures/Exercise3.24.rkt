#! /usr/bin/env racket
#lang racket
; vim:ts=4:sw=4:sts=4:et:syntax=scheme
;
; Exercise 3.34 - Find a sublist in a list.
;
;
(require "listfuncs.rkt")

;
; slice a list from start to end position
;
(define (slice lst start end)
  (define (_slice lst start end pos)
    (cond [(< pos start) (_slice (cdr lst) start end (+ pos 1))]
          [(and (>= pos start) (<= pos end)) (cons (car lst) (_slice (cdr lst) start end (+ pos 1)))]
          [else '()]))
   (_slice lst start (if (> end (length lst)) (length lst) end) 1))


; Returns the sublist that has value x as head or '() if not found
(define (findsl xs x)
  (cond [(empty? xs) '()]
        [(= x (car xs)) xs]
        [else (findsl (cdr xs) x)]))

; hasSubSequence
(define (hasSS sup sub)
  (if (or (empty? sub) (equal? sup sub))
      #t
      (let ([fsl (findsl sup (car sub))])
      (if (empty? fsl)
          #f
          (if (equal? (slice fsl 1 (length sub)) sub)
              #t
              (hasSS (cdr fsl) sub))))))

(define tconds (list
                (list '(1 2 3 4 5 6 7 8 9 10) '(6 7)    '#t)
                (list '(1 2 3 4 5 6 7 8 9 10) '(1 2)    '#t)
                (list '(1 2 3 4 5 6 7 8 9 10) '(9 10)   '#t)
                (list '(1 2 3 4 5 6 7 8 9 10) '(6 7 8)  '#t)
                (list '(1 2 3 4 5 6 7 8 9 10) '(1 2 3)  '#t)                
                (list '(1 2 3 4 5 6 7 8 9 10) '(8 9 10) '#t)                
                (list '(1 2 3 4 5 6 7 8 9 10) '(1)      '#t)                
                (list '(1 2 3 4 5 6 7 8 9 10) '(2)      '#t)                
                (list '(1 2 3 4 5 6 7 8 9 10) '(10)     '#t)                
                (list '(1 2 3 4 5 6 7 8 9 10) '(6 1)    '#f)
                (list '(1 2 3 4 5 6 7 8 9 10) '(1 10)   '#f)
                (list '(1 2 3 4 5 6 7 8 9 10) '(9 11)   '#f)
                (list '(1 2 3 4 5 6 7 8 9 10) '(6 6 8)  '#f)
                (list '(1 2 3 4 5 6 7 8 9 10) '(1 1 1)  '#f)                
                (list '(1 2 3 4 5 6 7 8 9 10) '(8 9 8)  '#f)                
                (list '(1 2 3 4 5 6 7 8 9 10) '(11)     '#f)                
                (list '(1 2 3 4 5 6 7 8 9 10) '(12)     '#f)                
                (list '(1 2 3 4 5 6 7 8 9 10) '(-10)    '#f)
                (list '(1 2)                  '(2)      '#t)
                (list '(1)                    '(1)      '#t)
                (list '(1 3 1 2 1 4)          '(1 2)    '#t)
                ))

(define (run-tests tc)
  (for-each (lambda (l)
              (define actual (hasSS (car l) (car (cdr l))))
              (define result (if (equal? actual (car (cdr (cdr l)))) "pass" "FAIL"))
              (printf "~a sup = ~a sub = ~a expected = ~a actual = ~a\n" result (car l) (car (cdr l)) (car (cdr (cdr l))) actual ))
            tc)
  )

(run-tests tconds)



  
  