package org.jolt.fablab.models;

import com.mysql.cj.protocol.a.OffsetDateTimeValueEncoder;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jolt.fablab.MainApplication;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Appointment {
    private IntegerProperty id;
    private StringProperty customerName, date, time, service, purpose, status;
    private Customer customer;

    public enum Status {
        pending,
        approved,
        denied,
        cancelled
    }
    public static final Status[] statuses = Status.values();

    public static Map<String, String> getAppointmentMap(Object[] objects) {
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(objects[0]));
        map.put("customer_id", String.valueOf(objects[1]));
        map.put("datetime", String.valueOf(objects[2]));
        map.put("service", String.valueOf(objects[3]));
        map.put("purpose", String.valueOf(objects[4]));
        map.put("status", String.valueOf(objects[5]));

        return map;
    }

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

                appointments.add(new Appointment(id, rs.getInt("customer_id"), customerName, datetime.toLocalDate().toString(), datetime.toLocalTime().toString(), service, purpose, status));
            }

            return appointments;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static OffsetDateTime getOffsetDateTime(int id) {
        Connection conn = MainApplication.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT datetime FROM appointments WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
                return rs.getObject(1, OffsetDateTime.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Appointment(int id, int customer_id, String customerName, String date, String time, String service, String purpose, String status) {
        this.id = new SimpleIntegerProperty(id);
        this.customer = Objects.requireNonNull(Customer.getCustomerFromId(customer_id));
        this.customerName = new SimpleStringProperty(customerName);
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
        this.service = new SimpleStringProperty(service);
        this.purpose = new SimpleStringProperty(purpose);
        this.status = new SimpleStringProperty(status);
    }

    public Appointment(Object[] objects) {
        OffsetDateTime dateTime = getOffsetDateTime(((Long)objects[0]).intValue());
        Customer customer = Objects.requireNonNull(Customer.getCustomerFromId(((Long)objects[1]).intValue()));

        this.id = new SimpleIntegerProperty(((Long)objects[0]).intValue());
        this.customer = customer;
        this.customerName = new SimpleStringProperty(customer.getFullName());
        this.date = new SimpleStringProperty(dateTime.toLocalDate().toString());
        this.time = new SimpleStringProperty(dateTime.toLocalTime().toString());
        this.service = new SimpleStringProperty(objects[3].toString());
        this.purpose = new SimpleStringProperty(objects[4].toString());
        this.status = new SimpleStringProperty(statuses[((Integer)objects[5])].name());
    }

    public Appointment(Map<String, String> appointmentMap) {
        OffsetDateTime dateTime = getOffsetDateTime(Integer.parseInt(appointmentMap.get("id")));
        assert dateTime != null;
        Customer customer = Objects.requireNonNull(Customer.getCustomerFromId(Integer.parseInt(appointmentMap.get("customer_id"))));

        this.id = new SimpleIntegerProperty(Integer.parseInt(appointmentMap.get("id")));
        this.customer = customer;
        this.customerName = new SimpleStringProperty(customer.getFullName());
        this.date = new SimpleStringProperty(dateTime.toLocalDate().toString());
        this.time = new SimpleStringProperty(dateTime.toLocalTime().toString());
        this.service = new SimpleStringProperty(appointmentMap.get("service"));
        this.purpose = new SimpleStringProperty(appointmentMap.get("purpose"));
        this.status = new SimpleStringProperty(statuses[Integer.parseInt(appointmentMap.get("status"))].name());
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

    public void setStatus(String status) {
        this.status.set(status);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
