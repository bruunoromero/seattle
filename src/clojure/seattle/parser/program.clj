(ns seattle.parser.program
  (:require [seattle.parser.common :as p.common]
            [seattle.parser.stmts :as p.stmts]
            [blancas.kern.core :as kern]))

(def program
  (p.common/bind-tracking
   [module p.stmts/module-stmt
    imports (kern/many p.stmts/import-stmt)
    decls (kern/many p.stmts/decl-stmt)]
   (kern/return {:kind :program
                 :decls decls
                 :module module
                 :imports imports})))
