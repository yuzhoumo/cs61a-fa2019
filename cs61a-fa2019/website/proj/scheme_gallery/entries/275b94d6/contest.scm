;;; Scheme Recursive Art Contest Entry
;;;
;;; Please do not include your name or personal info in this file.
;;;
;;; Title: <stay warm & toasty>
;;;
;;; Description:
;;;   <fear not for we are
;;;    Enlightened by Denero
;;;    let us Get This Bread.>

(speed 0)(screen_width)(screen_height)(hideturtle)
(bgcolor "#f3e3d2")

(define (draw)
;;; actual submission
;;;                                    //==\\
;;;                                   /.....|
;;;                                  |......|
;;;                                  |......|         ==\
;;;     //=...\\\=......=========    |...../       /....\|
;;;   //........\\................===\\..//       /......|
;;; //...........||......./===\........\\\\      |......|
;;; |........@@..||.....|   @@  |.//   \\\....=\|\...//
;;; ||.........../......\\  @  //||      ||.......\\\
;;;  \\........//.........\===/..||  @@@ ||..........\\
;;;    \\====//...................\\\ =///............\\
;;;       \\\...........................................\\
;;;         \\\..........................................\\
;;;            \\\\..............................!!!!!....|
;;;                ||..........................!!!!!!!!!..||
;;;                |..........................!!!!!!!!!!!..|
;;;                |....|.....................!!!!!!!!!!!..|
;;;                |..........................!!!!!!!!!!...|
;;;                ||....\\....................!!!!!!!....||
;;;                 |......\\........../..................|
;;;                 \\.......\======/....................//
;;;                  \\.................................//
;;;                    \\.............................//
;;;                     \\...........................//
;;;                       \\\.....................///
;;;                          \\\=.............=///
;;;                               ===========
;;;Peppa!!! What are you doing? :P

  ;;;crust
  (color "#9f6934")
    (pu)
    (goto 200 140)
    (begin_fill)
    (pd)
    (circle 60 90) ;;; at 140 200
    (goto -140 200)
    (circle 60 180) ;;; at -140 80
    (goto -150 -140)
    (goto 150 -140)
    (goto 140 80)
    (circle 60 90)
    (end_fill)
    (pu)

    ;;;filling
    (color "#f3e3d2")
      (goto 180 140)
      (begin_fill)
      (pd)
      (circle 50 90) ;;; at 140 200
      (goto -130 190)
      (circle 50 180) ;;; at -140 80
      (goto -140 -130)
      (goto 140 -130)
      (goto 130 90)
      (circle 50 90)
      (end_fill)
      (pu)

    ;;;smile
    (color "#000000")
      (goto -40 40)
      (pd)
      (begin_fill)
      (circle 10)
      (end_fill)
      (pu)
      (goto 45 40)
      (pd)
      (begin_fill)
      (circle 10)
      (end_fill)
      (pu)
      (goto 10 -10)
      (circle 20 180)
      (pd)
      (begin_fill)
      (circle 20 180)
      (end_fill)


    (define (diag a b c)
      (pu)
      (color "#d98695")
      (goto a b)
      (pd)
      (goto (+ a c) (- b (* c 2)))
    )

  (diag -80 15 5)
  (diag -70 15 7)
  (diag -60 15 7)
  (diag -50 15 5)

  (diag 70 15 5)
  (diag 60 15 7)
  (diag 50 15 7)
  (diag 40 15 5)
  (exitonclick))

; Please leave this last line alone.  You may add additional procedures above
; this line.
(draw)
