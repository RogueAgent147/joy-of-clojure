(ns joy-of-clojure.chapter4.lists-code-form-data-structure)

;; A PersistentList is a singly linked list where each node knows its distance from
;; the end.

;; Lists like lisps like

(cons 1 '(2 3))
(conj '(2 3) 1)

(peek '(1 2 3 4))
(pop '(1 2 3 4 5))