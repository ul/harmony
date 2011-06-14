# harmony

A simple implementation of harmony search algorithm. 
Please read the description on [wikipedia](http://en.wikipedia.org/wiki/Harmony_search) for details.

## Description

### harmony.core

`harmony-seq` creates an inifinite sequence of harmony memories

    [& {:keys [hms hmcr par dims f] :or {hms 30 hmcr 0.9 par 0.3}}]

Most arguments are optional. You only have to provide a sequence of dimensions
implementing `Dimension` protocol and a function that takes a sequence of arguments
and returns a numeric value. 

Please note that the algorithm tries to maximize vector values, so if you want to
minimize something you should use for example something like:

    #(- 0 my-foo-to-be-minimized)

`harmony-search` is a helper function and returns a harmony memory after a specified 
number of iterations

It takes and additional argument `:n` which specifies the desired number of iterations

    [& {:keys [hms hmcr par dims f n] :or {hms 30 hmcr 0.9 par 0.3}}]

#### arguments

`hms` - harmony memory size, the number of solution vectors kept in memory. 
Usually between 1 and 100.

`hmcr` - considering rate. Probability that we will use old value in the 
new candidate vector. Should be between 0 and 1.

`par` - probability that we will slightly modify a value picked from harmony memory.
Should be between 0 and 1.

`dims` - sequence of dimensions

`f` - evaluation function

###harmony.dimension

Contains the definition of `Dimension` protocol and two sample dimensions.

`Dimension` protocol specifies three operations: `get-random [this]` - returns a valid random value;
`to-bounds [this x]` - returns its argument if it's within allowed range (if any) or a corrected value;
`modify [this x]` - returns a modified argument value which can exceed allowed range as it will be 
corrected with `to-bounds`.

There is one sample dimension: `RealDimension`.

## Usage

    (require '[harmony.core :as hc]
	    '[harmony.dimension :as hd])

    ;;create some dimensions 
    (def dims [(hd/get-real-dim 0 5 0.05) (hd/get-real-dim 5 10 0.05)])

    ;;returns the best harmony memory vector after 100 iterations
    (last (hs/harmony-search :dims dims :f #(apply + %) :n 100))

    ;;returns the best vector from the first harmomy memory that meets the specified condition
    (last (first (drop-while #(< (first (last %)) 32)
			     (hs/harmony-seq :dims dims :f #(reduce * %)))))

## License

Copyright (C) 2011 Piotr Borzymek

Distributed under the Eclipse Public License, the same as Clojure.
