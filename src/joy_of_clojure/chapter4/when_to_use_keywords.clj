(ns joy-of-clojure.chapter4.when-to-use-keywords)

:a-keyword

::also-a-keyword

;; Application of keywords
;; Keywords always refer to themselves whereas symbols don't

(def population {:zombies 2700, :humans 9})

(get population :zombies)

(println (str (/ (get population :zombies)
                 (get population :humans))
              " zombies per capita"))

;; using keywords as map-lookup functions leads to much more concise code.

(:zombies population)

(println (/ (:zombies population)
            (:humans population))
         "zombies per capita")

;; Enumeration
;; clojure uses keywords as enumerations values, such as :small :medium and :large

(defn pour [lb ub]
  (cond
    (= ub :toujours) (iterate inc lb)
    :else (range lb ub)))
(pour 1 10)

(pour 1 :toujours)

(ns another )

:user/in-another
