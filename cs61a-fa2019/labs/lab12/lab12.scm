(define (partial-sums stream)
    (define (make-result sum substream)
        (if (null? substream) nil
            (let ((sum (+ sum (car substream))))
                (cons-stream sum
                (make-result sum
                    (cdr-stream substream))))))

    (make-result 0 stream))
