;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: <Your title here>
;;;
;;; Description:
;;;   <"It's a leap of faith.
;;;    That's all it is, Miles.
;;;    A leap of faith." -Peter Parker>
(define (combine-with f xs ys)
    	(if (or (null? xs) (null? ys))
    		nil
    		(cons-stream (f (car xs) (car ys))
    		(combine-with f (cdr-stream xs) (cdr-stream ys)))))
(define fib (cons-stream 0 (cons-stream 1 (combine-with + fib (cdr-stream fib)))))
(define (draw)
    (bgcolor "black")
    (define (draw-spirals amount rot factor)
    	(cond ((> amount 0) 
    		(draw-spiral fib 0 factor)
    		(goto 0 0)
    		(right rot)
    		(draw-spirals (- amount 1) rot factor))
    	)
    )
    (define (draw-spiral seq count factor)
		  (cond ((< count 16)
		  	(right 90)
		  	(circle (* factor (car seq)) -180)
		  	(draw-spiral (cdr-stream seq) (+ count 1) factor))
		  )
	)
	(speed 5000)
	(color "#E8DE2A")
	(begin_fill)
	(define num-spirals 30)
	(draw-spirals num-spirals (/ 360 num-spirals) 0.3)
	(end_fill)
	(penup)	
  (exitonclick))

; Please leave this last line alone.  You may add additional procedures above
; this line.
(draw)