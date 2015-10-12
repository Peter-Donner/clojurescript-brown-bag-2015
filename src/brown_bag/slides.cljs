(ns brown-bag.slides
  (:require [reagent.core :as r]))

(enable-console-print!)

(.log js/console "hello")
(println "foo")

(defonce page (r/atom 1))

(println "page=" @page)

(def clojure-syntax-code
  "(println \"Hello\")

(defn [arg1] (+  10 (* 2 arg1)))

(let [a 1, b 2] (println a b))")

(def slides
  [[:div [:h1 "ClojureScript"]]

   [:div [:h1 "Clojure Syntax"]
    [:pre clojure-syntax-code]]

   [:div [:h1 "IDEs"]
    [:ul
     [:li "Emacs"]
     [:li [:a {:href "http://lighttable.com/"} "Light Table"]]
     [:li [:a {:href "https://cursiveclojure.com/"} "Cursive (IntelliJ)"]]
     [:li [:a {:href "http://doc.ccw-ide.org/"} "Counterclockwise (Eclipse)"]]]]

   [:div [:h1 "Build Tool"]
    [:h2 "Leiningen"]
    [:pre "lein clean

lein figwheel

lein with-profile prod do clean, cljsbuild once"]]

   [:div [:h1 "Google Closure Compiler"]
    [:ul
     [:li [:a {:href "https://developers.google.com/closure/compiler/"}
           "https://developers.google.com/closure/compiler/"]]]]

   [:div [:h1 "Figwheel"]
    "Live code reloading"]

   [:div [:h1.foo "Reagent"]
    [:ul
     [:li "React.js wrapper"]
     [:li "Hiccup"]]]

   [:div [:h1 "JavaScript Interop"]]

   [:div [:h1 "Benefits"]
    [:ul
     [:li "persistent data structures (bitmapped vector trie)"]
     [:li "core.async"]]]

   [:div [:h1 "CLJSJS"]
    [:a {:href "http://cljsjs.github.io/"} "CLJSJS"]]
   
   [:div [:h1 "File Extension"]
    [:ul
     [:li ".clj"]
     [:li ".cljs"]
     [:li ".cljc"]]]

   [:div [:h1 "The End"]]])

(defn slide-component [] (nth slides (dec @page)))

(defn has-more-slides? [] (< @page (count slides)))

(defn first-slide? [] (= @page 1))

(defn slideshow-component []
  [:div
   [:button {:on-click
             #(set! (-> js/document .-location .-hash) "#1")
             :disabled (first-slide?)}
    "first" ] " "
   [:button {:on-click
             #(set! (-> js/document .-location .-hash) (str "#" (dec @page)))
             :disabled (first-slide?)}
    "prev" ] " "
   [:button {:on-click
             #(set! (-> js/document .-location .-hash) (str "#" (inc @page)))
             :disabled (not (has-more-slides?))}
    "next" ] " "
   [:button {:on-click
             #(set! (-> js/document .-location .-hash) (str "#" (count slides)))
             :disabled (not (has-more-slides?))}
    "last" ]
   [slide-component]])

(loop [a 1]
  (if (< a 10)
    (do (println "a = " a)
        (recur (inc a)))))

(r/render [slideshow-component] (js/document.getElementById "brown-bag"))
