class MainController < ApplicationController
    def index

    end

    def signup
        @customer = Customer.new(signup_login_params)
        @customer_detail = CustomerDetail.new(signup_detail_params)

        if @customer.save
            @customer_detail.customer = @customer
            if @customer_detail.save
                puts "successful registration"
            else
                @customer.destroy
                puts "failed on customer detail"
            end
        else
            puts "failed on customer"
        end
    end

    def login
        @customer = Customer.find_by(username: login_params[:username])

        if @customer.present? && @customer.authenticate(login_params[:password])
            puts "successful login"
        else
            puts "not successful login"
        end
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