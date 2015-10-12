(ns brown-bag.core
  (:require [brown-bag.slides :as s]
            [schopfhirsch.hash-router :refer [set-routes!]]))

(enable-console-print!)

(defn slide [slide-str]
  (let [slide (js/parseInt slide-str)]
    (reset! s/page slide)))

(def routes
  [{:rule #"#([1-9]+[0-9]*)" :enter slide :args [1]}
   {:rule #".*"
    :enter (fn []
             (set! (-> js/document .-location .-hash) "#1"))}])

(defn ^:export run []
  ;;(set! (-> js/document .-location .-hash) "#1")
  (set-routes! routes))

