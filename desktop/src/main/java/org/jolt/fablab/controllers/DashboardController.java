package org.jolt.fablab.controllers;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jolt.fablab.MainApplication;
import org.jolt.fablab.models.Appointment;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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

    private ObservableList<Appointment> appointments = Appointment.fetchDataFromDb(Appointment.Status.pending);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerName.setCellValueFactory(data -> data.getValue().customerNameProperty());
        date.setCellValueFactory(data -> data.getValue().dateProperty());
        time.setCellValueFactory(data -> data.getValue().timeProperty());
        service.setCellValueFactory(data -> data.getValue().serviceProperty());
        status.setCellValueFactory(data -> data.getValue().statusProperty());

        appList.setRowFactory(tv -> {
            TableRow<Appointment> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    try {
                        Appointment appointment = row.getItem();
                        FXMLLoader fxmlLoader = application.fxmlLoaderBuilder("views/Dashboard/client-view.fxml");
                        Parent root = fxmlLoader.load();
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        ((ClientController)fxmlLoader.getController()).setAppointment(appointment);
                        ((ClientController)fxmlLoader.getController()).setDetails();
                        ((ClientController)fxmlLoader.getController()).setScene(scene);
                        ((ClientController)fxmlLoader.getController()).setStage(stage);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initStyle(StageStyle.UTILITY);
                        stage.setResizable(false);
                        stage.setScene(scene);
                        stage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
        appList.setItems(appointments);

        try {
            BinaryLogClient client = MainApplication.getClient();
            new Thread(() -> {
                final Map<String, Long> tableMap = new HashMap<String, Long>();
                client.registerEventListener(event -> {
                    EventData data = event.getData();

                    if (data instanceof TableMapEventData tableData) {
                        tableMap.put(tableData.getTable(), tableData.getTableId());
                    } else if (data instanceof WriteRowsEventData eventData) {
                        if (eventData.getTableId() == tableMap.get("appointments")) {
                            System.out.println("New appointment");

                            for (Object[] row: eventData.getRows()) {
                                System.out.println(Arrays.asList(row));
                                appointments.add(new Appointment(row));
                            }
                            appList.refresh();
                        }
                    } else if (data instanceof UpdateRowsEventData eventData) {
                        if (eventData.getTableId() == tableMap.get("appointments")) {
                            System.out.println("Appointment update");

                            for (Map.Entry<Serializable[], Serializable[]> row : eventData.getRows()) {
                                Map<String, String> appointmentMap = Appointment.getAppointmentMap(row.getValue());
                                System.out.println(appointmentMap);

                                for (Appointment appointment : appointments) {
                                    if (appointment.getId() == Integer.parseInt(appointmentMap.get("id"))) {
                                        int index = appointments.indexOf(appointment);
                                        appointments.set(index, new Appointment(appointmentMap));
                                        break;
                                    }
                                }
                            }
                            appList.refresh();
                        }
                    } else if (data instanceof DeleteRowsEventData eventData) {
                        if (eventData.getTableId() == tableMap.get("appointments")) {
                            System.out.println("Appointment delete");

                            for (Object[] row : eventData.getRows()) {
                                System.out.println(Arrays.asList(row));
                                for (Appointment appointment : appointments) {
                                    if (appointment.getId() == (Long) row[0]) {
                                        appointments.remove(appointment);
                                        break;
                                    }
                                }
                            }
                            appList.refresh();
                        }
                    }

                    appList.refresh();
                });
                try {
                    client.connect();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void pendingBtnClicked(MouseEvent event) {
        appointments.clear();
        appointments = Appointment.fetchDataFromDb(Appointment.Status.pending);
        appList.setItems(appointments);
        appList.refresh();
    }

    @FXML
    void cancelBtnClicked(MouseEvent event) {
        appointments.clear();
        appointments = Appointment.fetchDataFromDb(Appointment.Status.cancelled);
        appList.setItems(appointments);
        appList.refresh();
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



