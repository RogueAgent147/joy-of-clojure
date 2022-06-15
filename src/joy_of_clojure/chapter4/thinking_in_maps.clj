(ns joy-of-clojure.chapter4.thinking-in-maps)

;; It’s difficult to write a program of any significant size without the need for a map of
;; some sort.

;; The use of maps is ubiquitous in writing software because, frankly, it’s hard
;; to imagine a more robust data structure.

;; Hash maps
(hash-map :a 1, :b 2, :c 3, :d 4, :e 5)

;; Clojure hash maps support heterogeneous keys, meaning they can be of any type and
;; each key can be of a differing type, as this code shows:

(let [m {:a 1, 1 :b, [1 2 3] "4 5 6"}]
  [(get m :a) (get m [1 2 3])])

;; Providing a map to the seq function returns a sequence of map entries:

(seq {:a 1 :b 2})

;; In fact, you can create a new hash map using this precise structure:

(into {} [[:a 1] [:b 2]])

;; if your embedded pairs aren't vectors, they can be made to be for building a new map:

(into {} (map vec '[(:a 1) (:b 2)]))

;; Another fun way to build a map is to use zipmap to
;; “zip” together two sequences, the first of which contains
;; the desired keys and the second their corresponding values:

(zipmap [:a :b] [1 2])

;; 5.6.2 keeping your keys in order with sorted maps

(sorted-map :thx 1138 :r2d 2)
(sorted-map :a 44 :b 66 :c 33)

;; You may require an alternative key ordering, or perhaps an ordering for keys that
;; aren’t easily comparable. In these cases, you must use sorted-map-by , which takes an
;; additional comparison function:

(sorted-map "bac" 2 "abc" 9)
(sorted-map-by #(compare (subs %1 1) (subs %2 1)) "bac" 2 "abc" 9)

(sorted-map :a 1, "b" 2)

(assoc {1 :int} 1.0 :float)
(assoc (sorted-map 1 :int) 1.0 :float)

;; keeping your insertions in order with the array maps
;; clojure provides a special map, called an array map that ensures insertion ordering:

(seq (hash-map :a 1 :b 2 :c 3))
(seq (array-map :a 1 :b 2 :c 2))

(array-map :a 1 :b 2 :c 2)

;; when insertion order is important, you should explicitly use an array map


