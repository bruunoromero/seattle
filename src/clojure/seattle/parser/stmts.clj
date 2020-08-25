(ns seattle.parser.stmts
  (:require [blancas.kern.core :as kern :refer [<|>]]
            [seattle.lexer :as l]
            [seattle.parser.common :as p.common]
            [seattle.parser.expr :as p.expr]))

(def module-stmt
  (p.common/bind-tracking
   [_ (l/word "module")
    access p.common/access
    _ p.common/program-ending]
   (kern/return {:kind :module
                 :ns access})))

(def import-stmt
  (p.common/bind-tracking
   [_ (l/word "import")
    access p.common/access
    _ p.common/program-ending]
   (kern/return {:kind :import
                 :ns access})))

(def data-fields
  (l/comma-sep
   (p.common/bind-tracking
    [id l/identifier
     _ l/colon
     type p.common/access]
    (kern/return {:kind :data-field
                  :id id
                  :type type}))))

(def type-ctor
  (p.common/bind-tracking
    [id l/identifier
     type-args p.common/type-args
     args (kern/optional (l/parens (l/comma-sep1 p.common/access)))]
    (kern/return {:kind :type-ctor
                  :id id
                  :args (or args [])
                  :type-args type-args})))

(def type-decl-stmt
  (p.common/bind-tracking
   [_ (l/word "type")
    id l/identifier
    type-args p.common/type-args
    _ (l/word "=")
    ctors (kern/sep-by1 (l/word "|") type-ctor)]
   (kern/return {:kind :type-decl
                 :id id
                 :ctors ctors
                 :type-args type-args})))

(def data-decl-stmt
  (p.common/bind-tracking
   [_ (l/word "data")
    id l/identifier
    _ (l/word "=")
    fields (l/braces data-fields)]
   (kern/return {:kind :data-decl
                 :id id
                 :fields fields})))

(def def-decl-stmt
  (p.common/bind-tracking
   [_ (l/word "def")
    id l/identifier
    type (kern/optional p.common/type)
    _ (l/word "=")
    value p.expr/expr]
   (kern/return {:kind :def-decl
                 :id id
                 :type type
                 :value value})))

(def decl-stmt
  (p.common/bind-tracking
   [decl (<|> def-decl-stmt
              data-decl-stmt
              type-decl-stmt)
    _ p.common/program-ending]
   (kern/return {:kind :decl
                 :stmt decl})))