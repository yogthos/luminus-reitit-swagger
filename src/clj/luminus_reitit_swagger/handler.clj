(ns luminus-reitit-swagger.handler
  (:require
    [luminus-reitit-swagger.layout :refer [error-page]]
    [luminus-reitit-swagger.routes.home :refer [home-routes]]
    [luminus-reitit-swagger.routes.services :refer [service-routes]]
    [luminus-reitit-swagger.env :refer [defaults]]
    [mount.core :as mount]
    [reitit.ring :as ring]
    [reitit.swagger-ui :as swagger-ui]
    [ring.middleware.content-type :refer [wrap-content-type]]
    [ring.middleware.webjars :refer [wrap-webjars]]))

(mount/defstate init-app
  :start ((or (:init defaults) identity))
  :stop  ((or (:stop defaults) identity)))

(mount/defstate app
  :start
  (ring/ring-handler
    (ring/router
      (concat (home-routes) (service-routes)))
    (ring/routes
      (swagger-ui/create-swagger-ui-handler {:root "/swagger-ui" :path "/" :url "/api/swagger.json"})
      (ring/create-resource-handler {:path "/"})
      (wrap-content-type (wrap-webjars (constantly nil)))
      (ring/create-default-handler
        {:not-found
         (constantly (error-page {:status 404, :title "404 - Page not found"}))
         :method-not-allowed
         (constantly (error-page {:status 405, :title "405 - Not allowed"}))
         :not-acceptable
         (constantly (error-page {:status 406, :title "406 - Not acceptable"}))}))))
