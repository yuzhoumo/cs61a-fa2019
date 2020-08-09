;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: the sad and sudden death of kiwibot
;;;
;;; Description:
;;; why did you leave us?
;;; here alone in the darkness
;;; dying of hunger

(define (map fn vals) 
  (if (null? vals) 
      () 
      (cons (fn (car vals)) 
            (map fn (cdr vals)))))


(define-macro (for sym vals expr)
  (list 'map (list 'lambda (list sym) expr) vals))

(define (pos x y)
  (penup)
  (setposition x y)
  (pendown)
)

(define (drawcircle radius x y)
    (pos x y)
    (begin_fill)
    (circle radius)
    (end_fill)
    (pos (- x radius 15) (- y 10))
    (color "white")
    (write 'RIP 16 'Arial)
    (penup)
)

(define (range a b step)
  (if (> a b) nil (cons a (range (+ a step) b step))))

(define (get index lst)
  (if (= index 0) 
    (car lst)
    (get (- index 1) (cdr lst))
  )
)

(define colors '("green" "blue" "cadetblue" "burlywood" "cornflowerblue" "orange" "olive" "rosybrown" "tan" "indianred" "chocolate" "crimson" "darkseagreen" "slategray" "darkslategray" "darkred" "purple" "brown"))

(define (drawcircles radius)
    (define i 0)
    (for x (range (/ (screen_width) -2) (+ (/ (screen_width) 2) radius radius) (* radius 2))
        (for y (range (/ (screen_height) -2) (/ (screen_height) 2) (* radius 2))
              (begin 
                (color (get (random 0 (length colors)) colors))
                (drawcircle radius x y)
              )
        )
    )
)

(define (drawrectangle x y width height c)
    (pos x y)
    (color c)
    (begin_fill)
    (setheading -90)
    (fd width)
    (left 90)
    (fd height)
    (left 90)
    (fd width)
    (left 90)
    (fd height)
    (end_fill)
    (penup)
)

(define (drawkiwibot)
    (drawrectangle -150 -100 70 140 "black")
    (drawrectangle 220 -100 70 140 "black")
    (drawrectangle (/ (screen_width) 4) (/ (screen_height) 4) (/ (screen_width) 2) (/ (screen_height) 2) "white")
    (drawrectangle 150 162 300 262 "black")
    (drawrectangle 100 100 200 150 "white")
    (drawrectangle 65 45 45 45 "dodgerblue")
    (pos -50 50)
    (setheading 135)
    (color "dodgerblue")
    (begin_fill)
    (fd 40)
    (right 90)
    (fd 40)
    (right 90)
    (fd 10)
    (right 90)
    (fd 30)
    (left 90)
    (fd 30)
    (right 90)
    (fd 10)
    (end_fill)
    (drawrectangle -160 312 10 150 "black")
    (pos -170 312)
    (setheading -130)
    (color "dodgerblue")
    (begin_fill)
    (fd 100)
    (right 37)
    (bk 80)
    (right 90)
    (fd 60)
    (end_fill)
    (pos -50 -90)
    (color "white")
    (write 'kiwibot)
)

(define (draw)
  (bgcolor "mintcream")
  (speed 0)
  (drawcircles 50)
  (drawkiwibot)
  (hideturtle)
  (exitonclick))

; Please leave this last line alone.  You may add additional procedures above
; this line.
(draw)