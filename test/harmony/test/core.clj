(ns harmony.test.core
  (:use [harmony.core]
        [harmony.dimension]
        [harmony.functions])
  (:use [clojure.test]))

(deftest real-dimension-test
  (let [d (get-real-dim 0 1 10)]
    (is (= 0 (to-bounds d -1)))
    (is (= 1 (to-bounds d 2)))))

(deftest discrete-dimension-test
  (let [d (get-discrete-dim 0 5 0.1 0.2)
        x (modify d 0.2)]
    (is (= 0 (to-bounds d -1)))
    (is (= 0.2 (to-bounds d 0.2)))
    (is (= 5 (to-bounds d 5.1)))
    (is (= true  (or (= x 0) (= x 0.4))))))

(deftest dejong-1-test 
  (is (= 8 (dejong-1 [2 2]))
  (is (= 14 (dejong-1 [1 2 3])))))

(deftest dejong-2-test
  (is (= 16909 (dejong-2 [4 3]))))
 