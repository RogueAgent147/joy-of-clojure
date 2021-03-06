(ns joy-of-clojure.chapter-7.pathfinding)

(def world [[1 1 1 1 1]
            [999 999 999 999 1]
            [1 1 1 1 1]
            [1 999 999 999 999]
            [1 1 1 1 1]])


(defn estimate-cost [step-cost size y x]
  (* step-cost
     (- (+ size size) y x 2)))

(estimate-cost 900 5 0 0)

(estimate-cost 900 5 4 4)

(defn path-cost [node-cost cheapest-nbr]
  (+ node-cost
     (or (:cost cheapest-nbr) 0)))

(path-cost 900 {:cost 1})

(defn total-cost [newcost step-cost-est size y x]
  (+ newcost
     (estimate-cost step-cost-est size y x)))

(total-cost 0 900 5 0 0)

(total-cost 1000 900 5 3 4)

(defn min-by [f coll]
  (when (seq coll)
    (reduce (fn [min other]
              (if (> (f min) (f other))
                other
                min))
            coll)))

(min-by :cost [{:cost 100} {:cost 36} {:cost 9}])

(defn astar [star-yx step-est cell-costs]
  (let [size (count cell-costs)]
    (loop [steps 0
           routes (vec (replicate size (vec (replicate size nil))))
           work-todo (sorted-set [0 star-yx])]
      (if (empty? work-todo)
        [([peek (peek routes) :steps steps]
          (let [[_ yx :as work-item] (first work-todo)
                rest-work-todo  (disj work-todo work-item)
                nbr-yxs (neighbors size yx)
                cheapest-nbr (min-by :cost
                                     (keep #(get-in routes %)
                                           nbr-yxs))
                newcost (path-cost (get-in cell-costs yx)
                                   cheapest-nbr)
                oldcost (:cost (get-in routes yx))]
            (if (and oldcost (>= newcost oldcost))
              (recur (inc steps routes rest-work-todo)
                     (recur inc steps)
                     (assoc-in routes yx
                               {:cost newcost
                                :yxs (conj (:yxs cheapest-nbr [])
                                           yx)})
                     (into rest-work-todo
                           (map
                             (fn [w]
                               (let [[y x] w]
                                 [(total-cost newcost step-est size y x) w]))
                             nbr-yxs))))))]))))

