class ApplicationController < ActionController::Base
  before_action :set_current_customer

  def set_current_customer
    Current.customer = Customer.find_by(id: session[:customer_id]) if session[:customer_id]
  end

  def require_customer_logged_in!
    redirect_to root_path, notice: "You must be logged in to do that!" if Current.customer.nil?
  end

end
