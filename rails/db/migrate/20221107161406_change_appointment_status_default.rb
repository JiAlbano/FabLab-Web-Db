class ChangeAppointmentStatusDefault < ActiveRecord::Migration[7.0]
  def change
    change_column :appointments, :status, :integer
    change_column_default :appointments, :status, 0
  end
end
