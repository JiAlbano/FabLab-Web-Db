package org.jolt.fablab.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import org.jolt.fablab.models.Appointment;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController extends BaseController implements Initializable {

    @FXML
    private TableView<Appointment> appList;

    @FXML
    public TableColumn<Appointment, String> customerName;
    public TableColumn<Appointment, String> date;
    public TableColumn<Appointment, String> time;
    public TableColumn<Appointment, String> service;
    public TableColumn<Appointment, String> status;

    private ObservableList<Appointment> appointments = Appointment.fetchDataFromDb();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerName.setCellValueFactory(data -> data.getValue().customerNameProperty());
        date.setCellValueFactory(data -> data.getValue().dateProperty());
        time.setCellValueFactory(data -> data.getValue().timeProperty());
        service.setCellValueFactory(data -> data.getValue().serviceProperty());
        status.setCellValueFactory(data -> data.getValue().statusProperty());

        appList.setItems(appointments);
    }

    @FXML
    void closeBtnClicked(MouseEvent event) {
        getStage().close();
    }

    @FXML
    void minimizeBtnClicked(MouseEvent event) {
        getStage().setIconified(true);
    }
}



