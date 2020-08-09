


; Please leave this last line alone.  You may add additional procedures above
; this line.

(bgcolor "black")
(speed 8000)

(define c
  (list
   "#ff0000" "#ff0500" "#ff0a00" "#ff0f00" "#ff1400"
   "#ff1800" "#ff1d00" "#ff2200" "#ff2700" "#ff2c00"
   "#ff3100" "#ff3600" "#ff3b00" "#ff4000" "#ff4400"
   "#ff4900" "#ff4e00" "#ff5300" "#ff5800" "#ff5d00"
   "#ff6200" "#ff6700" "#ff6b00" "#ff7000" "#ff7500"
   "#ff7a00" "#ff7f00" "#ff8400" "#ff8900" "#ff8e00"
   "#ff9300" "#ff9800" "#ff9d00" "#ffa100" "#ffa600"
   "#ffab00" "#ffb000" "#ffb500" "#ffba00" "#ffbf00"
   "#ffc400" "#ffc900" "#ffce00" "#ffd300" "#ffd800"
   "#ffdd00" "#ffe100" "#ffe600" "#ffeb00" "#fff000"
   "#fff500" "#fffa00" "#ffff00" "#f5ff00" "#ebff00"
   "#e2ff00" "#d8ff00" "#ceff00" "#c4ff00" "#baff00"
   "#b1ff00" "#a7ff00" "#9dff00" "#93ff00" "#89ff00"
   "#7fff00" "#76ff00" "#6cff00" "#62ff00" "#4eff00"
   "#45ff00" "#3bff00" "#31ff00" "#27ff00" "#1dff00"
   "#14ff00" "#0aff00" "#00ff00" "#00ff0a" "#00ff14"
   "#00ff1d" "#00ff27" "#00ff31" "#00ff3b" "#00ff45"
   "#00ff4e" "#00ff58" "#00ff62" "#00ff6c" "#00ff76"
   "#00ff80" "#00ff89" "#00ff93" "#00ff9d" "#00ffa7"
   "#00ffb1" "#00ffba" "#00ffc4" "#00ffce" "#00ffd8"
   "#00ffe2" "#00ffeb" "#00fff5" "#00ffff" "#00f5ff"
   "#00ebff" "#00e2ff" "#00d8ff" "#00ceff" "#00c4ff"
   "#00baff" "#00b1ff" "#00a7ff" "#009dff" "#0093ff"
   "#0089ff" "#007fff" "#0076ff" "#006cff" "#0062ff"
   "#0058ff" "#004eff" "#0045ff" "#003bff" "#0031ff"
   "#0027ff" "#001dff" "#0014ff" "#000aff" "#0000ff"
   "#0500ff" "#0b00ff" "#1000ff" "#1500ff" "#1b00ff"
   "#2000ff" "#2500ff" "#2b00ff" "#3000ff" "#3500ff"
   "#3b00ff" "#4000ff" "#4500ff" "#4b00ff" "#5000ff"
   "#5600ff" "#5b00ff" "#6000ff" "#6600ff" "#6b00ff"
   "#7000ff" "#7600ff" "#7b00ff" "#8000ff" "#8600ff" "#8b00ff")
  )

(define (get i lst)
        (cond
          ((= i 0) (car lst))
          ((>= i 154)  (get (- i 154) lst)        )
          (else (get (- i 1) (cdr lst)))
        ))

(define (side1) (pendown) (right 157) (forward 200))
(define (side2) (pendown) (right 137) (forward 200))
(define (side3) (pendown) (right 127) (forward 200))


(define (repeat f n) (begin (color (get n c))(f) (if (= n 1) (f) (repeat f ( - n 1)))) (penup))

(define (draw1) (setposition (- 250) (+ 36)) (repeat side1 100) )
(define (draw2) (setposition (+ 30) (+ 41)) (repeat side2 200) )
(define (draw3) (setposition (+ 340) (- 180)) (repeat side3 300) )



(define (draw) (begin (draw1) (draw2) (draw3)))

(draw)