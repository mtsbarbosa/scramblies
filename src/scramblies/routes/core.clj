(ns scramblies.routes.core
  (:require [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as p.body-params]
            [scramblies.interceptors :as interceptors]
            [scramblies.logic :as s.logic]
            [clojure.string :as cstring])
  (:use clojure.pprint)
  (:import (clojure.lang ExceptionInfo)))

(def common-interceptors
  [(p.body-params/body-params)
   interceptors/json-out])

(def headers
  {"Content-Type" "application/json"})

(def common-messages
  {:success     {:status 200 :headers headers :body {}}
   :bad-format  {:status 400 :headers headers :body {:message "Two texts must be provided in order to check."}}
   :none        {:status 500 :headers headers :body {:message "Something wrong has happened, please retry later."}}})

(defn message-catch
  [e]
  (let [data (.getData e)
        type (get data :type :none)]
    (get common-messages type)))

(defn post-scramble?
  [request]
  (try
    (let [crude-body (:json-params request)
          str-1 (get crude-body :str-1)
          str-2 (get crude-body :str-2)
          is-blank? (or (cstring/blank? str-1)
                        (cstring/blank? str-2))]
      (cond is-blank? (get common-messages :bad-format)
            :else (let [scramble? (s.logic/scramble? str-1 str-2)
                        success-msg (get common-messages :success)]
                    (assoc success-msg :body {:result scramble?}))))
    (catch ExceptionInfo e
      (message-catch e))
    ))

(def all-routes
  (route/expand-routes
    #{["/scramble" :post (conj common-interceptors `post-scramble?) :route-name :post-scramble?]}))
