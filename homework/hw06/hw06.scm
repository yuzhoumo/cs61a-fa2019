; ;;;;;;;;;;;;;;
; ; Questions ;;
; ;;;;;;;;;;;;;;
; Scheme
(define (cddr s) (cdr (cdr s)))

(define (cadr s) (car (cdr s)))

(define (caddr s) (car (cddr s)))

(define (sign x)
    (cond
        ((> x 0) 1)
        ((< x 0) -1)
        (else 0)))

(define (square x) (* x x))

(define (pow b n)
    (cond
        ((= n 0) 1)
        ((even? n) (square (pow b (quotient n 2))))
        ((odd? n) (* b (square (pow b (quotient n 2)))))))

(define (unique s)
    (if (equal? s nil)
        nil
        (cons
            (car s)
            (unique
                (filter
                    (lambda (x) (not (equal? x (car s))))
                    (cdr s))))))
