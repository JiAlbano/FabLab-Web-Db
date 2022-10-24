class ApplicationController < ActionController::Base
  before_action :set_current_customer

  def set_current_customer
    Current.customer = Customer.find_by(id: session[:customer_id]) if session[:customer_id]
  end

  def require_customer_logged_in!
    redirect_back_or_to root_path if Current.customer.nil?
  end

end
