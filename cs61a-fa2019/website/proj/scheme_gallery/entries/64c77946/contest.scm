;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: <pg&e bad>
;;;
;;; Description:
;;;   <pg&e bad
;;;    cal good
;;;    why use many word when few word do trick>

(define (draw)
  (bgcolor (rgb 0 0.196 0.384))
  (penup)
  (goto -180 -100)
  (pendown)
  (color "white")
  (goto -180 100)
  (goto -110 100)
  (goto -110 30)
  (goto -180 30)
  (penup)
  (goto -10 100)
  (pendown)
  (goto -80 100)
  (goto -80 -100)
  (goto -10 -100)
  (goto -10 -30)
  (goto -50 -30)
  (penup)
  (goto 20 0)
  (pendown)
  (goto 70 0)
  (penup)
  (goto 45 25)
  (pendown)
  (goto 45 -25)
  (penup)
  (goto 180 100)
  (pendown)
  (goto 110 100)
  (goto 110 -100)
  (goto 180 -100)
  (penup)
  (goto 180 0)
  (pendown)
  (goto 110 0)
  (penup)
  (color (rgb 0.992 0.71 0.082))
  (goto -210 130)
  (pendown)
  (goto 210 -130)
  (penup)
  (goto 247 0)
  (pendown)
  (circle 247)
  (exitonclick))

; Please leave this last line alone.  You may add additional procedures above
; this line.
(draw)