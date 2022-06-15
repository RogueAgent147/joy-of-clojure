(ns joy-of-clojure.combining-data-and-code.clojure-multimethods-with-universal-design-pattern
  (:refer-clojure :exclude [get]))


;; beget get put has? and forget
;; the entire udp is built on this five function


;; The beget function performs a simple task
;; it takes a map and associate its prototype
;; reference to another map

(defn beget [this proto]
  (assoc this ::prototype proto))

(beget {:sub 0} {:super 1})


;; The get function

(defn get [m k]
  (when m
    (if-let [[_ v] (find m k)]
      v
      (recur (::prototype m) k))))

(get (beget {:sub 0} {:super 1})
     :super)

(get (beget {:sub 0 :sub1 3 :sub2 4} {:super1 9 :super2 13}) :sub1)
(get (beget {:sub 0 :sub1 3 :sub2 4} {:super1 9 :super2 13}) :super2)


;; The put function
;; The function takes a key and an associated value and puts them into the supplied map,
;; overwriting any key of the same name

(def put assoc)

;; basic use of the universal design pattern

(def cat {:likes-dogs true :ocd-bathing true})
(def morris (beget {:likes-9lives true} cat))
(def post-traumatic-morris (beget {:likes-dogs nil} morris))

(get cat :likes-dogs)
(get morris :likes-dogs)
(get post-traumatic-morris  :likes-dogs)
(get post-traumatic-morris :likes-9lives)

;; Multi methods to the rescue

;; You can easily add behaviors to the UDP using Clojure’s multimethod facilities

(defmulti compiler :os)
(defmethod compiler ::unix [m] (get m :c-compiler))
(defmethod compiler ::osx [m] (get m :llvm-compiler))

(def clone (partial beget {}))
(def unix {:os ::unix :c-compiler "cc" :home "/home" :dev "/dev"})
(def osx (-> (clone unix)
           (put :os ::osx)
           (put :llvm-compiler "clang")
           (put :home "/Users")
             (put :dev "/Hart")))

(compiler unix)

(compiler osx)

(derive ::osx ::unix)

(defmulti dev :os)
(defmethod dev ::unix [m] (get m :dev))
(dev unix)
(dev osx)

(parents ::osx)
(ancestors ::osx)
(descendants ::unix)
(isa? ::osx ::unix)
;; Ad hoc hierarchies for inherited behaviors

(defmulti home :os)
(defmethod home ::unix [m] (get m :home))

(home unix)
(home osx)

(home osx)


;; you can query the derivation hierarchy using the functions parents, ancestors, descendants, and
;; isa?

(parents ::osx)
(ancestors ::osx)
(descendants ::osx)
(isa? ::osx ::unix)
(isa? ::unix ::osx)


;; Resolving  conflict in hierarchies

(derive ::osx ::bsd)
(defmethod home  ::bsd [m] "/home")
(home osx)

(prefer-method home ::unix ::bsd)
(home osx)

(derive (make-hierarchy) ::osx ::unix)


(defmulti compile-cmd (juxt :os compiler))

(defmethod compile-cmd [::osx "cc"] ;match the vector exactly
[m] (str "/usr/bin/g" (get m :c-compiler)))

(defmethod compile-cmd :default [m]
  (str "Unsure where to locate, " (get m :c-compiler)))

(compile-cmd osx)
(compile-cmd unix)

(def each-math (juxt + * - /))
(each-math 2 3)

((juxt take drop) 3 (range 9))

