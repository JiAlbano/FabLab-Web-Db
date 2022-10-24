package org.jolt.fablab.controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class DashboardController extends BaseController {
    @FXML
    void closeBtnClicked(MouseEvent event) {
        getStage().close();
    }

    @FXML
    void minimizeBtnClicked(MouseEvent event) {
        getStage().setIconified(true);
    }
}
