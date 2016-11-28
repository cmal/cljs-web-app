(ns cljs-web-app.core
  (:require
   [hipo.core :as hipo]
   [enfocus.core :as ef]
   [enfocus.events :as events]
   [enfocus.effects :as effects]
   )
  (:require-macros
   [enfocus.macros :as em])
  )

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

;; (def cnt-holder (gdom/getElement "clicks"))
;; (def reset-btn (gdom/getElement "reset-btn"))

;; (def cnt (atom 0))

;; (defn inc-clicks! []
;;   (gdom/setTextContent cnt-holder (swap! cnt inc)))

;; (defn reset-clicks! []
;;   (gdom/setTextContent cnt-holder (reset! cnt 0)))

;; (gevents/listen cnt-holder "click" inc-clicks!)
;; (gevents/listen reset-btn "click" reset-clicks!)

;; (def domina-div (css/sel "#domina-div"))
;; (def domina-href (dom/html-to-dom "<a></a>"))
;; (def domina-btn (dom/html-to-dom "<button></button>"))

;; (defn add-dom-elts! []

;;   (doto domina-href
;;     (dom/set-text! "Wikipedia")
;;     (dom/set-attr! :href "http://en.wikipedia.org"))
;;   (dom/append! domina-div domina-href)

;;   (doto domina-btn
;;     (dom/set-text! "Click me!")
;;     (dom/set-attr! :type "button"))
;;   (events/listen!
;;    domina-btn :click
;;    (fn [evt]
;;      (let [my-name (-> evt events/current-target dom/text)]
;;        (js/alert (str "Hello world! from : " my-name)))))
;;   (dom/append! domina-div domina-btn))

;; #_(add-dom-elts!)

;; (defn set-borders1! []
;;   (let [all-ps (sel [:#dommy-div :p])]
;;     (->> all-ps
;;          (map #(dommy/remove-class! % :changeme))
;;          (map #(dommy/add-class! % :border))
;;          (map #(dommy/set-text! % "I now have a border!"))
;;          doall)))

;; (defn set-borders2! []
;;   (let [all-ps (sel [:#dommy-div :p])]
;;     (js/console.log "log all p s" all-ps)
;;     (let [new-ps (->> all-ps
;;                       (map #(dommy/remove-class! % :changeme))
;;                       (map #(dommy/add-class! % :border))
;;                       (map #(dommy/set-text! % "I now have a border!")))]
;;       (js/console.log "log all p s done" (apply list new-ps)))))

;; (defn add-btn! []
;;   (let [the-div (sel1 :#dommy-div)
;;         a-btn (dommy/create-element "button")]
;;     (dommy/set-text! a-btn "Click me!")
;;     (dommy/listen! a-btn :click
;;                    (fn [e] (js/alert "You clicked me!")))
;;     (-> the-div (dommy/append! a-btn))))
;; (set-borders1!)  ;; 没有doall不成功是因为lazyseq, doall可以保证三个函数被执行
;; #_(set-borders2!)
;; #_(add-btn!)



;; ;; hipo

;; (defn create-menu-v [items]
;;   [:ul#my-menu
;;    (for [x items]
;;      [(:li {:id x}) x])])

;; (def menu (hipo/create (create-menu-v ["it1" "it2" "it3"])))

;; (defn add-menu! []
;;   (.appendChild js/document.body menu))

;; (defn reconcile-new-menu! []
;;   (hipo/reconciliate! menu (create-menu-v ["new it1" "new it2" "new it3"])))

;; #_(add-menu!)
;; #_(reconcile-new-menu!)

(defn gen-button
  [id caption]
  (ef/html [:button {:id id} caption])
  )

(defn say-hello! []
  (ef/at js/document
         ["#enfocus-div"] (ef/content "Hello From Enfocus!")
         ["body"] (ef/append (gen-button "btn1" "Click me!"))
         ["body"] (ef/append (gen-button "btn2" "Resize the div!"))
         ))

(em/defaction activate-button! []
  ["#btn1"] (events/listen :click #(js/alert "I am Clicked!"))
  )

(em/defaction resize-div! [param]
  ["#enfocus-div"] (effects/chain
                   (effects/resize param :curheight 500)
                   (effects/resize :curwidth (* 2 param) 500)
                   )
  )

(em/defaction activate-resize! []
  ["#btn2"] (events/listen :click #(resize-div! 200))
  )

(say-hello!)
(activate-button!)
(activate-resize!)
