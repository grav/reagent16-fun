(ns reagent16-fun.core
  (:require [reagent.core]))

(enable-console-print!)

(println "This text is printed from src/reagent16-fun/core.cljs. Go ahead and edit it and see reloading in action.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (reagent.core/atom {:text "Hello world!"}))

(defn my-component []
  (throw (js/Error. "bang!")))

(defn app []
  (let [state (reagent.core/atom nil)]
    (reagent.core/create-class
      {:render              (fn []
                              [:div (str "this is my app. counter: " (:counter @app-state))
                               [:button {:on-click #(swap! app-state update :counter inc)} "click"]
                               [:div
                                (if (:error @state)
                                  [:div "error: " (:error @state)]
                                  [my-component])]])
       :component-did-catch (fn [_ _]
                              (swap! state assoc :error "crash!")
                              (prn "did catch"))})))
(defn mount-root []
  (reagent.core/render [app] (.getElementById js/document "app")))



(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  (mount-root)
  )
