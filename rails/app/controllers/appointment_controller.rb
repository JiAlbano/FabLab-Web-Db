class AppointmentController < ApplicationController
  before_action :require_customer_logged_in!

  def confirm
    @appointment = Appointment.new(appointment_params)
  end

  def submit_appointment
    @appointment = Appointment.new(appointment_params)

    if @appointment.save
      redirect_to dashboard_path(:src => pending_path), notice: "Appointment has been requested!", status: :see_other
    else
      redirect_to dashboard_path(:src => request_path), notice: "#{@appointment.errors.full_messages.to_sentence}", status: :unprocessable_entity
    end
  end

  def cancel_appointment
    @appointment = Appointment.find_by(id: appointment_params[:id])

    if @appointment.present? && @appointment.status != :cancelled
      if @appointment.update(status: :cancelled)
        redirect_to dashboard_path(:src => pending_path), notice: "Appointment has been cancelled!", status: :see_other
      else
        redirect_to dashboard_path(:src => pending_path), notice: "#{@appointment.errors.full_messages.to_sentence}", status: :unprocessable_entity
      end
    else
      redirect_to dashboard_path(:src => pending_path), notice: "Appointment does not exist or is already cancelled!", status: :unprocessable_entity
    end
  end

  private
  def appointment_params
    params.require(:appointment).permit(:id, :customer_id, :datetime, :service, :purpose, :status)
  end

end