;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: <REDACTED>
;;;
;;; Description:
;;;   leaving my mark here
;;;    how long will this be around
;;;    maybe forever

(define (draw)
  (real_draw 1)
  (exitonclick))

(define (real_draw step)
	(cond
		((= step 500) nil)
		(else
			(begin
				(penup)
				(forward (* 75 (sin step)))
				(pendown)
				(right (* step 1.62))
				(circle step)
				(real_draw (+ step 1))
			)
		)

	)
)

; Please leave this last line alone.  You may add additional procedures above
; this line.
(draw)