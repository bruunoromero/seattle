(ns seattle.schemata.stmts
  (:require [seattle.schemata.node :as s.node]
            [schema.core :as s]))

(s/defschema ModuleStmt
  s.node/node-skeleton)