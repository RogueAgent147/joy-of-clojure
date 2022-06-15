(ns joy-of-clojure.chapter4.be-rational)

;; clojure provides a data type representing a rational number, and all
;; it's core mathematics functions operate with rational numbers

;; clojure's rational number type provides a way to retain perfect
;; accuracy when needed.

(class 1.0E-430000000M)

;;(class 1.0E-4300000000M)

;; floating-point arithmetic is neither
;; associative nor distributive, which may lead to the shocking result that many floating
;; point calculations are dependent on the order in which they’re carried out


(def a 1.0e50)
(def b -1.0e50)
(def c 17.0e00)

(+ (+ a b) c)                                               ;associativity should guarantee 17.0 for both

(+ a (+ b c))

;; for absolutely precise calculations rationals are the best choice.

;; How to be rational
;; clojure provides functions that can help maintain your sanity
;; ratio? rational? and rationalize

;;  the best way to ensure that your calculation remains accurate as possible is to do them all
;; using rational numbers.

(def a (rationalize 1.0e50))
(def b (rationalize  -1.0e50))
(def c (rationalize 17.0e00))

(+ (+ a b) c)                                               ; Associativity preserved
(+ a (+ b c))

(rationalize Math/PI)
(rationalize 1.5)
;; you can use rational? to check whether a given number is rational and then use rationalize to
;; convert it to one

;; There are a few rules of thumb to remember if you  want to maintain
;; perfect accuracy in your computations

;; Never use Java math libraries unless they return results of BigDecimal , and even
;; then be suspicious.
;; Don’t rationalize values that are Java float or double primitives.
;; If you must write your own high-precision calculations, do so with rationals
;; Only convert to a floating-point representation as a last resort

;; you can extract the constituent parts of a rational using the denominator and numerator

(numerator (/ 123 10))
(denominator (/ 123 10))