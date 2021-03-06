# Knockbox

## Build status

[![Build Status](https://secure.travis-ci.org/reiddraper/knockbox.png)](http://travis-ci.org/reiddraper/knockbox)

knockbox is an eventual-consistency toolbox for Clojure,
and eventually the JVM in general. It's inspired by
[statebox](https://github.com/mochi/statebox) and
the paper
[A comprehensive study of Convergent and Commutative Replicated Data Types](http://hal.archives-ouvertes.fr/inria-00555588/).


Databases like [Riak](https://github.com/basho/riak) let you trade consistency for availability.
This means that you can have two conflicting values for a particular key. Resolving these conflicts
is up to application-logic in the database clients. Certain data-types and operations are suited
for automatic conflict-resolution. This project is a collection of these data-types and operations.

There is also a blog post about knockbox [here](http://reiddraper.com/introducing-knockbox/).

## Status

knockbox is currently in development and the API will be changing quickly, without notice.

## Resolution Protocol

Each of the data type in knockbox implement the `knockbox.resolvable/Resolvable`
protocol, which is currently simply:

```clojure
(resolve [a b]))
```

When it comes time to resolve sibling, you can resolve
a vector of them like this:

```clojure
;; notice the namespace difference
(knockbox.core/resolve [a b c d e])
```

The data types also implement any appropriate Java Interfaces
and Clojure Protocols. The different knockbox set implementations,
for example, can be used just like Clojure Sets.

## Sets

```clojure
(require 'knockbox.sets)

;; last-write-wins set
(def a (knockbox.sets/lww))
;; => #{}

;; two-phase set
(def b (knockbox.sets/two-phase))
;; => #{}

;; observed-remove set 
(def c (knockbox.sets/observed-remove))
;; => #{}

(disj a :foo)
;; => #{}

(conj b :bar)
;; => #{:bar}
```

## Registers

Registers are simple containers for values.
Currently there is one Register implementation with
last-write-wins semantics.

```clojure
(require '(knockbox core registers))

;; the only argument to lww is the value
;; of the register
(def a (knockbox.registers/lww "1"))
(def b (knockbox.registers/lww "2"))
(def c (knockbox.registers/lww "3"))

;; the value can be queried like
(.value a)
;; => "1"

(.value (knockbox.core/resolve [c b a]))
;; => "3"
```

## Tests

Tests can be run by typing:

    lein deps # this only needs to be done once
    lein midje


## Documentation

Source documentation can be generated by typing:

    lein deps
    lein marg

`lein deps` only needs to be run once.
