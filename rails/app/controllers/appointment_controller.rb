class AppointmentController < ApplicationController
  before_action :require_customer_logged_in!

  def confirm
    @appointment = Appointment.new(appointment_params)
  end

  def submit_appointment
    @appointment = Appointment.new(appointment_params)

    respond_to do |format|
      if @appointment.save
        format.html {
          redirect_to dashboard_path(:src => pending_path), notice: "Appointment has been requested!"
        }
      else
        format.html {
          redirect_to dashboard_path(:src => request_path), notice: "#{@appointment.errors.full_messages.to_sentence}", status: :see_other
        }
      end
    end
  end

  def cancel_appointment
    @appointment = Appointment.find_by(id: appointment_params[:id])

    respond_to do |format|
      if @appointment.present? && @appointment.status != :cancelled
        if @appointment.update(status: :cancelled)
          format.html {
            redirect_to dashboard_path(:src => pending_path), notice: "Appointment has been cancelled!"
          }
        else
          format.html {
            redirect_to dashboard_path(:src => pending_path), notice: "#{@appointment.errors.full_messages.to_sentence}", status: :see_other
          }
        end
      else
        format.html {
          redirect_to dashboard_path(:src => pending_path), notice: "Appointment does not exist or is already cancelled!", status: :see_other
        }
      end
    end
  end

  private

  def appointment_params
    params.require(:appointment).permit(:id, :customer_id, :datetime, :service, :purpose, :status)
  end

end