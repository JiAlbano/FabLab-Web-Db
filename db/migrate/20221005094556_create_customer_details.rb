class CreateCustomerDetails < ActiveRecord::Migration[7.0]
  def change
    create_table :customer_details do |t|
      t.belongs_to :customer
      t.string :first_name
      t.string :last_name
      t.string :email
      t.string :contact_number

      t.timestamps
    end
  end
end
