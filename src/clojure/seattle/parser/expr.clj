(ns seattle.parser.expr
  (:require [seattle.lexer :as l]
            [seattle.parser.common :as p.common]
            [blancas.kern.core :as kern :refer [<|>]]))

(declare expr)

(def int-lit
  (p.common/bind-tracking [value l/dec-lit]
                          (kern/return {:kind :int
                                        :value value})))

(def float-lit
  (p.common/bind-tracking [value l/float-lit]
                          (kern/return {:kind :float
                                        :value value})))

(def string-lit
  (p.common/bind-tracking [value l/string-lit]
                          (kern/return {:kind :string
                                        :value value})))

(def char-lit
  (p.common/bind-tracking [value l/char-lit]
                          (kern/return {:kind :char
                                        :value value})))

(def null-lit
  (p.common/bind-tracking [_ (l/word "null")]
                          (kern/return {:kind :null})))

(def fn-arg
  (p.common/bind-tracking
    [id l/identifier
     type p.common/type]
    (kern/return {:kind :arg
                  :id id
                  :type type})))

(def fn-expr
  (p.common/bind-tracking
    [args (l/parens (l/comma-sep fn-arg))
     ret-type (kern/optional p.common/type)
     _ (l/word "=>")
     body (kern/fwd expr)]
    (kern/return {:kind :fn-expr
                  :args args
                  :body body
                  :ret-type ret-type})))

(def if-expr
  (p.common/bind-tracking
    [_ (l/word "if")
     cond (kern/fwd expr)
     _ (l/word "then")
     if-true (kern/fwd expr)
     _ (l/word "else")
     if-false (kern/fwd expr)]
    (kern/return {:kind :if-expr
                  :cond cond
                  :if-true if-true
                  :if-false if-false})))

(def expr
  (p.common/bind-tracking
   [expr (<|> fn-expr if-expr string-lit char-lit int-lit float-lit null-lit p.common/access)]
   (kern/return {:kind :expr
                 :expr expr})))