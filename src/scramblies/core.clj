(ns scramblies.core
  (:require [io.pedestal.http :as http]
            [scramblies.routes.core :as r.core]
            [environ.core :refer [env]]))

(defn service-map
  []
  {::http/routes r.core/all-routes
   ::http/type   :jetty
   ::http/port   (Integer/parseInt (env :port))})

(defn create-server []
  (http/create-server
    (service-map)))

(defn start []
  (cond (= (env :name) "test") (-> (service-map)
                                   (http/create-servlet)
                                   :io.pedestal.http/service-fn)
        :else (http/start (create-server))))

(defn -main
  [& _]
  (start))
