package org.jolt.fablab.controllers;

import javafx.scene.Scene;
import org.jolt.fablab.MainApplication;

import java.sql.Connection;

public abstract class BaseController {

    protected Connection conn;
    protected MainApplication application;
    protected Scene scene;

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setMainApp(MainApplication application) {
        this.application = application;
    }

    public void setDatabaseConnection(Connection conn) {
        this.conn = conn;
    }
}
