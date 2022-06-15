(ns joy-of-clojure.joy.chapter2.clojure-fire-hose
  (:require joy.ch2)
  (:require clojure.set)
  (:require [clojure.set :as s])
  (:require [clojure.string :refer (capitalize)]))

(map capitalize ["kilgore" "trout"])

;; The
;; difference is that a namespace symbol can only be used as a qualifier, whereas a class
;; symbol can also be referenced independently

clojure.set
java.lang.Object

(joy.ch2/report-ns)
(joy.ch2/hello)

(clojure.set/intersection #{1 2 3} #{3 4 5})
(clojure.set/union #{1 2 3} #{3 4 5})

(s/union #{1 2 3} #{3 4 5})
(s/difference #{1 2 3} #{3 4 5})
;; it provides scalar type such as integer strings and floating point number
;; each representing a single unit of data.

;; Clojure provides several categories of scalar
;; data types: integers, floats, rationals, symbols, keywords, strings, characters, Booleans,
;; and regex patterns.

;;list

'(1 2 3 4)
'()
'(:fred ethel)
'(1 2 (a b c) 4 5)

;; vector

[1 2 :a :b :c]

;; maps

{1 "one", 2 "two", 3 "three"}

;; set

#{1 2 "three" :four 0x5}

;; Making things happen: calling functions

(vector 1 2 3)


(def x 42)
;; Vars are not variables

;; Creating named functions with def and defn

(defn make-set
  ([x] #{x})
  ([x y] #{x y})
  ([x y & rest] #{x y rest}))

(make-set 3)
(make-set 3 4)
(make-set 48 20 22 47)

(hash-set 5)
(hash-set 6 7 8 9)

(defn arity2+ [first second & more]
  (vector first second more))

(arity2+ 1 2 3 4)

(def make-list0 #(list))
(make-list0)

(def make-list2 #(list %1 %2))
(make-list2 1 2)

(def make-list2+ #(list %1 %2 %&))

;; Local loops and blocks

(do
  (def x 5)
  (def y 4)
  (+ x y)
  [x y])

(let [r  5
      pi  3.1415
      r-squared (* r r)]
  (println "radius is " r)
  (* pi r-squared))

(defn print-down-from [c]
  (when (pos? c)
    (println c)
    (recur (dec c))))

(defn sum-down-from [sum x]
  (if (pos? x)
    (recur (+ sum x) (dec x))
    sum))

(sum-down-from 0 6)


(defn sum-down-from-* [initial-x]
  (loop [sum 0
           x initial-x]
    (if (pos? x)
      (recur (+ sum x) (dec x))
      sum)))

(sum-down-from-* 13)

;; if you try to use they recur from somewhere other than a tail position, clojure will remind
;; you at compile time

(fn [c]
  (recur c)
  (println c))

;; instead

(def glo (fn adage
           ([x y] (if (pos? x)
                    (recur (+ y x) (dec x))
                    y))))
(glo 0 4)

(cons 1 [2 3])

;; quoting

(def age 9)
(quote age)

(quote (cons 1  [2 3]))

(cons 1 ( 2 3))

(cons 1 (quote (2 3)))

(cons 1 '(2 3))

[1 ( + 2 3)]
'(1 (+ 2 3))

clojure.core/map
clojure.set/union

(map even? [1 2 3])

(clojure.core/map clojure.core/even? [12 21 3 4])

`(+ 10 (* 3 2))

`(+ 10 ~(* 3 2))

(let [x 2]
  `( 1 ~x 3))

`(1 ~(2 3))

(let [x '(2 3)]
  `(1 ~x))

(let [x '(2 3)]
  `(1 ~@x))


;; Auto-gensym

`potion#


;; using host libraries via interoperability

java.util.Locale/JAPAN

(Math/PI)
(Math/sqrt 16)

(new java.awt.Point 0 1)

(new java.util.HashMap {"foo" 42 "bar" 9 "baz" "quux"})

(java.util.HashMap. {"foo" 42 "bar" 9 "baz" "quux"})

(js/Date.)

;; setting instance fields

(let [origin (java.awt.Point. 0 0)]
  (set! (.-x origin) 15)
  (str origin))

;; Exceptional Circumstances

;; throw and catch

(throw (Exception. "I done throwed"))

(defn throw-catch [f]
  [(try
     (f)
     (catch ArithmeticException e "No dividing by zero!")
     (catch Exception e (str "You are so bad " (.getMessage e)))
     (finally (println "returning...")))])

(throw-catch #(/ 10 5))

(throw-catch #(/ 10 0))

(throw-catch #(throw (Exception. "Crybaby")))

(throw (Exception. "Crybaby"))

(try
  (throw (Error. "I done throwed in CLJS"))
  (catch js/Error err "I done catched in CLJS"))


;; 2.10   Modularizing code with namespaces

;; Clojure’s namespaces provide a way to bundle related functions, macros, and values.

;; To create a new namespace, you can use the ns macro

(ns joy.ch2)

*ns*

(defn hello []
  (println "Hello Cleveland"))

(defn report-ns []
  (str "The current namespace is " *ns*))

(report-ns)

hello

;; you can create a namespace at any time

(ns joy.another)

*ns*
(ns joy-of-clojure.joy.chapter2.clojure-fire-hose)

;; loading other namespaces with :require


(ns joy.yet-another
  (:refer joy.ch2)
  (:refer clojure.set :rename {union onion})
  (:require [clojure.string :as bolt])
  (:require [joy.ch2 :as aloha]))

(joy.ch2/hello)
(onion #{1 2} #{4 5})

(aloha/hello)

(bolt/capitalize "feelings pulling through the vent")

(report-ns)

;; Loading java classes with :import

(ns joy.java
  (:import [java.util HashMap]
           [java.util.concurrent.atomic AtomicLong]))

(HashMap. {"happy?" true})

(AtomicLong. 42)