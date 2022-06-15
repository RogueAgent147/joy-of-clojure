(ns joy-of-clojure.chapter-7.functions-all-forms)

;; not only can clojure collection act as functions
;; but clojure functions, but clojure functions can
;; also act as data
(map [:chi-town :phthor :beowulf :grendel] #{0 3})
(map [:chi :fist :reckon :code] #{1 2})

;; first-class functions
;; application of functions to data

;; It can be created on demand.
;; It can be stored in a data structure.
;; It can be passed as an argument to a function.
;; It can be returned as the value of a function.

;; creating functions on demand using composition

(def fifth (comp first rest rest rest rest))
(fifth [1 2 3 4 5])

(defn fnth [n]
  (apply comp
         (cons first
               (take (dec n) (repeat rest)))))

((fnth 5) '[a b c d e])

(map (comp keyword #(.toLowerCase %)
           name)
     '(a B C))

;; creating functions on demand using partial functions

((partial + 5) 100 200)                                     ; is similar to
(#(apply + 5 %&) 100 200)

;; reversing truth with complement

(let [truthiness (fn [v] v)]
  [((complement truthiness) true)
   ((complement truthiness) 42)
   ((complement truthiness) false)
   ((complement truthiness) nil)])

((complement even?) 2)

;; using function as data

(defn join
  {:test (fn []
           (assert
             (= (join "," [1 2 3]) "1,3,3")))}
  [sep s]
  (apply str (interpose sep s)))

(clojure.test/run-tests)

;; Higher-order functions

(sort [1 5 7 0 -42 13])
;; the sort function works on many types of elements

(sort ["z" "x" "a" "aa"])

(sort [(java.util.Date.) (java.util.Date. 100)])

(sort [[1 2 3], [-1 0 1] [3 2 1]])

(sort > [7 1 4])

;; But sort fails if given seqs containing elements that aren't mutually comparable

(sort ["z" "x" "a" "aa" 1 5 8])
(sort [{:age 99}, {:age 13}, {:age 7}])

(sort [[:a 7], [:c 13], [:b 21]])

;; That is in the examples above you want to sort by second element in the vectors

(sort second [[:a 7] [:c 13] [:b 21]])

;; instead clojure provides the sort-by function which takes a function as an argument that is used
;; to preprocess each sortable element

(sort-by :age [{:age 99}, {:age 13}, {:age 7}])
(sort-by second [[:a 7] [:c 13] [:b 21]])
(sort-by str  ["z" "x" "a" "aa" 1 5 8])

(def plays [{:band "Burial" :plays 979 :loved 9}
            {:band "Eno" :plays 2333 :loved 15}
            {:band "Bill Evans" :plays 979 :loved 9}
            {:band "Magma" :plays 2665 :loved 31}])

(def sort-by-loved-ratio (partial sort-by #(/ (:plays %) (:loved %))))

(sort-by-loved-ratio plays)

;; Functions As Return Values

(defn columns [column-names]
  (fn [row]
    (vec (map row column-names))))

(def col-clos (columns [:plays :loved :band]))
(col-clos  {:band "Burial" :plays 979 :loved 9})

(vec (map (plays 0) [:plays :loved :band]))

(sort-by (columns [:plays :loved :band]) plays)

;; pure functions

;; The function always returns the same result, given the same arguments.
;;  The function doesn’t cause any observable side effects.

;; Referential Transparency

(defn keys-apply [f ks m]
  (let [only (select-keys m ks)]
    (zipmap (keys only)
            (map f (vals only)))))

(keys-apply #(.toUpperCase %) #{:band} (plays 0))

(defn manip-map [f ks m]
  (merge m (keys-apply f ks m)))

(manip-map #(int (/ % 2)) #{:plays :loved} (plays 0))

(defn mega-love! [ks]
  (map (partial manip-map #(int (* % 1000)) ks) plays))

(mega-love! [:loved])


;; Named arguments

(defn slope [& {:keys [p1 p2] :or {p1 [0 0] p2 [1 1]}}]
  (float (/ (- (p2 1) (p1 1))
            (- (p2 0) (p1 0)))))

(slope :p1 [4 15] :p2 [3 21])

(slope :p2 [2 1])

(slope)


;; Constraining functions with pre- and post-conditions


(defn slope* [p1 p2]
  {:pre [(not= p1 p2) (vector? p1) (vector? p2)]
   :post [(float? %)]}
  (/ (- (p2 1) (p1 1))
     (- (p2 0) (p1 0))))

(slope* [10 10] [10 10])

(slope [10 1] '(1 20))

(slope [10 1] [1 20])

(slope [10.0 1] [1 20])

;; DECOUPLING ASSERTIONS FROM FUNCTIONS

(defn put-thing [m]
  (into m {:meat "beef" :veggie "broccoli"}))

(put-thing {})

(defn vegan-constraints [f m]
  {:pre [(:veggie m)]
   :post [(:veggie %) (nil? (:meat %))]}
  (f m))

(vegan-constraints put-thing {:veggie "carrot"})

(defn balanced-diet [f m]
  {:post [(:meat %) (:veggie %)]}
  (f m))

(balanced-diet put-thing  {})

(defn finicky [f m]
  {:post [(= (:meat %) (:meat m))]}
  (f m))

(finicky put-thing {:meat "chicken"})