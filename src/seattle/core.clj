(ns seattle.core
  (:gen-class)
  (:require [insn.core :as insn])
  (:import (java.io PrintStream)))

(def class-data
  "This creates a class my.pkg.Adder with a main function that prints `Hello world`"
  {:name    'my.pkg.Adder
   :methods [{:flags #{:public :static}, :name "main", :desc [[String] :void]
              :emit  [[:getstatic System "out" PrintStream]
                      [:ldc "Hello world"]
                      [:invokevirtual PrintStream "println" [String :void]]
                      [:return]]}]})

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (-> class-data
      insn/visit
      insn/write))
