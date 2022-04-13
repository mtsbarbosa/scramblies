(ns scramblies.routes.core-test
  (:require [clojure.test :refer :all]
            [integration_core :refer :all]
            [io.pedestal.test :as p.test]
            [clojure.data.json :as json])
  (:use clojure.pprint))

(setup)

(def json-header
  {"Content-Type" "application/json"})

(defn- map-as-json
  [map]
  (json/write-str map))

(defn- json-as-map
  [json-str]
  (json/read-str json-str :key-fn keyword))

(def scramble-400-message
  {:status 400 :headers json-header :body {:message "Two texts must be provided in order to check."}})

(defn call-post-scramble
  [body]
  (p.test/response-for (:core @test-server)
                       :post "/scramble"
                       :headers json-header
                       :body (map-as-json body)))

(deftest post-scramble-test?
  (testing "blank strings give a 400 with a message"
    (let [actual-resp (call-post-scramble {:str-1 "" :str-2 ""})
          body-as-map (json-as-map (:body actual-resp))]
      (is (= 400 (:status actual-resp)))
      (is (= (:body scramble-400-message)
             body-as-map))))
  (testing "missing str-1 give a 400 with a message"
    (let [actual-resp (call-post-scramble {:str-2 "something"})
          body-as-map (json-as-map (:body actual-resp))]
      (is (= 400 (:status actual-resp)))
      (is (= (:body scramble-400-message)
             body-as-map))))
  (testing "missing str-2 give a 400 with a message"
    (let [actual-resp (call-post-scramble {:str-1 "something" :str2 "else"})
          body-as-map (json-as-map (:body actual-resp))]
      (is (= 400 (:status actual-resp)))
      (is (= (:body scramble-400-message)
             body-as-map))))

  (testing "having strings scrambled 200 with result true is returned"
    (do
      (let [actual-resp (call-post-scramble {:str-1 "rekqodlw" :str-2 "world"})
            body-as-map (json-as-map (:body actual-resp))]
        (is (= 200 (:status actual-resp)))
        (is (= {:result true}
               body-as-map)))
      (let [actual-resp (call-post-scramble {:str-1 "cedewaraaossoqqyt" :str-2 "codewars"})
            body-as-map (json-as-map (:body actual-resp))]
        (is (= 200 (:status actual-resp)))
        (is (= {:result true}
               body-as-map)))
      (let [actual-resp (call-post-scramble {:str-1 "cedewaraa ossoqqyt " :str-2 " codewars"})
            body-as-map (json-as-map (:body actual-resp))]
        (is (= 200 (:status actual-resp)))
        (is (= {:result true}
               body-as-map)))))
  (testing "having strings not scrambled 200 with result false is returned"
    (let [actual-resp (call-post-scramble {:str-1 "katas" :str-2 "steak"})
          body-as-map (json-as-map (:body actual-resp))]
      (is (= 200 (:status actual-resp)))
      (is (= {:result false}
             body-as-map)))))