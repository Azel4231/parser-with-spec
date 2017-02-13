(ns parser-with-spec.core
  (:require [clojure.spec :as s]
            [clojure.spec.gen :as gen]))

(s/def ::method (s/cat :visibility (s/? #{"public" "protected" "private"})
                       :return-type string?
                       :method-name string?
                       :args ::arg-def))

(s/def ::arg-def (s/cat :opening-paren #{"("}
                        :args ::args
                        :closing-paren #{")"}))

(s/def ::args (s/alt :no-args nil?
                     :arg-list (s/cat :arg ::arg :more (s/* (s/cat :comma #{","}
                                                                   :arg ::arg)))))
(s/def ::arg (s/cat :type string? :name string?))

(def code "public void main ( String arg1 , Object arg2 )")
(def split-code (clojure.string/split code #" "))
(str split-code)
(s/conform ::method split-code)

(s/exercise ::method)
