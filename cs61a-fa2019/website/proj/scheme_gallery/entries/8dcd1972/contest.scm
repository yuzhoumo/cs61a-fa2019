;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: <Why So Serious>
;;;
;;; Description:
;;;   <Life is hard.
;;;    But you are sweet.
;;;    Smile.>

(define (draw_eye)
  (begin_fill)
  (forward 47.5)
  (left 90)
  (forward 22.5)
  (right 90)
  (forward 2.5)
  (right 90)
  (forward 50)
  (right 90)
  (forward 2.5)
  (right 90)
  (forward 22.5)
  (left 90)
  (forward 47.5)
  (right 90)
  (forward 5)
  (end_fill))

(define (draw)
  ; YOUR CODE HERE
  (bgcolor "#282A36")

  (hideturtle)

  (begin_fill)
  ;draw inner big circle
  (penup)
  (left 90)
  (forward 170)
  (right 90)
  (pendown)

  (color "white")
  (circle -170)

  ;draw outer big circle
  (left 90)
  (forward 10)
  (right 90)

  (circle -180)
  (end_fill)

  ;move to face drawing pos
  (penup)
  (right 90)
  (forward 80)
  (left 90)
  (pendown)

  ;draw smiling mouth
  (begin_fill)
  (color "white")
  (circle -100 -180)


  (left 90)
  (forward 5)
  (left 90)
  (circle 105 -180)
  (left 90)
  (forward 5)

  (end_fill)

  (penup);move to left eye pos

  (forward 47.5)
  (left 90)
  (forward 25)

  ;draw left eye
  (pendown)
  (draw_eye)  

  (penup);move to right eye pos

  (backward 100)
  (right 90)

  ;draw right eye
  (pendown)
  (draw_eye)


  (exitonclick))


; Please leave this last line alone.  You may add additional procedures above
; this line.
(draw)