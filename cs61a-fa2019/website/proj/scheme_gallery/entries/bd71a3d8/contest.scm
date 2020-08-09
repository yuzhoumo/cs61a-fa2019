;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: help me oski senpai
;;;
;;; Description:
;;;   <oski my good friend
;;;    i like need to pass this class
;;;    please help i can pay>

(define main "#003262")
(define alt "#FFFFFF")
(define color1 "#FDB515")


(define (draw)
  (hideturtle)
  (speed 100)
  (recur 100 20 (- 0 (/ (screen_width) 2)) (/ (screen_height) 2) 30)
  (oski-face)
)

(define (oski-face)
  
  ;Ears
  (color color1)
  (penup)
  (setposition -125 50)
  (pendown)
  (begin_fill)
  (circle (- 0 50))
  (end_fill)
  (penup)
  (setposition 0 75)
  (pendown)
  (begin_fill)
  (circle (- 0 50))
  (end_fill)
  (color "#d79702")
  (penup)
  (setposition -105 50)
  (pendown)
  (begin_fill)
  (circle (- 0 30))
  (end_fill)
  (setposition 20 75)
  (pendown)
  (begin_fill)
  (circle (- 0 30))
  (end_fill)
  
  ;Face shape
  (penup)
  (setposition -100 0)
  (pendown)
  (color color1)
  (begin_fill)
  (circle (- 0 100))
  (end_fill)
  
  ;Face parts
  (color "black")
  (penup)
  (setposition -59 10)
  (pendown)
  (begin_fill)
  (circle (- 0 18))
  (end_fill)
  (penup)
  (setposition 30 25)
  (pendown)
  (begin_fill)
  (circle (- 0 18))
  (end_fill)
  
  ;Nose
  (penup)
  (setposition 0 -20)
  (pendown)
  (begin_fill)
  (triangle 20)
  (end_fill)
  
  ;Mouth part of nose
  (penup)
  (setposition 12 -30)
  (pendown)
  (setheading 170)
  (circle (- 0 15) 180)
  (penup)
  (setposition 12 -30)
  (pendown)
  (setheading 170)
  (circle 15 180)
)

(define (triangle size)
  (setheading 80)
  (forward size)
  (right 120)
  (forward size)
  (right 120)
  (forward size)
)

(define (semicircle size)
  (setheading 90)
  (forward size)
  (backward size)
  (right 90)
  (circle (/ size 2) 180)
)

(define (semicircle2 angle size)
  (setheading angle)
  (forward size)
  (backward size)
  (right 90)
  (circle (/ size 2) 180)
)

  
(define (recur r w x y g)
  (if (> y (- (- 0 50) (/ (screen_height) 2)))
    (begin
      (waves r w x y alt)
      (if (> x (/ (screen_width) 2))
        (if (= (modulo (/ (- (/ (screen_height) 2) y) (/ r 2)) 2) 0)
          (recur r w (- (- (- 0 100) 60) (/ (screen_width) 2)) (- y (/ r 2)) g)
          (recur r w (- (- 0 45) (/ (screen_width) 2)) (- y (/ r 2)) g)
        )
        (recur r w (+ (+ x g) (* 2 r)) y g)
      )
    )
  )          
)

(define (waves r w x y c)
  (if (<= 0 r)
    (begin 
      (penup)
      (setposition x y)
      (pendown)
      (if (equal? c main)
        (define c alt)
        (define c main)
      )
      (color c)
      (begin_fill)
      (circle (- 0 r))
      (end_fill)
      (waves (- r w) w (+ x w) y c)
    )
  )
)

; Please leave this last line alone.  You may add additional procedures above
; this line.
(draw)