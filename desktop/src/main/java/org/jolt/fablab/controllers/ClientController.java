package org.jolt.fablab.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.jolt.fablab.models.Appointment;

import java.net.URL;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.ResourceBundle;

public class ClientController extends BaseController {

    private Appointment appointment;
    @FXML
    private Label name, email, status, service, purpose;
    @FXML
    private Label day, month, year, time, am_pm;
    @FXML
    private MFXButton approveBtn, denyBtn;

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public void setDetails() {
        name.setText(appointment.getCustomer().getFullName());
        email.setText(appointment.getCustomer().getEmail());
        status.setText(appointment.getStatus());
        service.setText(appointment.getService());
        purpose.setText(appointment.getPurpose());

        LocalDate date = LocalDate.parse(appointment.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalTime _time = LocalTime.parse(appointment.getTime(), DateTimeFormatter.ofPattern("HH:mm"));

        day.setText(String.valueOf(date.getDayOfMonth()));
        month.setText(date.format(DateTimeFormatter.ofPattern("MMM")));
        year.setText(String.valueOf(date.getYear()));

        time.setText(_time.format(DateTimeFormatter.ofPattern("h:mm")));
        am_pm.setText(_time.format(DateTimeFormatter.ofPattern("a")));
    }

    @FXML
    void approveBtnClicked(MouseEvent event) {
        appointment.updateStatus(Appointment.Status.approved);
        getStage().close();
    }

    @FXML
    void denyBtnClicked(MouseEvent event) {
        appointment.updateStatus(Appointment.Status.denied);
        getStage().close();
    }
}
