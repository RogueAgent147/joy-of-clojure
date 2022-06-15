(ns joy-of-clojure.chapter4.understand-precision)

;; it requires an unusual mind to undertake the analysis of the
;; obvious.

;; Truncation refers to limiting accuracy for a floating-point
;; number based on a deficiency in the corresponding representation.

;; imadeuapi  uses clojure's literal notation, a suffix character
;; M, to declare a value as requiring arbitrary decimal representation

(let [imadeuapi 3.14159265358979323846264338327950288419716939937M]
  (println (class imadeuapi))
  imadeuapi)

(let [butieatedit 3.14159265358979323846264338327950288419716939937]
  (println (class butieatedit))
  butieatedit)


;; Promotion

(def clueless 9)

(class clueless)                                            ; Long by default

(class (+ clueless 9000000000000000))                       ; Long can hold large values

(class (+ clueless 90000000000000000000N))                   ; But when to large type promotes to Bigint

(class (+ clueless 9.0))                                    ; Floating point doubles are contagious

;; Overflow

;; integer and float values in java are subject to overflow errors.
;; when working with clojure overflow isn't an issue thanks to the promotion

(+ Long/MAX_VALUE Long/MAX_VALUE)

(unchecked-add (Long/MAX_VALUE)  (Long/MAX_VALUE))

;; Underflow
;; when number is so small its value collapse into zero
;; occurs only with floating point numbers

(float 0.0000000000000000000000000000000000000000000001)

1.0E-430

;; Rounding errors

(let [approx-interval (/ 209715 2097152)
      actual-interval (/ 1 10)
      hours (* 3600 100 10)
      actual-total (double (* hours actual-interval))
      approx-total (double (* hours approx-interval))]
  (- actual-total approx-total))

;; in clojure any computation involving even a single double results in a value that's a double

(+ 0.1M 0.1M 0.1M 0.1 0.1M 0.1M 0.1M 0.1M 0.1M 0.1M)

(type (+ 0.1M 0.1M 0.1M 0.1 0.1M 0.1M 0.1M 0.1M 0.1M 0.1M))

(type (+ 4 2.0))

