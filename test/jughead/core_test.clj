(ns jughead.core-test
  (:require [clojure.test :refer :all]
            [jughead.core :refer :all])
  (:use midje.sweet))


(facts "parsing values"
       (fact "archiparsers key : value pairs"
             (archie-parser "key:value")
              => {:key "value"})

       (fact "Ignores spaces on either side of the key"
             (archie-parser "  key  :value") 
              => {:key "value"})

       (fact "Ignores tabs on eight side of the key"
             (archie-parser "\t\tkey\t\t:value")
              => {:key "value"})

       (fact "Ignores spaces on eight side of the value"
             (archie-parser "key:  value  ")
              => {:key "value"})

       (fact "Ignores tabs on either side of the value"
             (archie-parser "key:\t\tvalue\t\t")
              => {:key "value"})

       (fact "Duplicate keys are assigned to the last given value"
             (archie-parser "key:value\nkey:newvalue")
              => {:key "newvalue"})

       (fact "Allows non-letter characters at the start of values"
             (archie-parser "key::value")
              => {:key ":value"})

       (fact "Keys are case sensitive"
             (keys (archie-parser "key:value\nKey:Value"))
              => (just [:key :Key] :in-any-order true))


       (fact "Non-keys don't affect parsing"
             (archie-parser "other stuff\nkey:value\nother stuff")
              => {:key "value"}))
