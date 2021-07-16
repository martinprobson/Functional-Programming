#lang scheme
;
;Simulate a simple class mechanism in Scheme part 1
;
; ( http://people.cs.aau.dk/~normark/prog3-03/html/notes/oop-scheme_themes-classes-objects-sec.html )
;
;

(define function-with-private-context
  (let ([me "Martin"])
    (lambda (x y)
      (printf "x: ~a y: ~a me: ~a" x y me))))


(define (point x y)
  (letrec ((getx (lambda () x))
           (gety (lambda () y))
           (add  (lambda (p)
                   (point
                    (+ x (send-msg 'getx p))
                    (+ y (send-msg 'gety p)))))
           (type-of (lambda () 'point)))
    (lambda (message)
      (cond ((eq? message 'getx) getx)
            ((eq? message 'gety) gety)
            ((eq? message 'add) add)
            ((eq? message 'type-of) type-of)
            (else (error "Message not understood!"))))))

(define (send-msg message obj . par)
  (let ([method (obj message)])
    (apply method par)))

;
;
; define a new point object
(define p1 (point 100 200))
; call methods on p1 - send a message to p1
(send-msg 'getx p1)
(send-msg 'gety p1)
(send-msg 'type-of p1)
;
; Use add method
(define p2 (send-msg 'add p1 (point 300 400)))
(send-msg 'getx p2)
(send-msg 'gety p2)
(send-msg 'type-of p2)

