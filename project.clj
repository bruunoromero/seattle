(defproject seattle "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [prismatic/schema "1.1.12"]
                 [insn "0.4.0"]
                 [org.blancas/kern "1.1.0"]
                 [cli-matic "0.4.3"]
                 [funcool/cats "2.3.6"]
                 [nubank/mockfn "0.6.1"]]
  :plugins [[lein-cljfmt "0.6.8"]]
  :aliases {"lint-fix" ["do" ["cljfmt" "fix"]]}
  :main ^:skip-aot seattle.core
  :target-path "target/%s"
  :source-paths ["src/clojure"]
  :java-source-paths ["src/java"]
  :profiles {:uberjar {:aot :all}})
