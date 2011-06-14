(ns harmony.core
  (:use harmony.dimension))

(defn- create-memory 
  "Creates an initial harmony memory"
  [hms dims f]
  (let [v (repeatedly hms #(map get-random dims))]
    (vec
      (sort-by first (map #(vec (concat [(f %)] %)) v)))))

(defn- new-elt
  "Returns a new value for the given column index."
  [i hms hm hmcr par dim] 
  (if (> (rand) hmcr)
    (get-random dim)
    (let [x (nth (nth hm (rand-int hms)) i)]
      (if (<= (rand) par)
        (->> x (modify dim) (to-bounds dim))
        x))))
        
(defn- update-row
  "Creates a new candidate vector"
  [hms hm hmcr par dims f]
  (let [r (map-indexed #(new-elt (inc %1) hms hm hmcr par %2) dims)]
    (vec (concat [(f r)] r))))

(defn- update-memory
  "Creates an updated harmony memory"
  [hms hm hmcr par dims f]
  (let [worst (first (first hm))
        [value :as row] (update-row hms hm hmcr par dims f)]
    (if (> value worst)
     (vec (sort-by first (conj (rest hm) row)))
      hm)))

(defn harmony-seq
  "Creates an infinite sequence of harmony memory vectors.
 
  Takes the following attributes:
  :hms - harmony memory size, defaults to 30
  :hmcr - harmony memory considering rate, defaults to 0.9, typically between 0.7 and 0.99
  :par - the rate of choosing a neighbouring value, defaults to 0.3, typically between 0.1 and 0.5
  :dims - sequence of objects implementing harmony.dimension/Dimension protocol, specifies search dimensions
  :f - evaluation function that will be maximized. Should accept a sequence of values.
   
   Each harmony memory is a vector of vectors, sorted in the ascending order (best result in the last vector).
   Each result vector has the form [f-value, x1, x2, ..., xn]"
  [& {:keys [hms hmcr par dims f] :or {hms 30 hmcr 0.9 par 0.3}}]
  (iterate #(update-memory hms % hmcr par dims f) (create-memory hms dims f)))

(defn harmony-search
  "Returns the harmony memory after nth iteration. 
   Takes the same arguments as harmony.core/harmony-seq plus:
   :n - iteration count"
  [& {:keys [hms hmcr par dims f n] :or {hms 30 hmcr 0.9 par 0.3}}]
  (nth (harmony-seq :hms hms :hmcr hmcr :par par :dims dims :f f) n))