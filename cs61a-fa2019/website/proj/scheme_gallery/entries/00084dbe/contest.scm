;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: <The Axe is Back!!!>
;;;
;;; Description:
;;;   <Eighty seconds left...
;;;    Chase Garbers with the touchdown!
;;;    The axe is ours!!!>

(define (lower-curve-rev n)
  (if (>= n 0.38) 
    nil 
    (begin
      (left (* n 5))
      (forward 3)
      (lower-curve-rev (+ n 0.01))
    )
  )
)

(define (rev-bottom)
  (left 90)
  (forward 200)
  (right 5)
  (forward 50)
  (right 90)
  (forward 100)
  (right 120)
  (lower-curve-rev 0)
  (setposition 0 -100)
)

(define (lower-curve n)
  (if (>= n 0.38) 
    nil 
    (begin
      (right (* n 5))
      (forward 3)
      (lower-curve (+ n 0.01))
    )
  )
)

(define (bottom)
  (right 90)
  (forward 200)
  (left 5)
  (forward 50)
  (left 90)
  (forward 100)
  (left 120)
  (lower-curve 0)
  (setposition 0 -100)
)

(define (upper-curve n)
  (if (>= n 0.38)
    nil
    (begin
      (left (* n 11))
      (forward 3)
      (upper-curve (+ n 0.01))
    )
  )
)

(define (bump n)
  (if (>= n 0.19)
    nil
    (begin
      (right (* n 15))
      (forward 3)
      (bump (+ n 0.01))
    )
  )
)

(define (upper-curve-rev n)
  (if (>= n 0.38)
    nil
    (begin
      (right (* n 11))
      (forward 3)
      (upper-curve-rev (+ n 0.01))
    )
  )
)

(define (bump-rev n)
  (if (>= n 0.19)
    nil
    (begin
      (left (* n 15))
      (forward 3)
      (bump-rev (+ n 0.01))
    )
  )
)


(define (bottom-curve)
  (penup)
  (setheading 0)
  (setposition 0 -150)
  (color "gray")
  (begin_fill)
  (pendown)
  (bottom)

  (penup)
  (setposition 0 -150)
  (setheading 0)
  (pendown)
  (rev-bottom)
  (end_fill)
)



(define (upper-box) 
  (penup)
  (setposition 73.5 236)
  (color "gray")
  (begin_fill)
  (setheading 0)
  (forward 25)
  (left 90)
  (forward 147)
  (left 90)
  (forward 25)
  (left 90)
  (forward 147)
  (end_fill)
)

(define (red-middle)
  (penup)
  (setposition 240 -46)
  (color "red")
  (begin_fill)
  (setheading 0)
  (pendown)
  (upper-curve 0)

  (forward 120)
  (setheading 0)
  (bump 0)
  (circle 50 65)

  (right 10)
  (forward 25)
  (left 180)
  (circle 5 -30)
  (setheading 10)
  (forward 29)
  (setposition 0 236)
  (setposition 0 -100)

  (penup)
  (setposition -240 -46)
  (setheading 0)
  (pendown)
  (upper-curve-rev 0) 
  (forward 120)
  (setheading 0)
  (bump-rev 0)
  (circle -50 65)
  
  (left 10)
  (forward 25)
  (right 180)
  (circle -5 -30)
  (setheading -10)
  (forward 29)
  (setposition 0 236)
  (setposition 0 -100)

  (penup)
  (setposition 240 -46)
  (setheading -125)
  (pendown)
  (lower-curve 0)
  (setposition 0 -100)

  (penup)
  (setposition -240 -46)
  (setheading 125)
  (pendown)
  (lower-curve-rev 0)
  (setposition 0 -100)
  (end_fill)
)

(define (left-handle)
  (penup)
  (color "#ce9f6f")
  (begin_fill)
  (setposition -65 100)
  (setheading -90)
  (pendown)
  (forward 65)
  (setheading -20)
  (forward 57)
  (setheading 20)
  (forward 57)
  (setheading 90)
  (forward 65)
  (setposition -65 100)
  (end_fill)
)

(define (right-handle)
  (penup)
  (begin_fill)
  (setposition 70 100)
  (setheading 100)
  (pendown)
  (forward 250)
  (setheading 0)
  (forward 105)
  (setheading -95)
  (long-dip 0)
  (setheading -105)
  (small-dip 0)
  (setposition 70 100)
  (end_fill)
)

(define (long-dip n)
  (if (>= n 0.48) 
    nil 
    (begin
      (right (* n 5))
      (forward 4.7)
      (long-dip (+ n 0.01))
    )
  )
)

(define (small-dip n)
  (if (>= n 0.30) 
    nil 
    (begin
      (right (* n 15))
      (forward 1.5)
      (small-dip (+ n 0.01))
    )
  )
)



(define (draw)
  (bgcolor "#b88645")
  (left-handle)
  (right-handle)
  (bottom-curve)
  (red-middle)
  (upper-box)
  (hideturtle)

  (exitonclick))

; Please leave this last line alone.  You may add additional procedures above
; this line.
(draw)