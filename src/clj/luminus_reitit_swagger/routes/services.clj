(ns luminus-reitit-swagger.routes.services
  (:require
    [reitit.swagger :as swagger]
    [reitit.ring.coercion :as rrc]
    [ring.middleware.params :as params]
    [ring.util.http-response :refer :all]
    [muuntaja.middleware :as muuntaja]))

(defn service-routes []
  ["/api"
   {:middleware [params/wrap-params
                 muuntaja/wrap-format
                 swagger/swagger-feature
                 rrc/coerce-exceptions-middleware
                 rrc/coerce-request-middleware
                 rrc/coerce-response-middleware]
    :swagger {:id ::api
              :info {:title "my-api"
                     :description "using [reitit](https://github.com/metosin/reitit)."}
              :produces #{"application/json"
                          "application/edn"
                          "application/transit+json"}
              :consumes #{"application/json"
                          "application/edn"
                          "application/transit+json"}}}
   ["/swagger.json"
    {:get {:no-doc true
           :handler (swagger/create-swagger-handler)}}]

   ["/ping" {:get (constantly (ok {:message "ping"}))}]
   ["/pong" {:post (constantly (ok {:message "pong"}))}]])
