(ns harmony.dimension)

(defprotocol Dimension
  (get-random [this] 
   "Returns a random value, valid within implementing dimension")
  (to-bounds [this x] 
   "Checks whether an argument is within dimension bounds (if any) 
    and returns a corrected value if neccessary")
  (modify [this x]
   "Returns a sligthly modified argument value. New value doesn't
    have to be within dimension range, as long as it can be corrected 
    with to-bounds"))
                        
(defrecord RealDimension [lower upper fw]
  Dimension
  (get-random [this] (+ (rand (- upper lower)) lower)) 
  
  (to-bounds  [this x] (if-not (and (>= upper x) (<= lower x))
                         (max (min x upper) lower)
                         x))
  
  (modify     [this x] (+ x (* fw (dec (rand 2))))))
  
(defn get-real-dim
  "Returns an instance of a dimension of real numbers. 
  Takes 3 arguments: lower boundary, upper boundary and fret width -
  the amount of maximum change in pitch adjustment, usually between 0.01 * allowed range and 0.001 * allowed range"
  [l u fw]
  (RealDimension. l u fw))  
