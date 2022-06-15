(ns joy-of-clojure.chapter3.destructuring)

(def guys-whole-name ["Guy" "Lewis" "Steele"])

(str (nth guys-whole-name 2) ", "
     (nth guys-whole-name 1) " "
     (nth guys-whole-name 0))

(let [[f-name m-name l-name] guys-whole-name]
  (str l-name ", " f-name " " m-name))

(let [[a b c & more] (range 10)]
      (println "a b c are:" a b c)
      (println "more is:" more))

(let [range-vec (vec (range 10))
      [a b c & more :as all] range-vec]
  (println "a b c are:" a b c)
  (println "more is:" more)
  (println "more is:" all))


;; Destructuring with a map

(def guys-name-map
  {:f-name "Guy" :m-name "Lewis" :l-name "Steele"})

(let [f-name (:f-name guys-name-map)
      m-name (:m-name guys-name-map)
      l-name (:m-name guys-name-map)]
  (println f-name m-name l-name))

(let [{f-name :f-name m-name :m-name l-name :l-name} guys-name-map]
  (str l-name ", " f-name " " m-name))

(let [{:keys [f-name m-name l-name]} guys-name-map]
  (str l-name ", " f-name " " m-name))

(let [{f-name :f-name :as whole-name} guys-name-map]
  (println "First name is" f-name)
  (println "Whole name is below:"
           whole-name))

;; if destructuring maps looks up a key that's not in the source map, it's normally bound
;; to nil, but you can provide different defaults with :or:

(let [{:keys [title f-name m-name l-name], :or {title "Mr. "}} guys-name-map]
  (println title f-name m-name l-name))

(let [{:keys [id f-name m-name l-name] :or {id "234"}} guys-name-map]
  (println id f-name m-name l-name))

(defn whole-name [& args]
  (let [{:keys [f-name m-name l-name]} args]
    (str l-name ", " f-name " " m-name)))

(whole-name :f-name "Guy" :m-name "Lewis" :l-name "Steele")

;; Associative Destructuring

(let [{first-thing 0, last-thing 3} [1 2 3 4]]
  [first-thing last-thing])

;; Destructuring in function parameter

(defn print-last-name [{:keys [l-name]}]
  (println l-name))

(print-last-name guys-name-map)