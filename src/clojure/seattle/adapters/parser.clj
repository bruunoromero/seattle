(ns seattle.adapters.parser
  (:require [schema.core :as s]
            [seattle.schemata.parser :as s.parser]))

(s/defn kern-position->internal :- s.parser/Position
  [{:keys [line col]}]
  {:col col
   :line line})