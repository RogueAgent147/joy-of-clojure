(ns joy-of-clojure.collection-types)

;; It’s better to have 100 functions operate on one data
;; structure than 10 functions on 10 data structures.


(def ds (into-array [:willie :barnabas :adam]))
(seq ds)
(aset ds 1 :quentin)
(seq ds)

(def my-array (into-array Integer/TYPE [1 2 3 4 5]))

(aset my-array 2 30)
(seq my-array)
(into [] my-array)

;; but using clojure's persistent data structures paints a different picture

(def ds1 [:willie :barnabas :adam])
ds1
(def ds2 (replace {:barnabas :quentin} ds1))
ds2

(def my-persistance [1 2 3 4 5 ])

(seq (replace {1 30} my-persistance))

;; Nothing really changes on the persistant side just a modification of the original data and allocation
;; of new memory for it.


;; A sequential collection is one that holds a series of values without reordering them.
;; A sequence is a sequential collection that represents a series of values that may or
;; may not exist yet.
;; Clojure has a simple API called seq for navigating collections


;;  Clojure classifies each collection data type into one of
;; three logical categories or partitions: sequentials, maps, and sets

;; sequentials -> list, vectors and java list such as java.util.Arraylist.
;; the other partition are set or map

(= [ 1 2 3] '(1 2 3))

(= [1 2 3] #{1 2 3})


(class (hash-map :a 1))
(seq (hash-map :a 1))
(class (seq (hash-map :a 1)))

(seq (keys (hash-map :a 1)))
(class (keys (hash-map :a 1)))

;; Algorithmic complexity is a system for describing the relative space and time costs
;; for algorithms.