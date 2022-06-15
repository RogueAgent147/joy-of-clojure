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


(rec-step [1 2 3 4])

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

;; Employing infinite sequences

(take 5 (iterate inc 5))

(take 5 (iterate (partial + 2) 0))

(take 5 (iterate (fn [n] (/ n 2)) 1))

(defn triangle [n]
  (/ (* n (+ n 1)) 2))

(triangle 10)

(map triangle (range 1 11))

(def tri-nums (map triangle (iterate inc 1)))

(take 10 tri-nums)
(take 10 (filter even? tri-nums))

(nth tri-nums 99)

(double (reduce + (take 1000 (map / tri-nums))))

(take 2 (drop-while #(< % 10000) tri-nums))


;; The delay and force macros

(defn defer-expensive [cheap expensive]
  (if-let [good-enough (force cheap)]
    good-enough
    (force expensive)))

(defer-expensive (delay :cheap) (delay (do (Thread/sleep 5000) :expensive)))

(defer-expensive (delay false)
                 (delay (do (Thread/sleep 5000) :expensive)))

(if :truthy-thing
  (let [res :truthy-thing]
    (println res))
  :truthy-thing)
(if-let [res :truthy-thing]
  (println res)
  :truthy-thing)

(defn inf-triangle [n]
  {:head (triangle n)
   :tail (delay (inf-triangle (inc n)))})

(defn head [l] (:head 1))
(defn tail [l] (force (:tail 1)))

(def tri-nums (inf-triangle 1))

(head tri-nums)
(head (tail tri-nums))


(defn taker [n l ]
  (loop [t n src l ret []]
    (if (zero? t)
      ret
      (recur (dec t) (tail src) (conj ret (head src))))))

(defn nthr [l n]
  (if (zero? n)
    (head l)
    (recur (tail l) (dec n))))

(taker 10 tri-nums)
(nthr tri-nums 99)