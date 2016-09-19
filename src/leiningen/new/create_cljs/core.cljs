(ns {{name}}.core
  (:require {{#rum?}}[rum.core :as rum]{{/rum?}}))

(enable-console-print!)

(println "This text is printed from src/{{name}}/core.cljs. Go ahead and edit it and see reloading in action.")

{{#rum?}}
(defonce app-state (atom 0))

(rum/defc hello-world < rum/reactive []
  [:div
    [:h1 "Counter"]
    [:div
      [:button {:on-click #(swap! app-state dec)} "-"]
      [:span {:style {:margin "0 10px"}} (rum/react app-state)]
      [:button {:on-click #(swap! app-state inc)} "+"]]])

(rum/mount
  (hello-world)
  (. js/document (getElementById "app")))
{{/rum?}}
{{^rum?}}
(def html "<h1>{{name}}</h1><h2>Hello, world!</h2>")
(def app-root (.getElementById js/document "app"))

(set! (.-innerHTML app-root) html)
{{/rum?}}
