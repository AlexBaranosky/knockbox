(ns knockbox.twopsets
    "This is an implementation of a
    state-based
    Two-Phase set data type.
    This data type allows deletes,
    with the limitation that items
    can only be deleted that are in
    the set (the local replica) and
    that items can not be added back
    once they've been deleted.")

(defstruct kb2pset :adds :dels)

(defn twopset
    "Create a new 2p-set,
    optionally accepting a starting
    set"
    [& args]
    (let [initial (or (first args) #{})]
        (struct-map kb2pset
                    :adds initial
                    :dels #{})))

(defn add
    "Add an item to a set"
    [s item]
    (assoc s :adds
        (conj (:adds s) item)))

(defn remove
    "Remove an item from a set"
    [s item]
    (assoc s :dels
        (conj (s :dels) item)))

(defn merge
    "Merge two sets together"
    [a b]
    (let [adds (clojure.set/union
                    (a :adds) (b :adds))
          dels (clojure.set/union
                    (a :dels) (b :dels))]
        (struct-map kb2pset :adds adds :dels dels)))

(defn exists?
    "Check for the existence
    if a particular item in the
    set"
    [s item]
    (and ((s :adds) item) ((s :dels) item)))

(defn items
    "Return all of the items in
    the set that haven't been
    deleted"
    [s]
    (clojure.set/difference (s :adds) (s :dels)))