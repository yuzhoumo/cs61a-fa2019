;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: <Epstein didn't kill himself>
;;;
;;; Description:
;;;   <The Truth:
;;;    I probably spent more effort on this contest than Epstein did on his death
;;;    Bottom Text>
;;; If you came here for a real Haiku here you go:
;;;	  <The truth I speak clear:
;;;	   Epstein didn't kill himself
;;;    F to pay respects.>
(define cos30 0.866025404)
(define sin30 0.5)
(define (eye radius) 
	(pendown)
	(rt 45)
	(forward radius)
	(backward (* radius 2))
	(forward radius)
	(lt 90)
	(forward radius)
	(backward (* radius 2))
	(forward radius)
	(rt 45)
	(penup)
)
(define (head)
	(pendown)
	(circle 100)
	(penup)
)
(define (nose size)
	(pendown)
	(rt 60)
	(forward (* size sin30))
	(lt 90)
	(forward (* size cos30))
	(rt 30)
	(penup)
	(backward size)
)
(define (mouth radius)
	(forward 50)
	(lt 90)
	(backward 50)
	(rt 15)
	(pendown)
	(circle radius 30)
	(penup)
)
(define (tongue radius)
	(pendown)
	(rt 90)
	(circle (* -1 radius) 194)
	(hideturtle)
	(penup)
)
(define (draw)
	(penup)
 	(head)
  	(lt 90)
  	(forward 67)
  	(rt 90)
  	(forward 25)
  	(eye 25)
  	(backward 25)
  	(lt 90)
  	(forward 66)
  	(rt 90)
  	(forward 25)
  	(eye 25)
  	(backward 25)
  	(rt 90)
  	(forward 33)
  	(lt 90)
  	(backward 25)
  	(nose 50)
  	(forward 25)
  	(lt 180)
  	(mouth 200)
  	(tongue 20)
  	(exitonclick))

; Please leave this last line alone.  You may add additional procedures above
; this line.
(draw)