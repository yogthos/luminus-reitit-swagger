(ns luminus-reitit-swagger.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [luminus-reitit-swagger.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[luminus-reitit-swagger started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[luminus-reitit-swagger has shut down successfully]=-"))
   :middleware wrap-dev})
