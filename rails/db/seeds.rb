# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the bin/rails db:seed command (or created alongside the database with db:setup).
#
# Examples:
#
#   movies = Movie.create([{ name: "Star Wars" }, { name: "Lord of the Rings" }])
#   Character.create(name: "Luke", movie: movies.first)
Employee.create(username: "admin", password: "admin")

Service.create(name: "Workspace")
Service.create(name: "Training")
Service.create(name: "Workshop")
Service.create(name: "Consultation")
Service.create(name: "3D Scanning")
Service.create(name: "3D Printing")
Service.create(name: "Print & Cut")
Service.create(name: "CNC Machine")
Service.create(name: "Universal Laser System (ULC)")
Service.create(name: "Embroidery Machine")