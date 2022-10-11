package org.jolt.fablab.controllers;

import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import io.github.palexdev.materialfx.enums.ScrimPriority;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import io.github.palexdev.materialfx.controls.MFXButton;

import java.util.Map;

public class LoginController {

    @FXML
    private MFXButton closeBtn;
    @FXML
    private MFXButton minimizeBtn;
    @FXML
    private MFXButton loginBtn;

    @FXML
    void closeBtnClicked(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    void minimizeBtnClicked(MouseEvent event) {
        Stage stage = (Stage) minimizeBtn.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private AnchorPane loginPane;

    @FXML
    public void loginBtnClicked(MouseEvent mouseEvent) {
        MFXGenericDialog dialogContent = MFXGenericDialogBuilder.build()
                .setContentText("You have clicked the login button")
                .makeScrollable(false)
                .get();
        MFXStageDialog dialog = MFXGenericDialogBuilder.build(dialogContent)
                .toStageDialogBuilder()
                .initOwner(loginBtn.getScene().getWindow())
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

        dialog.showDialog();
    }
}
