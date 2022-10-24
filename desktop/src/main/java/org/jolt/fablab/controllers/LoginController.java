package org.jolt.fablab.controllers;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import io.github.palexdev.materialfx.enums.ScrimPriority;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.stage.StageStyle;
import org.jolt.fablab.MainApplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

public class LoginController extends BaseController {

    public MFXTextField usernameField;
    public MFXPasswordField passwordField;

    @FXML
    void closeBtnClicked(MouseEvent event) {
        getStage().close();
    }

    @FXML
    void minimizeBtnClicked(MouseEvent event) {
        getStage().setIconified(true);
    }

    @FXML
    private AnchorPane loginPane;

    @FXML
    public void loginBtnClicked(MouseEvent mouseEvent) {
        MFXGenericDialog dialogContent = MFXGenericDialogBuilder.build()
                .makeScrollable(false)
                .get();
        MFXStageDialog dialog = MFXGenericDialogBuilder.build(dialogContent)
                .toStageDialogBuilder()
                .initOwner(scene.getWindow())
                .initModality(Modality.APPLICATION_MODAL)
                .setDraggable(false)
                .setTitle("Login")
                .setOwnerNode(loginPane)
                .setScrimPriority(ScrimPriority.WINDOW)
                .setScrimOwner(true)
                .get();

        dialogContent.addActions(
                Map.entry(new MFXButton("OK"), event -> {
                    dialog.close();
                })
        );
        dialogContent.setHeaderText("Login");
        dialogContent.setMaxSize(400, 200);

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT EXISTS(SELECT * FROM employees WHERE username = ? AND password = ?)");
            ps.setString(1, usernameField.getText());
            ps.setString(2, passwordField.getText());

            ResultSet rs = ps.executeQuery();
            rs.next();
            if (rs.getInt(1) == 1) {
                FXMLLoader fxmlLoader = application.fxmlLoaderBuilder("views/Dashboard/dashboard-view.fxml");
                Parent root = fxmlLoader.load();
                Scene dbScene = new Scene(root);
                Stage dbStage = new Stage();

                dbScene.setFill(Color.TRANSPARENT);
                dbStage.initStyle(StageStyle.TRANSPARENT);
                dbStage.setScene(dbScene);
                ((BaseController) fxmlLoader.getController()).setScene(dbScene);
                ((BaseController) fxmlLoader.getController()).setStage(dbStage);
                dbStage.show();

                stage.close();
            } else {
                dialogContent.setContentText("Invalid username and/or password.");
                dialog.showDialog();
            }
        } catch (Exception ex) {
            dialogContent.setContentText("Error on handling login.\n\n" + ex.getMessage());
            dialog.showDialog();
        }
    }
}
