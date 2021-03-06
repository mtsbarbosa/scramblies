(defproject scramblies "0.1.0-SNAPSHOT"
  :description "scramblies web project"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [io.pedestal/pedestal.service "0.5.7"]
                 [io.pedestal/pedestal.route "0.5.7"]
                 [io.pedestal/pedestal.jetty "0.5.7"]
                 [environ "1.2.0"]
                 [org.slf4j/slf4j-simple "1.7.28"]
                 [org.clojure/data.json "2.4.0"]

                 [org.clojure/test.check "0.10.0-alpha3"]]
  :plugins [[lein-environ "1.2.0"]]
  :profiles {:dev [:project/dev :profiles/dev]
             :test [:project/test :profiles/test]
             ;; only edit :profiles/* in profiles.clj
             :profiles/dev  {}
             :profiles/test {}
             :project/dev {:source-paths ["src" "tool-src"]
                           :plugins [[lein-auto "0.1.3"]
                                     [lein-pprint "1.3.2"]]}
             :project/test {:source-paths ["src" "tool-src"]
                            :plugins [[lein-auto "0.1.3"]
                                      [lein-pprint "1.3.2"]]}}
  :main ^{:skip-aot true} scramblies.core)
