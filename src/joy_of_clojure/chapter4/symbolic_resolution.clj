(ns joy-of-clojure.chapter4.symbolic-resolution)

;; symbols aren't unique based on their name alone

(identical? 'goat 'goat)

;; the name is above is a symbolic representation but that name is
;; the basis for symbol equality

(= 'goat 'goat)
(name 'goat)

;; identical? only ever returns true when the symbols are the
;; same object:

(let [x 'goat
      y x]
  (identical? y x))

;; metadata

(let [x  (with-meta 'goat {:ornery true})
      y (with-meta 'goat {:ornery false})]
  [(= x y) (identical? x y) (meta x) (meta y)])

;; Symbols and namespaces
;; Like keywords, symbols don’t belong to any specific namespace

(ns where-is)
(def a-symbol 'where-am-i)
a-symbol

(resolve 'a-symbol)