;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: Customizeable Fireworks (see code!)
;;;
;;; Description:
;;;   Scheme turtle graphics,
;;;    while slow, can still animate
;;;    pretty fireworks :)

; CUSTOMIZEABLE FIREWORKS!

; How can I draw my own fireworks?

;   Add fireworks to the append in my-fireworks.
;   (1) Use the three types of built-in fireworks!
;       The following parameters need to be specified:
;           time: starting time of the firework, as an integer
;           location: (x y) as a list
;           direction: (vx vy) as a list
;           color: maximum color, as a Scheme color
;           size: maximum size, as an integer
;       See my-fireworks as an example.
;   (2) Make your own fireworks!
;       Pass in your fireworks' specifications to generate-firework.
;       The same parameters as above are necessary, in addition to:
;           gradient: 
;           velocity, v0: same as direction
;           gravity: downward vertical acceleration, as an integer
;           lifespan: time after which the firework dies out and final sequence is executed
;           final: function with corresponding parameters that creates any branching fireworks after the main reaches its end
;               see nil-fn as example (which also can be used if no final is needed)
;           tmp: always nil, for tail recursion

; How does it work behind the scenes?

;   A firework is encoded as a list of draw instructions to execute at given (discrete) times.
;   A queue in the main draw function processes the instructions per each time step.
;       The current queue works in quadratic time; I'd improve it to linearithmic using a priority queue but heaps are hard to code in Scheme.
;       (if interested, see "Purely Functional Data Structures" by Okasaki for further reading)
;   Each instruction draws a circle with specified features.


; (beta) Animated fireworks! 

;   This was my original idea, but the animation was incredibly slow ... now I know why it's called turtle :P
;   In theory, it works; in practice, we must do the following:
;       Due to built-in turtle's slow animation time, we must speed up by at least a factor of five (such as using a timelapse).
;       Remove the if so the firework generated moves across the screen.
;       Uncomment the line in generate-firework to simulate movement by deleting old locations
;       Increase the delay in the draw function to create pause between "frames" of drawing

; -------------- CODE BELOW -------------- 

; PART 1: Motion

(define (get-x p) (car p))
(define (get-y p) (car (cdr p)))

; Move the position (p) by the current velocity (v)
(define (move p v)
    (list (+ (get-x p) (get-x v)) (+ (get-y p) (get-y v))))

; Update the velocity (v) by the downward acceleration (g)
(define (gravitate v g)
    (list (get-x v) (- (get-y v) g)))

; PART 2: Drawing

(define (get-time evt) 
    (car evt))

(define (get-r c) (car c))
(define (get-g c) (car (cdr c)))
(define (get-b c) (car (cdr (cdr c))))

; Scale the brightness of a color (c) by a factor (g)
(define (scale-color c g)
    (rgb (* (get-r c) g) (* (get-g c) g) (* (get-b c) g)))

; Draw a circle centered at position (p) with size (s) and color (c), with brightness scaled by (g)
(define (draw-pt c s g p)
    (penup)
    (setposition (+ (* s g) (get-x p)) (get-y p))
    (pendown)
    (color (scale-color c g))
    (begin_fill)
    (circle (* s g))
    (end_fill))

; (beta) Delays drawing for a specified amount of time
(define (draw-delay n)
    (if (> n 0) (begin 
                       (penup)
                       (setposition 0 0)
                       (speed 1)
                       (forward 100)
                       (speed 0)
                       (pendown)
                       (draw-delay (- n 1)))))

(define (process-event evt)
    (draw-pt (car (cdr evt))
             (car (cdr (cdr evt)))
             (car (cdr (cdr (cdr evt))))
             (car (cdr (cdr (cdr (cdr evt)))))))

; Part 3: Creating Fireworks!

(define (generate-firework time color size gradient position velocity v0 gravity lifespan final tmp)
    (if (= lifespan 0) (append tmp (final time color size gradient (car (cdr position)) v0 gravity))
        (begin
            (define (loop grad pos tmp2)
                ; Remove the following line (and corresponding end parenthesis) for animation.
                (if (= (length grad) (length pos))
                (if (or (null? pos) (null? grad))
                    tmp2
                    (loop (cdr grad) (cdr pos) (append tmp2 (list 
                                (list time color size (car grad) (car pos))
                                ; Uncomment to enable deletion.
                                ; (list (+ time 1) (list 0 0 0) size (car grad) (car pos))
                                )))) nil ))
            (generate-firework (+ time 1) color size gradient (append (list (move (car position) velocity)) position) (gravitate velocity gravity) v0 gravity (- lifespan 1) final (append tmp (loop gradient position nil))))))


(define (nil-fn time color size gradient location v0 gravity) nil)

(define pi (* 2 (asin 1)))

; Creates a vector with radius (r) and polar angle (th) in radians
(define (polar r th) (list (* r (cos th)) (* r (sin th))))

(define (radian th) (* (/ pi 180) th))
(define (degree th) (* (/ 180.0 pi) th))

; Gets the angle of a vector (v0), ranging from 0 to pi
(define (ang v0) 
    (if (= (get-x v0) 0) (/ pi 2) 
    (let ((ans (atan (/ (get-y v0) (get-x v0)))))
    (if (>= ans 0)
        ans
        (+ ans pi)))))

; Example firework endings

(define larger-gradient (list 1.0 0.9 0.8 0.7 0.6 0.5 0.4 0.3 0.2 0.1))
(define (basic-final time color size gradient location v0 gravity) (append
        (generate-firework time color (* 0.5 size) gradient (list location) (polar 20 (radian (+ (degree (ang v0)) 0))) (polar 0 0) gravity 10 nil-fn nil)
        (generate-firework time color (* 0.5 size) gradient (list location) (polar 20 (radian (+ (degree (ang v0)) -20))) (polar 0 0) gravity 10 nil-fn nil)
        (generate-firework time color (* 0.5 size) gradient (list location) (polar 20 (radian (+ (degree (ang v0)) 20))) (polar 0 0) gravity 10 nil-fn nil)
        (generate-firework time color (* 0.5 size) gradient (list location) (polar 20 (radian (+ (degree (ang v0)) -40))) (polar 0 0) gravity 10 nil-fn nil)
        (generate-firework time color (* 0.5 size) gradient (list location) (polar 20 (radian (+ (degree (ang v0)) 40))) (polar 0 0) gravity 10 nil-fn nil)
    ))
 
(define (second-final time color size gradient location v0 gravity) (append
        (generate-firework time color (* 5 size) gradient (list location) (polar 20 (radian (+ (degree (ang v0)) 0))) (polar 0 0) (* gravity 5) 15 basic-final nil)
        (generate-firework time color (* 5 size) gradient (list location) (polar 20 (radian (+ (degree (ang v0)) -20))) (polar 0 0) (* gravity 5) 15 nil-fn nil)
        (generate-firework time color (* 5 size) gradient (list location) (polar 20 (radian (+ (degree (ang v0)) 20))) (polar 0 0) (* gravity 5) 15 nil-fn nil)
        (generate-firework time color (* 5 size) gradient (list location) (polar 20 (radian (+ (degree (ang v0)) -40))) (polar 0 0) (* gravity 5) 15 nil-fn nil)
        (generate-firework time color (* 5 size) gradient (list location) (polar 20 (radian (+ (degree (ang v0)) 40))) (polar 0 0) (* gravity 5) 15 nil-fn nil)
    ))

(define (third-final time color size gradient location v0 gravity) (append
        (generate-firework time color (* 0.5 size) gradient (list location) (polar 20 (radian (+ (degree (ang v0)) 0))) (polar 0 0) 0 5 basic-final nil)
        (generate-firework time color (* 0.5 size) gradient (list location) (polar 20 (radian (+ (degree (ang v0)) -120))) (polar 0 0) 0 5 nil-fn nil)
        (generate-firework time color (* 0.5 size) gradient (list location) (polar 20 (radian (+ (degree (ang v0)) 120))) (polar 0 0) 0 5 nil-fn nil)
        (generate-firework time color (* 0.5 size) gradient (list location) (polar 20 (radian (+ (degree (ang v0)) -60))) (polar 0 0) 0 5 nil-fn nil)
        (generate-firework time color (* 0.5 size) gradient (list location) (polar 20 (radian (+ (degree (ang v0)) 60))) (polar 0 0) 0 5 nil-fn nil)
        (generate-firework time color (* 0.5 size) gradient (list location) (polar 20 (radian (+ (degree (ang v0)) 180))) (polar 0 0) 0 5 nil-fn nil)
    ))

; Example fireworks 

(define (basic-firework time location direction color size)
    (generate-firework time color size larger-gradient (list location) direction direction 2 10 basic-final nil))

(define (second-firework time location direction color size)
    (generate-firework time color size larger-gradient (list location) direction direction 2 10 second-final nil))

(define (third-firework time location direction color size)
    (generate-firework time color size larger-gradient (list location) direction direction 2 10 third-final nil))

; Put the fireworks to draw here!
(define my-fireworks (append
    (basic-firework 5 (list -300 0) (polar 30 (radian 70)) (list 1 1 1) 10)
    (basic-firework 5 (list -300 0) (polar 30 (radian 110)) (list 1 1 1) 10)
    (second-firework 5 (list 300 0) (polar 30 (radian 90)) (list 1 0 1) 1)
    (third-firework 5 (list 0 -400) (polar 30 (radian 90)) (list 1 1 0) 10)
    ))

(define (draw)
    (hideturtle)
    (speed 0)
    (bgcolor "black")
    (print my-fireworks)
    (define (firework queue time)
        (if (not (null? queue))
            (begin
                (define (iterate queue q2)
                    (if (null? queue)
                        q2
                        (begin
                            (if (= (get-time (car queue)) time)
                                (begin
                                    (process-event (car queue))
                                    (iterate (cdr queue) q2))
                                (iterate (cdr queue) (cons (car queue) q2))))
                    ))
                (draw-delay 0)
                (firework (iterate queue nil) (+ time 1)))))
    (firework my-fireworks 0)
    (exitonclick))

; Please leave this last line alone.  You may add additional procedures above
; this line.
(draw)
