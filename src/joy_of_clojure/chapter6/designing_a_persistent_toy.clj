(ns joy-of-clojure.chapter6.designing-a-persistent-toy)

;; The simplest shared-structure type is the list.

(def base-list (list :barnabas :adam))
(def lst1 (cons :willie base-list))
(def lst2 (cons :phoenix base-list))
lst1
lst2

;; you can think of base-list as a historical version of both lst1 and
;; lst2. But also the shared part of both lists.
;; the next part of both lists are identical the same instance

(= (next lst1) (next lst2))
(identical? (next lst1) (next lst2))

;; Build a simple tree to help demonstrate how a tree can allow interior changes and maintain
;; shared structure at the same time.

{:val 5 :L nil :R nil}

(defn xconj [ t v]
  (cond
    (nil? t) {:val v :L nil :R nil}))

(xconj nil 5)

(defn xconj [t v]
  (cond
    (nil? t) {:val v :L nil :R nil}
    (< v (:val t)) {:val (:val t)
                    :L (xconj (:L t) v)
                    :R (:R t)}))

(def tree1 (xconj nil 5))

tree1

(def tree1 (xconj tree1 3))

tree1

(def tree1 (xconj tree1 2))

tree1

(defn xseq [t]
  (when t
    (concat (xseq (:L t)) [(:val t)] (xseq (:R t)))))

(xseq tree1)