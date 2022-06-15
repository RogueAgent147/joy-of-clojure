(ns joy-of-clojure.chapter3.Truthiness)


;; Truthfulness may be an important virtue, but it doesn't
;; come up much in programming

;; what's truth
;; every value looks like true to if, except for false and nil.

(if true :truthy :falsey)
(if [] :truthy :falsey)
(if nil :truthy :falsey)
(if false :truthy :falsey)

;; every object is "true" all the time, unless it's nil or false.

;; Don't create Boolean objects
;; it is possible to create an object that looks a lot like, but isn't actually, false

(def evil-false (Boolean. "false"))                         ; never do this
evil-false
(if evil-false :truthy :falsey)

;; use this instead
(if (Boolean/valueOf "false") :truthy :falsey)


;; nil vs. false

(when (nil? nil)
  "Actually nil, not false")

;; keeping in mind the basic rule that everything in Clojure is truthy unless it's false or nil