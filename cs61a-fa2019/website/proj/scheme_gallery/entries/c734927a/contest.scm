;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: Happy x 6
;;;
;;; Description:
;;;   (let ((param1 happiness)
;;;         (param2 exam-score))
;;;   (eq? param1 param2)) -> #f
;;;
;;;

(define (smiley x y r count)
  (if (= 0 count) nil
  (begin
  (setheading 0)
  (penup)
  (goto x y)
  (pendown)
  (color "yellow")
  (begin_fill)
  (circle r)
  (end_fill)
  (color "blue")
  (begin_fill)
  (penup)
  (goto (- x (/ r 3)) (+ y (/ r 3)))
  (pendown)
  (circle (/ r 10))
  (end_fill)
  (begin_fill)
  (penup)
  (goto (- x (/ r 0.8)) (+ y (/ r 3)))
  (pendown)
  (circle (/ r 10))
  (end_fill)
  (penup)
  (goto (- x (/ r 3)) y)
  (pendown)
  (circle (* r 0.6) -180)
  (smiley (+ (* 3 r)) y (/ r 2) (- count 1))
  (smiley (- (* 5 r)) y (/ r 2) (- count 1))
  ))
)

(define (draw)
  (smiley 0 0 50 3)
  (exitonclick))

; Please leave this last line alone.  You may add additional procedures above
; this line.
(draw)
