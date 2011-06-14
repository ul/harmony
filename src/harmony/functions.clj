(ns harmony.functions)

(defn dejong-1 
  "De Jong's function 1. Takes a sequence of numbers and calculates the result as
     x_1^2 + x_2^2 + .. + x_n^2"
  [coll]
  (apply + (map #(* % %) coll)))

(defn dejong-2 
  "De Jong's function 2 (Rosenbrock's valley). Takes a sequence of numbers and calculates the result as
     sum of 100*((x_(i+1) - x_i^2)^2+(1-x_i)^2, for i from 1 to n-1"
  [coll]
  (letfn [(f [[x1 x2]] (+ (* 100 (Math/pow (- (Math/pow x1 2) x2) 2))
                          (Math/pow (- 1 x1) 2)))]
         (apply + (map f (partition 2 1 coll)))))
