;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: BALLS BALLS BALLS
;;;
;;; Description:
;;;    Take a careful look.
;;;    This piece features many balls.
;;;    Hmm, yes. Balls balls balls.

;; TODO:
;; Different shapes
;; Different materials (i.e. textures for objects, transparency)
;; Multiple light sources
;; rotate camera
;; Fractals?

;; UTILITY --------------------------------------------------------------------

; Gets element of lst at index. Pretty simple. Also tail recursive!
; I like this better than writing the 50th cadddddddddddr function
(define (elem lst index)
  (cond
    ((null? lst)    nil)
    ((zero? index)  (car lst))
    (else           (elem (cdr lst) (- index 1)))))

; base ^ power, tail recursive!
(define (exp base power)
  (define (helper total n)
    (if (zero? n)
      total
      (helper (* base total) (- n 1))))
  (helper 1 power))

; Combines two n-length lists representing colors c1 c2
; with a 2 argument function f such that: 
; --> (f(c1[0], c2[0]), f(c1[1], c2[1]), ... f(c1[n], c2[n])) tail recursive!
(define (combine-v c1 c2 f)
  (define (helper l1 l2 total)
    (if (or (null? l1) (null? l2))
        total
        (helper (cdr l1)
                (cdr l2)
                (append total (list (f (car l1) (car l2)))))))
  (helper c1 c2 nil))

; Vector operations
(define (add-v c1 c2) (combine-v c1 c2 +))
(define (sub-v c1 c2) (combine-v c1 c2 -))
(define (dot-v c1 c2) (reduce + (combine-v c1 c2 *)))
(define (scale v k)   (map (lambda (x) (* x k)) v))

; Comparisons

; Compares all elements of lst using two element predicate compare
(define (extrema compare lst)
  (cond 
    ((null? lst)        nil)
    ((null? (cdr lst))  (car lst))
    (else
     (begin
      (define extrem-rest (extrema compare (cdr lst)))
      (if (compare extrem-rest (car lst))
          extrem-rest
          (car lst))))))

(define (max lst) (extrema > lst))
(define (min lst) (extrema < lst))

; Finds minimum of a list of lists by comparing first element
(define (min-first zipped)
  (extrema (lambda (x y) (< (car x) (car y))) zipped))

; Sphere data abstraction
(define (col-s sphere) (elem sphere 2))
(define (pos-s sphere) (elem sphere 1))
(define (rad-s sphere) (elem sphere 0))

; Normalizes vector, computing unit vector with same direction
(define (normalize v) (scale v (/ 1 (sqrt (dot-v v v)))))

; Range of all natural numbers in interval [start, end) (tail recursive!)
(define (range start end)
  (define (helper total current)
    (if (< current start)
        total
        (helper (cons current total) (- current 1))))
  (helper nil (- end 1)))

;; MACROS ---------------------------------------------------------------------
(define-macro (for sym in vals expr) `(map (lambda (,sym) ,expr) ,vals))

; List comprehension
(define-macro (list-of map-expr for var in lst if filter-expr)
 `(map (lambda (,var) ,map-expr)
       (filter (lambda (,var) ,filter-expr) ,lst)))

;; VARIABLES ------------------------------------------------------------------
(define camera  '(1.025 1.3 -4))
(define light   '(1 1 -4))
(define AMBIENT 0.3)
(define BLOCK  1)
(define MARGIN  10)

;TEMPORARY FUNCTION - JUST ME SCREWING AROUND
;positive z is away from the camera
; NEED TO FIND A WAY TO RECURSIVELY FIND ALL POINTS
(define (generate-spheres-cube floor-rad rad)
  `((,rad (0 0 0) (   1    0    0))
    (,rad (0 0 1) (   1 .111    0))
    (,rad (0 0 2) (   1 .222    0))
    (,rad (0 1 0) (   1 .333    0))
    (,rad (0 1 1) (   1 .444    0))
    (,rad (0 1 2) (   1 .555    0))
    (,rad (0 2 0) (   1 .666    0))
    (,rad (0 2 1) (   1 .777    0))
    (,rad (0 2 2) (   1 .888    0))
    (,rad (1 0 0) (   0    1    0))
    (,rad (1 0 1) (   0    1 .111))
    (,rad (1 0 2) (   0    1 .222))
    (,rad (1 2 0) (   0    1 .666))
    (,rad (1 2 1) (   0    1 .777))
    (,rad (1 2 2) (   0    1 .888))
    (,rad (2 0 0) (   0    0    1))
    (,rad (2 0 1) (.111    0    1))
    (,rad (2 0 2) (.222    0    1))
    (,rad (2 1 0) (.333    0    1))
    (,rad (2 1 1) (.444    0    1))
    (,rad (2 1 2) (.555    0    1))
    (,rad (2 2 0) (.666    0    1))
    (,rad (2 2 1) (.777    0    1))
    (,rad (2 2 2) (.888    0    1))

    ;find some way to recursively generate this
    (,rad (0 0 3) (   1    0    0))
    (,rad (0 0 4) (   1 .111    0))
    (,rad (0 0 5) (   1 .222    0))
    (,rad (0 1 3) (   1 .333    0))
    (,rad (0 1 4) (   1 .444    0))
    (,rad (0 1 5) (   1 .555    0))
    (,rad (0 2 3) (   1 .666    0))
    (,rad (0 2 4) (   1 .777    0))
    (,rad (0 2 5) (   1 .888    0))
    (,rad (1 0 3) (   0    1    0))
    (,rad (1 0 4) (   0    1 .111))
    (,rad (1 0 5) (   0    1 .222))
    (,rad (1 2 3) (   0    1 .666))
    (,rad (1 2 4) (   0    1 .777))
    (,rad (1 2 5) (   0    1 .888))
    (,rad (2 0 3) (   0    0    1))
    (,rad (2 0 4) (.111    0    1))
    (,rad (2 0 5) (.222    0    1))
    (,rad (2 1 3) (.333    0    1))
    (,rad (2 1 4) (.444    0    1))
    (,rad (2 1 5) (.555    0    1))
    (,rad (2 2 3) (.666    0    1))
    (,rad (2 2 4) (.777    0    1))
    (,rad (2 2 5) (.888    0    1))


    ;floor and ceiling
    (,floor-rad (0 ,(- floor-rad rad) 0)   (1 1 1))
    (,floor-rad (0 ,(+ floor-rad (* rad 6)) 0)   (1 1 1))))

; List of sphere data abstractions: (radius, (x, y, z), (r, g, b))
(define spheres (generate-spheres-cube 5000 .5))

;; DRAW -----------------------------------------------------------------------

; Mix colors A and B with ratio R
(define (mix a b r)
  (add-v (scale a r) (scale b (- 1 r))))

; Return distances from source in direction to reach sphere
(define (intersect source direction sphere min-distance)
  (define v   (sub-v source (pos-s sphere)))
  (define b   (* -1 (dot-v v direction)))
  (define d2  (+ (- (* b b) (dot-v v v)) (* (rad-s sphere) (rad-s sphere))))
  (cond
    ((<= d2 0)                        #f) ; #f means failed to hit anything
    ((> (- b (sqrt d2)) min-distance) (- b (sqrt d2)))
    ((> (+ b (sqrt d2)) min-distance) (+ b (sqrt d2)))
    (else                             #f)))

; Return the illumination of the pixel at a surface point
(define (illumination surface center col direction depth)
  (define to-surface (normalize (sub-v surface center)))
  (define to-light   (normalize (sub-v light center)))
  (define intensity  (max (list AMBIENT (dot-v to-light to-surface))))
  (define direct     (scale col intensity))
  (if (= depth 1)
    direct
    (begin
      (define cosine    (dot-v direction to-surface))
      (define bounce    (sub-v direction (scale to-surface (* 2 cosine))))
      (define reflected (trace-ray surface bounce (- depth 1)))
      (mix direct reflected (+ .5 (* .5 (exp intensity 30)))))))

; Returns all distance-sphere pairs for all valid hits
(define (hits list-d list-s)
  (cond 
    ((or (null? list-d) (null? list-s))
     nil)
    ((not (car list-d))
     (hits (cdr list-d) (cdr list-s)))
    (else
     (cons (list (car list-d) (car list-s))
           (hits (cdr list-d) (cdr list-s))))))

; Return the color of the pixel from source in direction
(define (trace-ray source direction depth)
  (define distances
    (list-of (intersect source direction s .01) for s in spheres if #t))
  (define hit-list (hits distances spheres))  ;haha hitlist
  (if (null? hit-list)
    (list 0 0 0)
    (begin
      (define closest-hit (min-first hit-list))
      (define surface     (add-v source (scale direction (car closest-hit))))
      (illumination surface 
                    (pos-s (elem closest-hit 1))
                    (col-s (elem closest-hit 1))
                    direction
                    depth))))

; Renders all ray-traced pixels in (x-list, y-list) tail recursive!
(define (render-ray-trace x-list y-list size)
  (define (render-along-x xl)
    (if (null? xl)
      nil
      (begin
        (define x (car xl))
        (define y (car y-list))
        (define direction (normalize 
          (list (- (/ x size) .5) (- (/ y size) .5) 1)))
        (define col (scale (trace-ray camera direction 2) .95))
        (pixelsize BLOCK)
        (pixel x y  (rgb
                     (max (list 0 (elem col 0)))
                     (max (list 0 (elem col 1)))
                     (max (list 0 (elem col 2)))))
        (render-along-x (cdr xl)))))
  (if (or (null? x-list) (null? y-list))
    nil
    (begin
      (render-along-x x-list)
      (print (list "y: " (* BLOCK (car y-list))))
      (render-ray-trace x-list (cdr y-list) size))))

(define (draw)
  (bgcolor "black")
  (define size (max (list   ; should this be defined elsewhere?
                      (quotient (screen_width) BLOCK) 
                      (quotient (screen_height) BLOCK))))
  (define screen-range (range (- 0 MARGIN) (+ size MARGIN)))
  (render-ray-trace screen-range screen-range size)
  (exitonclick))

; Please leave this last line alone.  You may add additional procedures above
; this line.
(draw)