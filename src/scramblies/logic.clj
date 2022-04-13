(ns scramblies.logic
  (:require [clojure.string :as cstring])
  (:use clojure.pprint))

(defn str-to-seq
  [str]
  (cond (cstring/blank? str) []
        :else (->> str
                   seq
                   (filter #(not (= % \space))))))

(defn accumulate-char-hash-map
  [map ch]
  (let [current-counter (get map ch 0)
        new-counter (inc current-counter)]
    (assoc map ch new-counter)))

(defn reduce-str-to-char-hash-map
  [str]
  (let [char-seq (str-to-seq str)]
    (reduce accumulate-char-hash-map {} char-seq)))

(defn scramble?
  [str1 str2]
  (let [str-1-char-count-map (reduce-str-to-char-hash-map str1)
        str-2-char-count-map (reduce-str-to-char-hash-map str2)]
    (let [non-matching (reduce (fn [col [ch counter-2]]
                                 (let [counter (get str-1-char-count-map ch 0)
                                       counters-match? (>= counter counter-2)]
                                   (cond counters-match? col
                                         :else (conj col ch))))
                               []
                               str-2-char-count-map)
          not-matching? (empty? non-matching)]
      not-matching?)))