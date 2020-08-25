(ns seattle.schemata.node
  (:require [schema.core :as s]
            [seattle.schemata.parser :as s.parser]))

(def node-kinds
  #{:int
    :null
    :expr
    :decl
    :float
    :string
    :import
    :module
    :program
    :def-decl
    :data-decl
    :data-field})

(s/defschema NodeKind (s/enum apply node-kinds))

(s/defschema NodeLocation
  {:start s.parser/Position
   :end s.parser/Position})

(def node-skeleton
  {:kind     NodeKind})