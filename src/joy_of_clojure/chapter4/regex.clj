(ns joy-of-clojure.chapter4.regex)

#"an example pattern"

(type #"an example pattern")
(class #"an example pattern")

;; regex functions

(re-seq #"\w+" "one-two/three")
(re-seq #"\w*(\w)" "one-two/three")