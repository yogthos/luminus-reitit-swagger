(ns luminus-reitit-swagger.routes.home
  (:require [luminus-reitit-swagger.layout :as layout]
            [clojure.java.io :as io]
            [luminus-reitit-swagger.middleware :as middleware]))

(defn home-page [_]
  (layout/render
    "home.html" {:docs (-> "docs/docs.md" io/resource slurp)}))

(defn about-page [_]
  (layout/render "about.html"))

(defn home-routes []
  [""
   {:middleware [middleware/wrap-base
                 middleware/wrap-csrf
                 middleware/wrap-formats]}
   ["/" {:get home-page}]
   ["/about" {:get about-page}]])
