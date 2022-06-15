(ns joy-of-clojure.chapter4.persistant-queue)

;; described by conj adding to the rear, pop removing from the front, and peek
;; returning the front element without removal.

(def schedule
  (conj clojure.lang.PersistentQueue/EMPTY
        :wake-up :shower :brush-teeth))

schedule

(peek schedule)

(pop schedule)