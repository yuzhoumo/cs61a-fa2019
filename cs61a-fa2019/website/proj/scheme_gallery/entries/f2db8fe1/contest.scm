;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: Make this spiral go viral
;;;
;;; Description:
;;;   CREATE TABLE Smartest_Students AS
;;;   SELECT name FROM 61A_Students
;;;   WHERE contest_vote = "This Entry".

(define (draw)
  (begin

  	(speed 10)
    (color "white")
    (bgcolor "black")

    ;-----------------------------------------------------------

    (define (draw-helper direction cycles)

      (if (> cycles 30) 
      	(cons direction nil)
        (begin

          (circle 40 120)

          (if (eq? direction "left")
            (left 60)
            (right 60)
          )

          (if (eq? direction "left")
            (define new-direction "right")
            (define new-direction "left")
          )
  
          (if (= 0 (modulo cycles 6))
            (right 20)
          )

          (draw-helper new-direction (+ cycles 1))

        )
      )
    )

    ;-----------------------------------------------------------

    (define (hexagon x)
      (begin
        (setposition (* x (sqrt 3) 0.5 (- 0 1.0)) (* x 1.5))
        (setposition (* x (sqrt 3) 0.5) (* x 0.5))
        (setposition (* x (sqrt 3) 0.5 (- 0 1.0)) (* x 0.5))
        (setposition (* x (sqrt 3) 0.5) (* x 1.5))
        (setposition (* x (sqrt 3) 0.5 (- 0 1.0)) (* x 1.5))
        (setposition (* x (sqrt 3) 0.5) (* x 1.5))
        (setposition 0 0)
        (setposition 0 (* x 2))
        (setposition (* x (sqrt 3) 0.5 (- 0 1.0)) (* x 0.5))
        (setposition (* x (sqrt 3) 0.5) (* x 0.5))
        (setposition 0 (* x 2))
      )
    )

    ;-----------------------------------------------------------

    (define (bigger-helper direction big-cycles)

      (if 
        (< 6 big-cycles)
        (hexagon 70)

        (begin
          (bigger-helper 
            (car (draw-helper direction 0))
            (+ big-cycles 1)
          )
        )
      )
    )

    ;-----------------------------------------------------------

    (bigger-helper "left" 1)

  )
  (exitonclick))

; Please leave this last line alone.  You may add additional procedures above
; this line.
(draw)