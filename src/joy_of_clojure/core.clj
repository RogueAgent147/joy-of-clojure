(ns joy-of-clojure.core
  (:gen-class))

(defprotocol Concatenable
  (cat [other friends]))

(extend-type String
  Concatenable
  (cat [other friends]
    (.concat other friends)))

(cat "Fish" "Corals")

(extend-type java.util.List
  Concatenable
  (cat [other friends]
    (concat other friends)))

(cat "Rollings" "Faith")

(cat [1 2 3] [4 5 6])

