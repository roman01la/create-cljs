(ns leiningen.new.create-cljs
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]))

(def render (renderer "create-cljs"))

(defn create-cljs
  "Generates ClojureScript project.
  lein new create-cljs project-name"
  [name]
  (let [data {:name name
              :sanitized (name-to-path name)}]

    (main/info "Generating fresh 'lein new' create-cljs project.")
    (->files data
             ["README.md" (render "README.md" data)]
             ["project.clj" (render "project.clj" data)]
             ["src/{{sanitized}}/core.cljs" (render "core.cljs" data)]
             ["resources/public/index.html" (render "index.html" data)]
             ["resources/public/css/main.css" (render "main.css" data)]
             [".gitignore" (render "gitignore" data)])))
