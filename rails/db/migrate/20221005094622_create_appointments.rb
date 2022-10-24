class CreateAppointments < ActiveRecord::Migration[7.0]
  def change
    create_table :appointments do |t|
      t.belongs_to :customer
      t.date :date
      t.time :time
      t.string :service
      t.string :purpose
      t.string :status

      t.timestamps
    end
  end
end
