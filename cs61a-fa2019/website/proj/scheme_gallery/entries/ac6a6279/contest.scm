;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: <I like em curves>
;;;
;;; Description:
;;;   <Gonna tell my kids
;;;    I got first place in the cs scheme contest
;;;    cuz of this drawing>


(define (draw)
    (define (stupid-polygon n len)
        (define (helper num)
            (if (> num 0) (begin (forward (+ num len)) (left (/ 360 n)) (helper (- num 1)))))
        (helper n)
    )
  ; YOUR CODE HERE
  (stupid-polygon 30 1)
  (exitonclick))

; Please leave this last line alone.  You may add additional procedures above
; this line.
(draw)