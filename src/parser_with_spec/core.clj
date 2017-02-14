(ns parser-with-spec.core
  (:require [clojure.spec :as s]))

(s/def ::method (s/cat :visibility (s/? #{"public" "protected" "private"})
                       :return-type string?
                       :method-name string?
                       :arg-def ::arg-def))

(s/def ::arg-def (s/cat :opening-paren #{"("}
                        :args ::args
                        :closing-paren #{")"}))

(s/def ::args (s/? (s/cat :arg ::arg
                          :more (s/* (s/cat :comma #{","}
                                            :arg ::arg)))))

(s/def ::arg (s/cat :type string?
                    :name string?))

(def code "public void doSomething ( String arg1 , Object arg2 )")
(def code2 "void doSomething ( )")

(defn parse [s]
  (s/conform ::method (clojure.string/split s #" "))) ;; sneak in the lexing step

(parse code)
(parse code2)

