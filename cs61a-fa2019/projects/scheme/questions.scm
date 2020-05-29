(define (caar x) (car (car x)))
(define (cadr x) (car (cdr x)))
(define (cdar x) (cdr (car x)))
(define (cddr x) (cdr (cdr x)))

; Some utility functions that you may find useful to implement.

(define (cons-all first rests)
    (map (lambda (rest) (cons first rest)) rests))

(define (zip pairs)
    (define (make-pair lst)
        (if (null? lst) nil
            (cons (caar lst) (make-pair (cdr lst)))))
    (if (null? (car pairs)) nil
        (cons (make-pair pairs) (zip (map (lambda (x) (cdr x)) pairs)))))

(define (range start end)
    (if (>= start end) nil
        (cons start (range (+ start 1) end))))

;; Problem 16
;; Returns a list of two-element lists
(define (enumerate s)
    ; BEGIN PROBLEM 16
    (zip (list (range 0 (length s)) s)))
    ; END PROBLEM 16

;; Problem 17
;; List all ways to make change for TOTAL with DENOMS
(define (list-change total denoms)
    ; BEGIN PROBLEM 17
    (cond
        ((eq? total 0) '(()))
        ((< total 0) nil)
        ((null? denoms) nil)
        (else (append
                (cons-all
                    (car denoms)
                    (list-change (- total (car denoms)) denoms))
                    (list-change total (cdr denoms))))))
    ; END PROBLEM 17

;; Problem 18
;; Returns a function that checks if an expression is the special form FORM
(define (check-special form)
    (lambda (expr) (equal? form (car expr))))

(define lambda? (check-special 'lambda))
(define define? (check-special 'define))
(define quoted? (check-special 'quote))
(define let?    (check-special 'let))

;; Converts all let special forms in EXPR into equivalent forms using lambda
(define (let-to-lambda expr)
; BEGIN PROBLEM 18
    (cond
        ((or (atom? expr) (quoted? expr))
            expr)

        ((or (lambda? expr) (define? expr))
            (let
                ((form (car expr))
                (params (cadr expr))
                (body (let-to-lambda (cddr expr))))
                (append (list form params) body)))

        ((let? expr)
            (let
                ((values (let-to-lambda
                    (cadr expr)))
                (body (let-to-lambda
                    (cddr expr))))
                (cons
                    (append
                        (list 'lambda (car (zip values)))
                        body)
                    (cadr (zip values)))))

        (else (map let-to-lambda expr))))
; END PROBLEM 18
