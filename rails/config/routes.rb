Rails.application.routes.draw do
  # Define your application routes per the DSL in https://guides.rubyonrails.org/routing.html

  # Defines the root path route ("/")
  root "main#index"

  get "sidebar", to: "dashboard#sidebar"
  get "index", to: "dashboard#index"
  get "request_appointment", to: "dashboard#request_appointment"
  
end
