package org.jolt.fablab.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jolt.fablab.MainApplication;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.OffsetDateTime;
import java.util.Objects;

public class Appointment {
    private IntegerProperty id;
    private StringProperty customerName, date, time, service, purpose, status;

    public enum Status {
        pending,
        approved,
        denied,
        cancelled
    }
    public static final Status[] statuses = Status.values();

    public static ObservableList<Appointment> fetchDataFromDb() {
        Connection conn = MainApplication.getConnection();
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM appointments");

            while (rs.next()) {
                int id = rs.getInt("id");
                String customerName = Objects.requireNonNull(Customer.getCustomerFromId(rs.getInt("customer_id"))).getFullName();
                OffsetDateTime datetime = rs.getObject("datetime", OffsetDateTime.class);
                String service = rs.getString("service");
                String purpose = rs.getString("purpose");
                String status = statuses[rs.getInt("status")].name();

                appointments.add(new Appointment(id, customerName, datetime.toLocalDate().toString(), datetime.toLocalTime().toString(), service, purpose, status));
            }

            return appointments;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Appointment(int id, String customerName, String date, String time, String service, String purpose, String status) {
        this.id = new SimpleIntegerProperty(id);
        this.customerName = new SimpleStringProperty(customerName);
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
        this.service = new SimpleStringProperty(service);
        this.purpose = new SimpleStringProperty(purpose);
        this.status = new SimpleStringProperty(status);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(int status) {
        this.status.set(statuses[status].name());
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public StringProperty customerNameProperty() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getTime() {
        return time.get();
    }

    public StringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public String getService() {
        return service.get();
    }

    public StringProperty serviceProperty() {
        return service;
    }

    public void setService(String service) {
        this.service.set(service);
    }

    public String getPurpose() {
        return purpose.get();
    }

    public StringProperty purposeProperty() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose.set(purpose);
    }
}
