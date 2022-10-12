class CustomerDetail < ApplicationRecord
    belongs_to :customer

    validates :customer, :first_name, :last_name, :email, :contact_number, presence: true
    validates :email, format: { with: URI::MailTo::EMAIL_REGEXP }
    validates :contact_number, format: { with: /\A(09)+[\d]{9}\z/, message: "Number must be 11 digits long and start with '09'" }, length: { is: 11 }
end
