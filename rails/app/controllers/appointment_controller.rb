class AppointmentController < ApplicationController
  before_action :require_customer_logged_in!

  def confirm
    @appointment = Appointment.new(appointment_params)
  end

  def submit_appointment
    @appointment = Appointment.new(appointment_params)

    if @appointment.save
      redirect_to dashboard_path(:src => pending_path), notice: "Appointment has been requested!", status: :ok
    else
      redirect_to dashboard_path(:src => request_path), notice: "#{@appointment.errors.full_messages.to_sentence}", status: :unprocessable_entity
    end
  end

  def cancel_appointment

  end

  private
  def appointment_params
    params.require(:appointment).permit(:customer_id, :datetime, :service, :purpose, :status)
  end

end