class DashboardController < ApplicationController
  before_action :require_customer_logged_in!

  def index

  end
end