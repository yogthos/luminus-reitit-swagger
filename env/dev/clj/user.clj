(ns user
  (:require [luminus-reitit-swagger.config :refer [env]]
            [clojure.spec.alpha :as s]
            [expound.alpha :as expound]
            [mount.core :as mount]
            [luminus-reitit-swagger.core :refer [start-app]]))

(alter-var-root #'s/*explain-out* (constantly expound/printer))

(defn start []
  (mount/start-without #'luminus-reitit-swagger.core/repl-server))

(defn stop []
  (mount/stop-except #'luminus-reitit-swagger.core/repl-server))

(defn restart []
  (stop)
  (start))


