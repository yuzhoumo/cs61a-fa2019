;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: <Christmas Decorating>
;;;
;;; Description:
;;;   <Season's greetings
;;;    from PG&E,
;;;    Fiat Lux...oops.>

(define (star)
  (begin_fill)
  (star-piece)
  (end_fill)
  (begin_fill)
  (star-piece)
  (star-piece)
  (star-piece)
  (star-piece)
  (end_fill)
)

(define (star-piece)
  (right 72)
  (forward 50)
  (left 144)
  (forward 50)
)

(define (fiat-lux)
  ;f
  (pendown)
  (forward 10)
  (right 90)
  (forward 10)
  (right 180)
  (forward 10)
  (right 90)
  (forward 5)
  (right 90)
  (forward 10)

  ;i
  (penup)
  (forward 5)
  (pendown)
  (right 90)
  (forward 15)

  ;a
  (left 90)
  (penup)
  (forward 5)
  (left 71.565)
  (pendown)
  (forward 15.8113883)
  (right 143.17)
  (forward 15.8113883)

  (right 180)
  (forward 10)
  (left 71.565)
  (forward 4)
  (right 180)
  (forward 4)
  (left 108.435)
  (forward 5.8113883)

  ;t
  (setheading 90)
  (penup)
  (forward 5)
  (pendown)
  (forward 10)
  (right 180)
  (forward 5)
  (left 90)
  (forward 15)

  ;space
  (penup)
  (right 180)
  (forward 15)
  (right 90)
  (forward 15)

  ;l
  (pendown)
  (right 90)
  (forward 15)
  (left 90)
  (forward 10)

  ;u
  (penup)
  (forward 5)
  (left 90)
  (forward 15)
  (right 180)
  (pendown)
  (forward 10)
  (circle 5 180)
  (forward 10)

  ;x
  (penup)
  (right 90)
  (forward 5)
  (pendown)
  (right 56.31)
  (forward 18.028)
  (setheading 0)
  (penup)
  (forward 15)
  (pendown)
  (left 146.31)
  (forward 18.028)
)

(define (motion-dashes)
;center motion dash
(right 180)
(forward 75)
(pendown)
(forward 25)

;left motion dash
(penup)
(left 90)
(forward 15)
(left 90)
(pendown)
(forward 25)

;second from right motion dash
(penup)
(left 90)
(forward 30)
(left 90)
(pendown)
(forward 25)

;rightmost motion dash
(penup)
(right 90)
(forward 15)
(right 90)
(pendown)
(forward 25)
)

(define (bauble x y bauble-color)
  (setposition x y)
  (color bauble-color)
  (begin_fill)
  (circle 10)
  (end_fill)
  (color "black")
)

(define (draw)
  ; YOUR CODE HERE
  (speed 0)
  (penup)
  (right 90)
  (forward 125)
  (left 90)
  (forward 100)

  ;sign
  (color "#a1c7b6")
  (begin_fill)
  (pendown)
  ;(circle 125 180)
  (left 45)
  (forward 176.777)
  (left 90)
  (forward 176.777)
  (left 45)
  (forward 150)
  (circle 125 180)
  (forward 150)
  (end_fill)
  (penup)
  (color "black")

  ;fiat lux setup
  (left 90)
  (forward 175)
  (left 90)
  (forward 100)
  (right 180)
  (fiat-lux)

  ;star setup
  (penup)
  (setheading 0)
  (forward 50)
  (left 135)
  (forward 400)
  (star)

  ;motion dashes
  (motion-dashes)
  (penup)

  ;base
  (setheading 180)
  (setposition (- 1) (- 175))
  (begin_fill)
  (color "brown")
  (forward 150)
  (left 90)
  (forward 10)
  (left 90)
  (forward 150)
  (end_fill)
  (hideturtle)
  (bgcolor "#D3D3D3")

  ;baubles
  (bauble 5 120 "cyan")
  (bauble 65 145 "brown")
  (bauble 55 70 "red")
  (bauble (- 36) (- 45) "green")
  (bauble 48 (- 74) "blue")
  (bauble (- 48) 79 "yellow")
  (bauble (- 46) (- 100) "orange")
  (bauble 64 (- 120) "purple")
  (bauble 85 (- 15) "pink")
  (bauble (- 82) 30 "white")
  (bauble 10 (- 135) "gold")
  (bauble 100 28 "black")
  (bauble (- 82) (- 75) "silver")
  (exitonclick))

; Please leave this last line alone.  You may add additional procedures above
; this line.
(draw)
