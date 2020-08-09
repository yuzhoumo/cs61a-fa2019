;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: <Spherical Object>
;;;
;;; Description:
;;;   <Friends: you can't win with
;;;    that. It's not even 3D.
;;;    Me: okay Boomer>

(define (draw)
  (speed 0)
  (define (repeat k fn)
    (fn)
    (if (> k 1) (repeat (- k 1) fn))
  )
  (bgcolor (rgb 0 .5 .5))
  (define (line) (fd 220))
  (penup)
  (bk 20)
  (lt 90)
  (line)
  (pendown)
  (rt 90)
  (color (rgb 0 1 1))
  (repeat 36 (lambda () (fd 40) (rt 10) (repeat 36 (lambda () (rt 10) (fd 20)))))


  (exitonclick))


; Please leave this last line alone.  You may add additional procedures above
; this line.
(draw)
