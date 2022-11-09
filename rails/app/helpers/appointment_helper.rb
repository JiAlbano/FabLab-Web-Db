module AppointmentHelper
  def statusColor(status)
    case status
    when :pending # pending
      "#eded85"
    when :approved # approved
      "#12a282"
    else # denied/cancelled
      "#e63939"
    end
  end
end