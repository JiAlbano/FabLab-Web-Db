class Appointment < ApplicationRecord
    belongs_to :customer

    enum status: [:pending, :approved, :denied, :cancelled]

    validates :datetime, :service, :purpose, presence: true
    validates_uniqueness_of :datetime, conditions: -> { where(status: :pending) }, message: "You must only have one pending appointment!"
end