(ns joy-of-clojure.chapter4.vector-5-2)

(vec (range 10))
(conj (vec (range 10)) :ade :simbi)

(let [my-vector [:a :b :c]]
  (into my-vector (range 5)))

;; if you want it to return a vector, the first argument to into
;; must be a vector The second arg can be any sequence.
;; you can view the operation of into as similar to an O(n)
;; concatenation based on the size of the second argument
;; vector can't be concatenated any more efficiently than O(n)



;; Clojure can store primitive types inside of vectors using the vector-of function
;; which takes any of :int , :long , :float , :double , :byte , :short , :boolean , or :char
;; as its argument and returns an empty vector

(vector-of :int)
(into (vector-of :int) [Math/PI 2 1.3])
(into (vector-of :char) [100 101 102])
(into (vector-of :int) [1 2 623876371267813267326786327863])
(into (vector-of :double) [2.3 4.5 6.6 7.8 9.3])

;; LARGE VECTORS
;; when collections are small the performance difference between vectors and lists hardly matter.
;; Vectors are particularly efficient at three things
;; relative to lists: adding or removing things from the right end of the collection, accessing
;; or changing items in the interior of the collection by numeric index, and walking
;; in reverse order.

(def a-to-j (vec (map char (range 65 75))))
a-to-j

;; All three of these do the same work, and each returns \E :

(nth a-to-j 4)
(nth a-to-j 10)
(get a-to-j 4)
(get a-to-j 10)
(a-to-j 10)

(seq a-to-j)
(rseq a-to-j)

;; any value in a vector can be changed using the assoc function.
;; clojure does this essentially in constant time using structural sharing between the old and new
;; vectors

(assoc a-to-j 4 "no longer E")
(assoc a-to-j 7 "take me and pour me out")

;; A few higher-powered functions are provided that use assoc internally.

(def try=out (replace {2 :a 3 :you-know-who 8 :read-apply} [1 2 3 4 5 6 7 8 9 0]))
try=out

;; The function assoc-in and update-in are for working with nested structures of vectors / maps

(def matrix [[1 2 3]
             [4 5 6]
             [7 8 9]])
(get-in matrix [0 1])
(get-in matrix [2 2])
(get-in matrix [2 3] :whoops)
(get-in matrix [4 5] :whoops)

(assoc-in matrix [0 2] :finish)
(assoc-in matrix [1 1] :swinging)
(assoc-in matrix [1 1] 'x)

;; update-in function works the same way as assoc, but instead of taking a value to overwrite
;; an existing value, it takes a function to apply to an existing value.
;; It replaces the value at the given coordinates with the return value of the function given

(update-in matrix [0 1] inc)
(update-in matrix [0 2] (fn [x] (* x x)))
(update-in matrix [1 2] + 100)
(update-in matrix [1 2] * 50)

(defn neighbors
  ([size yx] (neighbors [[-1 0] [1 0] [0 -1] [0 1]]
                        size yx))
  ([deltas size yx]
   (filter (fn [new-yx]
             (every? #(< -1 % size) new-yx))
           (map #(vec (map + yx %))
                deltas))))

(neighbors 3 [0 0])
(neighbors 3 [1 1])

(map #(get-in matrix %) (neighbors 3 [0 0]))

;; 5.2.3 vectors as stacks

;; Classic stacks have at least two operations, push and pop.
;; With respect to Clojure vectors,
;; these operations are called conj and pop , respectively. The conj function adds
;; elements to, and pop removes elements from, the right side of the stack.
;; Consequently, peek becomes more important as the primary way to get an item from
;; the top of the stack:

(def my-stack [1 2 3])
(peek my-stack)
(pop my-stack)
(conj my-stack 4)
(+ (peek my-stack) (peek (pop my-stack)))
(- (peek my-stack) (peek (pop my-stack)))

;; Each of these operations completes in essential constant time.
;; Most of the time, a vector that’s
;; used as a stack is used that way throughout its life.
;; If the algorithm involved calls for a stack, use conj , not assoc , for
;; growing the vector; peek , not last ; and pop , not dissoc , for shrinking it.


;; 5.3.4 Using vectors instead of reverse

(defn strict-map1 [f coll]
  (loop [coll coll acc nil]
    (if (empty? coll)
      (reverse acc)
      (recur (next coll)
             (cons (f (first coll)) acc)))))

(strict-map1 - (range 5))

;; This is adequate code, except for that glaring reverse of the final return value.
;; After the entire list has been walked once to produce the desired values, reverse walks it
;; again to get the values in the right order. This is both inefficient and unnecessary.
;; one way to get rid of this reverse is to use vector instead of list as the accumulator.

(defn strict-map2 [f coll]
  (loop [coll coll, acc []]
    (if (empty? coll)
      acc
      (recur (next coll)
             (conj acc (f (first coll)))))))

(strict-map2 - (range 7))


;; 5.2.5 Sub-vector
;; clojure provides a fast way to take a slice of an existing vector such as a-to-j

(def a-to-j (vec (map char (range 65 75))))
a-to-j
(time (get a-to-j 4))
(subvec a-to-j 3 6)
(subvec a-to-j 2 5)
(def tru (subvec a-to-j 2 5))
(time (get tru 1 ))

;; The new sub-vector internally hangs on to the entire original
;; a-to-j vector, making each lookup performed on the new vector cause the sub-vector
;; to do a little offset math and then look it up in the original.

;; 5.2.6 Vectors as maps entries

(first {:width 20 :height 30 :depth 15})

;; But not only does a map entry look like a vector it is really one

(vector? (first {:width 20 :height 30 :depth 15}))

;; This means you can use all the regular functions on it: conj get, and so on.

(doseq [[dimension amount] {:width 10 :height 20 :depth 15}]
  (println (str (name dimension) ": " amount " inches")))