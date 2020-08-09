;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: Flowers
;;;
;;; Description:
;;;   When am I supposed
;;;   to see Denero dancing
;;;   like blooming flowers?


;Complex Implementation
(define (add x y) (list (+ (car x) (car y)) (+ (car (cdr x)) (car (cdr y)))))

(define (mul x y)
    (
        list
        (- (* (car x) (car y)) (* (car (cdr x)) (car (cdr y))))
        (+ (* (car x) (car (cdr y))) (* (car (cdr x)) (car y)))
    )
)

(define (norm c) (+ (* (car c) (car c)) (* (car (cdr c)) (car (cdr c)))))

(define (pow b n) (cond ((= n 0) (list 1 0)) ((even? n) (pow (mul b b) (/ n 2))) (else (mul b (pow b (- n 1))))))

(define (shift c) (add (pow c 5) (list -0.0562 -0.636)))

(define (grad c prev)
    (
        cond
        ((= prev 272) prev)
        ((> (norm c) 1.27) prev)
        (else (grad (shift c) (+ prev 1)))
    )
)

;Draw
(define radius 270) ;Increase the radius to view more details

(define range (* 1.18 radius))

(define (paint position)
    (define coordinate (list (/ (car position) radius) (/ (car (cdr position)) radius)))
    (define nit (grad coordinate 0))
    (rgb (- 1 (/ nit 272)) (- 1 (/ nit 272)) (- 1 (/ nit 272)))
)

(define (point position)
    (begin
        (penup)
        (setposition (car position) (car (cdr position)))
        (color (paint position))
        (pendown)
        (backward 1)
    )
)

(define (create x y)
    (
        cond
        ((< x (- 0 range)) (exit))
        ((< y (- 0 range)) (create (- x 1) range))
        (else 
            (begin
                (point (list x y))
                (create x (- y 1))
            )
        )
    )
)

(define (draw)
	(showturtle)
	(bgcolor (rgb 1 1 1))
	(create range range)
  (exitonclick))

; Please leave this last line alone.  You may add additional procedures above
; this line.
(draw)