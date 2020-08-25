(ns seattle.adapters.node
  (:require [schema.core :as s]
            [seattle.schemata.node :as s.node]
            [seattle.schemata.parser :as s.parser]))

(s/defn positions->location :- s.node/NodeLocation
  [start :- s.parser/Position
   end :- s.parser/Position]
  {:end end
   :start start})
