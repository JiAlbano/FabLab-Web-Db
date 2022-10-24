class Appointment < ApplicationRecord
    belongs_to :customer

    validates :date, :time, :service, :purpose, :status, presence: true
end
