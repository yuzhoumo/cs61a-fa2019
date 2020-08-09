;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: HK Peace
;;;
;;; Description:
;;; Hong Kong is part of China but using violence to solve the problem is not
;;; necessary. Hope Hong Kong people and the Beijing government can deal with
;;; the problem in peace so soldiers and people do not bleed.


(define (hk)
  (begin

    (penup)
    (setposition -200 100)
    (pendown)
    (backward 200)

    (penup)
    (setposition -100 100)
    (pendown)
    (backward 200)

    (penup)
    (setposition -200 0)
    (pendown)
    (setposition -100 0)

    (penup)
    (setposition -50 100)
    (pendown)
    (backward 200)

    (penup)
    (setposition 25 100)
    (pendown)
    (setposition -50 0)
    (setposition 25 -100)
  )
)

(define (peace)
  (begin
    (penup)
    (setposition 250 0)
    (pendown)
    (circle 100)

    (penup)
    (setposition 230 0)
    (pendown)
    (circle 80)

    (penup)
    (setposition 140 78)
    (pendown)
    (backward 156)

    (penup)
    (setposition 160 78)
    (pendown)
    (backward 156)

    (penup)
    (setposition 140 -10)
    (pendown)
    (setposition 96 -59)

    (penup)
    (setposition 140 -30)
    (pendown)
    (setposition 108 -66)

    (penup)
    (setposition 160 -10)
    (pendown)
    (setposition 204 -59)

    (penup)
    (setposition 160 -30)
    (pendown)
    (setposition 193 -66)
  )
)

(define (draw)
  (begin
    (hideturtle)
    (speed 100)
    (hk)
    (peace)
    (exitonclick)
  )
)

; Please leave this last line alone.  You may add additional procedures above
; this line.
(draw)
