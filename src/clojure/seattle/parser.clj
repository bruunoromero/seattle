(ns seattle.parser
  (:require [schema.core :as s]
            [blancas.kern.core :refer [<|>] :as kern]
            [seattle.parser.program :as p.program]))

(s/defn string->ast
  [content :- s/Str]
  (kern/parse p.program/program content))