package org.jolt.fablab;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jolt.fablab.controllers.BaseController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

public class MainApplication extends Application {
    private static Connection conn = null;
    private static BinaryLogClient client = null;

    @Override
    public void init() throws Exception {
        super.init();

        // Connect to Database
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://root:password@localhost:3307/fablab_dev");
            System.out.println("Connection successful!");

            client = new BinaryLogClient("localhost", 3307, "fablab_dev","root", "password");
            System.out.println("Set BinaryLogClient!");
        } catch (Exception ex) {
            System.out.println("Connection unsuccessful.");
            Platform.exit();
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = fxmlLoaderBuilder("views/Login/login-view.fxml");
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        ((BaseController) fxmlLoader.getController()).setScene(scene);
        ((BaseController) fxmlLoader.getController()).setStage(stage);
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

    public FXMLLoader fxmlLoaderBuilder(String resourcePath) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(resourcePath));
        fxmlLoader.setControllerFactory(_class -> {
            Object controller;
            try {
                controller = _class.getConstructor().newInstance();
            } catch (ReflectiveOperationException ex) {
                throw new RuntimeException(ex);
            }

            if (controller instanceof BaseController) {
                ((BaseController) controller).setMainApp(this);
            }
            return controller;
        });

        return fxmlLoader;
    }

    public static Connection getConnection() {
        return conn;
    }
    public static BinaryLogClient getClient() { return client; }
}