(ns joy-of-clojure.chapter6.laziness)

;; The expression (+ 2 2) is eagerly evaluated,
;; in that its result 4 is passed on to the subtraction
;; function during the actual call and not at the point of need.


(- 13 (+ 2 2))


(defn if-chain [x y z]
  (if x
    (if y
      (if z
        (do (println "Made it!")
            :all-truthy)))))

(if-chain () 42 true)
(if-chain true true false)

(defn and-chain
  [x y z]
  (and x y z (do println "Made it!" :all-truthy)))
(and-chain () 42 true)

;; Understanding the lazy-seq recipe
;;  takes a sequence and makes a deeply nested structure from:

;; (steps [1 2 3 4])
;; => [1 [2 [3 [4 []]]]]

(defn rec-step [[x & xs]]
  (if x
    [x (rec-step xs)]
    []))

;; lazy-seq
;; rest instead of next
;; prefer higher-order functions when processing sequences.
;; Don't hold on to your head.


;; (rec-step [1 2 3 4])

;;; (rec-step (range 200000))

(defn lz-rec-step
  ([n]
   (if (seq n)
     (lazy-seq [(first n) (lz-rec-step (rest n))])
     [])))

(lz-rec-step [1 2 3])

(class (lz-rec-step [1 2 3 4]))

(defn lz-rec-step-original [s]
  (lazy-seq
    (if (seq s)
      [(first s ) (lz-rec-step (rest s))]
      [])))

(lz-rec-step-original [1 2 3 4])

(class (lz-rec-step-original [1 2 3 4 5]))

(dorun (lz-rec-step-original (range 200000)))


(defn little-inc
  ([] (little-inc 1))
  ([n] (lazy-seq (cons n (little-inc (inc n))))))

(take 5 (little-inc))
(take 20 (little-inc))

(defn our-sqr [n]
  (println "our-sqr")
  (* n n))

(defn square
  ([] (square 1))
  ([n] (lazy-seq (cons (our-sqr n) (square (inc n))))))

(take 5 (square))
(take 5 (square 5))


;; the use of lazy-seq as the outermost form in the function and the use of rest .
;; You’ll see this pattern over and over in Clojure code found in the wild.


(let [r (range 1e9)]
  (first r)
  (last r))

(let [r (range 1e9)]
  (last r)
  (first r))



