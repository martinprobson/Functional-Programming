#lang scheme
;
;Simulate a simple class mechanism in Scheme part 2
;
; ( http://people.cs.aau.dk/~normark/prog3-03/html/notes/oop-scheme_themes-classes-objects-sec.html )
;
;

(define (new-instance class . parameters)
  (apply class parameters))

(define (send-msg msg obj . args)
  (let ((method (obj msg)))
    (cond ((procedure? method) (apply method args))
          (else (error "Error in method lookup " method)))))

(define (person name age)
  (let ((n name)
        (a age))
    (define (get-name)
      n)
    (define (get-age)
      a)
    (define (type-of)
      'person)
    (define (equals that)
      (if (and (eqv? (send-msg 'type-of that) 'person)
               (eqv? (send-msg 'get-name that) n)
               (eqv? (send-msg 'get-age that) a))
          #t
          #f))
    (define (clone)
      (new-instance person n a))
                    

    (define (self msg)
      (cond ((eqv? msg 'get-name) get-name)
            ((eqv? msg 'get-age) get-age)
            ((eqv? msg 'type-of) type-of)
            ((eqv? msg 'equals) equals)
            ((eqv? msg 'clone) clone)
            (else (error "Undefined msg" msg))))
    self))
;
;
;
(define p1 (new-instance person "Martin" 50))
(define p2 (new-instance person "Martin" 51))
(send-msg 'equals p1 p2)
(send-msg 'equals p1 (send-msg 'clone p1))
