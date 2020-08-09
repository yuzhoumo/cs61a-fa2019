;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: Colorful Notes
;;;
;;; Description:
;;;   <It's your masterpiece.
;;;    Use these three lines to describe
;;;    its inner meaning.>

(define (note x y radius count factor)
  (if (= count 0) nil
  (begin
  (setheading 0)
  (penup)
  (goto x y)
  (pendown)
  (begin_fill)
  (circle radius)
  (end_fill)
  (forward (* 3 radius))
  (circle (* radius -2) -50)
  (note (+ x (* 2.5 radius)) y (/ radius factor) (- count 1) factor)
  ))
)

(define (draw)
  (color "red")
  (note -240 -260 40 11 1.15)
  (color "blue")
  (note -240 -130 7.52 13 0.8695)
  (color "green")
  (note -240 0 40 11 1.15)
  (color "orange")
  (note -240 130 7.52 13 0.8695)
  (color "yellow")
  (note -240 260 40 11 1.15)
  (exitonclick)
)

; Please leave this last line alone.  You may add additional procedures above
; this line.
(draw)
