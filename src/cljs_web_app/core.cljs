(ns cljs-web-app.core
  (:use [jayq.core :only [$ css html]]))

(enable-console-print!)

(println "This text is printed from src/cljs-web-app/core.cljs. Go ahead and edit it and see reloading in action.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"}))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)

(def $some-div ($ :#some-div))

(defn change-the-div! []
  (-> $some-div
      (css {:background "cyan"})
      (html "changed Inner HTML")))

#_ (change-the-div!)
