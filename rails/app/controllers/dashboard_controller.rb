class DashboardController < ApplicationController
  before_action :require_customer_logged_in!

  def index
  end

  def pending
    @appointment = Appointment.pending.find_by(customer_id: Current.customer.id)
  end

  def history
    @appointments = Current.customer.appointments.where.not(status: 0).order("status ASC, datetime DESC")
  end

  def request_new
  end
end