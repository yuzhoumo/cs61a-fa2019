;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: Revolt.
;;;
;;; Description:
;;;
;;;
;;;
;;;


(define (draw)
  (speed 0)

; Trigonometric identities
(define (deg a) (* 57.296 a))
(define (rad a) (* 0.01745 a))
(define (len x y) (sqrt (+ (* x x) (* y y))))
(define (len_n x y) (sqrt (- (* x x) (* y y))))

; Definitions
(define (cloud r e a l i h t)
  (if (zero? t) (pu)
      (begin
      (if (zero? i)
          (lt a)
          (rt a)
      )
      (begin_fill)
      (circle r e)
      (pu)
      (if (zero? i)
          (fd l)
          (bk l)
      )
      (seth h)
      (end_fill)
      (cloud (- r 2) (- e 5) a l i h (- t 1))
      )
  )
)


(define (heart h a R G B)
  (cond ((<= h 0) (pu))
        ((<= a 0) (pu))
        ((>= R 1) (pu))
        (else (begin
        (define u (- 90 (deg (atan (/ h (* 2 a))))))
        (define d (len (* 2 a) h))
        (begin_fill)
        (color (rgb R G B))
        (rt u)
        (fd d)
        (lt u)
        (circle a 180)
        (rt 180)
        (circle a 180)
        (lt u)
        (fd d)
        (rt (+ 180 u))
        (end_fill)
        (heart (- h 5) (- a 1.35) (+ R 0.01) G (+ B 0.005))))))

(define (hexa l h1 h2 a R G B t)
  (define d1 (/ h1 (sin (rad a))))
  (define d2 (len_n d1 h1))
  (define d3 (len d2 h2))
  (define b_ (deg (atan (/ h2 d2))))
  (define c (- 180 (+ a b_)))
  (if (zero? t) (pu)
  (begin
  (begin_fill)
  (color (rgb R G B))
  (fd l)
  (lt a)
  (fd d1)
  (lt c)
  (fd d3)
  (lt b_)
  (fd (* l 2))
  (lt b_)
  (fd d3)
  (lt c)
  (fd d1)
  (lt a)
  (fd l)
  (end_fill)
  (rt 90)
  (fd 0.8)
  (lt 90)
  (hexa l (/ h1 1.010) (/ h2 1.010) a (* R 1.017) (* G 1.017) (* B 1.017) (- t 1))
  )
  )
)

; Kagelo
(define (jail l w)
  (pd)
  (begin_fill)
  (color (rgb 0 0 0))
  (fd l)
  (rt 90)
  (fd w)
  (rt 90)
  (fd l)
  (end_fill)
  (pu)
  (seth 0)
)

(define (curve_r l a da cut)
  (if (<= cut 0) (pd)
  (begin
  (fd l)
  (rt a)
  (curve_r l (+ a da) da (- cut l))
  )
  )
)

(define (curve_l l a da cut)
  (if (<= cut 0) (pd)
  (begin
  (fd l)
  (lt a)
  (curve_l l (+ a da) da (- cut l))
  )
  )
)

(define (wing l m e R G B i rot dir t)
  (define dir_ (zero? dir))
  (if (zero? t) (pu)
      (begin
      (pd)
      (if dir_
          (rt rot)
          (lt rot)
          )
      (begin_fill)
      (color (rgb R G B))
      (if dir_
          (curve_l l 0.12 0.002 e)
          (curve_r l 0.12 0.002 e)
          )
      (if dir_
          (rt 170)
          (lt 170)
          )
      (if dir_
          (curve_r (* l m) 0.85 0.0015 (+ e 10))
          (curve_l (* l m) 0.85 0.0015 (+ e 10))
          )
      (end_fill)
      (wing l m e (- R i) (- G i) (- B i) i rot dir (- t 1))
      )
  )
)

; Begin
(bgcolor (rgb 0.7 0.7 1))
(color (rgb 0.75 0.75 1))
(pu)
(goto 0 -400)
(cloud 40 180 5 10 0 0 45)
(goto 0 -400)
(cloud 50 200 5 20 0 0 48)
(goto 0 -400)
(cloud 30 100 5 30 0 0 44)
(goto 0 -400)
(cloud 20 220 5 35 0 0 48)
(goto 0 -400)
(cloud 50 95 5 20 0 0 49)
(goto 0 -400)
(cloud 40 115 5 25 0 0 47)
(goto 0 -400)
(cloud 40 180 5 10 1 180 45)
(goto 0 -400)
(cloud 50 200 5 20 1 180 48)
(goto 0 -400)
(cloud 30 100 5 30 1 180 44)
(goto 0 -400)
(cloud 20 220 5 35 1 180 48)
(goto 0 -400)
(cloud 50 95 5 20 1 180 49)
(goto 0 -400)
(cloud 40 180 5 10 1 0 45)
(goto 0 -400)
(cloud 50 200 5 20 1 0 48)
(goto 0 -400)
(cloud 30 100 5 30 1 0 44)
(goto 0 -400)
(cloud 20 220 5 35 1 0 48)
(goto 0 -400)
(cloud 50 95 5 20 1 0 49)
(goto 0 -400)
(cloud 40 115 5 25 1 0 47)
(goto 0 -400)
(cloud 40 180 5 10 0 180 45)
(goto 0 -400)
(cloud 50 200 5 20 0 180 48)
(goto 0 -400)
(cloud 30 100 5 30 0 180 44)
(goto 0 -400)
(cloud 20 220 5 35 0 180 48)
(goto 0 -400)
(cloud 50 95 5 20 0 180 49)
(goto 0 -400)
(cloud 40 115 5 25 0 180 47)



(goto 0 0)
(seth 0)

; Bottom cage
(bk 20)
(rt 90)
(hexa 50 30 20 15 0.08 0.08 0.08 40)
(goto 0 0)
(seth 0)
(bk 40)
(color (rgb 1 1 1))
(pu)

; Kagela - pisw
(goto -162 3)
(jail 286 25)
(goto 136 3)
(jail 286 25)

; Left wing
(goto -42 125)
(rt 255)
(wing 1 1.8 290 0.95 0.95 0.95 0.03 100 0 4)
(goto -42 155)
(lt 221.5)
(wing 1.4 2.0 200 0.98 0.98 0.98 0.005 145 0 11)
(seth 0)

; Right wing
(goto 42 125)
(lt 255)
(wing 1 1.8 290 0.95 0.95 0.95 0.03 100 1 4)
(goto 42 155)
(rt 221.5)
(wing 1.4 2.0 200 0.98 0.98 0.98 0.005 145 1 11)
(seth 0)

; Heart
(goto 0 0)
(heart 200 50 0.7 0 0.25)

; Kagela - mprosta
(goto -129 -35)
(jail 290 25)
(goto 100 -35)
(jail 290 25)
(goto -50 -55)
(jail 290 25)
(goto 25 -55)
(jail 290 25)

; Top cage
(goto 0 260)
(seth 0)
(rt 90)
(hexa 50 30 20 15 0.08 0.08 0.08 40)

; Bottom hexa
(goto 0 -200)
(seth 90)
(hexa 100 50 20 15 0.2 0.2 0.2 1)

; Top hexa
(goto 0 390)
(seth 90)
(hexa 100 50 20 15 0.2 0.2 0.2 1)

(define (hd x1 y1 z)
  (goto x1 y1)
  (pd)
  (color (rgb 0 0 0))
  (goto 0 z)
  (pu)
)

; Hyperbottom
(hd -100 -200 0)
(hd 100 -200 0)
(hd -285 -150 0)
(hd 285 -150 0)
(hd -100 -130 0)
(hd 100 -130 0)

; Hypertop
(hd -100 390 280)
(hd 100 390 280)
(hd -285 440 280)
(hd 285 440 280)
(hd -100 460 280)
(hd 100 460 280)

; Hide the turtle
(ht)
)
; Please leave this last line alone.  You may add additional procedures above
; this line.
(draw)
