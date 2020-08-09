;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: PolyNova
;;;
;;; Description:
;;;   <Polys and Gons
;;;    Making the journey around
;;;    Saluntant solis.>

(define (draw)
  ; YOUR CODE HERE

  (speed 0)
  (bgcolor "#000000")

  (define cal
    (cons-stream "#FFFFFF"
      (cons-stream "#FDB515" cal))
  )

  (define (polygon size sides)
    (define (poly-helper num)
      (if (<= num 0)
        nil
        (begin
          (forward size)
          (left (/ 360 sides))
          (poly-helper (- num 1))
        )
      )
    )
    (poly-helper sides)
  )

  (define (poly-circle size sides angle num hue)
    (if (zero? num)
      nil
      (begin
        (color (car hue))
        (polygon size sides)
        (left angle)
        (poly-circle size sides angle (- num 1) (cdr-stream hue))
      )
    )
  )

  (penup)
  (setposition 100 -420)
  (pendown)
  (poly-circle 900 2.5 5 180 cal)

  (exitonclick)
)

; Please leave this last line alone.  You may add additional procedures above
; this line.
(draw)
