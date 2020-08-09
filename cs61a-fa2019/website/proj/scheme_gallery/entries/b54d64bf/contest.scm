;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: High-five Me, Mr. Meseeks!
;;;
;;; Description:
;;;   	Existence is pain
;;;    	but at least we have our friends
;;;    	smiled the shy blue guy


(define (draw)

(define color1 "#9CCBDD")
(define color2 "#8AD6EE")
(define color3 "#550B10")
(define color4 "#911318")
(define color5 "#C8CABD")
(define color6 "#FFFFFF")
(define color7 "#000000")

(define (mr-meseeks x y size)
	(pixelsize size)

	;row 1
	(pixel (+ x 7) (+ y 1) color1)
	(pixel (+ x 8) (+ y 1) color1)
	(pixel (+ x 9) (+ y 1) color1)
	(pixel (+ x 10) (+ y 1) color1)
	(pixel (+ x 13) (+ y 1) color2)
	(pixel (+ x 14) (+ y 1) color2)
	(pixel (+ x 15) (+ y 1) color1)

	;row 2
	(pixel (+ x 8) (+ y 2) color5)
	(pixel (+ x 9) (+ y 2) color2)
	(pixel (+ x 10) (+ y 2) color2)
	(pixel (+ x 11) (+ y 2) color1)
	(pixel (+ x 13) (+ y 2) color2)
	(pixel (+ x 14) (+ y 2) color2)

	;row 3
	(pixel (+ x 10) (+ y 3) color2)
	(pixel (+ x 13) (+ y 3) color2)
	(pixel (+ x 14) (+ y 3) color2)

	;row 4
	(pixel (+ x 10) (+ y 4) color2)
	(pixel (+ x 13) (+ y 4) color2)
	(pixel (+ x 14) (+ y 4) color2)

	;row 5
	(pixel (+ x 10) (+ y 5) color2)
	(pixel (+ x 13) (+ y 5) color2)
	(pixel (+ x 14) (+ y 5) color1)

	;row 6
	(pixel (+ x 9) (+ y 6) color1)
	(pixel (+ x 10) (+ y 6) color1)
	(pixel (+ x 13) (+ y 6) color1)
	(pixel (+ x 14) (+ y 6) color1)

	;row 7
	(pixel (+ x 9) (+ y 7) color2)
	(pixel (+ x 13) (+ y 7) color1)
	(pixel (+ x 14) (+ y 7) color1)

	;row 8
	(pixel (+ x 9) (+ y 8) color2)
	(pixel (+ x 13) (+ y 8) color1)
	(pixel (+ x 14) (+ y 8) color5)

	;row 9
	(pixel (+ x 9) (+ y 9) color2)
	(pixel (+ x 13) (+ y 9) color2)

	;row 10
	(pixel (+ x 9) (+ y 10) color2)
	(pixel (+ x 10) (+ y 10) color1)
	(pixel (+ x 13) (+ y 10) color2)

	;row 11
	(pixel (+ x 10) (+ y 11) color2)
	(pixel (+ x 13) (+ y 11) color2)

	;row 12
	(pixel (+ x 10) (+ y 12) color2)
	(pixel (+ x 12) (+ y 12) color2)
	(pixel (+ x 13) (+ y 12) color2)

	;row 13
	(pixel (+ x 10) (+ y 13) color2)
	(pixel (+ x 11) (+ y 13) color2)
	(pixel (+ x 12) (+ y 13) color2)

	;row 14
	(pixel (+ x 9) (+ y 14) color1)
	(pixel (+ x 10) (+ y 14) color2)
	(pixel (+ x 11) (+ y 14) color2)
	(pixel (+ x 12) (+ y 14) color2)
	(pixel (+ x 13) (+ y 14) color2)

	;row 15
	(pixel (+ x 9) (+ y 15) color2)
	(pixel (+ x 10) (+ y 15) color2)
	(pixel (+ x 11) (+ y 15) color2)
	(pixel (+ x 12) (+ y 15) color2)
	(pixel (+ x 13) (+ y 15) color2)

	;row 16
	(pixel (+ x 9) (+ y 16) color2)
	(pixel (+ x 10) (+ y 16) color2)
	(pixel (+ x 11) (+ y 16) color2)
	(pixel (+ x 12) (+ y 16) color2)
	(pixel (+ x 13) (+ y 16) color2)

	;row 17
	(pixel (+ x 9) (+ y 17) color2)
	(pixel (+ x 10) (+ y 17) color2)
	(pixel (+ x 11) (+ y 17) color2)
	(pixel (+ x 12) (+ y 17) color2)
	(pixel (+ x 13) (+ y 17) color2)

	;row 18
	(pixel (+ x 9) (+ y 18) color2)
	(pixel (+ x 10) (+ y 18) color2)
	(pixel (+ x 11) (+ y 18) color2)
	(pixel (+ x 12) (+ y 18) color2)
	(pixel (+ x 13) (+ y 18) color2)

	;row 19
	(pixel (+ x 9) (+ y 19) color2)
	(pixel (+ x 10) (+ y 19) color2)
	(pixel (+ x 11) (+ y 19) color2)
	(pixel (+ x 12) (+ y 19) color2)
	(pixel (+ x 13) (+ y 19) color2)

	;row 20 
	(pixel (+ x 10) (+ y 20) color2)
	(pixel (+ x 11) (+ y 20) color2)
	(pixel (+ x 12) (+ y 20) color2)

	;row 21
	(pixel (+ x 10) (+ y 21) color2)
	(pixel (+ x 11) (+ y 21) color2)
	(pixel (+ x 12) (+ y 21) color2)

	;row 22 
	(pixel (+ x 10) (+ y 22) color2)
	(pixel (+ x 11) (+ y 22) color2)
	(pixel (+ x 12) (+ y 22) color2)

	;row 23
	(pixel (+ x 8) (+ y 23) color1)
	(pixel (+ x 9) (+ y 23) color2)
	(pixel (+ x 10) (+ y 23) color2)
	(pixel (+ x 11) (+ y 23) color2)
	(pixel (+ x 12) (+ y 23) color2)
	(pixel (+ x 13) (+ y 23) color2)

	;row 24
	(pixel (+ x 5) (+ y 24) color2)
	(pixel (+ x 6) (+ y 24) color2)
	(pixel (+ x 7) (+ y 24) color1)
	(pixel (+ x 10) (+ y 24) color5)
	(pixel (+ x 11) (+ y 24) color2)
	(pixel (+ x 12) (+ y 24) color2)
	(pixel (+ x 13) (+ y 24) color2)
	(pixel (+ x 14) (+ y 24) color2)
	(pixel (+ x 15) (+ y 24) color2)
	(pixel (+ x 16) (+ y 24) color2)
	(pixel (+ x 17) (+ y 24) color1)

	;row 25
	(pixel (+ x 4) (+ y 25) color2)
	(pixel (+ x 10) (+ y 25) color5)
	(pixel (+ x 11) (+ y 25) color1)
	(pixel (+ x 12) (+ y 25) color5)
	(pixel (+ x 13) (+ y 25) color5)
	(pixel (+ x 14) (+ y 25) color5)
	(pixel (+ x 15) (+ y 25) color2)
	(pixel (+ x 18) (+ y 25) color2)
	(pixel (+ x 19) (+ y 25) color2)
	(pixel (+ x 20) (+ y 25) color5)

	;row 26
	(pixel (+ x 3) (+ y 26) color2)
	(pixel (+ x 4) (+ y 26) color1)
	(pixel (+ x 9) (+ y 26) color3)
	(pixel (+ x 10) (+ y 26) color3)
	(pixel (+ x 11) (+ y 26) color3)
	(pixel (+ x 12) (+ y 26) color3)
	(pixel (+ x 13) (+ y 26) color4)
	(pixel (+ x 14) (+ y 26) color3)
	(pixel (+ x 15) (+ y 26) color2)
	(pixel (+ x 16) (+ y 26) color2)
	(pixel (+ x 20) (+ y 26) color2)
	(pixel (+ x 21) (+ y 26) color5)

	;row 27
	(pixel (+ x 3) (+ y 27) color2)
	(pixel (+ x 9) (+ y 27) color3)
	(pixel (+ x 10) (+ y 27) color3)
	(pixel (+ x 11) (+ y 27) color5)
	(pixel (+ x 12) (+ y 27) color5)
	(pixel (+ x 13) (+ y 27) color3)
	(pixel (+ x 14) (+ y 27) color3)
	(pixel (+ x 15) (+ y 27) color2)
	(pixel (+ x 16) (+ y 27) color2)
	(pixel (+ x 21) (+ y 27) color2)

	;row 28
	(pixel (+ x 2) (+ y 28) color1)
	(pixel (+ x 3) (+ y 28) color2)
	(pixel (+ x 9) (+ y 28) color2)
	(pixel (+ x 10) (+ y 28) color2)
	(pixel (+ x 11) (+ y 28) color2)
	(pixel (+ x 12) (+ y 28) color2)
	(pixel (+ x 13) (+ y 28) color2)
	(pixel (+ x 14) (+ y 28) color2)
	(pixel (+ x 15) (+ y 28) color2)
	(pixel (+ x 16) (+ y 28) color2)
	(pixel (+ x 21) (+ y 28) color5)
	(pixel (+ x 22) (+ y 28) color2)

	;row 29
	(pixel (+ x 2) (+ y 29) color2)
	(pixel (+ x 3) (+ y 29) color1)
	(pixel (+ x 9) (+ y 29) color2)
	(pixel (+ x 10) (+ y 29) color2)
	(pixel (+ x 11) (+ y 29) color7)
	(pixel (+ x 12) (+ y 29) color2)
	(pixel (+ x 13) (+ y 29) color7)
	(pixel (+ x 14) (+ y 29) color2)
	(pixel (+ x 15) (+ y 29) color2)
	(pixel (+ x 16) (+ y 29) color2)
	(pixel (+ x 22) (+ y 29) color2)
	(pixel (+ x 23) (+ y 29) color2)

	;row 30
	(pixel (+ x 2) (+ y 30) color2)
	(pixel (+ x 3) (+ y 30) color1)
	(pixel (+ x 10) (+ y 30) color2)
	(pixel (+ x 11) (+ y 30) color2)
	(pixel (+ x 12) (+ y 30) color2)
	(pixel (+ x 13) (+ y 30) color2)
	(pixel (+ x 14) (+ y 30) color2)
	(pixel (+ x 15) (+ y 30) color2)
	(pixel (+ x 16) (+ y 30) color2)
	(pixel (+ x 22) (+ y 30) color2)
	(pixel (+ x 23) (+ y 30) color2)

	;row 31
	(pixel (+ x 1) (+ y 31) color1)
	(pixel (+ x 2) (+ y 31) color2)
	(pixel (+ x 3) (+ y 31) color2)
	(pixel (+ x 10) (+ y 31) color2)
	(pixel (+ x 11) (+ y 31) color2)
	(pixel (+ x 12) (+ y 31) color2)
	(pixel (+ x 13) (+ y 31) color2)
	(pixel (+ x 14) (+ y 31) color2)
	(pixel (+ x 15) (+ y 31) color2)
	(pixel (+ x 23) (+ y 31) color2)

	;row 32
	(pixel (+ x 1) (+ y 32) color1)
	(pixel (+ x 2) (+ y 32) color2)
	(pixel (+ x 3) (+ y 32) color2)	
	(pixel (+ x 12) (+ y 32) color2)
	(pixel (+ x 13) (+ y 32) color2)
)

  (define (mini-meseeks x y size)
  	(if (= size 10)
  		(hideturtle)
  		(begin 
  		(mr-meseeks x y size)
  		(mini-meseeks (+ x 24) y (- size 2)))))


  (mini-meseeks 0 0 16)

  (exitonclick))
 
; Please leave this last line alone.  You may add additional procedures above
; this line.
(draw)