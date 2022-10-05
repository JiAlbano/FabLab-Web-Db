class Employee < ApplicationRecord
    validates :username, :password, presence: true
end
