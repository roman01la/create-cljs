(defproject {{name}} "0.1.0-SNAPSHOT"
  :license {:name "Mozilla Public License 2.0" :url "https://www.mozilla.org/en-US/MPL/2.0/"}

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.229"]]

  :profiles {:dev {:dependencies [[figwheel-sidecar "0.5.4-7"]]}}

  :source-paths ["src"]

  :plugins [[lein-cljsbuild "1.1.4"]
            [lein-figwheel "0.5.4-7"]]

  :clean-targets ^{:protect false}
  [:target-path :compile-path "resources/public/js/out"]

  :cljsbuild {:builds
              {:dev {:source-paths ["src"]
                     :figwheel true
                     :compiler {:main {{name}}.core
                                :optimizations :none
                                :output-to "resources/public/js/bundle.js"
                                :output-dir "resources/public/js/out"
                                :asset-path "js/out"
                                :source-map-timestamp true}}

               :prod {:source-paths ["src"]
                      :compiler {:main {{name}}.core
                                 :optimizations :advanced
                                 :output-to "resources/public/js/bundle.js"
                                 :pretty-print false}}}}

  :figwheel {:css-dirs ["resources/public/css"]})
