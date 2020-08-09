;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: Bear Territory
;;;
;;; Description:
;;;   <Day and night
;;;    From Soda to Cory
;;;    Infinite is Bear Territory.>

(define (rect x1 x2 x3 x4 y1 y2)
  (penup)
  (goto x1 y2)
  (color "black")
  (begin_fill)
  (pendown)
  (goto x2 y1)
  (goto x3 y1)
  (goto x4 y2)
  (goto x1 y2)
  (end_fill)
 )

(define (Stanford)
  (rect -420 -420 410 410 392 300)
  (penup)
  (setposition -400 350)
  (color "white")
  (write "California vs Stanford")

  (color "black")
  (setposition 340 250)
  (write_smaller "Final")

  (setposition -360 125)
  (write_smaller "California Golden Bears")
  (setposition 180 125)
  (write_smaller "Stanford Cardinal")

  (setposition -130 170)
  (write_bigger "24")
  (setposition 100 170)
  (write_bigger "20")
  (setposition 0 170)
  (write_bigger "-")

  (color "grey")
  (setposition -400 320)
  (write_smaller "NCAA football")
  (setposition -360 250)
  (write_smaller "NCAA football • Sat, 11/23")
  (setposition -280 100)
  (write_smaller "(6 - 5)")
  (setposition 240 100)
  (write_smaller "(4 - 7)")
 )

(define (UCLA)
  (rect -420 -420 410 410 -290 -382)
  (penup)
  (setposition -400 -332)
  (color "white")
  (write "California vs UCLA")

  (color "black")
  (setposition 340 -115)
  (write_smaller "Final")

  (setposition -360 -240)
  (write_smaller "California Golden Bears")
  (setposition 200 -240)
  (write_smaller "UCLA Bruins")

  (setposition -130 -200)
  (write_bigger "28")
  (setposition 100 -200)
  (write_bigger "18")
  (setposition 0 -200)
  (write_bigger "-")

  (color "grey")
  (setposition -400 -362)
  (write_smaller "NCAA football")
  (setposition -360 -115)
  (write_smaller "NCAA football • Sat, 11/30")
  (setposition -280 -265)
  (write_smaller "(7 - 5)")
  (setposition 240 -265)
  (write_smaller "(4 - 8)")
 )

(define (print_gold x l)
	(color "#E6C500")
  	(setposition x -60)
  	(write_big l)
 )

(define (print_navy x l)
	(color "navy")
  	(setposition x -60)
  	(write_big l)
 )

(define (draw)
  	(Stanford)

  	(color "grey")
  	(setposition -400 75)
  	(pendown)
  	(setposition 400 75)
  	(penup)
  	(setposition -400 -65)
  	(pendown)
  	(setposition 400 -65)

  	(UCLA)
  	(penup)

  	(print_gold -330 "G")
  	(print_navy -255 "o")
  	(print_gold -110 "B")
	(print_navy -35 "e")
  	(print_gold 40 "a")
  	(print_navy 115 "r")
  	(print_gold 190 "s")
  	(print_navy 265 "!")

  	(setposition -250 190)
  	(addshape 'cal.gif)
  	(stamp)
  	(setposition -260 -175)
  	(stamp)
  	(setposition 260 200)
  	(addshape 'stanford.gif)
  	(stamp)
  	(setposition 260 -175)
  	(addshape 'ucla.gif)
  	(stamp)

  (exitonclick))

; Please leave this last line alone.  You may add additional procedures above
; this line.
(draw)