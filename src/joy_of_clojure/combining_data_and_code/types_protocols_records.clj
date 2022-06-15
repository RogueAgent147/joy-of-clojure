(ns joy-of-clojure.combining-data-and-code.types-protocols-records)

;; Clojure provides facilities for creating logically grouped
;; polymorphic functions that are both simple and performant
;; types records and protocols.
;; We'll delve into topics in this section and introduce
;; The concept of abstraction oriented programming
;; predicated on the creation of logical groupings

;; Records

{:val 5 :1 nil :r nil}

;; it misses a type of it own
;; isn't as far as clojure is concerned a TreeNode

(defrecord TreeNode [val l r])

(TreeNode. 5 nil nil)

;; Replacing defstruct with defrecord

(defn change-age [p] (assoc p :age 286))

(defstruct person :fname :lname)
(change-age (struct person "Immanuel" "Kant"))

(defrecord Person [fname lname])
(change-age (Person. "Immanuel" "Kant"))                    ; change age works with either struct or records -no change is required
;; Only the definition and the mechanism of instantiation need to be updated

;; Records can't serve as functions
;; Records are never equal to maps with the same key/value mappings

;; You still look up values in records by doing (:keyword obj) ; it’s just that if obj is a
;; record, this code will run dramatically faster.


;; Protocols
;; But in order for a protocol to do any good, something must implement it.
;; Protocols are implemented using one of the extend forms: extend, extend-type or extend-protocol
;; extend-type and extend-protocol are convenience macros for when you want to provide multiple functions
;; for a given type

;; Isaiah 6

(defprotocol FIXO
  (fixo-push [fixo value])
  (fixo-pop [fixo])
  (fixo-peek [fixo])
  (fixo-into [fixo]))

(extend-type clojure.lang.IPersistentVector
  FIXO
  (fixo-push [vector value]
    (conj vector value)))

(fixo-push [12 3 1 2 123 ] 5/2)

(extend-type clojure.lang.APersistentMap
  FIXO
  (fixo-push [myapp value]
    (assoc myapp :age value)))

(fixo-push {:fname "Adoyi" :lname "Stephen"} 24)

(extend-type clojure.lang.APersistentSet
  FIXO
  (fixo-into [values]
    (into [2] values)))

(fixo-into #{1 2  3 4 5  6})

(type (fixo-into #{1 2  3 4 5  6}))

;;// Clojure-style mixins
;; As we've proceeded through this section you'll notice that you can extend the FIXO protocol's
;; fixo-push function in isolation. This works fine but you might want to take note of the implications
;; of this approach

(defprotocol StringOps
  (rev [s])
  (upp [s]))

(extend-type String
  StringOps
  (rev [s] (clojure.string/reverse s)))
(rev "Works")

(extend-type String
  StringOps
  (upp [s] (clojure.string/upper-case s)))

(upp "Works")
(rev "Works?")                                              ; antithetical to the common notions on mixins

(def rev-mixins
  {:rev clojure.string/reverse})
(def upp-mixins
  {:upp (fn [this] (.toUpperCase this))})
(def fully-mixed
  (merge upp-mixins rev-mixins))
(extend
  String StringOps fully-mixed)
(-> "Works"
    upp
    rev)

;; Sharing Method Implementations
;; how best to avoid repeating code when similar objects implement the same protocol method
;; The simplest solution is to write a regular function that builds on a protocols methods.

(defn fixo-intoo [c1 c2]
  (reduce fixo-push c1 c2))

(seq (fixo-intoo [5] [2 4 6 7]))

;; If this isn’t the case, you may need the more nuanced solution
;; provided by the extend function.

(def tree-node-fixo
  {:fixo-push (fn [node value]
                (conj node value))
   :fixo-peek (fn [node]
                (if (:l node)
                  (recur (:l node))
                  (:val node)))
   :fixo-pop (fn [node]
               (if (:l node)
                 (TreeNode. (:val node) (fixo-pop (:l node)) (:r node))
                 (:r node)))})

;; THE REIFY METHOD