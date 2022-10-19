class MainController < ApplicationController
    def index
    end

    def signup
        @customer = Customer.new(signup_login_params)
        @customer_detail = CustomerDetail.new(signup_detail_params)

        if @customer.save
            @customer_detail.customer = @customer
            if @customer_detail.save
                redirect_to root_path, notice: "Successfully created account!"
            else
                @customer.destroy
                warn "Failed on saving @customer_detail"
                redirect_to root_path, notice: "Failed in creating account..."
            end
        else
            warn "Failed on saving @customer"
            redirect_to root_path, notice: "Failed in creating account..."
        end
    end

    def login
        @customer = Customer.find_by(username: login_params[:username])

        if @customer.present? && @customer.authenticate(login_params[:password])
            session[:customer_id] = @customer.id
            redirect_to root_path, notice: "Welcome, #{@customer.customer_detail.full_name}"
        else
            redirect_to root_path, notice: "Invalid username and/or password."
        end
    end

    def logout
        session[:customer_id] = nil

        redirect_to root_path, notice: "You have logged out."
    end

    private
    def signup_detail_params
        params.require(:customer_detail).permit(:first_name, :last_name, :email, :contact_number);
    end

    def signup_login_params
        params.require(:customer).permit(:username, :password, :password_confirmation);
    end

    def login_params
        params.require(:customer).permit(:username, :password);
    end
end