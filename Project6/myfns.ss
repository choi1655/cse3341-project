
; John Choi choi.1655
; CSE3341 Project 6
; This project is incomplete. Only the test cases
; c1 through c5 are verified to be working. Any
; commands that has to do with mylet and myfunction
; are not expected to work at this point.
;
; My current train of thought on implementing mylet:
; I am attempting to start an empty 2D list at the beginning of the program
; to store the variables and its values. There are two functions that handles
; the variable addition and variable lookups. Right now, I'm convinced that
; the variable addition isn't working properly. For the lookup, since it has to be
; done recursively, the program checks the first element in the 2D list to see if
; it contains the variable that we are looking for. If it isn't the variable we are
; looking for, it trims the first element and calls the lookup function recursively.


(define (myinterpreter expr) ; prog (myadd 1 2)
    (cond
      ((null? expr) expr)
      (#t (evalExpr (car (cdr expr)) '(()) ))
    )
)

; expr statement - (myadd x x)
(define (evalExpr statement dictionary)
    ;(display 'InEvalExpr)
    ;(newline)
    ;(display statement)
    ;(newline)
    (cond
        ((integer? statement) statement)
        ((symbol? statement) (lookup dictionary statement))
        ((equal? (car statement) 'myadd) (evalMyAdd (cdr statement) dictionary))
        ((equal? (car statement) 'mymul) (evalMyMul (cdr statement) dictionary))
        ((equal? (car statement) 'mysub) (evalMySub (cdr statement) dictionary))
        ((equal? (car statement) 'myif) (evalMyIf (cdr statement) dictionary))
        ((equal? (car statement) 'mylet) (evalMyLet (cdr statement) dictionary))
    )
)

(define (evalMyAdd statement dictionary) ; x x
    ;(display 'InMyAdd)
    ;(newline)
    (+ (evalExpr (car statement) dictionary) (evalExpr (car (cdr statement)) dictionary))
)

(define (evalMyMul statement dictionary) ; 1 2
    ;(display 'InMyMul)
    ;(newline)
    (* (evalExpr (car statement) dictionary) (evalExpr (car (cdr statement)) dictionary))
)

(define (evalMySub statement dictionary) ; 1 2
    ;(display 'InMySub)
    ;(newline)
    (- (evalExpr (car statement) dictionary) (evalExpr (car (cdr statement)) dictionary))
)

(define (evalMyIf statement dictionary) ; 1 2 4
    ;(display 'InMyIf)
    ;(newline)
    (if (car statement) ;0
        (car (cdr (cdr statement))) ;10
        (car (cdr statement)) ;5
    )
)

(define (evalMyLet statement dictionary) ; x 10 (myadd x x)
    ;(display 'InMyLet)
    ;(newline)
    ; myadd 10 10
    (addVar dictionary (car statement) (evalExpr (car (cdr statement)) dictionary))
    (evalExpr (car (cdr (cdr statement))) dictionary)
)

(define (lookup dictionary id)
    ;(display 'Inlookup)
    ;(newline)
    ;(display dictionary)
    (cond
        ((null? dictionary) -1)
        ((equal? (car (car dictionary)) id) (car (cdr (car dictionary))))

        (#t (lookup (cdr dictionary) id))
    )
)

(define (addVar dictionary id value)
    ;(display 'Inaddvar)
    ;(newline)
    (cons (cons id value) dictionary)
    ;(display dictionary)
    ;(newline)
)

; ID

; Const

; myif
;(define (evalMyIf) expr))
; myadd
;(define (evalMyAdd) expr))
; mymul
;(define (evalMyMul) expr))
; mysub
;(define (evalMySub) expr))
; mylet
;(define (evalMyLet) expr one two))
; mylet
;(define (evalMyLet) expr))
