
;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: A Web (& Farewell to dear Kiwi)
;;;
;;; Description:
;;;   Webs of good and bad.
;;;   Sunrise; won back the axe! But 
;;;   for Kiwi I'm sad. 

(define (draw)
  (speed 0)
  (hideturtle)
  (right 90)
  (setposition 100 -150)
  (bgcolor "black")
  (sphere 65 1 0 0 0.1)
  (web 110 20 38)
  (web 110 20 35)
  (web 110 20 35)
  (web 110 20 35)
  (web 110 20 35)
  (web 110 20 35)
  (web 110 20 35)
  (web 110 20 35)
  (web 110 20 35)
  (kiwi)
  (exitonclick))

(define (sphere radius r g b x)
    (color (rgb r g b))
    (if (or (> g 0.99) (< radius 0))
        nil
        (append (list (begin_fill) (circle radius) (end_fill) (forward x))    
        (sphere (- radius 1) r (+ g 0.015) b x))
    )
)

(define (tear radius r g b x)
    (color (rgb r g b))
    (if (or (> r 0.99) (< radius 0))
        nil
        (append (list (begin_fill) (circle radius) (end_fill) (forward x))    
        (tear (- radius 1) (+ r 0.1) g b x))
    )
)

(define (web angle x n)
    (color "white")
    (if (< n 0)
        (list (forward (+ x 50)) (left 40))
        (append (list (forward x)(right angle))
                (web angle (+ x 2)(- n 1)))
    )
)

(define (kiwi)
  (penup)
  (color "blue")
  (setposition -30 20)
  (pendown)
  (begin_fill)
  (circle 10)
  (end_fill)
  (penup)
  (setposition 35 20)
  (pendown)
  (begin_fill)
  (circle 10)
  (end_fill)
  (penup)
  (right 30)
  (setposition -65 -15)
  (pendown)
  (forward 130)
  (left 90)
  (forward 75)
  (left 90)
  (forward 130)
  (left 90)
  (forward 75)
  (left 210)
  (penup)
  (setposition -43 10)
  (pendown)
  (tear 4 0 1 1 2)
)

; Please leave this last line alone.  You may add additional procedures above
; this line.
(draw)