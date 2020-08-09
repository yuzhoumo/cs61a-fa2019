;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: Big Sad
;;;
;;; Description:
;;;   <This is the big sad
;;;    It is representative
;;;    *PLEASE FILL IN THIS LINE*>

(define (draw)
    (bgcolor "#000020")
    (speed 0)

    (define (xpos point) (car point))
    (define (ypos point) (car (cdr point)))

    (define sad_color "#000022")

    (penup)

    (define (thick_arc center radius width start angle fill)
        (let
            (
                (outer_rad (+ radius (/ width 2)))
                (inner_rad (- radius (/ width 2)))
            )

            (seth 0)
            (goto (+ outer_rad (xpos center)) (ypos center))
            (circle outer_rad start)
            (color fill)
            (pendown)
            (begin_fill)
            (circle outer_rad angle)
            (left 90)
            (forward width)
            (right 90)
            (circle inner_rad (- angle))
            (end_fill)
            (penup)
        )
    )

    (define (thick_circle center radius width fill)
        (thick_arc center radius width 0 360 fill)
    )

    (define (quad p1 p2 p3 p4 fill)
        (goto (xpos p1) (ypos p1))
        (color fill)
        (pendown)
        (begin_fill)
        (goto (xpos p2) (ypos p2))
        (goto (xpos p3) (ypos p3))
        (goto (xpos p4) (ypos p4))
        (goto (xpos p1) (ypos p1))
        (end_fill)
        (penup)
    )

    ; H
    (thick_arc '(-160 30) 30 20 45 225 sad_color)
    (thick_arc '(-160 -40) 40 20 90 -225 sad_color)

    ; A
    (quad '(-10 70) '(-60 -90) '(-40 -90) '(10 70) sad_color)
    (quad '(10 70) '(60 -90) '(40 -90) '(-10 70) sad_color)
    (quad '(-20 -10) '(20 -10) '(15 10) '(-15 10) sad_color)

    ; PPY
    (quad '(110 -90) '(110 70) '(130 70) '(130 -90) sad_color)
    (thick_arc '(110 -10) 72 20 75 -150 sad_color)

    ; face
    (thick_circle '(0 50) 200 5 "yellow")
    (quad '(-75 120) '(-70 120) '(-70 80) '(-75 80) "yellow")
    (quad '(75 120) '(70 120) '(70 80) '(75 80) "yellow")
    (thick_arc '(0 130) 180 5 245 50 "yellow")

    ; k
    (quad '(-30 -220) '(-30 -280) '(-25 -280) '(-25 -220) "white")
    (quad '(15 -220) '(10 -220) '(-30 -252) '(-25 -252) "white")
    (quad '(15 -280) '(10 -280) '(-30 -248) '(-25 -248) "white")
    (thick_circle '(26 -276) 2 2 "white")

    (hideturtle)
    (exitonclick)
 )

; Please leave this last line alone.  You may add additional procedures above
; this line.
(draw)
