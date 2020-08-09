;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: Leaves Within Leaves
;;;
;;; Description:
;;;   < Look at these green leaves,
;;;     Why are they so similar?
;;;     Oh my, a fractal!>

(define min_x -4.5)
(define min_y 0)
(define max_x 5.5)
(define max_y 10)
(define max_iterations 90000)
(define img_width 400)
(define img_height 400)

(define xgap (/ (- max_x min_x) img_width))
(define ygap (/ (- max_y min_y) img_height))
(define px0 (floor (/ (- (screen_width) img_width) 2)))
(define py0 (floor (/ (- (screen_height) img_height) 2)))
(define ra 69069)
(define rc 1)
(define rm (expt 2 32))
(define (transform a b c x y) (+ (* a x) (* b y) c))
(define (drawfern x y num_iter rand)
  (if (< num_iter max_iterations)
    (begin
	  (if (= (modulo num_iter 1000) 0) (print num_iter))
	  (define rand (modulo (+ (* rand ra) rc) rm))
	  (define rnd (/ rand rm))
	  (cond ((< rnd 0.01)
			  (define x2 (transform  0.00  0.00  0.00 x y))
			  (define y2 (transform  0.00  0.16  0.00 x y))
			)
			((< rnd 0.86)
			  (define x2 (transform  0.85  0.04  0.00 x y))
			  (define y2 (transform -0.04  0.85  1.60 x y))
			)
			((< rnd 0.93)
			  (define x2 (transform  0.20 -0.26  0.00 x y))
			  (define y2 (transform  0.23  0.22  1.60 x y))
			)
			(else 
			  (define x2 (transform -0.15  0.28  0.00 x y))
			  (define y2 (transform  0.26  0.24  0.40 x y))
			)
	  )
	  (begin_fill)
	  (pixel (+ px0 (floor (/ (- x2 min_x) xgap))) 
		(+ py0 (- img_height (floor (/ (- y2 min_y) ygap)))) "green")
	  (end_fill)
	  (drawfern x2 y2 (+ num_iter 1) rand)
    )
  )
)

(define (draw)
  (bgcolor (rgb 0.85 0.8 0.98))
  (hideturtle)
  (drawfern (/ (+ min_x max_x) 2.0) min_y 0 201911231120)
  (exitonclick))
; Please leave this last line alone.  You may add additional procedures above
; this line.
(draw)