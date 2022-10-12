class Appointment < ApplicationRecord
    belongs_to :customer

    validates :date, :time, :purpose, :status, presence: true
end
