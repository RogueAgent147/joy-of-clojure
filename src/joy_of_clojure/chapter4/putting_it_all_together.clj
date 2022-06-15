(ns joy-of-clojure.chapter4.putting-it-all-together)

;; The case study for this chapter is to design and implement a simple
;; function to locate the positional index of an element in a sequence.

;; The function named pos, must
;; work on any collection type, returning indices corresponding to some
;; value
;; Return a numerical index for sequential collections or associated key for maps
;; and sets
;; Otherwise return nil

;; IMPLEMENTATION

(defn pos [e coll]
  (let [cmp (if (map? coll)
              #(= (second %1) %2)
              #(= %1 %2))]
    (loop [s coll idx 0]
      (when (seq s)
        (if (cmp (first s) e)
          (if (map? coll)
            (first (first s))
            idx)
          (recur (next s) (inc idx)))))))

(pos 3 [:a 1 :b 2 :c 3 :d 4])

(pos :foo [:a 1 :b 2 :c 3 :d 4])

(pos 3 {:a 1 :b 2 :c 3 :d 4})

(pos \3 ":a 1 :b 2 :c 3 :d 4")


(defn index [coll]
  (cond
    (map? coll) (seq coll)
    (set? coll) (map vector coll coll)
    :else (map vector (iterate inc 0) coll)))

(index [:a 1 :b 2 :c 3 :d 4])
(index {:a 1 :b 2 :c 3 :d 4})
(index #{:a 1 :b 2 :c 3 :d 4})

(defn pos [e coll]
  (for [[i v] (index coll) :when (= e v)] i))

(pos 3 [:a 1 :b 2 :c 3 :d 4])

(pos #{3 4} {:a 1 :b 2 :c 3 :d 4})