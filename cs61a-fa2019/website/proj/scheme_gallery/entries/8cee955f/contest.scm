
;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: The Impossible Block Chain
;;;
;;; Description:
;;;   <[CS61A: Computer] Science and art sometimes can touch one another, 
;;;    like two pieces of the jigsaw puzzle which is our human life...
;;;    --M. C. Escher>


(define (draw)
  (hideturtle)
  (draw-background '(0.2083 0.5647 1) '(0.1254 0.6980 0.6667) '(1 0.95 0))
  (draw-base-design '(1 0.8641 0.7486)'(0.9803 0.5019 0.4470)
					'(0.9733333333 0.4977777778 0.6533333333))
  (redraw-blocks '(1 0.8641 0.7486)'(0.9803 0.5019 0.4470)
					'(0.9733333333 0.4977777778 0.6533333333))    
  (draw-diamonds (rgb 0.9412 0.9725 1) (rgb 0 0.95 1) 5)
  (exitonclick))

; Please leave this last line alone.  You may add additional procedures above
; this line.


(define (get-color-shade base-color orient)
  (if (= orient 1) (apply rgb base-color)
	     (apply rgb (map (lambda (x) (/ x (/ orient 1.4))) base-color))))

(define (get-index index lst)
	(cond ((null? lst) nil)
		((= index 0) (car lst))
		(else (get-index (- index 1) (cdr lst)))))

(define (zip l1 l2)
  (if (or (null? l1) (null? l2))
      '()
      (cons (list (car l1) (car l2))
            (zip  (cdr l1) (cdr l2)))))

(define (color-in c fn . args)
  (color c)
  (begin_fill)
  (apply fn args)
  (end_fill))

(define scrn_width 720)
(define scrn_height 675)
;possible step size 1 3 5 25 75
(define stp 25)


(define (sun col radius dn-pos)
	(if (> 5 radius) nil
	(begin
		;top sun
		(speed 10)
		(pu)
		(setpos 280 280)
		(pd)
		(color (apply rgb col))
		(begin_fill)
		(circle radius)
		(end_fill)
		(pu)
		;reflection sun
		(setpos 280 dn-pos)
		(pd)
		(begin_fill)
		(circle radius)
		(end_fill)
		(pu)
		;gradient sun
		(sun (map (lambda (x) (/ (+ 1 x) 2)) col) 
			(/ radius 1.5) (- dn-pos (/ radius 2))))))

(define (water-diamond size-fact pos-x pos-y c)
	(let ((side (* 10 size-fact)))
		(pu)
		(speed 10)
		(setpos pos-x pos-y)
		(seth 110)
		(color c)
		(begin_fill)
		(fd side)
		(rt 130)
		(fd side)
		(rt 50)
		(fd side)
		(rt 130)
		(fd side)
		(end_fill)
		(pu)))


(define (another-water-diamond size-fact pos-x pos-y c)
	(let ((side (* 10 size-fact)))
		(pu)
		(speed 10)
		(setpos pos-x pos-y)
		(seth 130)
		(color c)
		(begin_fill)
		(fd side)
		(lt 60)
		(fd side)
		(lt 170)
		(fd (/ side 1.07))
		(rt 39)
		(fd (/ side 1.1))
		(end_fill)))

(define (lots-of-diamond lst fn c)
	(if (null? lst) nil
	(begin (apply fn (append (car lst) (list c)))
			(lots-of-diamond (cdr lst) fn c))))

(define (draw-background col-start col-end sun-col)
  (horz-grad 337.5 225 col-start col-end)
  (horz-grad 112.5 450 col-end col-start)
  (sun sun-col 30 -280))

(define (draw-diamonds c1 c2 n)
  (if (= n 0) nil
	  (begin
  		(pd)
  		(speed 10)
  		(lots-of-diamond 
 		 ;sun reflection
  		'((2 240 112.5)(3 260 100)(2 290 110)(1.5 280 70)(2 275 50)(1.5 270 40)
  		  (1.5 290 10)(2 255 0)(2 270 -50)(1.5 260 -100)(2 275 -75)
  	 	 (1 290 -60)(1.5 275 -150)(1 280 -200)(1.5 265 -310)

	  	 ;block stuff
  	   	 (1 40 80)(1.3 95 70) 
  	  	 (1.8 0 -80)(1 30 -110) 
  	  	 (1.5 -260 -220)(1.7 -210 -240)(2 -190 -280)(1.1 -20 -260)(1.8 0 -240)(1.8 100 -200))
  	   	water-diamond c1)

  		(lots-of-diamond 
  		'(
		  (1.3 0 120)
		  (1.5 65 67)
  	  	(1.3 -120 -45)(2 -70 -40)(1 -30 -70)(1.5 50 -200)(1.8 30 -80)
	  	(1.8 -220 -260)(2 -145 -260)(2.1 -50 -270)(1.8 30 -220)) 
  	  	another-water-diamond c1)

  		(pu)
  		(setpos -3000 -3000)
  		(setpos 3000 3000)
  		(draw-diamonds c2 c1 (- n 1))))
  )


(define (make-plate base-color points orient)
  (color (get-color-shade base-color orient))
  (begin_fill)
  (draw-n-gon
	`(,(car points)
	   ,(get-index 1 points)
	   ,(get-index 2 points)
	   ,(get-index 3 points)))
  (end_fill))

(define (draw-line coord1 coord2)
  (setpos (car coord1) (get-index 1 coord1))
  (pd)
  (setpos (car coord2) (get-index 1 coord2))
  (pu))

(define (draw-n-gon points)
  (define (n-gon-helper points first)
	(cond
	  ((null? points) (pu))
	  ((null? (cdr points)) (pu) (draw-line (car points) first))
	  (else
		(begin
		  (pu)
		  (draw-line (car points) (get-index 1 points))
		  (n-gon-helper (cdr points) first)))))
  (n-gon-helper points (car points)))


(define (horz-strip y-top col)
	(begin 
		(speed 10)
		(pu)
		(setpos (* -1 (/ scrn_width 2)) y-top)
		(pd)
		(seth 90)
		(color col)
		(begin_fill)
		(fd scrn_width)
		(rt 90)
		(fd stp)
		(rt 90)
		(fd scrn_width)
		(rt stp)
		(fd 10)
		(end_fill)))

(define (horz-grad y-top height col-start col-end)
	(if (= height 0) nil
		(begin 
			(define col-stp (list (/ (- (car col-end) (car col-start)) (/ height stp))
					(/ (- (get-index 1 col-end) (get-index 1 col-start)) (/ height stp))
					(/ (- (get-index 2 col-end) (get-index 2 col-start)) (/ height stp))))
			(define col (map (lambda (x) (+ (car x) (get-index 1 x))) (zip col-stp col-start)))
			(horz-strip y-top (eval (append '(rgb) col))) 
			(horz-grad (- y-top stp) (- height stp) col col-end))))

(define (h-shift-strip x-top y-top col x-step step)
  (begin 
	(speed 10)
	(pu)
	(setpos x-top y-top)
	(pd)
	(seth 90)
	(color col)
	(begin_fill)
	(fd x-step)
	(rt 90)
	(fd step)
	(rt 90)
	(fd x-step)
	(rt 90)
	(fd step)
	(end_fill)))

(define (h-shift-grad x-top y-top height col-start col-end x-step step)
  (begin
  	(if (or (< height 0) (= height 0)) nil
	  (begin 
		(define diff 6) 
		(define col-stp 
		  (list (/ (- (car col-end) (car col-start)) diff)
				(/ (- (get-index 1 col-end) (get-index 1 col-start)) diff)
				(/ (- (get-index 2 col-end) (get-index 2 col-start)) diff)))
		(define col (map (lambda (x) (+ (car x) (get-index 1 x))) (zip col-stp col-start)))
		(h-shift-strip x-top y-top (apply rgb col)
					   x-step step) 
		(h-shift-grad x-top (- y-top step) (- height step) col col-end
					  x-step step)))))

(define (v-shift delta-x delta-y start end block-color step)
  (define height 60)
  (define (v-shift-helper delta-x delta-y start end block-color step)
	  (if (> (car start) (car end)) nil
		  (begin
		  	(define relative-y (- 112.5 (get-index 1 end)))
		  	
		  	(define factor (/ relative-y 450))
		  	
		  	(define change-in-r (* (- 0.2083 0.1254) factor))
		  	(define change-in-g (* (- 0.5647 0.6980) factor))
		  	(define change-in-b (* (- 1 0.6667) factor)) 
		  	
		  	(define col-end `(,(+ 0.1254 change-in-r) 
		  					,(+ 0.6980 change-in-g) 
		  					,(+ 0.6667 change-in-b)))
			(h-shift-grad
			  (car start) (get-index 1 start)
			  height
			  block-color 
			  col-end
			  delta-x step)

			(define fn
			  (if (> (get-index 1 start) (get-index 1 end))
				  - +))

			(v-shift-helper 
			  delta-x delta-y
			  (list (+ (car start) delta-x)
					(fn (get-index 1 start) delta-y))
			  end
			  block-color
			  step)
			)))
  (v-shift-helper delta-x delta-y start end block-color step)
)



(define (draw-base-design . colors)
  (define base-color (car colors))
  (define second-color (get-index 1 colors))
  (define third-color (get-index 2 colors))
  
  (define h1 (stack `(,x ,y) 'up 6 base-color))
  (define h2 (stack h1 'up 5 third-color))
  (define s1 (stack h2 'up 3 base-color))
  
  (define s0 (stack s1 'rev-lt 4 base-color))
  (stack s0 'do 3 third-color)
  (stack s1 'rev-lt 4 base-color)

  (define long1 (stack s1 'rt 6 base-color))  
  (define long2 (stack long1 'rt 6 second-color))
  (define s2 (stack long2 'rt 2 base-color))

  (define med1 (stack s2 'lt 3 base-color))
  (define s3 (stack med1 'lt 4 second-color))

  (define almost4 (stack s3 'rev-rt 2 second-color))
  (define s4 (stack almost4 'rev-rt 1 base-color))
  (define ss4 (mask-stack almost4 'rt 1 base-color))
  (define ms0 (mask-stack ss4 'rt 2 second-color))

  (define s5 (stack 
			   (list (car s4) (+ (get-index 1 s4) block-s h-extra)) 
			   'up 6 third-color))

  (mask-stack s1 'rt 6 base-color)

  (define b1 (stack `(,(+ x block-w b-extra) ,(- y block-d h-extra)) 
					'rt 2 base-color))

  (define bb2 (stack b1 'rev-lt 2 base-color))
  (define bb3 (stack bb2 'rev-lt 4 third-color))
  (define bb4 (stack bb3 'rev-lt 2 base-color))

  (define bb6 (mask-stack bb3 'lt 5 third-color))
  (define bb7 (mask-stack bb6 'lt 2 base-color))

  (define num (* (/ 8 3) 2))

  (define reflectb0 (reflect-pt `(,x ,y) - -))
  (define reflectb1 (reflect-pt b1 nil -))
  (v-shift 10 num reflectb0 reflectb1 base-color 10)

  (define reflectb2 (reflect-pt bb2 nil -))
  (v-shift 10 num reflectb1 reflectb2 base-color 10)

  (define reflectb3 (reflect-pt bb3 + nil)) ;+ -
  (v-shift 10 num reflectb2 reflectb3 third-color 10)

  (define rb4 (reflect-pt bb4 + +))
  (define reflectb4 
	(list
	  (- (car rb4) 10)
	  (get-index 1 rb4)))
  (v-shift 10 num reflectb3 reflectb4 base-color 10)



  (define reflects0 (reflect-pt (reflect-pt almost4 - nil) - nil))
  (print reflects0)
  (define reflects1 (reflect-pt ss4 - -))
  (print reflects1)
  
  (v-shift 10 num reflects0 reflects1 base-color 10)

  (define reflects2 (reflect-pt ms0 - nil))
  (v-shift 10 num reflects1 reflects2 second-color 10)

  (define reflects3 (reflect-pt med1 + nil))
  (v-shift 10 num reflects2 reflects3 second-color 10)

  (define reflects4 (reflect-pt s2 + nil))
  (v-shift 10 num reflects3 reflects4 base-color 10)
)


(define (redraw-blocks . colors)
  (define base-color (car colors))
  (define second-color (get-index 1 colors))
  (define third-color (get-index 2 colors))

  (define h1 (stack `(,x ,y) 'up 6 base-color))
  (define h2 (stack h1 'up 5 third-color))
  (define s1 (stack h2 'up 3 base-color))
  
  (define s0 (stack s1 'rev-lt 4 base-color))
  (stack s0 'do 3 third-color)
  (stack s1 'rev-lt 4 base-color)

  (define long1 (stack s1 'rt 6 base-color))  
  (define long2 (stack long1 'rt 6 second-color))
  (define s2 (stack long2 'rt 2 base-color))

  (define med1 (stack s2 'lt 3 base-color))
  (define s3 (stack med1 'lt 4 second-color))

  (define almost4 (stack s3 'rev-rt 2 second-color))
  (define s4 (stack almost4 'rev-rt 1 base-color))
  (define ss4 (mask-stack almost4 'rt 1 base-color))
  (define ms0 (mask-stack ss4 'rt 2 second-color))

  (define s5 (stack 
			   (list (car s4) (+ (get-index 1 s4) block-s h-extra)) 
			   'up 6 third-color))

  (mask-stack s1 'rt 6 base-color)

  (define b1 (stack `(,(+ x block-w b-extra) ,(- y block-d h-extra)) 
					'rt 2 base-color))

  (define bb2 (stack b1 'rev-lt 2 base-color))
  (define bb3 (stack bb2 'rev-lt 4 third-color))
  (define bb4 (stack bb3 'rev-lt 2 base-color))

  (define bb6 (mask-stack bb3 'lt 5 third-color))
  (define bb7 (mask-stack bb6 'lt 2 base-color))
)  


(define (reflect-pt orig x-fn y-fn)
  (define x
	(if (null? x-fn) (car orig) (x-fn (car orig) block-w b-extra)))
  (define y
	(if (null? y-fn) (get-index 1 orig) (y-fn (get-index 1 orig) block-d h-extra)))
  `(,x ,y))


(define x -170)
(define y -200)

(define global-side 30) 
(define global-width 50) 
(define global-height 55) 

(define block-s global-side)
(define block-w (/ global-width 2))
(define block-d (/ (- global-height global-side) 2))

(define b-extra 1.5)
(define h-extra 2)


(define (mask-stack center direction n base-color)
  (define (mask-stack-helper center direction n)
	(if 
	  (= n 0) center
	  (let 
		((x (get-index 0 center))
		 (y (get-index 1 center)))
		(let
		  ((new-center 
			 (cond
			   ((eq? direction 'lt) 
				(list (- x block-w b-extra) (- y block-d b-extra)))
			   ((eq? direction 'rev-lt)
				(list (+ x block-w b-extra) (+ y block-d b-extra)))
			   ((eq? direction 'rt) 
				(list (+ x block-w b-extra) (- y block-d b-extra)))
			   ((eq? direction 'rev-rt)
				(list (- x block-w b-extra) (+ y block-d b-extra)))
			   ((eq? direction 'up) 
				(list x (+ y block-s h-extra)))
			   ((eq? direction 'do)
				(list x (- y block-s h-extra))))))
		  (mask center direction base-color)
		  (mask-stack-helper new-center direction (- n 1))))))
  (cond
	((eq? direction 'rev-rt)
	 (define c (mask-stack-helper center 'rev-rt n))
	 (mask-stack-helper c 'rt (+ n 1))
	 c)
	((eq? direction 'rev-lt)
	 (define c (mask-stack-helper center 'rev-lt n))
	 (mask-stack-helper c 'lt (+ n 1))
	 c)
	((eq? direction 'do)
	 (define c (mask-stack-helper center 'do n))
	 (mask-stack-helper c 'up (+ n 1))
	 c)
	(else (mask-stack-helper center direction n))))

; regular version of stack
(define (stack center direction n base-color)
  (define (stack-helper center direction n)
	(if 
	  (= n 0) center
	  (let 
		((x (get-index 0 center))
		 (y (get-index 1 center)))
		(let
		  ((new-center 
			 (cond
			   ((eq? direction 'lt) 
				(list (- x block-w b-extra) (- y block-d b-extra)))
			   ((eq? direction 'rev-lt)
				(list (+ x block-w b-extra) (+ y block-d b-extra)))
			   ((eq? direction 'rt) 
				(list (+ x block-w b-extra) (- y block-d b-extra)))
			   ((eq? direction 'rev-rt)
				(list (- x block-w b-extra) (+ y block-d b-extra)))
			   ((eq? direction 'up) 
				(list x (+ y block-s h-extra)))
			   ((eq? direction 'do)
				(list x (- y block-s h-extra))))))
		  (mask center 'norm base-color)
		  (stack-helper new-center direction (- n 1))))))
  (cond
	((eq? direction 'rev-rt)
	 (define c (stack-helper center 'rev-rt n))
	 (stack c 'rt (+ n 1) base-color)
	 c)
	((eq? direction 'rev-lt)
	 (define c (stack-helper center 'rev-lt n))
	 (stack c 'lt (+ n 1) base-color)
	 c)
	((eq? direction 'do)
	 (define c (stack-helper center 'do n))
	 (stack c 'up (+ n 1) base-color)
	 c)
	(else (stack-helper center direction n))))

(define (mask center direction base-color)
  (define depth 1)
  (define pgram-args (size-cube center depth 0))
  (define points (apply cube-pgram pgram-args))

  (define start (car points))

  (define top
	`(,start
	   ,(get-index 1 points)
	   ,(get-index 2 points)
	   ,(get-index 3 points)))

  (define rest (cdr (cdr (cdr points))))
  (define left
	`(,start
	   ,(car rest)
	   ,(get-index 1 rest)
	   ,(get-index 2 rest)))

  (define rest (cdr (cdr rest)))
  (define right
	`(,start
	   ,(car rest)
	   ,(get-index 1 rest)
	   ,(get-index 1 points)))

  (cond
	((or (eq? direction 'rt) (eq? direction 'rev-rt))
	 (plate-iter base-color (list top left) '(1 3)))
	((or (eq? direction 'lt) (eq? direction 'rev-lt))
	 (plate-iter base-color (list top right) '(1 2)))
	((or (eq? direction 'up) (eq? direction 'do))
	 (plate-iter base-color (list left right) '(3 2)))
	(else (plate-iter base-color (list top left right) '(1 3 2))))
)

(define (size-cube center depth t)
  (let
	((width global-width)
	 (height global-height)
	 (side global-side))
	(list
	  center
	  (+ (/ width depth) (* t 0.8))
	  (+ (/ height depth) t)
	  (+ (/ side depth) (/ t 3)))))


(define (plate-iter base-color curr-pts orient)
  (if (null? curr-pts) nil
	  (begin
		(make-plate base-color (car curr-pts) (car orient))
		(plate-iter base-color (cdr curr-pts) (cdr orient)))))


(define (cube-pgram center width height side)
  (let 
	((x (car center))
	 (y (car (cdr center)))
	 (h (/ height 2))
	 (w (/ width 2))
	 (s (/ side 2)))
	(let
	  ((top (+ y h))
	   (bottom (- y h))
	   (left (- x w))
	   (right (+ x w))
	   (side-upper (+ y s))
	   (side-lower (- y s)))

	  (list
		`(,x ,(+ side-upper s (* -1 h)))
		`(,right ,side-upper)
		`(,x ,top)		
		`(,left ,side-upper)
		`(,left ,side-lower)
		`(,x ,bottom)
		`(,right ,side-lower)))))



(draw)




