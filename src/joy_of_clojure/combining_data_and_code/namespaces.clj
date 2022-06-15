(ns joy-of-clojure.combining-data-and-code.namespaces)

(in-ns 'joy-free.ns)

(def authors ["Chouser"])

(in-ns 'your.ns)

(clojure.core/refer 'joy-free.ns)

joy-free.ns/authors

(in-ns 'joy-free.ns)
(def authors ["Chouser" "Fogus"])

(in-ns 'your.ns)
joy-free.ns/authors


(ns chimp)
(reduce + [1 2 (Integer. 3)])

(in-ns 'gibbon)
(clojure.core/refer 'clojure.core)
(reduce + [1 2 (Integer. 3)])

;; The finest level of control for creating namespaces

(def b (create-ns 'bonobo))
((ns-map b) 'String)

(intern b 'x 8)
bonobo/x

(intern b 'reduce clojure.core/reduce)

(intern b '+ clojure.core/+)

(in-ns 'bonobo)

(reduce + [1 2 3 4 5 6])

(remove-ns 'bonobo)

(all-ns)


(ns hider.ns)

(defn ^{:private true} answer [] 42)

(answer)

(def ruby {:a 1 :b 2 :c 3})

(defn ruby* []
  42)
(defn ruby** []
  53)

(ns seeker.ns
  (:refer hider.ns)
  (:import [hider.ns :only [ruby**]]))
(answer)


;; :exclude , :only , :as , :refer-clojure , :import , :use , :load , and require.

(ns joyr.ns-ex
  (:refer-clojure :exclude [defstruct])
  (:use (clojure set xml))
  (:use [clojure.test :only (are is)])
  (:require (clojure [zip :as z]))
  (:import (java.util Date)
           (java.io File)))