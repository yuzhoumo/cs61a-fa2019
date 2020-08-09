;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: Boxing Day
;;;
;;; Description:
;;;   Beepity boop beep
;;;     Staring at my code blankly
;;;       Waiting for something to work

(speed 500)
(define max 30)
(define (osc f r)
  (forward f)
  (right r)
)

(define (repeat func a b n)
  (if (= n 1)
    (func a b)
    (begin
      (func a b)
      (repeat func a b (- n 1))
    )
    )
)
(define (draw)
  (define (helper x y)
  (setposition x y)
  (bgcolor "black")
  (color "white")

  (if (= x max)
    (exit)
    (begin
      (repeat osc 100 90 4)
      (repeat osc 100 45 2)
      (repeat osc 100 45 1)
      (right 45)
      (repeat ((lambda (a b) (begin (osc a b) osc)) 100 45) 100 135 1)
      (repeat osc 100 45 1)
      (osc 100 135)
      (osc 100 45)
      (osc 100 180)
      (osc 100 225)
      (osc 100 90)
      (osc 100 180)
      (osc 100 45)
      (forward 100)
      (ht)
      (helper (+ 0.5 x) y)
      )
  )
  )
  (helper 0 0)
  (exitonclick))

; Please leave this last line alone.  You may add additional procedures above
; this line.
(draw)
