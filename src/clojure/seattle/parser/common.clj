(ns seattle.parser.common
  (:require [seattle.lexer :as s.lexer]
            [blancas.kern.core :as kern :refer [<|> >>]]
            [seattle.adapters.node :as a.node]
            [seattle.adapters.parser :as a.parser]
            [seattle.lexer :as l]))

(defmacro bind-tracking
  [[& bindings] & body]
  (let [v (gensym "v")
        end (gensym "end")
        start (gensym "start")
        location (gensym "location")
        ret kern/return
        bds (concat [start kern/get-position]
                    bindings
                    [end kern/get-position])]
    `(kern/bind ~bds
                (let [~location (a.node/positions->location (a.parser/kern-position->internal ~start)
                                                            (a.parser/kern-position->internal ~end))]
                  (with-redefs [kern/return
                                (fn [~v]
                                  (~ret
                                   (with-meta ~v {:location ~location})))]
                    ~@body)))))

(def ending (kern/skip (kern/many s.lexer/new-line) s.lexer/trim))
(def program-ending (<|> ending kern/eof))

(def access
  (bind-tracking
   [ids (kern/sep-by1 l/dot l/identifier)]
   (kern/return {:kind :access
                 :id (last ids)
                 :parents (or (vec (butlast ids)) [])})))

(def type-args
  (bind-tracking
    [args (kern/optional (l/angles (l/comma-sep l/identifier)))]
    (kern/return {:kind :type-args
                  :args (or args [])})))

(def type
  (>> (kern/skip l/colon) access))