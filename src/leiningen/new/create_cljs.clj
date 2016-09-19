(ns leiningen.new.create-cljs
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]))

(def render (renderer "create-cljs"))

;; Copied from https://github.com/bhauman/figwheel-template/blob/master/src/leiningen/new/figwheel.clj

(def valid-options
  ["rum"])

(doseq [opt valid-options]
  (eval
   `(defn ~(symbol (str opt "?")) [opts#]
     (some #{~(str "--" opt)} opts#))))

(defn valid-number-of-opts? [opts]
  (let [count 0
        count (if (rum? opts) (inc count) count)]
    (or (= count 0) (= count 1))))

(defn clean-opts
  "Takes the incoming options and compares them to the valid ones.
   It aborts the process and spits an error if an invalid option is present
   or more then one options was specified."
  [valid-options opts]
  (let [valid-opts (map (partial str "--") valid-options)]
    (doseq [opt opts]
      (if-not (some #{opt} valid-opts)
        (apply main/abort "Unrecognized option:" opt ". Should be one of" valid-opts)))
    (if (not (valid-number-of-opts? opts))
      (main/abort "Multiple options can't be specified at the same time. Please choose only one.")
      valid-opts)))

(defn create-cljs
  "Takes a name and options with the form --option and produces an interactive
   ClojureScript + Figwheel template.
   The valid options are:
     --rum     which adds a minimal Rum application in core.cljs
   Only one option can be specified at a time. If no option is specified,
   nothing but a print statment is added in core.cljs"
  [name & opts]
  (do
    (clean-opts valid-options opts)
    (let [data {:name name
                :sanitized (name-to-path name)
                :rum? (rum? opts)}]

      (main/info (str "Generating fresh 'lein new' create-cljs project.\n"
                      "Change into your '" name "' directory and run 'lein figwheel'\n"
                      "Wait for it to finish compiling\n"
                      "Then open 'http://localhost:3449/' in your browser"))
      (->files data
               ["README.md" (render "README.md" data)]
               ["project.clj" (render "project.clj" data)]
               ["src/{{sanitized}}/core.cljs" (render "core.cljs" data)]
               ["resources/public/index.html" (render "index.html" data)]
               ["resources/public/css/main.css" (render "main.css" data)]
               [".gitignore" (render "gitignore" data)]))))
