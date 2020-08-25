(ns seattle.schemata.parser
  (:require [schema.core :as s]))

(s/defschema Position
  {:line s/Int
   :col s/Int})