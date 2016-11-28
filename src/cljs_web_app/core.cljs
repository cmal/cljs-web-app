(ns cljs-web-app.core
  (:require [goog.dom :as gdom]
            [goog.events :as gevents]
            [domina :as dom]
            [domina.css :as css]
            [domina.events :as events]
            [dommy.core :as dommy :refer-macros [sel sel1]])
  (:use [jayq.core :only [$ css html]]))

;; (enable-console-print!)

;; (println "This text is printed from src/cljs-web-app/core.cljs. Go ahead and edit it and see reloading in action.")

;; ;; define your app data so that it doesn't get over-written on reload

;; (defonce app-state (atom {:text "Hello world!"}))

;; (defn on-js-reload []
;;   ;; optionally touch your app-state to force rerendering depending on
;;   ;; your application
;;   ;; (swap! app-state update-in [:__figwheel_counter] inc)
;; )

;; (def $some-div ($ :#some-div))

;; (defn change-the-div! []
;;   (-> $some-div
;;       (css {:background "cyan"})
;;       (html "changed Inner HTML")))

;; #_ (change-the-div!)

(def cnt-holder (gdom/getElement "clicks"))
(def reset-btn (gdom/getElement "reset-btn"))

(def cnt (atom 0))

(defn inc-clicks! []
  (gdom/setTextContent cnt-holder (swap! cnt inc)))

(defn reset-clicks! []
  (gdom/setTextContent cnt-holder (reset! cnt 0)))

(gevents/listen cnt-holder "click" inc-clicks!)
(gevents/listen reset-btn "click" reset-clicks!)

(def domina-div (css/sel "#domina-div"))
(def domina-href (dom/html-to-dom "<a></a>"))
(def domina-btn (dom/html-to-dom "<button></button>"))

(defn add-dom-elts! []

  (doto domina-href
    (dom/set-text! "Wikipedia")
    (dom/set-attr! :href "http://en.wikipedia.org"))
  (dom/append! domina-div domina-href)

  (doto domina-btn
    (dom/set-text! "Click me!")
    (dom/set-attr! :type "button"))
  (events/listen!
   domina-btn :click
   (fn [evt]
     (let [my-name (-> evt events/current-target dom/text)]
       (js/alert (str "Hello world! from : " my-name)))))
  (dom/append! domina-div domina-btn))

#_(add-dom-elts!)

(defn set-borders1! []
  (let [all-ps (sel [:#dommy-div :p])]
    (->> all-ps
         (map #(dommy/remove-class! % :changeme))
         (map #(dommy/add-class! % :border))
         (map #(dommy/set-text! % "I now have a border!"))
         doall)))

(defn set-borders2! []
  (let [all-ps (sel [:#dommy-div :p])]
    (js/console.log "log all p s" all-ps)
    (let [new-ps (->> all-ps
                      (map #(dommy/remove-class! % :changeme))
                      (map #(dommy/add-class! % :border))
                      (map #(dommy/set-text! % "I now have a border!")))]
      (js/console.log "log all p s done" (apply list new-ps)))))

(defn add-btn! []
  (let [the-div (sel1 :#dommy-div)
        a-btn (dommy/create-element "button")]
    (dommy/set-text! a-btn "Click me!")
    (dommy/listen! a-btn :click
                   (fn [e] (js/alert "You clicked me!")))
    (-> the-div (dommy/append! a-btn))))
(set-borders1!)  ;; 没有doall不成功是因为lazyseq, doall可以保证三个函数被执行
#_(set-borders2!)
(add-btn!)
