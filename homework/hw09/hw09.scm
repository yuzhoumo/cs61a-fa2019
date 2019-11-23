
; Tail recursion

(define (replicate x n)
    (cond ((= n 0) nil)
    (else
        (define (tail lst i)
            (if (= i 0) lst
                (tail (cons x lst) (- i 1))))
        (tail (list x) (- n 1)))))

(define (accumulate combiner start n term)
    (if (= n 0) start
        (accumulate combiner (combiner start (term n)) (- n 1) term)))

(define (accumulate-tail combiner start n term)
    (if (= n 0) start
        (accumulate-tail combiner (combiner start (term n)) (- n 1) term)))

; Streams

(define (map-stream f s)
    (if (null? s)
    	nil
    	(cons-stream (f (car s)) (map-stream f (cdr-stream s)))))

(define multiples-of-three
    (cons-stream 3
        (map-stream (lambda (x) (+ x 3)) multiples-of-three)))

(define (nondecreastream s)
    (define (make-stream substream prev lst)
        (cond ((null? substream)
                (cons-stream lst substream))
            ((< (car substream) prev)
                (cons-stream lst (nondecreastream substream)))
            (else (make-stream (cdr-stream substream) (car substream)
                (append lst (list (car substream)))))))

    (make-stream (cdr-stream s) (car s) (list (car s))))

(define finite-test-stream
    (cons-stream 1
        (cons-stream 2
            (cons-stream 3
                (cons-stream 1
                    (cons-stream 2
                        (cons-stream 2
                            (cons-stream 1 nil))))))))

(define infinite-test-stream
    (cons-stream 1
        (cons-stream 2
            (cons-stream 2
                infinite-test-stream))))
