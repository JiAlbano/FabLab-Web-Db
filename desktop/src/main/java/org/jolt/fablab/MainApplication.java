package org.jolt.fablab;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jolt.fablab.controllers.BaseController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

public class MainApplication extends Application {
    private Connection conn = null;

    @Override
    public void init() throws Exception {
        super.init();

        // Connect to Database
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://jolt:jolt@localhost:3306/fablab_dev");
            System.out.println("Connection successful!");
        } catch (Exception ex) {
            System.out.println("Connection unsuccessful.");
            Platform.exit();
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/Login/login-view.fxml"));
        fxmlLoader.setControllerFactory(_class -> {
            Object controller;
            try {
                controller = _class.getConstructor().newInstance();
            } catch (ReflectiveOperationException ex) {
                throw new RuntimeException(ex);
            }

            if (controller instanceof BaseController) {
                ((BaseController) controller).setMainApp(this);
                ((BaseController) controller).setDatabaseConnection(this.conn);
            }
            return controller;
        });
        Scene scene = new Scene(fxmlLoader.load());
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        ((BaseController) fxmlLoader.getController()).setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        do {
            conn.close();
        } while (!conn.isClosed());

        System.out.println("Database connection closed");
    }

    public static void main(String[] args) {
        launch();
    }
}