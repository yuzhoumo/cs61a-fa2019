;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: <D-D-Denero s-sempai  >
;;;
;;; Description:
;;;   <Daddy Denero,
;;;   I must know. Of you is there
;;;    a rule 34>

(define (draw)


(speed 10)

(pixelsize 1)



(define (rectangle side-one side-two rect-color)
  (color rect-color)
  (forward side-one)
  (left 90)
  (forward side-two)
  (left 90)
  (forward side-one)
  (left 90)
  (forward side-two)
  (left 90)
)

(define (denero)
(color "#ffceb4")
(begin_fill)
(seth 0)
(left 30)
(circle 100 120)
(left 30)
(forward 80)
(circle 80 70)
(seth 90)
(forward 63)
(left 18)
(circle 80 70)
(forward 80)
(end_fill)
(color "#0b0000")
(seth 90)
(begin_fill)
(circle 70 -73)
(seth 270)
(circle 70 -73)
(end_fill)
(seth 90)
(circle 70 -73)
(seth 45)
(begin_fill)
(circle 204 -35)
(seth 200)
(circle 100 -120)
(end_fill)

(penup)
(goto 0 0)
(pendown)
(left 96)
(forward 25)
(seth 270)
(rectangle 50 25 "#0b0000")
(forward 50)
(left 90)
(forward 10)
(right 90)
(forward 20)
(right 90)
(forward 10)
(left 90)
(rectangle 50 25 "#0b0000")
(forward 50)
(right 25)
(forward 40)

(penup)
(goto -20 -32)
(pendown)
(seth 290)
(circle 45 55)
(seth 270)
(penup)
(goto -95 -32)
(pendown)
(seth 290)
(circle 45 50)
(penup)
(goto -20 -12)
(pendown)
(seth -80)
(forward 40)
(penup)
(goto -100 -3)
(pendown)
(seth 260)
(forward 40)
(penup)
(goto -80 -80)
(pendown)
(color "#850F0F")
(begin_fill)
(circle 30 360)
(end_fill)
(color "#0b0000")
(penup)
(circle 30 245)
(pendown)
(color "#F4BBBF")
(seth 270)
(begin_fill)
(circle 60 45)
(seth 90)
(circle 100 25)
(end_fill)
(penup)
(goto -80 -40)
(seth 200)
(pendown)
(color "#0b0000")
(forward 20)
(seth 90)
(forward 20)
)


(define (squiggles num)
  (if (not (equal? 0 num))
      (begin
        (seth 40)
        (forward 30)
        (seth 20)
        (backward 23)
        (squiggles (- num 1))
        )
      (backward 0)

  )
  )

  (define (pink-rectangle)
    (color "#F9A8C4")
    (seth 270)
    (begin_fill)
    (rectangle 50 20 "#F9A8C4")
    (end_fill)
  )



  (denero)

  (penup)
  (goto -100 -50)
  (pendown)
  (pink-rectangle)

  (penup)
  (goto -10 -50)
  (pendown)
  (pink-rectangle)

  (color "#A71930")
  (penup)
  (goto -145 -75)
  (pendown)
  (squiggles 3)
  (penup)
  (goto -55 -75)
  (pendown)
  (squiggles 3)



(exitonclick))

; Please leave this last line alone.  You may add additional procedures above
; this line.
(draw)
