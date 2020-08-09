; ;; Scheme Recursive Art Contest Entry
; ;;
; ;; Please do not include your name or personal info in this file.
; ;;
; ;; Title: The "I FAILED CS 61A" T-shirt
; ;;
; ;; Description:
; ;;   <CS 61A T-shirts!
; ;;    If you wanna have a "I FAILED CS 61A" t-shirt designed,
; ;;    then vote!>
(define (draw)
  ; ;; positioning the turtle
  (penup)
  (backward 200)
  (left 90)
  (forward 200)
  (right 90)
  
  ; ;; start drawing
  (pendown)
  (begin_fill)
  (color "blue")
  
  ; ;; left side
  (forward 300)
  
  ; ;; left sleeve
  (left 135)
  (forward 45)
  (right 90)
  (forward 60)
  (right 90)
  (forward 105)
  (right 45)
  
  ; ;; top
  (forward 85)
  (right 90)
  (circle 40 180)
  (right 90)
  (forward 85)
  
  ; ;; right-sleeve
  (right 45)
  (forward 105)
  (right 90)
  (forward 60)
  (right 90)
  (forward 45)
  (left 135)
  
  ; ;; right side
  (forward 300)
  (right 90)
  
  ; ;; bottom
  (forward 250)
  (end_fill)
  
  ; ;; "engraving" the text
  (penup)
  (color "white")
  (right 90)
  (forward 230)
  (right 90)
  (forward 10)
  (pendown)
  
  ; ;; 'I'
  ; (setposition 100 100)
  (left 90)
  (forward 25)
  
  ; ;; 'F'
  (penup)
  (right 90)
  (forward 30)
  (pendown)
  (right 180)
  (forward 15)
  (left 90)
  (forward 25)
  (right 180)
  (forward 13)
  (right 90)
  (forward 12)
  
  ; ;; 'A'
  (penup)
  (forward 7) ; space
  (right 90)
  (forward 13)
  (pendown)
  
  (right 180)
  (forward 25)
  (right 90)
  (forward 15)
  (right 90)
  (forward 25)
  (backward 13)
  (right 90)
  (forward 15)
  
  ; ;; 'I'
  (penup)
  (left 90)
  (forward 13)
  
  (left 90)
  (forward 15)
  (forward 7) ; space
  (left 90)
  (pendown)
  (forward 25)
  
  ; ;; 'L'
  (penup)
  (right 90)
  (forward 7) ; space
  (right 90)
  (pendown)
  
  (forward 25)
  (left 90)
  (forward 15)
  (right 90)
  
  ; ;; 'E'
  (penup)
  (left 90)
  (forward 5) ; space
  (forward 15)
  (pendown)
  
  (right 180)
  (forward 15)
  (right 90)
  (forward 25)
  (right 90)
  (forward 15)
  (penup)
  (right 90)
  (forward 12)
  (right 90)
  (pendown)
  (forward 15)
  (left 90)
  (forward 13)
  
  ; ;; 'D'
  (penup)
  (left 90)
  (forward 21)
  (pendown)
  
  (forward 15)
  (left 90)
  (forward 25)
  (left 90)
  (forward 15)
  (left 90)
  (forward 25)
  
  ; ;; 'C'
  (penup)
  (left 90)
  (forward 30)
  (forward 15)
  (pendown)
  
  (right 180)
  (forward 15)
  (right 90)
  (forward 25)
  (right 90)
  (forward 15)
  
  ; ;; 'S'
  (penup)
  (forward 5) ; space
  (forward 15)
  (right 180)
  (pendown)
  
  (forward 15)
  (left 90)
  (forward 12)
  (left 90)
  (forward 15)
  (right 90)
  (forward 12)
  (right 90)
  (forward 15)
  
  ; ;; '6'
  (penup)
  (right 180)
  (forward 15) ; space
  (forward 15)
  (left 90)
  (forward 13)
  (right 90)
  (pendown)
  
  (forward 15)
  (right 90)
  (forward 13)
  (right 90)
  (forward 15)
  (right 90)
  (forward 25)
  (right 90)
  (forward 15)
  
  ; ;; '1'
  (penup)
  (forward 7)
  (right 90)
  (pendown)
  
  (forward 25)
  
  ; ;; 'A'
  (penup)
  (right 180)
  (forward 13)
  (right 90)
  (forward 7) ; space
  (right 90)
  (forward 13)
  (pendown)
  (right 180)
  (forward 25)
  (right 90)
  (forward 15)
  (right 90)
  (forward 25)
  (backward 13)
  (right 90)
  (forward 15)
  
  (hideturtle)
  (exitonclick)
)

; Please leave this last line alone.  You may add additional procedures above
; this line.
(draw)
