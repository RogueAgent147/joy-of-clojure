(ns joy-of-clojure.chapter4.persistent-sets
  (:require clojure.set))

;; Basic properties of Clojure sets

(#{:a :b :c :d} :c)

(#{:a :b :c :d} :e)

(get #{:a 1 :b 2} :b)

(get #{:a 1 :b 2} :z :nothing-doing)

(into #{[]} [()])

(into #{[] #{} {}} [()])

(some #{:b} [:a 1 :b 2])

(some #{1 :b} [:a 1 :b 2])

;; 5.5.2 Keeping your sets in order with sorted-set

(sorted-set :b :c :a)
(sorted-set [3 4] [1 2])
(sorted-set :b 1 :c :a 3 1)

(contains? #{1 2 4 3} 4)
(contains? [1 2 4 3] 4)

(clojure.set/intersection #{:humans :fruit-bats :zombies}
                          #{:chupacabra :zombies :humans})

(clojure.set/intersection #{:pez :gum :dots :skor}
                          #{:pez :skor :pocky}
                          #{:pocky :gum :skor})

(clojure.set/union #{:humans :fruit-bats :zombies}
                   #{:chupacabra :zombies :humans})

(clojure.set/union #{:pez :gum :dots :skor}
                   #{:pez :skor :pocky}
                   #{:pocky :gum :skor})


;;  this is not what really happens
(clojure.set/difference #{1 2 3 4} #{3 4 5 6})

;; ;=> #{1 2 5 6}

;; The reason for this result is that Clojure’s difference function calculates what’s
;; known as a relative complement (Stewart 1995) between two sets