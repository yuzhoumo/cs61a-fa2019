;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: Nice bridge you got there, California!
;;;
;;; Description:
;;;    Would be a shame if
;;;    Something were to...happen to it....
;;;    – Love, PG&E
(define (draw)
  
  ; BACKGROUND

  (hideturtle)
  (speed 0)

  (define (skew x a b)
    (* a x x (+ 1 (expt b x))))

  (define (slant x a b)
    (lambda (curr) (* 0.25 (skew x a b) (- 5 (/ curr x)))))

  (define (bound w curr n)
    (+ n (/ w 160)))

  (define (my-eq? x y exp)
    (if (< (abs (- x y)) (expt 10 exp)) #t #f))

  (define (move-pen x y)
    (penup)
    (setposition x y)
    (pendown))

  (define (vertical x1 y1 h)
    (move-pen (- x1 320) (- y1 300))
    (fd h))

  (define (horizontal x1 y1 w)
    (move-pen (- x1 320) (- y1 300))
    (rt 90)
    (fd w)
    (lt 90))

  (define (rect x1 y1 x2 y2)
    (define h (- y2 y1))
    (define (rect-helper currY r g b)
      (color (rgb r g b))
      (horizontal x1 currY (- x2 x1))
      (if (< currY y2)
        (rect-helper (+ 1 currY) (- r (/ h)) (- g (/ 0.8 h)) (+ b (/ h)))))
    (rect-helper y1 1 0.8 0))
  (rect 10 10 570 415)
  
  ; STARS

  (color "white")

  (define (star x y)
    (horizontal (- x 2) y 4)
    (vertical x (- y 2) 4))
  (star 380 395)
  (star 470 400)
  (star 300 350)
  (star 160 380)
  (star 40 392)
  (star 430 280)
  (star 310 250)
  (star 150 300)

  ; BRIDGE

  (color "red")

  (define (draw-p x0 y0 w h forwards)
  	(define x (/ w 4))
    (define b (expt 2.71828 (- (/ 1.28001954 x))))
  	(define a (/ (/ h 2) (* (* x x) (+ 1 (expt 2.71828 1.28001954)))))
    (define top-slant (slant (- x) a b))
    (define half-slant (slant (/ (- x) 1.5) a b))
    (if forwards
      (begin
        (define b (expt 2.71828 (/ 1.28001954 x)))
        (define top-slant (slant x a b))
        (define half-slant (slant (/ x 1.5) a b))))

  	(define (draw-helper x0 y0 curr)
  		(define h (skew curr a b))
      (if forwards
        (define lim x)
        (define lim (* x 3)))
  		(if (< curr lim)
  			(begin
  				(pixel (floor x0) (floor (+ y0 h)) "red")
          (if (my-eq? h (top-slant curr) (bound w curr 0.5))
            (vertical x0 y0 h))
          (if (my-eq? h (half-slant curr) (bound w curr 0.075))
            (vertical x0 y0 h))
  				(draw-helper (+ 0.1 x0) y0 (+ 0.1 curr)))))
    
    (horizontal x0 y0 w)
    (if forwards
      (draw-helper x0 y0 (- (* 3 x)))
      (draw-helper x0 y0 (- x))))

  (define (recurse x0 y0 w0 h0 factor i forwards)
  	(if (> i 0)
  		(begin
  			(draw-p x0 y0 w0 h0 forwards)
        (if forwards
  			 (recurse (+ x0 w0) y0 (* factor w0) (* factor h0) factor (- i 1) #t)
         (recurse (- x0 (* factor w0)) y0 (* factor w0) (* factor h0) factor (- i 1) #f)))))

  (recurse 10 10 170 405 0.4 5 #t)
  (recurse 400 10 170 405 0.4 5 #f)
  (exitonclick))

(draw)