(ns joy-of-clojure.chapter-7.thinking-recursively)


(defn pow [base exp]
  (if (zero? exp)
    1
    (* base (pow base (dec exp)))))

(pow 2 10)
(pow 1.01 925)
(pow 2 10000)

(defn pow [base exp]
  (letfn [(kapow [base exp acc]
            (if (zero? exp)
              acc
              (recur base (dec exp) (* base acc))))]
    (kapow base exp 1)))

(pow 2N 10000)

;; A recursive unit calculator
;; How many meters are in 3 kilometers, 10 meters, 80 centimeters, 10 millimeters?”

(def simple-metric {:meter 1
                    :km 1000
                    :cm 1/100
                    :mm [1/10 :cm]})

(->  (* 3 (:km  simple-metric))
     (+ (* 10 (:meter simple-metric)))
     (+ (* 80 (:cm simple-metric)))
     (* 10 (first (:mm simple-metric)))
     float)

(defn convert [context descriptor]
  (reduce (fn [result [mag unit]]
            (+ result
               (let [val (get context unit)]
                 (if (vector? val)
                   (* mag (convert context val))
                   (* mag val)))))
          0
          (partition 2 descriptor)))

(convert simple-metric [1 :meter])

(convert simple-metric [50 :cm])

(convert simple-metric [100 :mm])

(float (convert simple-metric [3 :km 10 :meter 80 :cm 10 :mm]))

;; Tail calls and recur

(defn gcd [x y]
  (cond
    (> x y) (gcd (- x y) y)
    (< x y) (gcd x (- y x))
    :else x))

;; Continuation passing style

