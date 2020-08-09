;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: 69
;;;
;;; Description:
;;;   < Sixty-nine is a
;;;   curious number, bringing
;;;   all pains and pleasures. >

; roughly 750 x 650 - + 375 x + 325 in WINDOWED mode
; py scheme.py contest.scm

; Draws a bolded_circle at coordinates X Y. 
; SCALE modifies the size of the circle by factor.
; REV determines which side circle is drawn (1 or -1).
; WEIGHT determines the boldness of outline.
; STROKE is color of stroke
; FILL is color of inside shape
(define (bolded_circle x y scale rev weight stroke fill)
  (color stroke)
  (pendown)
  (begin_fill)
  (circle (* 200 scale rev))
  (end_fill)
  (penup)
  (goto (- x (* weight scale rev)) y)
  (color fill)
  (pendown)
  (begin_fill)
  (circle (* (- 200 weight) scale rev))
  (end_fill)
  (penup)
  (goto x y))

; bolded_circle extension function:
; draws a line of circles
(define (circle_line x y scale rev weight stroke fill)
	(if (or (> x 500) (< x -500)) (goto x y)
	  (begin
	    (bolded_circle x y scale rev weight stroke fill)
	    (goto (+ x (* 200 scale 2 rev)) y)
	    (circle_line (+ x (* 200 scale 2 rev)) y scale rev weight stroke fill))))

; bolded_circle extension function:
; draws a rectangular grid of circles
; in addition, can take a list of colors and draw a gradient pattern.
; nil lists default to only using stroke colors.
(define (circle_grid x y scale up weight stroke fill gradient)
	(if (or (> y 400) (< y -400)) (goto x y)
	  (begin
	    (circle_line x y scale 1 weight (cond((null? gradient) stroke) (else (car gradient))) fill)
		(circle_line x y scale -1 weight (cond((null? gradient) stroke) (else (car gradient))) fill)
		(goto x (+ y (* 200 scale 2 up)))
		(circle_grid x (+ y (* 200 scale 2 up)) scale up weight stroke fill (cond((null? gradient) gradient) (else (cdr gradient)))))))

; bolded_circle extension function:
; draws a 6 figure according to
; the NINE argument (either 1 or -1)
; determines whether a 6 or 9 is drawn
(define (six x y scale nine weight stroke fill)
	(bolded_circle x y (* scale 0.5) nine weight stroke fill)
	(cond ((= nine -1) (setheading 0)) (else (setheading 180)))
	(begin_fill)
	(circle (* -100 scale) 140)
	(end_fill)
	(penup)
	(setheading 0)
	(goto (+ x (cond((= nine -1) (* 200 scale)) (else 0))) (+ y (* -110 scale nine)))
	(bolded_circle (+ x (cond((= nine -1) (* 200 scale)) (else 0))) (+ y (* -110 scale nine)) (* scale 0.5) 1 weight stroke fill)
	
	(color stroke)
	(define six_y (+ y (* -110 scale nine)))
	(goto (+ x (* -200 scale nine)) six_y)
	(begin_fill)
	(goto (+ x (* -200 scale nine)) (+ six_y (* 100 scale nine)))
	(goto (+ x (* -200 scale nine) (* (/ weight 2) scale nine)) (+ six_y (* 100 scale nine)))
	(goto (+ x (* -200 scale nine) (* (/ weight 2) scale nine)) six_y)
	(goto (+ x (* -200 scale nine)) six_y)
	(end_fill)
	(goto x y))

; NOTES: 
; Default size is 200, weight 50. Size to weight: 4 to 1
; The six function uses half the scale of the default size, so
; the default parameters of six would be
; scm> (six 0 0 2 1 50 "black" "#FFD512")
; SCALE scales both size and weight of drawn figures. 

(define colors '("black" "#272105" "#3D3408" "#5C4E0A" "#79660C" "#967E0F" "#C7A712" "#DAB713" "#EFC812" "#FFD512"))

(define (draw)
  ; YOUR CODE HERE
  (speed 0)
  (bgcolor "#FFD512")
  (penup)
  (goto 0 0)
  (circle_grid 0 0 0.1 1 90 "black" "#FFD92A" colors)
  (circle_grid 0 0 0.1 -1 90 "black" "#FFD92A" colors)
  (goto 160 0)
  (bolded_circle 160 0 0.8 1 30 "black" "#FFD512")
  (goto 0 0)
  (six 0 0 0.5 1 90 "black" "#FFD512")
  (six 0 0 0.5 -1 90 "black" "#FFD512")
  (goto 0 0)

  (hideturtle)
  (exitonclick))

; Please leave this last line alone.  You may add additional procedures above
; this line.
(draw)