(ns seattle.core
  (:gen-class)
  (:require [seattle.parser :as p]
            [clojure.java.io :as io]
            [clojure.pprint :refer [pprint]]))

;(def class-data
;  "This creates a class my.pkg.Adder with a main function that prints `Hello world`"
;  {:name    'my.pkg.Adder
;   :methods [{:flags #{:public :static}, :name "main", :desc [[String] :void]
;              :emit  [[:getstatic System "out" PrintStream]
;                      [:ldc "Hello world"]
;                      [:invokevirtual PrintStream "println" [String :void]]
;                      [:return]]}]})

(defn -main
  "I don't do a whole lot ... yet."
  [& _args]
  (-> "Main.sea"
      io/resource
      slurp
      p/string->ast
      :value
      pprint))