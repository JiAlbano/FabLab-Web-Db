class Customer < ApplicationRecord
    has_secure_password

    has_one :customer_detail, dependent: :destroy
    has_many :appointments, dependent: :destroy

    validates :username, :password_digest, presence: true
end
