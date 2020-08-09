;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: Gonna tell my kids this won the Scheme Recursive Art Contest
;;;
;;; Description:
;;;    Vote for this as
;;;    abstraction is key
;;;    to battling complexity

(define (drawSwing x0 y0 x1 y1 x2 y2 x3 y3)
  (penup)
  (goto x0 y0)

  (pendown)
  (goto x1 y1)
  (goto x2 y2)
  (goto x3 y3)
  (penup)
)

(define (drawChains1 x0 y0 x1 y1)
  (penup)
  (goto x0 y0)

  (pendown)
  (goto x1 y1)
  (penup)
)

(define (drawChains x0 y0 x1 y1)
  (cond
    ((<= y0 10)
      penup)
    (else
      (goto x0 y0)
      (pendown)
      (circle 10)
      (drawChains x0 (- y0 10) x1 (- y1 10))
    )
  )
  (penup)
)



(define (drawSeat x0 y0 x1 y1 x2 y2 x3 y3 x4 y4)
  (penup)
  (goto x0 y0)
  (pendown)
  (goto x1 y1)
  (goto x2 y2)
  (goto x3 y3)
  (goto x4 y4)
  (goto x0 y0)
  (penup)
)

(define (drawFace x y)
  (penup)
  (goto x y)
  (pendown)
  (circle 30)
  (penup)
)

(define (drawBody x0 y0 x1 y1 x2 y2 x3 y3 x4 y4 x5 y5 x6 y6 x7 y7 x8 y8)
  (penup)
  (goto x0 y0)
  (pendown)
  (goto x1 y1)
  (goto x2 y2)
  (penup)
  (goto x3 y3)
  (pendown)
  (goto x4 y4)
  (goto x5 y5)
  (goto x6 y6)
  (penup)
  (goto x7 y7)
  (pendown)
  (goto x8 y8)
  (penup)
)

(define (draw)
  ; YOUR CODE HERE
  (speed 10)
  (drawSwing -300 -120 -250 250 250 250 300 -120)
  (drawChains -125 250 -125 10)
  (drawChains 125 250 125 10)
  (drawSeat -150 10 -165 -50 150 -50 140 10 125 10)
  (drawFace 10 200)
  (drawBody -20 170 -20 130 -125 150 125 150 -20 130 -20 -20 -130 -100 -20 -20 130 -100)
  (ht)
  (exitonclick))

; Please leave this last line alone.  You may add additional procedures above
; this line.
(draw)