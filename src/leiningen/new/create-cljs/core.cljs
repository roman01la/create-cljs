(ns {{name}}.core)

(enable-console-print!)

(def html "<h1>{{name}}</h1><h2>Hello, world!</h2>")
(def app-root (.getElementById js/document "app"))

(set! (.-innerHTML app-root) html)

(println (str "Hello, ClojureScript!"))
