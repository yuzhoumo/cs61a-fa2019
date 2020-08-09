;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: Not Creative Enough To Make A Good Title
;;;
;;; Description:
;;;   Never full alone
;;;   One's void's filled by another
;;;   Insert Third Line Here

(define (draw)
  (ht)
  (speed 0)
  (begin_fill)
  (circle 256)
  (end_fill)
  (rt 180)
  (draw-circles 128 4)
  (exitonclick))

(define (fractal initial_size depth times)
  (cond
    ((= times 0))
    (else
        (draw-branch initial_size depth)
        (rt 60)
        (fractal initial_size depth (- times 1))
        (lt 60))))

(define (draw-branch size depth)
  (cond
    ((= depth 0))
    (else
        (lt 15)
        (fd size)
        (draw-branch (* size .75) (- depth 1))
        (bk size)
        (rt 30)
        (fd size)
        (draw-branch (* size .75) (- depth 1))
        (bk size)
        (lt 15))))

(define (draw-circles radius times)
  (cond
    ((= times 0))
    (else
      (black-circle radius)
      (white-circle radius)
      (draw-circles (/ radius 2) (- times 1)))))

(define (black-circle radius)
    (color "black")
    (begin_fill)
    (circle radius)
    (end_fill)
    (lt 90)
    (fd (* radius 2))
    (fractal (/ radius 2) 7 1)
    (bk (* radius 2))
    (lt 90))

(define (white-circle radius)
    (color "white")
    (begin_fill)
    (circle radius)
    (end_fill)
    (lt 90)
    (fd (* radius 2))
    (fractal (/ radius 2) 7 1)
    (bk (* radius 2))
    (rt 90))

; Please leave this last line alone.  You may add additional procedures above
; this line.
(draw)
