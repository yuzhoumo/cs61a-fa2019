;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: The Great Rhombicosidodecahedron
;;;
;;; Description:
;;;   One begets Two,
;;;    Two beget Three,
;;;    and Three beget everything.

; The code generates several polyhedrons with various shapes and orientations over the screen.
; The structure of polyhedrons are calculated on-the-fly by Wythoff construction.
; This contains some interesting application of abstract algebra and group theory,
; which is the coset enumeration problem (a simplified algorithm is implemented here).
; Rendering 3D objects follows the following architecture:
; 1. All surfaces are splitted into triangles.
; 2. Shading is computed on every vertecies of those triangles.
; 3. Transform the coordinates into the camera world.
; 4. Resolve the ordering of objects (to paint closer triangles last).
; 5. Rasterization.
; Hope that we can have time to optimize the code size even more,
; but given the amount of logic included, it is still slightly crossing the maximum tokens limit.
; Sorry about that.

; World parameters
; (define image-size '(200 200))
(define light-source '(4 0 10))
(define eye '(2 4 2))

; Some general helper functions and values
(define pi 3.14159265)

(define (cadr x) (car (cdr x)))
(define (caddr x) (car (cdr (cdr x))))

(define (min a b) (if (< a b) a b))
(define (max a b) (if (> a b) a b))

(define (cut lst)
	(if (null? (cdr lst)) nil
	    (cons (car lst) (cut (cdr lst)))))

(define (last lst)
	(if (null? (cdr lst))
	    (car lst)
	    (last (cdr lst))))

(define (reverse lst)
	(if (null? lst) nil
	    (append (reverse (cdr lst)) (list (car lst)))))

(define (repeat lst n)
	(if (zero? n) nil
	(append lst (repeat lst (- n 1)))))

(define (in? ele lst)
	(cond ((null? lst) #f)
	      ((eq? ele (car lst)) #t)
	      (else (in? ele (cdr lst)))))

(define (unique chk lst)
	(cond ((null? lst) nil)
	      ((chk (car lst) (cdr lst)) (unique chk (cdr lst)))
	      (else (cons (car lst) (unique chk (cdr lst))))))

(define (remove-first ele lst)
	(define (remove-first-helper acc ele lst)
		(cond ((null? lst) acc)
		      ((equal? ele (car lst)) (append acc (cdr lst)))
		      (else (remove-first-helper (append acc (list (car lst))) ele (cdr lst)))))
	(remove-first-helper '() ele lst))

(define (min-by key lst)
	(define (min-by-helper best key lst)
		(cond ((null? lst) best)
		      ((< (key (car lst)) (key best)) (min-by-helper (car lst) key (cdr lst)))
		      (else (min-by-helper best key (cdr lst)))))
	(min-by-helper (car lst) key (cdr lst)))

(define (sort key lst)
	(define (sort-helper sorted key lst)
		(if (null? lst) sorted
		    (begin (define bottom (min-by key lst))
		           (sort-helper (append sorted (list bottom)) key (remove-first bottom lst)))))
	(sort-helper '() key lst))

(define (zip (variadic lsts))
	(if (null? (car lsts)) nil
	    (cons (map car lsts) (apply zip (map cdr lsts)))))

(define (mmap op (variadic lsts))
	(map (lambda (eles) (apply op eles)) (apply zip lsts)))

(define (combinations lst n)
	(cond ((zero? n) '(()))
	      ((= (length lst) n) (list lst))
	      (else (append (map (lambda (l) (cons (car lst) l)) (combinations (cdr lst) (- n 1)))
	    	            (combinations (cdr lst) n)))))

(define (enumerate lst)
	(define (enumerate-helper lst n)
		(if (null? lst) nil
		    (cons (list n (car lst)) (enumerate-helper (cdr lst) (+ n 1)))))
	(enumerate-helper lst 0))

(define (range n)
	(define (range-helper k)
		(if (zero? k) nil (cons (- n k) (range-helper (- k 1)))))
	(range-helper n))

(define (nth lst n) (if (zero? n) (car lst) (nth (cdr lst) (- n 1))))
(define (index n-lst (variadic indecies)) (reduce nth (cons n-lst indecies)))
(define (set-nth! lst e n) (if (zero? n) (set-car! lst e) (set-nth! (cdr lst) e (- n 1))))
(define (set-at! n-lst e (variadic indecies)) (set-nth! (apply index (cons n-lst (cut indecies))) e (last indecies)))
(define (extend! lst-1 lst-2)
	(if (null? (cdr lst-1))
	    (set-cdr! lst-1 lst-2)
	    (extend! (cdr lst-1) lst-2)))

(define (rotation-z angle)
	`((,(cos angle) ,(- (sin angle)) 0)
	  (,(sin angle) ,(cos angle) 0)
	  (0 0 1)))

; The Todd Coxeter algorithm for coset enumeration.
; Coset enumeration help us identify all the combinations of reflections
; that would send the initial vertex to a different place,
; no more, and no less.
(define (todd-coxeter gens sub-gens rels)
	; Shared state, the necessary evil.
	(define table (list (repeat '(-1) (length gens))))
	(define (undefined? coset gen) (= -1 (index table coset gen)))
	(define (new-coset coset gen)
		(define n (length table))
		(extend! table (list (repeat '(-1) (length gens))))
		(set-at! table n coset gen)
		(set-at! table coset n gen))

	(define (scan-fill coset word)
		(define back (- (length word) 1))
		(define (helper i j f b)
			(define foreward (scan + i j f))
			(define i (car foreward)) (define f (cadr foreward))
			(define backward (scan - j i b))
			(define j (car backward)) (define b (cadr backward))
			(cond ((> i j) nil)
			      ((= i j) (begin (set-at! table b f (index word i))
			                      (set-at! table f b (index word i))))
			      (else (begin (new-coset f (index word i))
			      	           (helper i j f b)))))
		(define (scan dir i j coset)
			(if (or (< (* (- j i) (dir 1)) 0)
			        (undefined? coset (nth word i)))
			    (list i coset)
			    (scan dir (dir i 1) j (index table coset (nth word i)))))
		(helper 0 back coset coset))

	(map (lambda (word) (scan-fill 0 word)) sub-gens)
	(define (build-coset-table current)
		(if (>= current (length table)) nil
		    (begin (map (lambda (word) (scan-fill current word)) rels)
		           (map (lambda (gen) (new-coset current gen)) (filter (lambda (gen) (undefined? current gen)) gens))
		           (build-coset-table (+ 1 current)))))
	(build-coset-table 0)
	(define result (repeat '(-1) (length table)))
	(set-car! result nil)
	(define (get-words queue)
		(if (null? queue) nil
		    (begin (define coset (car queue))
		           (get-words (append (cdr queue)
		           	                  (map (lambda (gen) (define higher-coset (index table coset gen))
		                                                 (set-at! result (append (index result coset) (list gen)) higher-coset)
		                                                 higher-coset)
		                                   (filter (lambda (gen) (equal? -1 (index result (index table coset gen)))) gens)))))))
	(get-words '(0))
	(list result table))

(define (coxeter-matrix a b c)
	(list (list 1 a b)
	      (list a 1 c)
	      (list b c 1)))
(define (find-mirrors a b c)
	(define m (matrix-mmap (lambda (e) (- (cos (/ pi e)))) (coxeter-matrix a b c)))
	(define n10 (index m 0 1))
	(define n11 (sqrt (- 1 (* n10 n10))))
	(define n20 (index m 0 2))
	(define n21 (/ (- (index m 1 2) (* n10 n20)) n11))
	(define n22 (sqrt (- 1 (* n20 n20) (* n21 n21))))
	(list (list 1 0 0)
	      (list n10 n11 0)
	      (list n20 n21 n22)))
(define (reflection-matrix n)
	(matrix-mmap - '((1 0 0) (0 1 0) (0 0 1))
	               (matrix-mmap (lambda (e) (* 2 e)) (outer-product n n))))
(define (init-point mirrors truncs)
	(normalize (solver (mmap (lambda (mirror trunc) (append mirror (list trunc))) mirrors truncs))))

; Build the polyhedron given the placement of mirrors and initial vertex (also the surface color).
(define (build-polyhedron c-matrix mirrors truncs color)
	(define reflections (map reflection-matrix mirrors))
	(define initial (init-point mirrors truncs))
	(define (active? mirror) (> (index truncs mirror) 0))
	(define generators '(0 1 2))
	(define relators
		(map (lambda (gens) (repeat gens (apply index (cons c-matrix gens)))) (combinations generators 2)))
	(define (trans point word)
		(reduce (lambda (point gen) (index (cadr vertex-words) point gen)) (cons point word)))
	(define vertex-words
		(begin (define sub-gens (map (lambda (pair) (list (car pair))) (filter (lambda (pair) (zero? (cadr pair))) (enumerate truncs))))
		       (todd-coxeter generators sub-gens relators)))
	(define vertecies
		(map (lambda (word) (reduce transform (cons initial (map (lambda (w) (index reflections w)) word)))) (car vertex-words)))
	(define (check-duplicate f lst)
		(define (check-duplicate-helper n f lst)
			(cond ((zero? n) #f)
			      ((or (in? f lst) (in? (reverse f) lst)) #t)
			      (else (check-duplicate-helper (- n 1) (append (cdr f) (list (car f))) lst))))
		(check-duplicate-helper (length f) f lst))
	(define (split face)
		(define common (car face))
		(define (split-helper points)
			(if (eq? (length points) 2)
			    (list (cons common points))
			    (cons (list common (car points) (cadr points)) (split-helper (cdr points)))))
		(split-helper (cdr face)))
	(define (correct-order face)
		(define (correct-order-helper point face)
			(if (null? face) nil
			    (begin (define closest (min-by (lambda (face-point) (normal (vector-sub face-point point))) face))
			           (cons closest (correct-order-helper closest (remove-first closest face))))))
		(correct-order-helper (car face) face))
	(define (correct-orientation face) (if (< (dot-product (normal-vector face) (car face)) 0) (reverse face) face))
	(define (colorize triangles color)
		(map (lambda (triangle) (map (lambda (point) (list point color)) triangle)) triangles))
	(define faces-list
		(begin (define (run sub-gens fs)
		       	(define face-words (car (todd-coxeter generators sub-gens relators)))
		       	(define face-indicies (unique check-duplicate (map (lambda (word) (map (lambda (p) (trans p word)) fs)) face-words)))
		       	(map (lambda (face) (map (lambda (i) (index vertecies i)) face)) face-indicies))
		       (map (lambda (gens)
		       	            (define i (car gens))
		       	            (define j (cadr gens))
		       	            (define m (index c-matrix i j))
		       	            (cond ((and (active? i) (active? j))
		       	                   (run (list (list i j))
		       	                        (reduce append (map (lambda (k) (list (trans 0 (repeat (list i j) k)) (trans 0 (cons j (repeat (list i j) k))))) (range m)))))
		       	                  ((or (and (active? i) (> m 2)) (and (active? j) (> m 2)))
		       	                  	(run (list (list i) (list j)) (reduce append (map (lambda (k) (list (trans 0 (repeat (list i j) k)))) (range m)))))
		       	                  (else nil)))
		            (combinations generators 2))))
	(reduce append (map (lambda (faces) (reduce append (map (lambda (face) (colorize (split (correct-orientation (correct-order face))) color)) faces)))
		                (filter (lambda (faces) (not (null? faces))) faces-list))))

; Some helper function on matrix and vector manipulation
(define (solver matrix)
	(define (elim matrix)
		(if (null? (cdr matrix)) matrix
		    (cons (car matrix)
		          (map (lambda (row) (cons 0 row))
                       (elim (map (lambda (row)
                       	            	(cdr (mmap - (map (lambda (e) (* (car (car matrix)) e)) row)
                       	            	             (map (lambda (e) (* (car row) e)) (car matrix)))))
                                    (cdr matrix)))))))
	(define (flip matrix)
		(reverse (map (lambda (row) (append (reverse (cut row)) (list (last row)))) matrix)))
	(define (pivot row) (if (zero? (car row)) (pivot (cdr row)) (car row)))
	(reverse (map (lambda (row) (/ (last row) (pivot row)))
		      (elim (flip (elim matrix))))))

(define (matrix-mul matrix-1 matrix-2)
	(map (lambda (row)
		 	(map (lambda (column) (dot-product row column)) (apply zip matrix-2)))
	     matrix-1))
(define (matrix-multiply (variadic matrices)) (reduce matrix-mul matrices))

(define (matrix-mmap f (variadic matrices))
	(apply mmap (cons (lambda ((variadic rows)) (apply mmap (cons f rows))) matrices)))

(define (transform vector matrix)
	(map (lambda (row) (dot-product row vector)) matrix))

(define (scalar-mul scalar vector)
	(map (lambda (comp) (* comp scalar)) vector))

(define (dot-product vector-1 vector-2)
	(reduce + (mmap * vector-1 vector-2)))

(define (cross-product vector-1 vector-2)
	(define (helper op-1 op-2)
		(- (* (op-1 vector-1) (op-2 vector-2))
		   (* (op-1 vector-2) (op-2 vector-1))))
	(mmap helper (list cadr caddr car) (list caddr car cadr)))

(define (outer-product vector-1 vector-2)
	(map (lambda (comp) (scalar-mul comp vector-2)) vector-1))

(define (vector-sub vector-1 vector-2)
	(mmap - vector-1 vector-2))

(define (vector-add vector-1 vector-2)
	(mmap + vector-1 vector-2))

(define (normal vector) (sqrt (apply + (map (lambda (comp) (* comp comp)) vector))))

(define (normalize vector)
	(map (lambda (comp) (/ comp (normal vector))) vector))

(define (positions triangle) (map car triangle))
(define (colors triangle) (map cadr triangle))

(define (normal-vector positions)
	(normalize (cross-product (vector-sub (cadr positions) (car positions))
	                          (vector-sub (caddr positions) (car positions)))))

; (define (view-port w h)
; 		(list (list (/ w 2) 0 0 (/ (- w 1) 2))
; 		      (list 0 (/ h 2) 0 (/ (- h 1) 2))
; 		      (list 0 0 1 0)
; 		      (list 0 0 0 1)))

; (define (orthographic l r b t n f)
; 	(list (list (/ 2 (- r l)) 0 0 (/ (- (+ r l)) (- r l)))
; 	      (list 0 (/ 2 (- t b)) 0 (/ (- (+ t b)) (- t b)))
; 	      (list 0 0 (/ 2 (- n f)) (/ (- (+ n f)) (- n f)))
; 	      (list 0 0 0 1)))

; (define (camera e g t)
; 	(define w (map - (normalize g)))
; 	(define u (normalize (cross-product t w)))
; 	(define v (cross-product w u))
; 	(matrix-multiply (list (append u '(0)) (append v '(0)) (append w '(0)) (list 0 0 0 1))
; 	                 (list (list 1 0 0 (- (car e)))
; 	                       (list 0 1 0 (- (cadr e)))
; 	                       (list 0 0 1 (- (caddr e)))
; 	                       (list 0 0 0 1))))

; (define (perspective n f)
; 	(list (list n 0 0 0)
; 	      (list 0 n 0 0)
; 	      (list 0 0 (+ n f) (* (- f) n))
; 	      (list 0 0 1 0)))

; (define transition
; 	(matrix-multiply (apply view-port image-size)
; 	                 (orthographic -4 4 -4 4 -6 -10)
; 	                 (perspective -6 -10)
; 	                 (camera eye (map - eye) '(0 0 1))))

; The transition matrix is generated from the above code
(define transition '((174.78478355114174 14.159370477315065 40.620704901154376 -487.4484588138525) (68.00683277641268 136.01366555282536 -96.30993447513717 -487.4484588138525) (-1.6329931618554523 -3.2659863237109046 -1.6329931618554523 -10.404082057734573) (0.4082482904638631 0.8164965809277261 0.4082482904638631 -4.898979485566357)))

(define (canonicalize vector)
	(define w (last vector))
	(if (null? (cdr vector)) nil
	    (cons (/ (car vector) w) (canonicalize (cdr vector)))))

(define (projection triangle)
	(map (lambda (vertex) (cons (canonicalize (transform (append (car vertex) '(1)) transition)) (cdr vertex))) triangle))

(define (shade-triangle triangle)
	(map (lambda (vertex)
	     	(define point (car vertex))
	     	(define n (normal-vector (positions triangle)))
	     	(define l (normalize (vector-sub light-source point)))
	     	(define r (vector-sub (scalar-mul (* 2 (dot-product l n)) n) l))
	     	(define e (normalize (vector-sub eye point)))	
	     	(define diffusion (+ 0.3 (* 1.5 (max 0 (dot-product n l)))))
	     	(define reflection (scalar-mul (expt (max 0 (dot-product e r)) 2) '(0.8 0.8 0.8)))
	     	(cons point (list (vector-add (scalar-mul diffusion (cadr vertex)) reflection))))
	     triangle))

; Finding the barycentric coordinates of a point inside a triangle.
; Useful when rasterizing and interpolating colors.
(define (barycentric a b c)
	(define v0 (vector-sub b a))
	(define v1 (vector-sub c a))
	(define d00 (dot-product v0 v0))
	(define d01 (dot-product v0 v1))
	(define d11 (dot-product v1 v1))
	(define denom (- (* d00 d11) (* d01 d01)))
	(lambda (p)
		(define v2 (vector-sub p a))
		(define d20 (dot-product v2 v0))
		(define d21 (dot-product v2 v1))
		(define v (/ (- (* d11 d20) (* d01 d21)) denom))
		(define w (/ (- (* d00 d21) (* d01 d20)) denom))
		(define u (- 1 v w))
		(list u v w)))

(define (valid-rgb color) (apply rgb (map (lambda (n) (cond ((> n 1) 1) ((< n 0) 0) (else n))) color)))

; Rasterization
(define (rasterize-triangle triangle x y)
	(define (extreme op sel) (floor (reduce op (map sel (positions triangle)))))
	(define min-x (extreme min car))
	(define max-x (extreme max car))
	(define min-y (extreme min cadr))
	(define max-y (extreme max cadr))
	(define (in-range? position)
		(if (null? position) #t
		    (and (>= 0.5 (abs (- 0.5 (car position)))) (in-range? (cdr position)))))
	(define (bary i j) ((apply barycentric (map cut (positions triangle))) (list i j)))
	(define (rasterize i j)
		(cond ((< j min-y) (rasterize (- i 1) max-y))
		      ((< i min-x) nil)
		      (else (let ((bary-coor (bary i j)))
		      	         (if (in-range? bary-coor) (pixel (+ x i) (+ y j) (valid-rgb (car (matrix-multiply (list bary-coor) (colors triangle))))))
		      	         (rasterize i (- j 1))))))
	(rasterize max-x max-y))

(define (depth triangle)
	(/ (caddr (reduce vector-add (positions triangle))) 3))

(define (spin triangle cycle)
	(map (lambda (vertex) (set-car! vertex (transform (car vertex) (rotation-z (* cycle 2 pi)))) vertex) triangle))

(define (track f lst)
	(define (track-helper acc n f lst)
		(print (list n 'remaining))
		(if (null? lst) acc
		    (track-helper (cons (f (car lst)) acc) (- n 1) f (cdr lst))))
	(track-helper '() (length lst) f lst))

(define (draw)
  ; YOUR CODE HERE
  (bgcolor (rgb 0.92 0.92 0.92))
  (let ((c-matrix (coxeter-matrix 5 3 2))
  	    (mirrors (find-mirrors 5 3 2)))
       (map (lambda (n) (map (lambda (m) (print (list n m))
       									 (define triangles (build-polyhedron c-matrix mirrors (list (* n 0.25) (* m 0.25) 1) (list (- 1 (* 0.2 m)) (* 0.2 n) (* 0.2 m))))
                        	             (define projected (map (lambda (triangle) (projection (shade-triangle (spin triangle (+ (* m 0.05) (* n 0.05)))))) triangles))
                                         (track (lambda (triangle) (rasterize-triangle triangle (* 100 n) (* 100 m))) (sort depth projected)))
                             (range 4)))
            (range 4)))
  (exitonclick))

; Please leave this last line alone.  You may add additional procedures above
; this line.

(print transition)
(draw)