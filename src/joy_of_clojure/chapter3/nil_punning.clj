(ns joy-of-clojure.chapter3.nil-punning)

;; Because empty collections act like true in Boolean, you
;; need an idiom for testing whether there's anything in a collection
;; to process

(if []
  true
  false)

(if nil
  true
  false)

;; The seq function returns a sequence view of a collection, or nil
;; if the collection is empty

(seq [1 2 3])
(seq [])

;; clojure's empty sequences are instead truthy and therefore to use one as a pun
;; (a term with the same behavior) will lead to heartache and despair

;; what comes to mind is to use empty? leading to this =>

(empty? [])

(when-not (empty? []) true)                                 ; to awkward

;; use this instead

(defn print-seq [s]
  (when (seq s)
    (prn (first s))
    (recur (rest s))))

;; Thankfully the use of seq allows you to properly check for an empty collection

(print-seq [])
(print-seq [1 2 3])