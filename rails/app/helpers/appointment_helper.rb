module AppointmentHelper
  def statusColor(status)
    case status
    when "pending"
      "#eded85"
    when "approved"
      "#12a282"
    else # denied/cancelled
      "#e63939"
    end
  end
end