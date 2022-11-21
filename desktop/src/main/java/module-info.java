module org.jolt.fablab {
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;
    requires de.jensd.fx.glyphs.fontawesome;
    requires mysql.connector.java;
    requires mysql.binlog.connector.java;
    requires java.sql;

    opens org.jolt.fablab to javafx.fxml;
    opens org.jolt.fablab.controllers to javafx.fxml;
    exports org.jolt.fablab;
    exports org.jolt.fablab.controllers;
    exports org.jolt.fablab.models;
}