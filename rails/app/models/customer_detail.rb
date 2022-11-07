class CustomerDetail < ApplicationRecord
    belongs_to :customer, class_name: "Customer"

    validates :customer, :first_name, :last_name, :email, :contact_number, presence: true
    validates :email, format: { with: URI::MailTo::EMAIL_REGEXP }, uniqueness: true
    validates :contact_number, format: { with: /\A(09)+[\d]{9}\z/, message: "Number must be 11 digits long and start with '09'" }, length: { is: 11 }

    def full_name
        self.first_name + ' ' + self.last_name
    end
end
