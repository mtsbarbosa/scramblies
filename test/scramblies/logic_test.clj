(ns scramblies.logic-test
  (:require [clojure.test :refer :all]
            [scramblies.logic :refer :all]))

(deftest str-to-seq-test
  (testing "blank strings should be an empty col"
    (is (= []
           (str-to-seq "")))
    (is (= []
           (str-to-seq " ")))
    (is (= []
           (str-to-seq nil))))
  (testing "blank str in the middle should be ignored"
    (is (= [\a \n]
           (str-to-seq "a n")))
    (is (= [\a \n \g \y \A]
           (str-to-seq "angy A")))
    (is (= [\a \a]
           (str-to-seq " aa "))))
  (testing "non-blank strings should become col"
    (is (= [\a \n]
           (str-to-seq "an")))
    (is (= [\a \n \g \y \A]
           (str-to-seq "angyA")))
    (is (= [\a \a]
           (str-to-seq "aa")))))

(deftest reduce-str-to-char-hash-map-test
  (testing "An empty string should return an empty map"
    (is (= {}
           (reduce-str-to-char-hash-map "")))
    (is (= {}
           (reduce-str-to-char-hash-map " ")))
    (is (= {}
           (reduce-str-to-char-hash-map nil)))
    )
  (testing "Non empty string should return a char map"
    (is (= {\a 1}
           (reduce-str-to-char-hash-map "a")))
    (is (= {\a 4
            \c 1}
           (reduce-str-to-char-hash-map "ac aa a")))
    (is (= {\y 1
            \a 2
            \d 1
            \e 3}
           (reduce-str-to-char-hash-map "aa d eyee")))))

(deftest scramble-test?
  (testing "non-matching-strings return false"
    (is (scramble? "rekqodlw" "world"))
    (is (scramble? "cedewaraaossoqqyt" "codewars"))
    (is (scramble? "cedewaraa ossoqqyt " " codewars"))
    )
  (testing "matching-strings return true"
    (is (not (scramble? "katas" "steak")))
    (is (not (scramble? "" "steak")))))
