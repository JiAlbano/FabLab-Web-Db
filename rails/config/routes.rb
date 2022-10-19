Rails.application.routes.draw do
  # Define your application routes per the DSL in https://guides.rubyonrails.org/routing.html

  # Login, Register, and Logout
  post "/login", to: "main#login"
  post "/signup", to: "main#signup"
  get "/logout", to: "main#logout"

  # Dashboard related
  get "/dashboard", to: "dashboard#index"
  get "/sidebar", to: "dashboard#sidebar"
  get "/request_appointment", to: "dashboard#request_appointment"

  # Defines the root path route ("/")
  root "main#index"
  
end
