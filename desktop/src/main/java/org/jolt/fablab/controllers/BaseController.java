package org.jolt.fablab.controllers;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jolt.fablab.MainApplication;

import java.sql.Connection;

public abstract class BaseController {

    protected Connection conn;
    protected MainApplication application;
    protected Scene scene;
    protected Stage stage;

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setMainApp(MainApplication application) {
        this.application = application;
    }

    public void setDatabaseConnection(Connection conn) {
        this.conn = conn;
    }

    public Stage getStage() {
        return (Stage) scene.getWindow();
    }
}
