;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: Hope in Uncertainity, a lesson from Uncle Iroh
;;;
;;; Description:
;;;    "Even in the dark
;;;    keep moving forward; you shall
;;;    reach a better place"

(bgcolor "#070e21")
;(define pi 3.14159)
(speed 0)

; you'll notice the code below is a ghost town -
; there is a LOT of commented out code.
; this is because I tried a lot of different
; ideas before settling on the current design.
; If, for whatever reason, you are interested in
; some of these alt-designs, you will have to do
; a some amount of recoding. I apologize in advance
; for the minimal documentation

; gets the num'th object in a lst
(define (access-color lst num)
  (if (= num 1)
    (car lst)
    (access-color (cdr lst) (- num 1))
  )
)


; makes a single iteration of the yin-yang symbol
; of [radius] and with colors [light-dark]
(define (make-yin-yang radius light-dark)

  (color (car light-dark))
  (center-circle radius 180)

  (move-cart 0 (- radius))

  (color (car (cdr light-dark)))
  (center-circle radius 180)

  (move-cart 0 (* -1.5 radius))

  (color (car light-dark))
  (center-circle (* 0.5 radius) 360)

  (move-cart 0 (* 0.5 radius))

  (color (car (cdr light-dark)))
  (center-circle (* 0.5 radius) 360)
)

; makes a circle centered around the turtle
(define (center-circle radius degree)
  (begin_fill)
  ; (block-move (List 0 radius))
  (move-cart 0 radius)
  (circle radius degree)
  (end_fill)
)

; makes a yin-yang fractal with [colors], a
; number of layers [de[th], and [radius],
; and then moves the turtle to the center of
; the fractal
(define (make-fractal radius depth colors)
  ;(begin
    (define (inner radius depth colors)
      (if (> depth 0)

        (begin
          (make-yin-yang radius colors)

          (move-cart 0 (* -0.5 radius))

          (inner (* 0.25 radius) (- depth 1) colors)

          (move-cart 0 (* -0.875 radius))

          (inner (* 0.25 radius) (- depth 1) colors)

        )
      )
    )
    (inner radius depth colors)
    ; I have discovered this EXTREMELY remarkable
    ; logistic function that provides an excellent
    ; --approximation-- for the distance of the turtle
    ; after [depth] iterations to the center of the fractal.
    ; It took careful guessing and regression in order to
    ; guess this formula, and I have literally no clue why
    ; it holds. Further analysis is required, but it
    ; is truly spectacular.
    (move-cart 0 (ceil (/ (* 0.75 radius) (+ 1 (expt 2.7182818 (- 1 depth))))))
)

; moves the turtle in cartesian coords by [up1] and [left1]
; while preserving direction and not drawing a line
(define (move-cart up1 left1)
  (pu)
  (fd up1)
  (right 90)
  (fd left1)
  (left 90)
  (pd)
)
; a list implementation of move-cart
;(define (block-move coor-pair)
;  (pu)
;  (fd (car coor-pair))
;  (right 90)
;  (fd (car (cdr coor-pair)))
;  (left 90)
;  (pd)
;)

; Makes a cascade of fractals. Current implementation
; does not run; requires slight modification of parameters
; as well as inputs into make-fractal
;(define (make-cascade radius num angle)
;  (if (> num 0)
;    (begin
;      (make-fractal radius 4 angle #t)
;      (block-move `(,(* -1.5 radius) 20))
;      (make-cascade radius (- num 1) angle)
;        '(1)
;    )
;    '(1)
;  )
;)

; calls cascade a series of times in order
; to fill the entire backdrop with cascades
;(define (make-background total)
;  (if
;    (> total 0)
;      (begin
;        (make-cascade 100 7 0)
;        (block-move '(1000 450))
;        (make-background (- total 1))
;        '(1)
;      )
;    '(1)
;  )
;)



;(define (make-tendrils radius num count)
;  (if (and (< count (+ num 1)) (> num 1))
;    (begin
      ;(block-move `( 0 ,(* 0.65 radius)))
;      (define rup (* (cos (/ (* 6.282 count) num)) radius))
;      (define rright (* (sin (/ (* -6.282 count) num)) radius))
;      (block-move `(,rup ,rright))

      ;(define colors (get-color radius num count))

      ; a highly chaotic function of three parameters - psuedorandom
      ;(define (change param) (cos (abs (sin (tan param)))))
      ;(define sum (+ radius num count))
      ;(define p1 (change (+ sum 0.2)))
      ;(define p2 (change sum))
      ;(define p3 (change (+ sum 0.4)))

;      (make-fractal (* radius 0.33) 3 (rgb p1 p2  p3) (rgb (half p3) (half p2) (half p1)))

;      (make-tendrils (* radius 0.33) (half num) 0)

;      (block-move `(,(- rup) ,(- (/ radius 4) rright)))
;      (make-tendrils radius num (+ count 1))

      ;'(1)
;    )
    ;'(1)
;  )
;)

; makes [total tendrils] "tendrils" for a circle of [radius], with
; 3 tendrils each of their own, which goes on for [its] - [min-its]
; layers, each tendril 1/4 the size of the prior
(define (make-tendrils radius total-tendrils current-tendrils its min-its)
  (if (and (< current-tendrils total-tendrils) (> its min-its))
    (begin
      ; something I worked on for a long time before I remembered that I could
      ; turn by increments other than 90 degrees
      ;(define rup (* (cos (/ (* 360 current-tendrils) total-tendrils)) radius))
      ;(define rright (* (sin (/ (* -360 current-tendrils) total-tendrils)) radius))

      (left (/ 360 total-tendrils))
      (pu)
      (forward (* radius 1.27))
      (pd)

      (define clr
        (access-color
          (List
              `("#ffffff" "#ffa180")
              `("#ffffff" "#ff5e24")
              `("#ffffff" "#fc4b0a")
              `( "#ffffff" "#e00f00")
              `( "#ffffff" "#9e0b00")
              `( "#ffffff" "#380400" )
           )
          its)
      )

      (make-fractal (/ radius 4) (- its min-its) clr)

      (make-tendrils (/ radius 4) 3 0 (- its 1) min-its)

      (pu)
      (backward (* radius 1.27))
      (pd)

      (make-tendrils radius total-tendrils (+ 1 current-tendrils) its min-its)


    )
  )

)


(define (draw)
  ; starts make-background procedure;
  ; more info can be found on (make-background)
  ; documentation
  ;(block-move '(350 -350))
  ;(make-background 8)

  ; draws the inner white-black yin-yang
  (make-fractal 200 5 `("#ffffff" "#000000"))
  ; draws the first orbit of yin-yang "tendrils"
  (make-tendrils 200 8 0 6 2)
  (left 22.5)
  ; draws the second orbit of yin-yang "tendrils"
  (make-tendrils 300 8 0 5 1)
  (left 22.5)
  ; draws the third orbit of yin-yang "tendrils"
  (make-tendrils 420 4 0 4 0)


  (exitonclick)
)


(ht)
; Please leave this last line alone.  You may add additional procedures above
; this line.
(draw)