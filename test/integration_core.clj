(ns integration_core
  (:require [clojure.test :refer :all]
            [scramblies.core :as s.core])
  (:use clojure.pprint))

(def test-server (atom {:core {}}))

(defn setup []
  (let [core (:core @test-server)
        is-empty (empty? core)]
    (cond is-empty (do
                     (println "test server started")
                     (swap! test-server assoc :core (s.core/start))))))
