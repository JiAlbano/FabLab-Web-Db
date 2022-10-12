Rails.application.routes.draw do
  # Define your application routes per the DSL in https://guides.rubyonrails.org/routing.html

  # Defines the root path route ("/")
  root "main#index"
  get "/signup", to: "main#signup"
  get "/login", to: "main#login"

end
