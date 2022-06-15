(ns joy-of-clojure.chapter3.using-the-repl)

(range 5)

(for [x (range 2) y (range 2)]
  [x y])

(bit-xor 1 2)

(for [x (range 2) y (range 2)]
  [x y (bit-xor x y)])

(defn xors [max-x- max-y-]
  (for [x (range max-x-) y (range max-y-)]
    [x y (bit-xor x y)]))

(xors 2 2)

;; Experimenting with graphics

(def frame (java.awt.Frame.))

(for [meth (.getMethods java.awt.Frame)
      :let [name (.getName meth)]
      :when (re-find #"Vis" name)]
  name)

(.isVisible frame)
(.setVisible frame true)
(.setSize frame (java.awt.Dimension. 200 200))