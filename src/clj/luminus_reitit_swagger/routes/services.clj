(ns luminus-reitit-swagger.routes.services
  (:require
    [reitit.swagger :as swagger]
    [reitit.ring.coercion :as rrc]
    [ring.middleware.params :as params]
    [muuntaja.middleware :as muuntaja]))

(defn service-routes []
  [["/api"
    {:middleware [params/wrap-params
                  muuntaja/wrap-format
                  swagger/swagger-feature
                  rrc/coerce-exceptions-middleware
                  rrc/coerce-request-middleware
                  rrc/coerce-response-middleware]
     :swagger {:id ::api
               :produces #{"application/json"
                           "application/edn"
                           "application/transit+json"}
               :consumes #{"application/json"
                           "application/edn"
                           "application/transit+json"}}}
    ["/swagger.json"
     {:get {:no-doc true
            :swagger {:info {:title "my-api"}}
            :handler (swagger/create-swagger-handler)}}]

    ["/ping" {:get (constantly "ping")}]
    ["/pong" {:post (constantly "pong")}]]])