; Scheme Recursive Art Contest Entry

; Please do not include your name or personal info in this file.

; Title: CS61A Tree

; Description:
;   <Real tree recusion
;    Denero is root
;    We are leaves>
(define (pensize n l)
	(begin_fill)
	(left 90)
	(forward (/ n 2))
	(right 90)
	(forward l)
	(right 90)
	(forward n)
	(right 90)
	(forward l)
	(right 90)
	(forward (/ n 2))
	(right 90)
	(forward l)
	(end_fill)
	)

; (define random (random))

(define (tree n l)
  ; Draw the tree
  (pd)
  (define t (+ (/ (cos (radians (+ (heading) 45))) 8) 0.25))
  (color (rgb t t t))
  (pensize (* n 1.5) l)

  (cond ((> n 0)(define angle_l (+ (* (random) 13) 10))
  				 (define angle_r (+ (* (random) 13) 10))	
  				 (define next_l (* l (+ (* (random) 0.25 ) 0.7)))
  				 (right angle_r)
  				 (tree (- n 1) next_l)
  				 (left (+ angle_r angle_l))
  				 (tree (- n 1) next_l)
  				 (right angle_l)
  					
  			)
  		(else 	(right 90)
  				 (define t (+ (/ (cos (radians (- (heading) 45))) 4) 0.5))
  				 (color (rgb t (* t 0.8) (* t 0.8)))
  				 (circle 5)
  				 (left 90)
  				 ; (if (> (random) 0.7)
  				 ; 	 (random_flower)
  				 ; 	)
  					
  			)
  	)
  (penup)
  (back l)
  ; (exitonclick)
  )

(define (random_flower)
	    (penup)
 	   	(define curr_heading (heading))
 	   	(define an (- (* (random) 100) 40))
 	   	(setheading an)
 	   	(define dis (+ (+ (* 140 (random)) (+ (* 120 (random)) (* 140 (random)))) 400))
 	   	(forward dis)
 	   	(setheading curr_heading)
 	   	(pendown)
 	   	(right 90)
 	   	(define n (+ 0.5 (/ (cos (radians (- (heading) 45))) 9)) )
 	   	(color (rgb (+ 0.5 (* 0.5 n)) (+ 0.4 (* 0.4 n)) (+ 0.4 (* 0.4 n))))
 	   	(circle 5)
 	   	(left 90)
 	   	(penup)
 	   	(define curr_heading (heading))
 	   	(setheading an)
 	   	(back dis)
 	   	(setheading curr_heading)
	)

(define (draw)
	(penup)
	)

(define (repeat n)
        (random_flower)
        (if (> n 0)
                (repeat (- n 1))
                (penup)
        ))


; Please leave this last line alone.  You may add additional procedures above
; this line
(bgcolor "grey" )
; (hideturtle)
(speed 0)
(pu)
(setposition 0 0)
(back 360)
(pd)
(setheading 30)
(repeat 140)
(setheading 0)
(tree 10 140); depth and length

(draw)