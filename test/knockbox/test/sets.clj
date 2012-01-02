(ns knockbox.test.sets
  (:use [knockbox.sets])
  (:use midje.sweet)
  (:use clojure.test))

(fact
  (let [s (knockbox.sets/observed-remove)]
    (contains? s :foo) => false))

(fact
  (let [s (knockbox.sets/observed-remove)
        f (conj s :foo)]
    (contains? f :foo) => true))
