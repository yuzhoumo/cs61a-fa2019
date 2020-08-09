; ;; Scheme Recursive Art Contest Entry
; ;;
; ;; Please do not include your name or personal info in this file.
; ;;
; ;; Title: The back of the back of the back of the ...  of the turtle
; ;;
; ;; Description:
; ;;   <Summon a turtle
; ;;    Matters collide  Earth shatters
; ;;    And multiverse born.>
(define (cadr pair) (car (cdr pair)))

(define (polyline n length angle)
  (cond 
    ((eq? n 0)
     (penup))
    (else
     (begin (pendown)
            (forward length)
            (left angle)
            (polyline (- n 1) length angle)))))

(define pi (* 4 (atan 1.0)))

(define (arc r angle)
  (define arc_length (* 2 pi r (/ (abs angle) 360)))
  (define n (+ 1 (floor (/ arc_length 4))))
  (define step_length (/ arc_length n))
  (define step_angle (/ angle n))
  (begin (pendown)
         (left (/ step_angle 2))
         (polyline n step_length step_angle)
         (right (/ step_angle 2))))

(define (a-turtle size x y)
  (begin (penup)
         (setposition (+ x size) y)
         (color "#26803e")
         (begin_fill)
         (arc size 180)
         (setheading 90)
         (pendown)
         (forward (* 2 size))
         (end_fill)
         (feet (/ size 10) (- x (* 9 (/ size 10))) y)
         (feet (/ size 10) (+ x (* 7 (/ size 10))) y)
         (setposition (+ x size) y)
         (arc size 150)
         (setheading 270)
         (pendown)
         (begin_fill)
         (forward (/ size 5))
         (arc (/ size 5) 200)
         (end_fill)
         (eye (/ size 13)
              (- x (* 1.1 size))
              (+ y (/ size 3)))
         (color "#4ac26a")
         (if (< size 10)
             penup
             (fill_turtle x y size (/ size 10) 2))
         (setheading 0)))

(define (eye size x y)
  (penup)
  (color "white")
  (setposition (- x size) y)
  (begin_fill)
  (setheading 180)
  (arc size 180)
  (setheading 270)
  (forward (* size 2))
  (end_fill)
  (color "black")
  ; (setheading 180)
  ; (arc size 180)
  ; (setheading 270)
  ; (pendown)
  ; (forward (* size 2))
  (penup)
  (define small (/ size 10))
  (setposition (+ x small) (- y (/ size 5)))
  (begin_fill)
  (circle (/ size 10))
  (end_fill))

; (eye 30 0 0)
(define (feet size x y)
  (penup)
  (setposition x y)
  (setheading 180)
  (pendown)
  (color "#4ac26a")
  (begin_fill)
  (forward size)
  (arc size 180)
  (pendown)
  (forward size)
  (end_fill))

(define (stack-turtles size x y n)
  (cond 
    ((eq? n 0)
     penup)
    (else
     (begin (a-turtle size x y)
            (stack-turtles (* 3 (/ size 5))
                           x
                           (+ y size)
                           (- n 1))))))

(define (lang-flower size x y n light)
  (cond 
    ((eq? n 0)
     penup)
    (else
     (begin (penup)
            (if light
                (define c "#4d9cb0")
                (define c "#224c8c"))
            (color c)
            (setposition (+ x size) y)
            (pendown)
            (begin_fill)
            (circle size)
            (end_fill)
            (lang-flower (* size (/ (randint 4 9) 10))
                         (+ x (/ (* size (randint -3 3)) 10))
                         (+ y (/ (* size (randint -3 3)) 10))
                         (- n 1)
                         (not light))))))

(define
 (many-flowers size xl xh yl yh n trend1 trend2)
 (cond 
   ((eq? n 0)
    penup)
   (else
    (begin (define x (randint xl xh))
           (define y (randint yl yh))
           (if (> (random 1) trend1)
               (define c1 1)
               (define c1 -1))
           (if (> (random 1) trend2)
               (define c2 1)
               (define c2 -1))
           (define r
                   (+ size (* c1 (* size (/ (randint 1 4) 20)))))
           (lang-flower r x y 4 #f)
           (many-flowers size
                         xl
                         xh
                         yl
                         yh
                         (- n 1)
                         trend1
                         trend2)))))

(define random
        (let ((a 69069)
              (c 1)
              (m (expt 2 32))
              (seed 19380110))
          (lambda (new-seed)
            (if (pair? new-seed)
                (set! seed (car new-seed))
                (set! seed (modulo (+ (* seed a) c) m)))
            (/ seed m))))

(define (randint . args)
  (cond 
    ((= (length args) 1)
     (floor (* (random 1) (car args))))
    ((= (length args) 2)
     (+ (car args)
        (floor (* (random 1) (- (cadr args) (car args))))))
    (else
     (error 'randint "usage: (randint [lo] hi)"))))

(define (huawen x y size n)
  (begin (setheading 90)
         (cond 
           ((eq? n 0)
            (penup))
           (else
            (begin (penup)
                   (setposition (- x (/ size 2))
                                (- y (* (sqrt 3) (/ size 2))))
                   (pendown)
                   (polyline 6 size 60)
                   (huawen x y (* size (/ 4 7)) (- n 1)))))))

(define (huawenzhenlie x y rows size n)
  (define (helper x y rows)
    (cond 
      ((eq? rows 0)
       (penup))
      (else
       (begin (huawen x y size n)
              (helper x
                      (- y (* 2 (* (sqrt 3) (/ size 2))))
                      (- rows 1))))))
  (helper x
          (+ y
             (* (- rows 0.5) (* 2 (* (sqrt 3) (/ size 2)))))
          rows))

(define
 (fill_turtle x y turtle_size huawen_size n)
 (define huawen_x
         (+ huawen_size
            (- x
               (sqrt (- (* turtle_size turtle_size)
                        (* (* (sqrt 3) (/ huawen_size 2))
                           (* (sqrt 3) (/ huawen_size 2))))))))
 (define (helper start_x start_y move_up)
   (cond 
     ((> (+ start_x huawen_size) turtle_size)
      (penup))
     (else
      (define height
              (- (sqrt (- (* turtle_size turtle_size)
                          (* start_x start_x)))
                 (- start_y y)))
      (begin (huawenzhenlie start_x
                            start_y
                            (floor
                             (/ height (* 2 (* (sqrt 3) (/ huawen_size 2)))))
                            huawen_size
                            n)
             (cond 
               (move_up
                (helper (+ start_x (* 3 (/ huawen_size 2)))
                        (+ start_y (* (sqrt 3) (/ huawen_size 2)))
                        (not move_up)))
               (else
                (helper (+ start_x (* 3 (/ huawen_size 2)))
                        (- start_y (* (sqrt 3) (/ huawen_size 2)))
                        (not move_up))))))))
 (helper huawen_x y #t))

(define (draw)
  (begin (speed 0)
         (bgcolor "#848cb0")
         (many-flowers 13 -400 400 -300 300 5 0.1 0.5)
         (many-flowers 20 -400 400 -400 -200 15 0.1 0.5)
         (stack-turtles 300 0 -400 10)
         (many-flowers 40 -500 500 -500 -350 30 0.1 0.5)
         (many-flowers 20 -400 400 -400 -200 15 0.1 0.5)
         (many-flowers 13 -400 400 -300 300 15 0.1 0.5)
         (hideturtle))
  (exitonclick))

; Please leave this last line alone.  You may add additional procedures above
; this line.
(draw)
