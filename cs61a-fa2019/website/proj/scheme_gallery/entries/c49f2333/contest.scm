;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: Rooftop View of an Urban Sunset
;;;
;;; Description:
;;;   In the western sky,
;;;   A splendid star, waving bye.
;;;   Time to eat my pie!

(define (draw)

  (define buildw1 '(21 24 30 14 40 18 16 25 17 21 24 35 16 30 17 19 17))
  (define buildh1 '(135 124 152 131 146 139 132 154 128 141 132 146 130 115 144 136 161))
  (define buildw2 '(42 50 34 30 34 42 50 34 32 36))
  (define buildh2 '(112 145 116 130 102 125 150 112 140 130))

; Fills a rectangle with lower left corner at (x, y) and with width w and height h with color c
  (define (fillRect c x y w h)
    (penup)
    (color c)
    (setposition x y)
    (pendown)
    (begin_fill)
    (setposition (+ x w) y)
    (setposition (+ x w) (+ y h))
    (setposition x (+ y h))
    (setposition x y)
    (end_fill)
  )

; Draws the background sunset gradient recursively
  (define (drawbg layer)
    (cond ((< layer 13)
      (fillRect (rgb (+ (* layer 0.05) 0.4) (* layer 0.025) (- 1 (* layer 0.075))) -385 (- 323 (quotient (* 323 layer) 12)) (screen_width) (quotient 323 12))
      (drawbg (+ layer 1))))
  )

; Draws the background buildings recursively with color c
  (define (drawbuildings c x y widths heights)
    (cond ((not (equal? heights nil))
      (fillRect c x y (car widths) (car heights))
      (drawbuildings c (+ x (car widths)) y (cdr widths) (cdr heights)))
    )
  )

; Draws lines on the foreground building wall recursively
  (define (makelines count)
    (cond ((< count 6)
      (penup)
      (setposition (+ (* 64 count) 44) -243)
      (pendown)
      (setposition (+ (* 64 count) 44) -133)
      (setposition (+ (* 64 count) 52) -113)
      (makelines (+ count 1))
    ))
  )
  (hideturtle)
  (drawbg 1)
  (penup)
  (color "gold")
  (begin_fill)
  (setposition 267 60)
  (pendown)
  (circle 80)
  (end_fill)
  (drawbuildings (rgb 0.4 0 0.4) -385 -100 buildw1 buildh1)
  (drawbuildings (rgb 0.4 0 0.4) 0 -100 buildw1 buildh1)
  (drawbuildings (rgb 0.3 0 0.3) -385 -150 buildw2 buildh2)
  (drawbuildings (rgb 0.3 0 0.3) 0 -150 buildw2 buildh2)
  (fillRect "darkgray" -385 -323 770 210)
  (fillRect "gray" -385 -323 770 190)
  (fillRect "dimgray" -385 -323 770 80)
  (makelines -6)
  (fillRect "plum" -144 285 4 4)
  (fillRect "orchid" -295 224 3 3)
  (fillRect "orchid" 43 246 3 3)
  (fillRect "plum" 213 261 4 4)
  (exitonclick))

; Please leave this last line alone.  You may add additional procedures above
; this line.
(draw)
