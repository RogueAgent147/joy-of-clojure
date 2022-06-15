(ns joy-of-clojure.chapter-7.closures)

(def times-two
  (let [x 2]
    (fn [y] (* y x))))

(times-two 5)

;; This isn't terribly but one way to make a more
;; exciting closure is to have it close over something
;; mutable


(def add-and-get
  (let [ai (java.util.concurrent.atomic.AtomicInteger.)]
    (fn [y] (.addAndGet ai y))))

(add-and-get 2)
(add-and-get 2)
(add-and-get 7)

;; Functions returning closures

(defn times-n [n]
  (let [x n]
    (fn [y] (* y x))))

(times-n 4)
(def times-four (times-n 4))
(times-four 10)

(defn times-n* [n]
  (fn [y] (* y n)))

(defn divisible [denom]
  (fn [fn]
    (zero? (rem fn denom))))

(divisible 4)
((divisible 4) 100)

;; Parsing closures as functions

(filter even? (range 10))
(filter (divisible 4) (range 10))

;; it is common to define a closure right on the spot where it's used, closing whatever local
;; context is needed

(defn filter-divisible [denom s]
  (filter (fn [num] (zero? (rem num denom))) s))

(filter-divisible 4 (range 10))

(defn filter-divisible* [denom s]
  (filter #(zero? (rem % denom)) s))

;; sharing closures context

(def bearings [{:x 0 :y 1}                                  ; north
               {:x 1 :y 0}                                  ; east
               {:x 0 :y -1}                                 ; south
               {:x -1 :y 0}])                               ; west

(defn forward [x y bearing-num]
  [(+ x  (:x (bearings bearing-num)))
   (+ y (:y (bearings bearing-num)))])

(forward 5 5 0)
(forward 5 5 1)
(forward 5 5 2)


(defn bot [x y bearing-num]
  {:coords [x y]
   :bearing ([:north :east :south :west] bearing-num)
   :forward (fn [] (bot (+ x (:x (bearings bearing-num)))
                        (+ y (:y (bearings bearing-num)))
                        bearing-num))})

;; you can create an instance of this bot and query it
;; observation, I believe it takes the form of a dispatch function

(:coords (bot 5 5 0))
(:bearing (bot  5 5 0))
(:forward (bot 5 5 0))

(:coords ((:forward (bot 5 5 0))))

(defn bot* [x y bearing-num]
  {:coords [x y]
   :bearing ([:north :east :south :west] bearing-num)
   :forward (fn [] (bot (+ x (:x (bearings bearing-num)))
                        (+ y (:y (bearings bearing-num)))
                        bearing-num))
   :turn-right (fn [] (bot x y (mod (+ 1 bearing-num) 4)))
   :turn-left (fn [] (bot x y (mod (- 1 bearing-num) 4)))})

(:bearing ((:forward ((:forward ((:turn-right (bot* 5 5 0))))))))