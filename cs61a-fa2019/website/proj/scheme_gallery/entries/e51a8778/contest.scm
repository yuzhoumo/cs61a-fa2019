;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: Denero's Big Hairy Ball (Theorem)
;;;
;;; Description:
;;;   <Our big hairy ball
;;;    Looks a lot like a skittle.
;;;    Token count: lol, nice.>
(+)

(define (x y)
(right y)
(forward (* 5.4 y ))
(if (> 200 y)
    (x (* y 1.002))
    (hideturtle)
)
)
(define (draw)
    ;;;OKAY BOOMERS
    (penup)
    (setposition -300 0)
    (pendown)
    (x 1)
)


; Please leave this last line alone.  You may add additional procedures above
; this line.
(speed 0)
(draw)
